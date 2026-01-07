package com.company.ccops.dispute;

import com.company.ccops.dispute.dto.CreateDisputeRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

    private final DisputeService service;

    public DisputeController(DisputeService service) {
        this.service = service;
    }

    @PostMapping
    public Dispute create(@Valid @RequestBody CreateDisputeRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public Dispute get(@PathVariable String id) {
        return service.get(id);
    }

    @GetMapping
    public List<Dispute> listByCustomer(@RequestParam String customerId) {
        return service.listByCustomer(customerId);
    }
}
