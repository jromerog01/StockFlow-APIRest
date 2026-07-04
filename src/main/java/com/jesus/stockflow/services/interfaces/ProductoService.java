package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.*;

import java.util.List;


public interface ProductoService {
    ProductoResponseDTO save(ProductoRequestDTO producto);
    Producto save(Producto producto);
    List<ProductoResponseDTO> findAll();
    Producto findById(int id);
    ProductoResponseDTO findByIdMapeado(int id);
    ProductoResponseDTO findBySku(String sku);
    List<ProductoResponseDTO> findByNombreContainingIgnoreCase(String nombre);
    ProductoResponseDTO update(int id, ProductoUpdateRequestDTO producto);
    ProductoResponseDTO activarDesactivarProducto(int id, boolean estado);
    List<ProductoResponseDTO> findByStockIsLessThanEqual(boolean bajoStock);


}
