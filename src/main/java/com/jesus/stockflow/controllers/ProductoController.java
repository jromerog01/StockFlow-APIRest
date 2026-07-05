package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.*;
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
    public ProductoResponseDTO findById(@PathVariable int id){
        return service.findByIdMapeado(id);
    }

    @GetMapping(params = "sku")
    public ProductoResponseDTO findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }

    @GetMapping(params = "nombre")
    public List<ProductoResponseDTO> findByNombre(@RequestParam String nombre){
        return service.findByNombreContainingIgnoreCase(nombre);
    }

    @PutMapping("/{id}")
    public ProductoResponseDTO update (@PathVariable int id, @RequestBody ProductoUpdateRequestDTO producto){
        return service.update(id, producto);
    }

    @PatchMapping(value = "/{id}")
    public ProductoResponseDTO activarDesactivarProducto(@PathVariable int id, @RequestBody ActivarDesactivarDTO estado){
        return service.activarDesactivarProducto(id, estado.isActivo());
    }

    @GetMapping(params = "bajoStock")
    public List<ProductoResponseDTO> findByStockIsLessThanEqual(@RequestParam boolean bajoStock){
        return service.findByStockIsLessThanEqual(bajoStock);
    }

    @GetMapping(params = "activo")
    public List<ProductoResponseDTO> findByActivo(@RequestParam boolean activo){
        return service.findByActivo(activo);
    }





}

