package com.bridge.report.repository;

import com.bridge.report.model.SyncReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncReportRepository extends JpaRepository<SyncReport,Long> {
}
