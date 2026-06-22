package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.repositories.ProveedorRepository;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplProveedorService implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;


}
