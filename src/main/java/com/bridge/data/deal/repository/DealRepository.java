package com.bridge.data.deal.repository;

import com.bridge.data.deal.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal,Long> {
}
