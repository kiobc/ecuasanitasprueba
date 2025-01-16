package com.ecuasanitas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://ADMIN_CYBER\\FACTURACION;databaseName=Ecuasanitas;trustServerCertificate=true;integratedSecurity=false";
    private static final String USER = "sa";     // Reemplaza con tu usuario
    private static final String PASSWORD = "123"; // Reemplaza con tu contraseña
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver no encontrado.", e);
        }
    }
}