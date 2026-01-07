package com.company.ccops.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("select c from Customer c " +
           "where lower(c.fullName) like lower(concat('%', :q, '%')) " +
           "   or lower(c.email) like lower(concat('%', :q, '%')) " +
           "   or c.phone like concat('%', :q, '%') " +
           "order by c.createdAt desc")
    List<Customer> search(String q);
}
