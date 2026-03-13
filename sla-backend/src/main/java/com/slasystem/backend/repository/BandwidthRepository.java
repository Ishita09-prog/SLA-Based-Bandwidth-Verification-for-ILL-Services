package com.slasystem.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.slasystem.backend.model.BandwidthLog;

public interface BandwidthRepository extends JpaRepository<BandwidthLog, Long> {
}