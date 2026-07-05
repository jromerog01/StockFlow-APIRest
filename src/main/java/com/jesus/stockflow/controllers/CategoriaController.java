package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.CategoriaDTO;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> findAll(){
        return service.findAll();
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoria){
        return service.save(categoria);
    }

    @GetMapping("/{id}")
    public CategoriaDTO findById(@PathVariable int id){
        return service.findByIdMapeada(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO update(@PathVariable int id, @RequestBody CategoriaDTO nombre){
        return service.update(id, nombre);
    }

    @DeleteMapping("/{id}")
    public CategoriaDTO delete(@PathVariable int id){
        return service.delete(id);
    }


}
