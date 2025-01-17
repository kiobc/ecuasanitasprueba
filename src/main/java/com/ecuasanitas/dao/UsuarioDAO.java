package com.ecuasanitas.dao;

import java.sql.*;

public class UsuarioDAO {

     private static final String URL = "jdbc:sqlserver://ADMIN_CYBER\\FACTURACION;databaseName=Ecuasanitas;trustServerCertificate=true;integratedSecurity=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";
    
    public String obtenerContraseña(String username) {
        String password = null;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT contraseña FROM usuarios WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                password = resultSet.getString("contraseña");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }
}
