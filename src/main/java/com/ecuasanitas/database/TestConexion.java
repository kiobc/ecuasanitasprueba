package com.ecuasanitas.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
                
                System.out.println("¡Conexión exitosa!");
                System.out.println("Driver: " + metaData.getDriverName());
                System.out.println("Versión: " + metaData.getDriverVersion());
                System.out.println("URL de la base de datos: " + metaData.getURL());
                System.out.println("Usuario: " + metaData.getUserName());
                
                conn.close();
                System.out.println("Conexión cerrada correctamente");
            }
            
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}