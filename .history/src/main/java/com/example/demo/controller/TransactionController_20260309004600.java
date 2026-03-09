/*

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

    // Метод для удаления: http://localhost:8080/api/transactions/delete/2
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Record with ID " + id + " was removed!";
        }
        return "The record wasn't found";
    }
}
    

*/

/* 


package com.example.demo.controller;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository repository;

    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    // GET: Получить все транзакции
    @GetMapping
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    // POST: Создать новую транзакцию
    // @RequestBody говорит Spring достать данные из JSON в теле запроса
    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return repository.save(transaction);
    }

    // PUT: Обновить существующую транзакцию
    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @RequestBody Transaction details) {
        return repository.findById(id)
                .map(transaction -> {
                    transaction.setDescription(details.getDescription());
                    transaction.setAmount(details.getAmount());
                    transaction.setCategory(details.getCategory());
                    return repository.save(transaction);
                })
                .orElseGet(() -> {
                    details.setId(id);
                    return repository.save(details);
                });
    }

    // DELETE: Удалить по ID
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
    

*/

package com.example.demo.controller;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    // Внедряем сервис, а не репозиторий
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransactionDTO> getAll() {
        return service.getAllTransactions();
    }

    @PostMapping
    public TransactionDTO create(@RequestBody TransactionDTO transactionDTO) {
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
}