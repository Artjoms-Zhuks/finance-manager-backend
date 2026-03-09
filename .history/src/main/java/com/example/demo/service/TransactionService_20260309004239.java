package com.example.demo.service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final ModelMapper mapper;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        // 1. Сначала чистим описание, если оно есть
        if (transaction.getDescription() != null) {
            transaction.setDescription(transaction.getDescription().trim());
        }
        // 2. КАТЕГОРИЯ В КАПС (Добавь это сюда тоже!)
        transaction.setCategory(transaction.getCategory() != null ? transaction.getCategory().toUpperCase() : "OTHER");
        // 3. Проверяем сумму (используем compareTo, так как это BigDecimal)
        // compareTo возвращает: -1 (меньше), 0 (равно), 1 (больше)
        if (transaction.getAmount() == null || transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("(!) Warning! Added a negative amount: " + transaction.getAmount());
        }

        return repository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction details) {
        return repository.findById(id)
                .map(transaction -> {
                    transaction.setDescription(details.getDescription());
                    transaction.setAmount(details.getAmount());
                    transaction
                            .setCategory(details.getCategory() != null ? details.getCategory().toUpperCase() : "OTHER");
                    return repository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }
}