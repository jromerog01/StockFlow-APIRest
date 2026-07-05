package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.dtos.CategoriaDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaDTO> findAll();
    CategoriaDTO save(CategoriaDTO categoria);
    Categoria findById(int id);
    CategoriaDTO findByIdMapeada(int id);
    CategoriaDTO update(int id, CategoriaDTO nombre);
    CategoriaDTO delete(int id);

}
