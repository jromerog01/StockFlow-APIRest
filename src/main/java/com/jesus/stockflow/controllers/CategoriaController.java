package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Categoria;
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
    public List<Categoria> findAll(){
        return service.findAll();
    }

    @PostMapping
    public Categoria save(@RequestBody Categoria categoria){
        return service.save(categoria);
    }

    @GetMapping("/{id}")
    public Categoria findById(@PathVariable int id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable int id, @RequestBody Categoria nombre){
        return service.update(id, nombre);
    }

    @DeleteMapping("/{id}")
    public Categoria delete(@PathVariable int id){
        return service.delete(id);
    }


}
