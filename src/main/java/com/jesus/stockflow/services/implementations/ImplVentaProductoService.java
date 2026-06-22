package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.repositories.VentaProductoRepository;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplVentaProductoService implements VentaProductoService {

    @Autowired
    private VentaProductoRepository repository;


}
