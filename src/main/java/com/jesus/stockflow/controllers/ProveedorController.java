package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Proveedor;
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
    public Proveedor save(@RequestBody Proveedor proveedor){
        return service.save(proveedor);
    }

    @GetMapping
    public List<Proveedor> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Proveedor findById(@PathVariable int id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public Proveedor update(@PathVariable int id, @RequestBody Proveedor proveedor){
        return service.update(id, proveedor);
    }

    @DeleteMapping("/{id}")
    public Proveedor delete (@PathVariable int id){
        return service.delete(id);
    }


}
