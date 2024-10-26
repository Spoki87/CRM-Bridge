package com.bridge.data.quote.model;

import com.bridge.data.product.model.Product;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class QuotedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "quote", nullable = false)
    private Quote quote;
}
