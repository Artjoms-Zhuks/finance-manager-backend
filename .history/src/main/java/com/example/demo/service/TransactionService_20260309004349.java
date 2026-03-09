/*

package com.example.demo.service;

import com.example.demo.dto.TransactionDTO;
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

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        return transactions.stream()
                .map(entity -> mapper.map(entity, TransactionDTO.class))
                .toList();
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


*/

package com.example.demo.service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final ModelMapper mapper;

    // ОБНОВЛЕННЫЙ КОНСТРУКТОР: добавили ModelMapper в аргументы
    public TransactionService(TransactionRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        return transactions.stream()
                .map(entity -> mapper.map(entity, TransactionDTO.class))
                .toList();
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        // Конвертируем DTO в Entity для базы
        Transaction transaction = mapper.map(dto, Transaction.class);

        // Логика обработки
        if (transaction.getDescription() != null) {
            transaction.setDescription(transaction.getDescription().trim());
        }

        transaction.setCategory(transaction.getCategory() != null ? transaction.getCategory().toUpperCase() : "OTHER");

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }

        Transaction saved = repository.save(transaction);

        // Возвращаем обратно DTO
        return mapper.map(saved, TransactionDTO.class);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO detailsDto) {
        return repository.findById(id)
                .map(transaction -> {
                    transaction.setDescription(detailsDto.getDescription());
                    transaction.setAmount(detailsDto.getAmount());
                    transaction.setCategory(
                            detailsDto.getCategory() != null ? detailsDto.getCategory().toUpperCase() : "OTHER");

                    Transaction updated = repository.save(transaction);
                    return mapper.map(updated, TransactionDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    public void deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Transaction not found with id: " + id);
        }
        repository.deleteById(id);
    }
}