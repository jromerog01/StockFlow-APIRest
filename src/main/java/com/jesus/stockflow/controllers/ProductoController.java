package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.ProductoRequestDTO;
import com.jesus.stockflow.entities.dtos.ProductoResponseDTO;
import com.jesus.stockflow.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;


    @PostMapping
    public ProductoResponseDTO save(@RequestBody ProductoRequestDTO producto){
        return service.save(producto);
    }

    @GetMapping
    public List<ProductoResponseDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Producto findById(@PathVariable int id){
        return service.findById(id);
    }

    @GetMapping("/sku/{sku}")
    public Producto findBySku(@PathVariable String sku){
        return service.findBySku(sku);
    }

    @GetMapping("/buscar")
    public List<Producto> findByNombre(@RequestParam String nombre){
        return service.findByNombreContainingIgnoreCase(nombre);
    }

    


}
