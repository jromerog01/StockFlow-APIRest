package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.entities.dtos.ProveedorDTO;

import java.util.List;

public interface ProveedorService {
    ProveedorDTO save(ProveedorDTO proveedor);
    List<ProveedorDTO> findAll();
    Proveedor findById(int id);
    ProveedorDTO findByIdMapeado(int id);
    ProveedorDTO update(int id, ProveedorDTO proveedor);
    ProveedorDTO delete(int id);


}
