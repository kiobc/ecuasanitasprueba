package com.ecuasanitas.servlets;

import com.ecuasanitas.database.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros de usuario y contraseña
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT password FROM usuarios WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");

                if (storedPassword.equals(password)) {
                    response.sendRedirect("menu.jsp");
                } else {
                    request.setAttribute("error", "Usuario o contraseña incorrectos");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error de conexión a la base de datos");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
