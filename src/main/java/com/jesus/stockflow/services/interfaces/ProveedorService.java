package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    Proveedor save(Proveedor proveedor);
    List<Proveedor> findAll();
    Proveedor findById(int id);
    Proveedor update(int id, Proveedor proveedor);
    Proveedor delete(int id);


}
