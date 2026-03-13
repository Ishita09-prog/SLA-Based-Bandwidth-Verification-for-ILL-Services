package com.slasystem.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BandwidthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double downloadSpeed;
    private double uploadSpeed;
    private String status;
    private LocalDateTime timestamp;

    public BandwidthLog() {}

    public Long getId() { return id; }

    public double getDownloadSpeed() { return downloadSpeed; }
    public void setDownloadSpeed(double downloadSpeed) { this.downloadSpeed = downloadSpeed; }

    public double getUploadSpeed() { return uploadSpeed; }
    public void setUploadSpeed(double uploadSpeed) { this.uploadSpeed = uploadSpeed; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}