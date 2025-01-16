package com.ecuasanitas.dao;

import java.sql.*;

public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USER = "usuario";
    private static final String PASSWORD = "contraseña";
    
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
