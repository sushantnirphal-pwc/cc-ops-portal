package com.company.ccops.card;

import com.company.ccops.approval.ApprovalPolicyConfig;
import com.company.ccops.approval.ApprovalService;
import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import com.company.ccops.card.dto.ActionResponseDto;
import com.company.ccops.card.dto.BlockCardRequestDto;
import com.company.ccops.card.dto.CardDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepo;
    private final CardBlockRepository blockRepo;
    private final ApprovalService approvalService;
    private final ApprovalPolicyConfig policy;
    private final AuditService audit;

    public CardService(CardRepository cardRepo, CardBlockRepository blockRepo,
                       ApprovalService approvalService, ApprovalPolicyConfig policy, AuditService audit) {
        this.cardRepo = cardRepo;
        this.blockRepo = blockRepo;
        this.approvalService = approvalService;
        this.policy = policy;
        this.audit = audit;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<CardDto> listByAccount(String accountId) {
        return cardRepo.findByAccountIdOrderByExpiryYearDesc(accountId).stream()
                .map(c -> new CardDto(c.getId(), c.getAccountId(), c.getCardToken(), c.getLast4(),
                        c.getStatus(), c.getEmbossName(), c.getExpiryMonth(), c.getExpiryYear()))
                .toList();
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public CardDto get(String id) {
        Card c = cardRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Card not found: " + id));
        audit.record(AuditAction.CARD_VIEW, "Card", id, "OK", null);
        return new CardDto(c.getId(), c.getAccountId(), c.getCardToken(), c.getLast4(),
                c.getStatus(), c.getEmbossName(), c.getExpiryMonth(), c.getExpiryYear());
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    @Transactional
    public ActionResponseDto requestBlock(String cardId, BlockCardRequestDto req) {
        Card card = cardRepo.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Card not found: " + cardId));
        if (card.getStatus() == CardStatus.CLOSED) throw new IllegalArgumentException("Card is closed");
        String actor = SecurityContextHolder.getContext().getAuthentication().getName();

        audit.record(AuditAction.CARD_BLOCK_REQUESTED, "Card", cardId, "OK", "{\"reason\":\"" + req.reason() + "\"}");

        CardBlockRequest block = blockRepo.save(new CardBlockRequest(cardId, req.reason(), req.note(), actor,
                "PENDING_APPROVAL", java.time.Instant.now()));

        if (policy.requiresApproval("CARD_BLOCK")) {
            var approval = approvalService.create("CARD_BLOCK", block.getId());
            return new ActionResponseDto("PENDING_APPROVAL", "Block submitted for approval", approval.getId(), block.getId());
        }

        executeBlock(block.getId());
        return new ActionResponseDto("EXECUTED", "Card blocked", null, block.getId());
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    @Transactional
    public ActionResponseDto requestUnblock(String cardId, String note) {
        Card card = cardRepo.findById(cardId).orElseThrow(() -> new IllegalArgumentException("Card not found: " + cardId));
        if (card.getStatus() != CardStatus.BLOCKED) throw new IllegalArgumentException("Card is not blocked");
        String actor = SecurityContextHolder.getContext().getAuthentication().getName();

        audit.record(AuditAction.CARD_UNBLOCK_REQUESTED, "Card", cardId, "OK", null);

        CardBlockRequest unblock = blockRepo.save(new CardBlockRequest(cardId, BlockReason.OTHER, note, actor,
                "PENDING_APPROVAL", java.time.Instant.now()));

        if (policy.requiresApproval("CARD_UNBLOCK")) {
            var approval = approvalService.create("CARD_UNBLOCK", unblock.getId());
            return new ActionResponseDto("PENDING_APPROVAL", "Unblock submitted for approval", approval.getId(), unblock.getId());
        }

        executeUnblock(unblock.getId());
        return new ActionResponseDto("EXECUTED", "Card unblocked", null, unblock.getId());
    }

    @Transactional
    public void executeApprovedBlock(String blockRequestId) {
        executeBlock(blockRequestId);
    }

    @Transactional
    public void executeApprovedUnblock(String unblockRequestId) {
        executeUnblock(unblockRequestId);
    }

    private void executeBlock(String blockRequestId) {
        CardBlockRequest block = blockRepo.findById(blockRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Block request not found: " + blockRequestId));
        if ("EXECUTED".equals(block.getStatus()) || "REJECTED".equals(block.getStatus())) return;

        Card card = cardRepo.findById(block.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found: " + block.getCardId()));

        card.setStatus(CardStatus.BLOCKED);
        block.markExecuted();

        audit.record(AuditAction.CARD_BLOCKED, "Card", card.getId(), "OK", "{\"blockRequestId\":\"" + blockRequestId + "\"}");
    }

    private void executeUnblock(String unblockRequestId) {
        CardBlockRequest unblock = blockRepo.findById(unblockRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Unblock request not found: " + unblockRequestId));
        if ("EXECUTED".equals(unblock.getStatus()) || "REJECTED".equals(unblock.getStatus())) return;

        Card card = cardRepo.findById(unblock.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found: " + unblock.getCardId()));

        card.setStatus(CardStatus.ACTIVE);
        unblock.markExecuted();

        audit.record(AuditAction.CARD_UNBLOCKED, "Card", card.getId(), "OK", "{\"unblockRequestId\":\"" + unblockRequestId + "\"}");
    }
}
