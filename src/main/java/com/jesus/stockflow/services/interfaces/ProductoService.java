package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Producto;

import java.util.List;


public interface ProductoService {
    List<Producto> findAll();

}
