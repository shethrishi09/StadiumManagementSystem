# Stadium Management System (Java + SQL + JDBC)

This project is a **Java-based Stadium Management System** that integrates with a **MySQL database** using **JDBC**.  
It demonstrates the use of core Java concepts, database connectivity, and CRUD operations for managing stadium-related data.

## 📌 Features
- Manage **matches**, **users**, **staff**, and **tickets**  
- Perform CRUD operations using **JDBC**  
- SQL database (`stadium1.sql`) included with sample data  
- Supports multiple payment methods for ticket booking  
- Console-based interaction for simplicity  

## 🛠️ Technologies Used
- **Java (JDK 8 or higher)**  
- **MySQL / MariaDB**  
- **JDBC (Java Database Connectivity)**  

## 🚀 How to Run

Follow these steps to run the project:

1. **Setup Database**
   - Import the `stadium1.sql` file into your MySQL database:
     ```bash
     mysql -u root -p < stadium1.sql
     ```
   - Make sure your MySQL server is running.

2. **Configure Database Connection**
   - Open `Stadium.java` and update the database connection details  
     (username, password, URL) if required.

3. **Compile Java Files**
   - From the project folder, compile the Java source files:
     ```bash
     javac App.java Stadium.java
     ```

4. **Run the Project**
   - Start the application by running `App.java`:
     ```bash
     java App
     ```

## 📖 Notes
- Database name: `stadium1`  
- Tables: `matches`, `user`, `staff`, `ticket` (with sample data preloaded)  
- `App.java` → main entry point of the project  
- `Stadium.java` → handles JDBC connection and database operations
