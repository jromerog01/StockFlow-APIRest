package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.ProductoRequestDTO;
import com.jesus.stockflow.entities.dtos.ProductoResponseDTO;
import com.jesus.stockflow.entities.dtos.ProductoUpdateRequestDTO;
import com.jesus.stockflow.entities.dtos.StockRequestDTO;
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

    @PutMapping("/{id}")
    public Producto update (@PathVariable int id, @RequestBody ProductoUpdateRequestDTO producto){
        return service.update(id, producto);
    }

    @PatchMapping("/{id}/desactivar")
    public Producto desactivar(@PathVariable int id){
        return service.desactivarProducto(id);
    }

    @PatchMapping("/{id}/activar")
    public Producto activar(@PathVariable int id){
        return service.activarProducto(id);
    }

    @GetMapping("/bajo-stock")
    public List<Producto> findByStockIsLessThanEqual(){
        return service.findByStockIsLessThanEqual();
    }

    @PatchMapping("/{id}/entrada-stock")
    public Producto entradaStock(@PathVariable int id, @RequestBody StockRequestDTO cantidadUnidades){
        return service.entradaStock(id, cantidadUnidades);
    }




}

