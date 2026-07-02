package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.MovimientoInventarioResponseDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService service;

    @GetMapping
    public List<MovimientoInventarioResponseDTO> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MovimientoInventarioResponseDTO findById(@PathVariable int id){
        return service.findById(id);
    }

    @GetMapping("/buscar")
    public List<MovimientoInventarioResponseDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping("/sku")
    public List<MovimientoInventarioResponseDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }

    @GetMapping("/cantidad/mayor-que")
    public List<MovimientoInventarioResponseDTO> findByCantidadGreaterThan(@RequestParam int cantidadMin){
        return service.findByCantidadGreaterThan(cantidadMin);
    }

    @GetMapping("/cantidad/menor-que")
    public List<MovimientoInventarioResponseDTO> findByCantidadLessThan(@RequestParam int cantidadMax){
        return service.findByCantidadLessThan(cantidadMax);
    }

    @GetMapping("/tipo/{tipoMovimiento}")
    public List<MovimientoInventarioResponseDTO> findByTipoMovimiento(@PathVariable TipoMovimiento tipoMovimiento){
        return service.findByTipoMovimiento(tipoMovimiento);
    }










}
