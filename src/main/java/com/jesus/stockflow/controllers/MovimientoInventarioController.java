package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
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

    @PostMapping
    public MovimientoInventarioResponseDTO registrar(@RequestBody MovimientoInventarioDTO movimiento){
        return service.registrar(movimiento);
    }

    @GetMapping(params = "nombre")
    public List<MovimientoInventarioResponseDTO> findByNombreContaining(@RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @GetMapping(params = "sku")
    public List<MovimientoInventarioResponseDTO> findBySku(@RequestParam String sku){
        return service.findBySku(sku);
    }

    @GetMapping(params = "cantidadMin")
    public List<MovimientoInventarioResponseDTO> findByCantidadGreaterThan(@RequestParam int cantidadMin){
        return service.findByCantidadGreaterThan(cantidadMin);
    }

    @GetMapping(params = "cantidadMax")
    public List<MovimientoInventarioResponseDTO> findByCantidadLessThan(@RequestParam int cantidadMax){
        return service.findByCantidadLessThan(cantidadMax);
    }

    @GetMapping(params = "tipoMovimiento")
    public List<MovimientoInventarioResponseDTO> findByTipoMovimiento(@RequestParam TipoMovimiento tipoMovimiento){
        return service.findByTipoMovimiento(tipoMovimiento);
    }










}
