package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ConfirmarVentaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.services.interfaces.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService service;

    @GetMapping
    public List<ConfirmarVentaDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ConfirmarVentaDTO findById(@PathVariable int id){
        return service.findById(id);
    }

    @GetMapping("metodoPago/{metodoPago}")
    public List<ConfirmarVentaDTO> findByMetodoDePago(@PathVariable MetodoPago metodoPago){
        return service.findByMetodoDePago(metodoPago);
    }





}
