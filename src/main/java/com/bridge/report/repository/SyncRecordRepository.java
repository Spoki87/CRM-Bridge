package com.bridge.report.repository;

import com.bridge.report.model.SyncRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRecordRepository extends JpaRepository<SyncRecord,Long> {
}
