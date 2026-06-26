package com.jesus.stockflow.models;

import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Carrito {

    private List<VentaProductoNombresDTO> productos = new ArrayList<>();

    public Carrito(List<VentaProductoNombresDTO> productos) {
        this.productos = productos;
    }

    public Carrito() {
    }

    public List<VentaProductoNombresDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<VentaProductoNombresDTO> productos) {
        this.productos = productos;
    }
}
