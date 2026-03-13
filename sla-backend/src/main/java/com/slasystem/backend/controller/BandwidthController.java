package com.slasystem.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.slasystem.backend.model.BandwidthLog;
import com.slasystem.backend.service.BandwidthService;

import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BandwidthController {

    private final BandwidthService service;

    public BandwidthController(BandwidthService service) {
        this.service = service;
    }

    // POST from Python monitoring agent
    @PostMapping("/bandwidth")
    public BandwidthLog receiveData(@RequestBody BandwidthLog log) {
        log.setTimestamp(LocalDateTime.now());
        return service.save(log);
    }

    // GET all logs
    @GetMapping("/report")
    public List<BandwidthLog> getReport() {
        return service.getAll();
    }

    // GET compliance statistics
    @GetMapping("/compliance")
    public Map<String, Object> getCompliance() {

        long total = service.getTotalTests();
        long compliant = service.getCompliantTests();

        double percentage = total == 0 ? 0 :
                (compliant * 100.0) / total;

        return Map.of(
                "totalTests", total,
                "compliantTests", compliant,
                "compliancePercentage", percentage
        );
    }

    // Update SLA threshold
    @PostMapping("/threshold")
    public void updateThreshold(@RequestParam double value) {
        service.updateThreshold(value);
    }
}
