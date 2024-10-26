package com.bridge.data.salesorder.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_order")
    private SalesOrder salesOrder;
}
