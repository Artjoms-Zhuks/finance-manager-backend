package com.example.demo.service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        // Убираем лишние пробелы в описании (пример бизнес-логики)
        if ((transaction.getDescription() != null) && (transaction.setAmount() < 0)) {
            transaction.setDescription(transaction.getDescription().trim());
        } else {
            System.out.println("Strange amount");
        }
        return repository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction details) {
        return repository.findById(id)
                .map(transaction -> {
                    transaction.setDescription(details.getDescription());
                    transaction.setAmount(details.getAmount());
                    transaction.setCategory(details.getCategory());
                    return repository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }
}