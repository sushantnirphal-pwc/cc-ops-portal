package com.company.ccops.approval;

import com.company.ccops.approval.dto.DecisionRequest;
import com.company.ccops.card.CardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService service;
    private final CardService cardService;

    public ApprovalController(ApprovalService service, CardService cardService) {
        this.service = service;
        this.cardService = cardService;
    }

    @GetMapping
    public List<ApprovalRequest> listPending() {
        return service.listPending();
    }

    @PostMapping("/{id}/approve")
    public ApprovalRequest approve(@PathVariable String id, @Valid @RequestBody DecisionRequest req) {
        ApprovalRequest ar = service.approve(id, req.note());
        if ("CARD_BLOCK".equalsIgnoreCase(ar.getPolicyKey())) {
            cardService.executeApprovedBlock(ar.getPayloadRef());
        }
        if ("CARD_UNBLOCK".equalsIgnoreCase(ar.getPolicyKey())) {
            cardService.executeApprovedUnblock(ar.getPayloadRef());
        }
        return ar;
    }

    @PostMapping("/{id}/reject")
    public ApprovalRequest reject(@PathVariable String id, @Valid @RequestBody DecisionRequest req) {
        return service.reject(id, req.note());
    }
}
