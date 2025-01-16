package com.ecuasanitas.model;

import java.util.Date;
import java.util.List;

public class Factura {
    private int id;
    private int clienteId;
    private Date fecha;
    private List<DetalleFactura> detalles;

    public Factura(int clienteId, List<DetalleFactura> detalles) {
        this.clienteId = clienteId;
        this.fecha = new Date();
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Date getFecha() {
        return fecha;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }
}
