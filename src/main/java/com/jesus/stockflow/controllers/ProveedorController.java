package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ProveedorDTO;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @PostMapping
    public ProveedorDTO save(@RequestBody ProveedorDTO proveedor){
        return service.save(proveedor);
    }

    @GetMapping
    public List<ProveedorDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProveedorDTO findById(@PathVariable int id){
        return service.findByIdMapeado(id);
    }

    @PutMapping("/{id}")
    public ProveedorDTO update(@PathVariable int id, @RequestBody ProveedorDTO proveedor){
        return service.update(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public ProveedorDTO delete (@PathVariable int id){
        return service.delete(id);
    }


}
