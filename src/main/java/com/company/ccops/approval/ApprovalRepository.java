package com.company.ccops.approval;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalRequest, String> {
    List<ApprovalRequest> findByStatusOrderByCreatedAtDesc(ApprovalStatus status);
}
