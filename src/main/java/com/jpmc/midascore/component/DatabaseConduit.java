package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DatabaseConduit(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void processTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // Validation: IDs must exist and sender must have sufficient balance
        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {
            // Adjust balances
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount());

            // Save updated users and the transaction record
            userRepository.save(sender);
            userRepository.save(recipient);
            System.out.println(recipient.getName());
            System.out.println();
            System.out.println(sender.getBalance());
            transactionRepository.save(new TransactionRecord(sender, recipient, transaction.getAmount()));
        }
    }
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }
}
