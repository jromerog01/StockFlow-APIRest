package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoUpdateCantidadDTO;
import com.jesus.stockflow.services.implementations.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @PostMapping
    public List<VentaProductoNombresDTO> agregarProducto(@RequestBody VentaProductoIdDTO producto){
        return service.agregarProducto(producto);
    }

    @GetMapping
    public List<VentaProductoNombresDTO> verProductos(){
        return service.verProductos();
    }

    @DeleteMapping("/{id}")
    public VentaProductoNombresDTO eliminarProducto(@PathVariable int id){
        return service.eliminarProducto(id);
    }

    @PatchMapping("/{id}")
    public VentaProductoNombresDTO eliminarUnidadesProducto(@PathVariable int id, @RequestBody VentaProductoUpdateCantidadDTO cantidad){
        return service.eliminarUnidadesProducto(id, cantidad);
    }

    @PutMapping("{/id}")
    public VentaProductoNombresDTO actualizarUnidadesProducto (@PathVariable int id, @RequestBody VentaProductoUpdateCantidadDTO cantidad){
        return service.actualizarUnidadesProducto(id, cantidad);
    }



}
