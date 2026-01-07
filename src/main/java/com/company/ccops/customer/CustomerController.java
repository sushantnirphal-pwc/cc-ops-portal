package com.company.ccops.customer;

import com.company.ccops.customer.dto.CustomerDetailDto;
import com.company.ccops.customer.dto.CustomerSummaryDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerSummaryDto> search(@RequestParam(name = "query", defaultValue = "") String query) {
        return service.search(query);
    }

    @GetMapping("/{id}")
    public CustomerDetailDto get(@PathVariable String id) {
        return service.get(id);
    }
}
