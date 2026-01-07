package com.company.ccops.txn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("select t from Transaction t " +
           "where (:accountId is null or t.accountId = :accountId) " +
           "  and (:from is null or t.txnTime >= :from) " +
           "  and (:to is null or t.txnTime <= :to) " +
           "  and (:merchant is null or lower(t.merchantName) like lower(concat('%', :merchant, '%'))) " +
           "order by t.txnTime desc")
    List<Transaction> search(String accountId, Instant from, Instant to, String merchant);
}
