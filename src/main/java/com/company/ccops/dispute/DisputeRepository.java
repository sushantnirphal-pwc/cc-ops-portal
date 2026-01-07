package com.company.ccops.dispute;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeRepository extends JpaRepository<Dispute, String> {
    List<Dispute> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}
