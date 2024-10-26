package com.bridge.data.deal.model;

import com.bridge.data.account.model.Account;
import com.bridge.data.contact.model.Contact;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dealName;

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "contact")
    private Contact contact;

    private LocalDate closingDate;

    private String stage;

    private Long zohoId;
}
