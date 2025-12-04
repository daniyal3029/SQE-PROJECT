package com.saucedemo.utils;

import com.saucedemo.config.ConfigReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Purpose: Database Utility Class
 * This class provides methods to interact with H2 embedded database for test
 * data management.
 * Supports CRUD operations and test data initialization.
 * 
 * Database: H2 (embedded mode)
 * Features:
 * - Initialize database schema
 * - Insert test data
 * - Query test data
 * - Execute custom SQL queries
 * - Connection pooling support
 */
public class DatabaseUtil {

    private static ConfigReader config = ConfigReader.getInstance();
    private static Connection connection;

    /**
     * Get database connection
     * Creates new connection if not exists
     * 
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(config.getDbDriver());
                connection = java.sql.DriverManager.getConnection(
                        config.getDbUrl(),
                        config.getDbUsername(),
                        config.getDbPassword());
                System.out.println("Database connection established: " + config.getDbUrl());
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + e.getMessage());
            throw new RuntimeException("Failed to load database driver", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Failed to connect to database", e);
        }

        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Initialize database schema and insert test data
     * This method creates tables and populates them with test data
     */
    public static void initializeDatabase() {
        if (!config.isDbInitialize()) {
            System.out.println("Database initialization is disabled in configuration");
            return;
        }

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            // Drop existing tables if they exist
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("DROP TABLE IF EXISTS products");
            stmt.execute("DROP TABLE IF EXISTS checkout_info");

            // Create users table
            stmt.execute("CREATE TABLE users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "username VARCHAR(100) NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "user_type VARCHAR(50), " +
                    "is_active BOOLEAN DEFAULT TRUE)");

            // Create products table
            stmt.execute("CREATE TABLE products (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "product_name VARCHAR(200) NOT NULL, " +
                    "product_price DECIMAL(10,2), " +
                    "description TEXT)");

            // Create checkout_info table
            stmt.execute("CREATE TABLE checkout_info (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "first_name VARCHAR(100), " +
                    "last_name VARCHAR(100), " +
                    "zip_code VARCHAR(20))");

            // Insert test data - Users
            stmt.execute("INSERT INTO users (username, password, user_type, is_active) VALUES " +
                    "('standard_user', 'secret_sauce', 'standard', TRUE), " +
                    "('locked_out_user', 'secret_sauce', 'locked', FALSE), " +
                    "('problem_user', 'secret_sauce', 'problem', TRUE), " +
                    "('performance_glitch_user', 'secret_sauce', 'performance', TRUE)");

            // Insert test data - Products
            stmt.execute("INSERT INTO products (product_name, product_price, description) VALUES " +
                    "('Sauce Labs Backpack', 29.99, 'carry.allTheThings() with the sleek, streamlined Sly Pack'), " +
                    "('Sauce Labs Bike Light', 9.99, 'A red light is not desired'), " +
                    "('Sauce Labs Bolt T-Shirt', 15.99, 'Get your testing superhero on'), " +
                    "('Sauce Labs Fleece Jacket', 49.99, 'Its not every day that you come across a midweight quarter-zip fleece jacket'), "
                    +
                    "('Sauce Labs Onesie', 7.99, 'Rib snap infant onesie for the junior automation engineer'), " +
                    "('Test.allTheThings() T-Shirt (Red)', 15.99, 'This classic Sauce Labs t-shirt is perfect')");

            // Insert test data - Checkout Info
            stmt.execute("INSERT INTO checkout_info (first_name, last_name, zip_code) VALUES " +
                    "('John', 'Doe', '12345'), " +
                    "('Jane', 'Smith', '67890'), " +
                    "('Bob', 'Johnson', '54321'), " +
                    "('Alice', 'Williams', '98765')");

            stmt.close();
            System.out.println("Database initialized successfully with test data");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    /**
     * Execute SELECT query and return results as list of maps
     * 
     * @param query SQL SELECT query
     * @return List of maps, each map represents a row
     */
    public static List<Map<String, String>> executeQuery(String query) {
        List<Map<String, String>> results = new ArrayList<>();

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = rs.getString(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

            rs.close();
            stmt.close();

            System.out.println("Query executed successfully, returned " + results.size() + " rows");

        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            throw new RuntimeException("Failed to execute query", e);
        }

        return results;
    }

    /**
     * Execute UPDATE, INSERT, or DELETE query
     * 
     * @param query SQL query
     * @return Number of rows affected
     */
    public static int executeUpdate(String query) {
        int rowsAffected = 0;

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(query);
            stmt.close();

            System.out.println("Update executed successfully, " + rowsAffected + " rows affected");

        } catch (SQLException e) {
            System.err.println("Error executing update: " + e.getMessage());
            throw new RuntimeException("Failed to execute update", e);
        }

        return rowsAffected;
    }

    /**
     * Get user credentials from database
     * 
     * @param username Username to search for
     * @return Map containing user data
     */
    public static Map<String, String> getUserCredentials(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        List<Map<String, String>> results = executeQuery(query);

        if (results.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }

        return results.get(0);
    }

    /**
     * Get product information from database
     * 
     * @param productName Product name to search for
     * @return Map containing product data
     */
    public static Map<String, String> getProductInfo(String productName) {
        String query = "SELECT * FROM products WHERE product_name = '" + productName + "'";
        List<Map<String, String>> results = executeQuery(query);

        if (results.isEmpty()) {
            throw new RuntimeException("Product not found: " + productName);
        }

        return results.get(0);
    }

    /**
     * Get all products from database
     * 
     * @return List of all products
     */
    public static List<Map<String, String>> getAllProducts() {
        String query = "SELECT * FROM products";
        return executeQuery(query);
    }

    /**
     * Get checkout information from database
     * 
     * @param id Checkout info ID
     * @return Map containing checkout data
     */
    public static Map<String, String> getCheckoutInfo(int id) {
        String query = "SELECT * FROM checkout_info WHERE id = " + id;
        List<Map<String, String>> results = executeQuery(query);

        if (results.isEmpty()) {
            throw new RuntimeException("Checkout info not found for ID: " + id);
        }

        return results.get(0);
    }
}
