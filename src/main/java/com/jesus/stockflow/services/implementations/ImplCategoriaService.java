package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.repositories.CategoriaRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplCategoriaService implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

}
