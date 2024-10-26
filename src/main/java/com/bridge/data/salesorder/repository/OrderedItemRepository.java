package com.bridge.data.salesorder.repository;

import com.bridge.data.salesorder.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends JpaRepository<OrderedItem,Long> {
}
