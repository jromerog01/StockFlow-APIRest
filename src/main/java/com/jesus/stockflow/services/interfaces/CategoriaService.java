package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.dtos.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<Categoria> findAll();
    Categoria crearCategoria(Categoria categoria);
    Categoria findById(int id);
    int actualizarCategoria(int id, CategoriaDTO nombre);
    Categoria eliminarCategoria(int id);

}
