package com.example.demo.service;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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
        // Здесь в будущем будет логика: например, проверка баланса
        // Или автоматическое исправление текста (Пример: Pizza -> PIZZA)
        transaction.setDescription(transaction.getDescription().trim());
        return repository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        repository.deleteById(id);
    }

    // Добавь здесь метод для PUT
}