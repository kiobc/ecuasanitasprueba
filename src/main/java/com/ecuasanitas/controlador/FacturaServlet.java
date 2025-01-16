package com.ecuasanitas.controlador;

import com.ecuasanitas.database.DatabaseConnection;
import com.ecuasanitas.model.DetalleFactura;
import com.ecuasanitas.model.Factura;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/FacturaServlet")
public class FacturaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productos = request.getParameterValues("producto[]");
        String[] cantidades = request.getParameterValues("cantidad[]");
        String[] precios = request.getParameterValues("precio[]");

        List<DetalleFactura> detalles = new ArrayList<>();
        double subtotal = 0;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String numeroFactura = "F" + System.currentTimeMillis();

            for (int i = 0; i < productos.length; i++) {
                String productoNombre = productos[i].trim();
                double precioUnitario = Double.parseDouble(precios[i]);

                String codigoProducto = "P" + System.currentTimeMillis();
                String query = "SELECT id FROM productos WHERE nombre = ?";
                int productoId = -1;
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, productoNombre);
                    try (var rs = ps.executeQuery()) {
                        if (rs.next()) {
                            productoId = rs.getInt("id");
                        } else {
                            String insertProductoSQL = "INSERT INTO productos (codigo, nombre, precio_unitario) VALUES (?, ?, ?)";
                            try (PreparedStatement psInsertProducto = connection.prepareStatement(insertProductoSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                                psInsertProducto.setString(1, codigoProducto);
                                psInsertProducto.setString(2, productoNombre);
                                psInsertProducto.setDouble(3, precioUnitario);
                                psInsertProducto.executeUpdate();

                                try (var rsInsert = psInsertProducto.getGeneratedKeys()) {
                                    if (rsInsert.next()) {
                                        productoId = rsInsert.getInt(1);
                                    }
                                }
                            }
                        }
                    }
                }

                int cantidad = Integer.parseInt(cantidades[i]);
                double subtotalDetalle = cantidad * precioUnitario;
                subtotal += subtotalDetalle;

                detalles.add(new DetalleFactura(productoId, cantidad, precioUnitario, subtotalDetalle));
            }

            double iva = subtotal * 0.12;
            double total = subtotal + iva;

            String insertFacturaSQL = "INSERT INTO facturas (numero_factura, fecha, cliente_id, subtotal, iva, total) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psFactura = connection.prepareStatement(insertFacturaSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psFactura.setString(1, numeroFactura);
                psFactura.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                psFactura.setInt(3, Integer.parseInt(request.getParameter("cliente")));
                psFactura.setDouble(4, subtotal);
                psFactura.setDouble(5, iva);
                psFactura.setDouble(6, total);
                psFactura.executeUpdate();

                try (var rs = psFactura.getGeneratedKeys()) {
                    if (rs.next()) {
                        int facturaId = rs.getInt(1);

                        String insertDetalleSQL = "INSERT INTO detalle_factura (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement psDetalle = connection.prepareStatement(insertDetalleSQL)) {
                            for (DetalleFactura detalle : detalles) {
                                psDetalle.setInt(1, facturaId);
                                psDetalle.setInt(2, detalle.getProductoId());
                                psDetalle.setInt(3, detalle.getCantidad());
                                psDetalle.setDouble(4, detalle.getPrecioUnitario());
                                psDetalle.setDouble(5, detalle.getSubtotal());
                                psDetalle.addBatch();
                            }
                            psDetalle.executeBatch();
                        }
                    }
                }
            }

            response.setContentType("text/html");
            response.getWriter().println("<h1>Factura registrada con éxito</h1>");
            response.getWriter().println("<a href='menu.jsp'>Volver al Menú</a>");

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h1>Error al registrar la factura</h1>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
        }
    }
}
