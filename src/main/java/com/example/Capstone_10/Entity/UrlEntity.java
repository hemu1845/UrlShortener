package com.example.Capstone_10.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class UrlEntity {
    @Id
    private String hashCode;
    private String longUrl;
    private int clickCount;
    @Lob
    @Column(name = "qr_code", columnDefinition="BLOB")
    private byte[] qrCode;

	public byte[] getQrCode() {
		return qrCode;
	}
	public void setQrCode(byte[] qrCode) {
		this.qrCode = qrCode;
	}
	public String getHashCode() {
		return hashCode;
	}
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	
}