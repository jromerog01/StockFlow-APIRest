package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.dtos.VentaProductoDescripcionDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;

import java.util.List;

public interface VentaProductoService {

    void registrarVenta(Venta venta, List<VentaProductoNombresDTO> productos);
    List<VentaProductoNombresDTO> getProductosVenta(int idVenta);
    List<VentaProductoDescripcionDTO> findAll();
    VentaProductoDescripcionDTO findById(int id);
    List<VentaProductoDescripcionDTO> findByIdVenta(int idVenta);
    List<VentaProductoDescripcionDTO> findByIdProducto(int idProducto);
    List<VentaProductoDescripcionDTO> findBySku(String sku);
    List<VentaProductoDescripcionDTO> findByNombreContaining(String nombre);
    List<VentaProductoDescripcionDTO> findByCantidadGreaterThan(int cantidad);
    List<VentaProductoDescripcionDTO> findByCantidadLessThan(int cantidad);
    void reducirStock(Producto p, int cantidad);
    void registrarMovimientoInventario(List<VentaProductoNombresDTO> productos);

    }
