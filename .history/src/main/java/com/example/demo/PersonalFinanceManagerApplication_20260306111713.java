/*package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonalFinanceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalFinanceManagerApplication.class, args);
	}

}
*/

package com.example.demo;

import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class PersonalFinanceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalFinanceManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TransactionRepository repository) {
		return (args) -> {
			// Создаем и сохраняем первую транзакцию
			Transaction t1 = new Transaction();
			t1.setDescription("Покупка кофе");
			t1.setAmount(new BigDecimal("250.00"));
			t1.setCategory("Еда");

			repository.save(t1); // Вот и всё! SQL выполнится сам.

			System.out.println("Транзакция сохранена в базу!");

			// Выводим все транзакции из базы
			repository.findAll().forEach(System.out::println);
		};
	}
}