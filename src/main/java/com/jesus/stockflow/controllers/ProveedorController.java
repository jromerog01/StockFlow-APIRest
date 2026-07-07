package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.entities.dtos.ProveedorDTO;
import com.jesus.stockflow.services.interfaces.ProveedorService;
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
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores", description = "Gestion de proveedores de productos")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @Operation(summary = "Crear proveedor", description = "Registra un nuevo proveedor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor creado correctamente", content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del proveedor invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un proveedor con esos datos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ProveedorDTO save(@RequestBody ProveedorDTO proveedor){
        return service.save(proveedor);
    }

    @Operation(summary = "Listar proveedores", description = "Devuelve todos los proveedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores", content = @Content(schema = @Schema(implementation = ProveedorDTO.class)))
    @GetMapping
    public List<ProveedorDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Buscar proveedor por id", description = "Devuelve un proveedor segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado", content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un proveedor con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ProveedorDTO findById(@Parameter(description = "Id del proveedor") @PathVariable int id){
        return service.findByIdMapeado(id);
    }

    @Operation(summary = "Actualizar proveedor", description = "Actualiza los datos de un proveedor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado correctamente", content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del proveedor invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un proveedor con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Los datos actualizados entran en conflicto con otro proveedor", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ProveedorDTO update(@Parameter(description = "Id del proveedor") @PathVariable int id, @RequestBody ProveedorDTO proveedor){
        return service.update(id, proveedor);
    }

    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor existente segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente", content = @Content(schema = @Schema(implementation = ProveedorDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un proveedor con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El proveedor esta asociado a productos y no puede eliminarse", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ProveedorDTO delete (@Parameter(description = "Id del proveedor") @PathVariable int id){
        return service.delete(id);
    }


}
