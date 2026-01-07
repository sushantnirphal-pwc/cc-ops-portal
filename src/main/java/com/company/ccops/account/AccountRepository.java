package com.company.ccops.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByCustomerIdOrderByCreatedAtDesc(String customerId);
}
