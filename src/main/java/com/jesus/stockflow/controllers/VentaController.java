package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.services.interfaces.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService service;

    @GetMapping
    public List<VentaCompletaDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VentaCompletaDTO findById(@PathVariable int id){
        return service.findById(id);
    }

    @GetMapping(params = "metodoPago")
    public List<VentaCompletaDTO> findByMetodoDePago(@RequestParam MetodoPago metodoPago){
        return service.findByMetodoDePago(metodoPago);
    }

    @GetMapping(params = "nombre")
    public List<VentaCompletaDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping(params = "sku")
    public List<VentaCompletaDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }





}
