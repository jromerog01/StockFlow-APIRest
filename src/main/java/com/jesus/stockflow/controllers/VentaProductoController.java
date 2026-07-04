package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.VentaProductoDescripcionDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta-productos")
public class VentaProductoController {

    @Autowired
    private VentaProductoService service;

    @GetMapping
    public List<VentaProductoDescripcionDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VentaProductoDescripcionDTO findById(@PathVariable int id){
        return service.findById(id);
    }

    @GetMapping(params = "idVenta")
    public List<VentaProductoDescripcionDTO> findByIdVenta(@RequestParam int idVenta){
        return service.findByIdVenta(idVenta);
    }

    @GetMapping(params = "idProducto")
    public List<VentaProductoDescripcionDTO> findByIdProducto(@RequestParam int idProducto){
        return service.findByIdProducto(idProducto);
    }

    @GetMapping(params = "sku")
    public List<VentaProductoDescripcionDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }

    @GetMapping(params = "nombre")
    public List<VentaProductoDescripcionDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping(params = "cantidadMin")
    public List<VentaProductoDescripcionDTO> findByCantidadGreaterThan(@RequestParam int cantidadMin){
        return service.findByCantidadGreaterThan(cantidadMin);
    }

    @GetMapping(params = "cantidadMax")
    public List<VentaProductoDescripcionDTO> findByCantidadLessThan(@RequestParam int cantidadMax){
        return service.findByCantidadLessThan(cantidadMax);
    }



}
