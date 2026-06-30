package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ConfirmarVentaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
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
    public VentaProductoNombresDTO eliminarUnidadesProducto(@PathVariable int id, @RequestBody VentaProductoIdDTO cantidad){
        return service.eliminarUnidadesProducto(id, cantidad);
    }

    @PutMapping("/{id}")
    public VentaProductoNombresDTO actualizarUnidadesProducto (@PathVariable int id, @RequestBody VentaProductoIdDTO cantidad){
        return service.actualizarUnidadesProducto(id, cantidad);
    }

    @DeleteMapping
    public List<VentaProductoNombresDTO> vaciarCarrito(){
        return service.vaciarCarrito();
    }

    @PostMapping("/confirmar/{metodoPago}")
    public ConfirmarVentaDTO confirmarVenta(@PathVariable MetodoPago metodoPago){
        return service.confirmarVenta(metodoPago);
    }



}
