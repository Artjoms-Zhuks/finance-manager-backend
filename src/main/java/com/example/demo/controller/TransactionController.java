package com.example.demo.controller;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // For React
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransactionDTO> getAll() {
        return service.getAllTransactions();
    }

    @PostMapping
    public TransactionDTO create(@Valid @RequestBody TransactionDTO transactionDTO) {
        return service.createTransaction(transactionDTO);
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@PathVariable Long id, @RequestBody TransactionDTO details) {
        return service.updateTransaction(id, details);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTransaction(id);
    }

    @GetMapping("/search")
    public List<TransactionDTO> search(@RequestParam String query) {
        return service.searchTransactions(query);
    }
}