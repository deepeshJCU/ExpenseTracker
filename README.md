# Expense Tracker App

A simple yet powerful personal finance management app built using **Kotlin**, **Jetpack Compose**, **Room Database**, and **Material Design 3**. Easily track your expenses, manage your spending limit, and visualize your financial habits—all offline.

![screenshot](screenshots/home_screen.png)

---

## 📱 Features

- 🔐 **Login/Signup** with SharedPreferences (no Firebase)
- 🧾 **Add, Edit, Delete Transactions** (with category and date)
- 📅 **Transaction History** with filtering and summary
- 📊 **Weekly Expense Charts**
- 🎯 **Set Spending Limit**
- 🌙 **Dark/Light Mode** toggle
- 🙍‍♂️ **Profile Management** (name, email, phone, photo)
- 🔔 **Local Notifications** for new transactions or limits
- 🧠 Built with **MVVM**, **Room DB**, **WorkManager**, and **Navigation Component**

---

## 🛠 Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/deepeshJCU/ExpenseTracker.git
cd ExpenseTracker
````

2. **Open in Android Studio**

* Android Studio Arctic Fox or higher is recommended.

3. **Build and Run**

* Ensure you have an emulator or physical device running **API 26+**.
* Hit `Run ▶️` from Android Studio.

4. **Dependencies**

* Jetpack Compose
* Room DB
* Lifecycle ViewModel + LiveData
* WorkManager
* Coil for image loading
* Material3

---

##  Version Control Strategy

* Follows **Git Flow** branching model:

    * `main` – production-ready code
    * `develop` – integration branch for features
    * `feature/*` – new features (e.g., `feature/notifications`)
    * `bugfix/*` – patches and fixes (e.g., `bugfix/login-crash`)

* **Commits** follow clear message conventions:

    * `feat:` new features
    * `fix:` bug fixes
    * `refactor:` code restructuring
    * `docs:` README 
  
---

## Screenshots

| Home Screen                          | Add Expense                         | Transaction History                             |
| ------------------------------------ | ----------------------------------- | ----------------------------------------------- |
| ![home](screenshots/home_screen.png) | ![add](screenshots/add_expense.png) | ![history](screenshots/transaction_history.png) |

| Profile View                             | Dark Mode                          | Chart Summary                           |
| ---------------------------------------- | ---------------------------------- | --------------------------------------- |
| ![profile](screenshots/profile_view.png) | ![dark](screenshots/dark_mode.png) | ![chart](screenshots/chart_summary.png) |

---

##  Author

**Deepesh Bijarnia**

---

## License

This project is licensed under the [MIT License](LICENSE).

