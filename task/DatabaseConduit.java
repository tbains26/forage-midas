package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseGateway {

    private final UserRepository userRepo;
    private final TransactionRecordRepository txnRecordRepo;

    public DatabaseGateway(UserRepository userRepo, TransactionRecordRepository txnRecordRepo) {
        this.userRepo = userRepo;
        this.txnRecordRepo = txnRecordRepo;
    }

    public void saveUser(UserRecord user) {
        userRepo.save(user);
    }

    public void saveTransaction(Transaction txn) {
        // assumes validation already performed
        UserRecord sender = findUser(txn.getSenderId());
        UserRecord recipient = findUser(txn.getRecipientId());

        TransactionRecord record = new TransactionRecord(
                sender,
                recipient,
                txn.getAmount(),
                txn.getIncentive()
        );
        txnRecordRepo.save(record);

        // update balances
        sender.setBalance(sender.getBalance() - txn.getAmount());
        saveUser(sender);

        recipient.setBalance(recipient.getBalance() + txn.getAmount() + txn.getIncentive());
        saveUser(recipient);
    }

    public boolean validate(Transaction txn) {
        UserRecord sender = findUser(txn.getSenderId());
        if (sender == null) {
            return false;
        }

        UserRecord recipient = findUser(txn.getRecipientId());
        if (recipient == null) {
            return false;
        }

        return sender.getBalance() >= txn.getAmount();
    }

    public UserRecord findUser(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public float findUserBalance(Long id) {
        UserRecord user = findUser(id);
        return (user == null) ? 0 : user.getBalance();
    }
}
