package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.repositories.VentaRepository;
import com.jesus.stockflow.services.interfaces.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplVentaService implements VentaService {

    @Autowired
    private VentaRepository repository;

}
