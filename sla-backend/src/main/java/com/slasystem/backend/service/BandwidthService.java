package com.slasystem.backend.service;

import org.springframework.stereotype.Service;
import com.slasystem.backend.repository.BandwidthRepository;
import com.slasystem.backend.model.BandwidthLog;

import java.util.List;

@Service
public class BandwidthService {

    private final BandwidthRepository repository;
    private double threshold = 100; // Default SLA threshold

    public BandwidthService(BandwidthRepository repository) {
        this.repository = repository;
    }

    public BandwidthLog save(BandwidthLog log) {

        if (log.getDownloadSpeed() >= threshold) {
            log.setStatus("COMPLIANT");
        } else {
            log.setStatus("VIOLATION");
        }

        return repository.save(log);
    }

    public List<BandwidthLog> getAll() {
        return repository.findAll();
    }

    public long getTotalTests() {
        return repository.count();
    }

    public long getCompliantTests() {
        return repository.findAll()
                .stream()
                .filter(log -> "COMPLIANT".equals(log.getStatus()))
                .count();
    }

    public void updateThreshold(double newThreshold) {
        this.threshold = newThreshold;
    }

    public double getThreshold() {
        return threshold;
    }
}
