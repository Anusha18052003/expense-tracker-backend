package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> getExpense(
            @RequestParam(name = "minAmount", required = false) Double amount,
            @RequestParam(name = "from", required = false) LocalDate fromDate,
            @RequestParam(name = "to", required = false) LocalDate toDate) {

        if (amount != null && fromDate != null && toDate != null) {
            List<Expense> expenses = expenseService.findByAmountGreaterThanEqualAndDateBetween(amount, fromDate,
                    toDate);
            return ResponseEntity.ok(expenses);
        }

        else if (amount != null) {
            List<Expense> expenses = expenseService.getExpensesGreaterThan(amount);
            return ResponseEntity.ok(expenses);
        }

        else if (fromDate != null && toDate != null) {
            List<Expense> expenses = expenseService.getExpensesBetweenDates(fromDate, toDate);
            return ResponseEntity.ok(expenses);
        }

        return ResponseEntity.ok(expenseService.getExpenses());
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getexpenseById(@PathVariable int id) {
        Expense expense = expenseService.getExpenseById(id);
        if (expense == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expense);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody Expense expense) {
        expenseService.addExpense(expense);
        return ResponseEntity.status(201).body(expense);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable int id, @Valid @RequestBody Expense expense) {
        Expense e = expenseService.updateExpense(id, expense);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e);
        // expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable int id) {
        if (!expenseService.deleteExpense(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expenses/category/{category}")
    public ResponseEntity<List<Expense>> getexpenseByCategory(@PathVariable String category) {
        // List<Expense> expenses = expenseService.getExpenses().stream().filter(expense
        // -> expense.getCategory().equalsIgnoreCase(category)).toList();
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        if (expenses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(200).body(expenses);
    }

    @GetMapping("/expenses/sorted")
    public ResponseEntity<List<Expense>> getExpenseSorted(@RequestParam(name = "order", required = true) String order) {
        return ResponseEntity.ok(expenseService.findExpensesSortedByAmount(order));
    }

    @GetMapping("/expenses/page")
    public ResponseEntity<Page<Expense>> getExpensesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                expenseService.getExpenses(pageable));
    }
}
