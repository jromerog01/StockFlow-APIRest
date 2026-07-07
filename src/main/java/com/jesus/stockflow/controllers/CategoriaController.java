package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.CategoriaDTO;
import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Gestion de las categorias de productos")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(summary = "Listar categorias", description = "Devuelve todas las categorias registradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias", content = @Content(schema = @Schema(implementation = CategoriaDTO.class)))
    @GetMapping
    public List<CategoriaDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Crear categoria", description = "Registra una nueva categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria creada correctamente", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El nombre de la categoria es invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe una categoria con ese nombre", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoria){
        return service.save(categoria);
    }

    @Operation(summary = "Buscar categoria por id", description = "Devuelve una categoria segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe una categoria con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public CategoriaDTO findById(@Parameter(description = "Id de la categoria") @PathVariable int id){
        return service.findByIdMapeada(id);
    }

    @Operation(summary = "Actualizar categoria", description = "Actualiza el nombre de una categoria existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El nombre de la categoria es invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe una categoria con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe otra categoria con ese nombre", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public CategoriaDTO update(@Parameter(description = "Id de la categoria") @PathVariable int id, @RequestBody CategoriaDTO nombre){
        return service.update(id, nombre);
    }

    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria existente segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria eliminada correctamente", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe una categoria con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "La categoria esta asociada a productos y no puede eliminarse", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public CategoriaDTO delete(@Parameter(description = "Id de la categoria") @PathVariable int id){
        return service.delete(id);
    }


}
