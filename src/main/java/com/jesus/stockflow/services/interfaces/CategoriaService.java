package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Categoria;

import java.util.List;

public interface CategoriaService {

    List<Categoria> findAll();
    Categoria save(Categoria categoria);
    Categoria findById(int id);
    int update(int id, Categoria nombre);
    Categoria delete(int id);

}
