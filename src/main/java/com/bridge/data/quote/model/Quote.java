package com.bridge.data.quote.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quote" ,orphanRemoval = true)
    private List<QuotedItem> quotedItems;

    private Long zohoId;
}
