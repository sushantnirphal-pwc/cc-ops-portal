package com.company.ccops.card;

import com.company.ccops.card.dto.ActionResponseDto;
import com.company.ccops.card.dto.BlockCardRequestDto;
import com.company.ccops.card.dto.CardDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/accounts/{accountId}/cards")
    public List<CardDto> listByAccount(@PathVariable String accountId) {
        return service.listByAccount(accountId);
    }

    @GetMapping("/cards/{id}")
    public CardDto get(@PathVariable String id) {
        return service.get(id);
    }

    @PostMapping("/cards/{id}/block")
    public ActionResponseDto block(@PathVariable String id, @Valid @RequestBody BlockCardRequestDto req) {
        return service.requestBlock(id, req);
    }

    @PostMapping("/cards/{id}/unblock")
    public ActionResponseDto unblock(@PathVariable String id, @RequestParam(defaultValue = "Ops unblock") String note) {
        return service.requestUnblock(id, note);
    }
}
