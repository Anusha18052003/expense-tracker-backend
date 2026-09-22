package com.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.expensetracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
    List<Expense> findByCategoryIgnoreCase(String category);
    @Query("SELECT e FROM Expense e WHERE LOWER(e.category) = LOWER(:category)")
    List<Expense> findExpensesByCategory(@Param("category") String category);
    List<Expense> findByAmountGreaterThanEqual(double amount);
    List<Expense> findByDateBetween(LocalDate fromDate, LocalDate toDate);
    List<Expense> findByAmountGreaterThanEqualAndDateBetween(double amount, LocalDate fromDate, LocalDate toDate);
    @Query("""
    SELECT e
    FROM Expense e
    ORDER BY e.amount DESC """)
    List<Expense> findExpensesSortedAmountByDesc();
    @Query("""
    SELECT e
    FROM Expense e
    ORDER BY e.amount ASC """)
    List<Expense> findExpensesSortedAmountByAsc();
    Page<Expense> findByCategoryIgnoreCase(String category, Pageable pageable);
}
