package com.expensetracker.model;

import java.time.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    //@NotBlank(message = "Amount cannot be blank")
    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Date is required")
    @Column(name = "expense_date")
    private LocalDate date;

    protected Expense() {
    }

    public Expense(Integer id, String title, double amount, String category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void updateAmount(double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero");
        }
        this.amount = newAmount;
    }

    public void updateExpense(String title, double amount, String category, LocalDate date) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{id=" + id + ", title='" + title + "', amount=" + amount + ", category='" + category + "', date="
                + date + "}";
    }
}
