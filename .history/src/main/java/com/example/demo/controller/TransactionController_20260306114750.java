package com.example.demo.controller;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController // Говорим Spring, что это веб-контроллер
@RequestMapping("/api/transactions") // Базовый адрес для всех методов ниже
public class TransactionController {

    private final TransactionRepository repository;

    // Spring сам передаст сюда репозиторий (Dependency Injection)
    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    // Метод для получения всех записей: http://localhost:8080/api/transactions
    @GetMapping
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    // Метод для быстрого добавления:
    // http://localhost:8080/api/transactions/add?desc=Coffee&amount=250
    @GetMapping("/add")
    public Transaction addTransaction(
            @RequestParam String desc,
            @RequestParam BigDecimal amount) {

        Transaction t = new Transaction();
        t.setDescription(desc);
        t.setAmount(amount);
        t.setCategory("General");

        return repository.save(t);
    }
}