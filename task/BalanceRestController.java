package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Balance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    private final DatabaseConduit dbConduit;

    public BalanceController(DatabaseConduit dbConduit) {
        this.dbConduit = dbConduit;
    }

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam("userId") Long id) {
        float userBalance = dbConduit.queryUserBalance(id);
        return new Balance(userBalance);
    }
}
