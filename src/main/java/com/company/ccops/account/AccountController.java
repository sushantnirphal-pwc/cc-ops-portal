package com.company.ccops.account;

import com.company.ccops.account.dto.AccountDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<AccountDto> listByCustomer(@PathVariable String customerId) {
        return service.listByCustomer(customerId);
    }

    @GetMapping("/accounts/{id}")
    public AccountDto get(@PathVariable String id) {
        return service.get(id);
    }
}
