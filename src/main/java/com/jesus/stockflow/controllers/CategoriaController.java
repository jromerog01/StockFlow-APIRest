package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.Categoria;
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
    public List<Categoria> findAll(){
        return service.findAll();
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria){
        return service.crearCategoria(categoria);
    }

    @GetMapping("/{id}")
    public Categoria findById(@PathVariable int id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public int actualizarCategoria(@PathVariable int id, @RequestBody CategoriaDTO nombre){
        return service.actualizarCategoria(id, nombre);
    }

    @DeleteMapping("/{id}")
    public Categoria eliminarCategoria(@PathVariable int id){
        return service.eliminarCategoria(id);
    }


}
