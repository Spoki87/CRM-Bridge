package com.bridge.data.lead.model;

import com.bridge.data.owner.model.Owner;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Owner owner;

    private Long zohoId;

}

