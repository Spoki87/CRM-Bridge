package com.bridge.data.invoice.repository;

import com.bridge.data.invoice.model.InvoicedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicedItemRepository extends JpaRepository<InvoicedItem,Long> {
}
