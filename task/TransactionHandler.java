package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessor {

    private static final Logger log = LoggerFactory.getLogger(TransactionProcessor.class);

    private final DatabaseConduit dbConduit;
    private final IncentiveQuerier incentiveService;

    public TransactionProcessor(DatabaseConduit dbConduit, IncentiveQuerier incentiveService) {
        this.dbConduit = dbConduit;
        this.incentiveService = incentiveService;
    }

    public void process(Transaction txn) {
        if (!dbConduit.isValid(txn)) {
            return;
        }

        Incentive incentive = incentiveService.query(txn);
        txn.setIncentive(incentive.getAmount());

        dbConduit.save(txn);
        log.debug("Transaction processed and saved: {}", txn);
    }
}

