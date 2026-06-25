package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.ProductoRequestDTO;
import com.jesus.stockflow.entities.dtos.ProductoResponseDTO;
import com.jesus.stockflow.entities.dtos.ProductoUpdateRequestDTO;
import com.jesus.stockflow.entities.dtos.StockRequestDTO;

import java.util.List;


public interface ProductoService {
    ProductoResponseDTO save(ProductoRequestDTO producto);
    List<ProductoResponseDTO> findAll();
    Producto findById(int id);
    Producto findBySku(String sku);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    Producto update(int id, ProductoUpdateRequestDTO producto);
    Producto desactivarProducto(int id);
    Producto activarProducto(int id);
    List<Producto> findByStockIsLessThanEqual();
    Producto entradaStock(int id, StockRequestDTO cantidadUnidades);


}
