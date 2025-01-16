package com.ecuasanitas.model;

public class DetalleFactura {
    private int productoId;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleFactura(int productoId, int cantidad, double precioUnitario, double subtotal) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public int getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
