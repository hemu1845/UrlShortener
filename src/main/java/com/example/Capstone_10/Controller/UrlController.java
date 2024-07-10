

package com.example.Capstone_10.Controller;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Capstone_10.Entity.UrlEntity;
import com.example.Capstone_10.Repository.UrlRepository;
import com.example.Capstone_10.Service.UrlService;
import com.google.zxing.WriterException;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
public class UrlController {

    @Autowired
    UrlService urlService;

    @Autowired
    UrlRepository urlRepository;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortUrlAndQRCode(@RequestBody UrlEntity url) {
        Map<String, Object> response = new HashMap<>();
        UrlEntity existingUrl = urlRepository.findByLongUrl(url.getLongUrl());
        if (existingUrl != null) {
            // Long URL already exists, retrieve the existing QR code
            try {
                byte[] qrCodeImage = existingUrl.getQrCode();
                String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
                response.put("shortUrl", "http://localhost:8882/" + existingUrl.getHashCode());
                response.put("qrCode", qrCodeBase64);
                return ResponseEntity.ok().body(response);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving existing QR code");
            }
        } else {
            // Long URL does not exist, generate a new QR code
            url.setHashCode(urlService.generateShortCode(url.getLongUrl()));
            UrlEntity url2 = urlRepository.findByHashCode(url.getHashCode());
            while (url2 != null) {
                url.setHashCode(urlService.generateShortCode(url.getLongUrl()));
                url2 = urlRepository.findByHashCode(url.getHashCode());
            }
            try {
                byte[] qrCodeImage = urlService.generateQRCodeImage("http://localhost:8882/" + url.getHashCode());
                url.setQrCode(qrCodeImage);
                urlRepository.save(url);
                response.put("shortUrl", "http://localhost:8882/" + url.getHashCode());
                String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
                response.put("qrCode", qrCodeBase64);
                return ResponseEntity.ok().body(response);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating QR code");
            }
        }
    }

 
    @GetMapping("/{hashCode}")
    public void redirectToLongUrl(@PathVariable String hashCode, HttpServletResponse response) throws IOException {
        handleRedirect(hashCode, response);
    }
    
    @GetMapping("/qr/{hashCode}")
    public void redirectToLongUrlFromQR(@PathVariable String hashCode, HttpServletResponse response) throws IOException {
        handleRedirect(hashCode, response);
    }
    
    private void handleRedirect(String hashCode, HttpServletResponse response) throws IOException {
        UrlEntity url = urlRepository.findByHashCode(hashCode);
        if (url != null) {
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
            response.sendRedirect(url.getLongUrl());
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found");
        }
    }
}

