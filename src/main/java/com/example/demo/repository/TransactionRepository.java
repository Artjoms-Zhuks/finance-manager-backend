package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Мы говорим: "Работай с сущностью Transaction, у которой ID типа Long"
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Здесь уже "встроены" методы: save(), findAll(), deleteById() и т.д.
}
