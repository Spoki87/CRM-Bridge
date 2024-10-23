package com.bridge.report.model;

import com.bridge.zoho.enums.Module;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class SyncReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Module module;
    private int totalLeadsProcessed;
    private int successfulSyncs;
    private int failedSyncs;

    @OneToMany(mappedBy = "syncReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SyncRecord> syncRecords = new ArrayList<>();

    public SyncReport(Module module, int totalLeadsProcessed){
        this.module = module;
        this.totalLeadsProcessed = totalLeadsProcessed;
    }


    public void addSuccess(SyncRecord syncRecord) {
        successfulSyncs++;
        syncRecords.add(syncRecord);
    }

    public void addFailure(SyncRecord syncRecord) {
        failedSyncs++;
        syncRecords.add(syncRecord);
    }
}
