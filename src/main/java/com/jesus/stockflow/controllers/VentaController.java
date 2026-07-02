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

    @GetMapping("metodoPago/{metodoPago}")
    public List<VentaCompletaDTO> findByMetodoDePago(@PathVariable MetodoPago metodoPago){
        return service.findByMetodoDePago(metodoPago);
    }

    @GetMapping("/buscar")
    public List<VentaCompletaDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping("/sku")
    public List<VentaCompletaDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }





}
