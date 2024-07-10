package com.example.Capstone_10.Service;






import java.io.IOException;
import java.security.SecureRandom;

import java.util.*;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.Capstone_10.Repository.UrlRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


@Service
public class UrlService {
    @Autowired
    UrlRepository urlRepository;
   private final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateShortCode(String url) {
    	SecureRandom random = new SecureRandom();
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            shortCode.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return shortCode.toString();
    }
    public byte[] generateQRCodeImage(String text) throws WriterException, IOException {
    	final int DEFAULT_WIDTH = 250;
        final int DEFAULT_HEIGHT = 250;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, DEFAULT_WIDTH, DEFAULT_HEIGHT, hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

	

}
