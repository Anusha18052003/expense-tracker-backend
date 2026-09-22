package com.expensetracker.service;

import com.expensetracker.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

import com.expensetracker.model.Expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAll();
    }

    public void addExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public Expense getExpenseById(int id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public Expense updateExpense(int id, Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElse(null);

        if (existingExpense != null) {
            existingExpense.updateExpense(
                    expense.getTitle(),
                    expense.getAmount(),
                    expense.getCategory(),
                    expense.getDate());
            expenseRepository.save(existingExpense);
        }
        return existingExpense;
    }

    public boolean deleteExpense(int id) {
        if (!expenseRepository.existsById(id)) {
            return false;
        }
        expenseRepository.deleteById(id);
        return true;
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findExpensesByCategory(category);
    }

    public List<Expense> getExpensesGreaterThan(double amount) {
        return expenseRepository.findByAmountGreaterThanEqual(amount);
    }

    public List<Expense> getExpensesBetweenDates(LocalDate fromDate, LocalDate toDate) {
        return expenseRepository.findByDateBetween(fromDate, toDate);
    }

    public List<Expense> findByAmountGreaterThanEqualAndDateBetween(Double amount, LocalDate fromDate,
            LocalDate toDate) {
        return expenseRepository.findByAmountGreaterThanEqualAndDateBetween(amount, fromDate, toDate);
    }

    public List<Expense> findExpensesSortedByAmount(String order) {
        if (order.equalsIgnoreCase("asc")) {
            return expenseRepository.findExpensesSortedAmountByAsc();
        } else if (order.equalsIgnoreCase("desc")) {
            return expenseRepository.findExpensesSortedAmountByDesc();
        }

        throw new IllegalArgumentException("Order must be asc or desc");
    }

    public Page<Expense> getExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }
}