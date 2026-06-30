package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.*;

import java.util.List;


public interface ProductoService {
    ProductoResponseDTO save(ProductoRequestDTO producto);
    Producto save(Producto producto);
    List<ProductoResponseDTO> findAll();
    Producto findById(int id);
    Producto findBySku(String sku);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    Producto update(int id, ProductoUpdateRequestDTO producto);
    Producto desactivarProducto(int id);
    Producto activarProducto(int id);
    List<Producto> findByStockIsLessThanEqual();
    Producto entradaStock(int id, VentaProductoIdDTO cantidadUnidades);


}
