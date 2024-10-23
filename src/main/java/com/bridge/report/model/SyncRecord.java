package com.bridge.report.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class SyncRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recordId;
    private Long zohoId;
    private String status;
    private String message;

    @ManyToOne
    @JoinColumn(name = "sync_report_id")
    private SyncReport syncReport;

    public SyncRecord(Long recordId, Long zohoId, String status, String message, SyncReport syncReport){
        this.recordId = recordId;
        this.zohoId = zohoId;
        this.status = status;
        this.message = message;
        this.syncReport = syncReport;
    }
}
