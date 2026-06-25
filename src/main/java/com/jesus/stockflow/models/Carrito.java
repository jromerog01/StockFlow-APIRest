package com.jesus.stockflow.models;

import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Carrito {

    private List<VentaProductoIdDTO> productos = new ArrayList<>();

    public Carrito(List<VentaProductoIdDTO> productos) {
        this.productos = productos;
    }

    public Carrito() {
    }

    public List<VentaProductoIdDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<VentaProductoIdDTO> productos) {
        this.productos = productos;
    }
}
