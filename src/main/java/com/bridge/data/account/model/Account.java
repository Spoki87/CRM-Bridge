package com.bridge.data.account.model;

import com.bridge.data.owner.model.Owner;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Owner owner;

    private Long zohoId;
}
