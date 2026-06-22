package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.repositories.MovimientoInventarioRepository;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplMovimientoInventarioService implements MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository repository;
}
