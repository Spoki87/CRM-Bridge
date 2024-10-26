package com.bridge.data.invoice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class InvoicedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice")
    private Invoice invoice;
}
