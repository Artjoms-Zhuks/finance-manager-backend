package com.example.demo.service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final ModelMapper mapper;

    public TransactionService(TransactionRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // Total balance (total spent)
    public BigDecimal getTotalBalance() {
        return repository.findAll().stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Spent today (since 00:00 today)
    public BigDecimal getTodayTotal() {
        return repository.sumAmountAfter(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

    // Over the past 24 hours (last day)
    public BigDecimal getLast24HoursTotal() {
        return repository.sumAmountAfter(LocalDateTime.now().minusDays(1));
    }

    // For the current week (since Monday)
    public BigDecimal getThisWeekTotal() {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1)
                .atStartOfDay();
        return repository.findAll().stream()
                .filter(t -> t.getCreatedAt().isAfter(startOfWeek))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // For the current month (from the 1st)
    public BigDecimal getThisMonthTotal() {
        return repository.sumAmountAfter(LocalDate.now().withDayOfMonth(1).atStartOfDay());
    }

    // Show all transactions
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = repository.findAll();
        return transactions.stream()
                .map(entity -> mapper.map(entity, TransactionDTO.class))
                .toList();
    }

    // Outputs information from the database to the frontend (without ID and
    // creation date)
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = mapper.map(dto, Transaction.class);

        if (transaction.getDescription() != null) {
            transaction.setDescription(transaction.getDescription().trim());
        }

        transaction.setCategory(transaction.getCategory() != null ? transaction.getCategory().toUpperCase() : "OTHER");

        if (transaction.getAmount() == null || transaction.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }

        Transaction saved = repository.save(transaction);
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

    // Searching for information by category
    public Map<String, BigDecimal> getExpensesByCategoryThisMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return repository.getExpensesByCategoryAfter(startOfMonth).stream()
                .collect(Collectors.toMap(
                        TransactionRepository.CategorySum::getCategory,
                        TransactionRepository.CategorySum::getAmount));
    }

    public List<TransactionDTO> searchTransactions(String query) {
        return repository.searchByDescription(query).stream()
                .map(entity -> mapper.map(entity, TransactionDTO.class))
                .toList();
    }

    // Show all unique category names from the database
    public List<String> getUniqueCategories() {
        return repository.findAllUniqueCategories();
    }
}