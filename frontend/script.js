const API_URL = "http://localhost:8080/expenses";

async function loadExpenses() {

    const response = await fetch(API_URL);

    const expenses = await response.json();

    displayExpenses(expenses);
}

function displayExpenses(expenses) {

    const expenseList = document.getElementById("expenseList");

    expenseList.innerHTML = "";

    let total = 0;

    expenses.forEach(expense => {

        total += expense.amount;

        const expenseElement = document.createElement("div");

        expenseElement.className = "expense-item";

        expenseElement.innerHTML = `
            <div>
                <strong>${expense.title}</strong>
                <p>${expense.category} • ${expense.date}</p>
            </div>

            <strong>₹${expense.amount}</strong>
        `;

        expenseList.appendChild(expenseElement);
    });

    document.getElementById("totalAmount").textContent = `₹${total}`;
    document.getElementById("expenseCount").textContent = expenses.length;
}

loadExpenses();

const expenseForm = document.getElementById("expenseForm");

expenseForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const expense = {
        title: document.getElementById("title").value,
        amount: Number(document.getElementById("amount").value),
        category: document.getElementById("category").value,
        date: document.getElementById("date").value
    };

    const response = await fetch(API_URL, {
        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(expense)
    });

    if (response.ok) {
        expenseForm.reset();
        await loadExpenses();
    } else {
        console.error("Failed to add expense");
    }
});