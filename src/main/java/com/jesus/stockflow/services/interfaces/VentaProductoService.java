package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;

import java.util.List;

public interface VentaProductoService {

    public void registrarVenta(Venta venta, List<VentaProductoNombresDTO> productos);
    public List<VentaProductoNombresDTO> getProductosVenta(int idVenta);
    }
