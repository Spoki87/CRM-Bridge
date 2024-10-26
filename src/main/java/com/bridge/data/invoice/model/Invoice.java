package com.bridge.data.invoice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice" ,orphanRemoval = true)
    private List<InvoicedItem> invoicedItems;

    private Long zohoId;
}
