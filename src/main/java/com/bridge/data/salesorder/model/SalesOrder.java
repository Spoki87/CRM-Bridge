package com.bridge.data.salesorder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salesOrder" ,orphanRemoval = true)
    private List<OrderedItem> orderedItems;

    private Long zohoId;
}
