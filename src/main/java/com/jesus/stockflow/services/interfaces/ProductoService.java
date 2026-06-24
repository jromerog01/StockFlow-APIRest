package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.ProductoRequestDTO;
import com.jesus.stockflow.entities.dtos.ProductoResponseDTO;

import java.util.List;


public interface ProductoService {
    ProductoResponseDTO save(ProductoRequestDTO producto);
    List<ProductoResponseDTO> findAll();
    Producto findById(int id);
}
