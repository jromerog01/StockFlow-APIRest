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

    @GetMapping("/venta/{idVenta}")
    public List<VentaProductoDescripcionDTO> findByIdVenta(@PathVariable int idVenta){
        return service.findByIdVenta(idVenta);
    }

    @GetMapping("/producto/{idProducto}")
    public List<VentaProductoDescripcionDTO> findByIdProducto(@PathVariable int idProducto){
        return service.findByIdProducto(idProducto);
    }

    @GetMapping("/producto/sku")
    public List<VentaProductoDescripcionDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }

    @GetMapping("/producto")
    public List<VentaProductoDescripcionDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping("/cantidad/mayor-que")
    public List<VentaProductoDescripcionDTO> findByCantidadGreaterThan(@RequestParam int cantidad){
        return service.findByCantidadGreaterThan(cantidad);
    }

    @GetMapping("/cantidad/menor-que")
    public List<VentaProductoDescripcionDTO> findByCantidadLessThan(@RequestParam int cantidad){
        return service.findByCantidadLessThan(cantidad);
    }



}
