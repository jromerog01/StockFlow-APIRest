package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.*;
import com.jesus.stockflow.services.interfaces.ProductoService;
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
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestion de productos: creacion, consulta, actualizacion y activacion/desactivacion")
public class ProductoController {

    @Autowired
    private ProductoService service;


    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del producto invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Ya existe un producto con ese SKU", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public ProductoResponseDTO save(@RequestBody ProductoRequestDTO producto){
        return service.save(producto);
    }

    @Operation(summary = "Listar productos", description = "Devuelve todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de productos", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class)))
    @GetMapping
    public List<ProductoResponseDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Buscar producto por id", description = "Devuelve un producto segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un producto con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ProductoResponseDTO findById(@Parameter(description = "Id del producto") @PathVariable int id){
        return service.findByIdMapeado(id);
    }

    @Operation(summary = "Buscar producto por SKU", description = "Devuelve un producto segun su SKU exacto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un producto con ese SKU", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/sku")
    public ProductoResponseDTO findBySku(@Parameter(description = "SKU exacto del producto") @RequestParam String sku){
        return service.findBySku(sku);
    }

    @Operation(summary = "Buscar productos por nombre", description = "Devuelve los productos cuyo nombre contenga el texto indicado (ignorando mayusculas/minusculas)")
    @ApiResponse(responseCode = "200", description = "Lista de productos encontrados", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class)))
    @GetMapping("/nombre")
    public List<ProductoResponseDTO> findByNombre(@Parameter(description = "Texto a buscar dentro del nombre del producto") @RequestParam String nombre){
        return service.findByNombreContainingIgnoreCase(nombre);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del producto invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un producto con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "El SKU indicado ya pertenece a otro producto", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public ProductoResponseDTO update (@Parameter(description = "Id del producto") @PathVariable int id, @RequestBody ProductoUpdateRequestDTO producto){
        return service.update(id, producto);
    }

    @Operation(summary = "Activar o desactivar producto", description = "Cambia el estado activo/inactivo de un producto sin eliminarlo del inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado del producto actualizado correctamente", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estado invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un producto con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping(value = "/{id}")
    public ProductoResponseDTO activarDesactivarProducto(@Parameter(description = "Id del producto") @PathVariable int id, @RequestBody ActivarDesactivarDTO estado){
        return service.activarDesactivarProducto(id, estado.isActivo());
    }

    @Operation(summary = "Buscar productos con bajo stock", description = "Devuelve los productos cuyo stock esta por debajo o igual al minimo configurado, segun el filtro indicado")
    @ApiResponse(responseCode = "200", description = "Lista de productos con bajo stock", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class)))
    @GetMapping("/bajo-stock")
    public List<ProductoResponseDTO> findByStockIsLessThanEqual(@Parameter(description = "true para filtrar productos con bajo stock") @RequestParam boolean bajoStock){
        return service.findByStockIsLessThanEqual(bajoStock);
    }

    @Operation(summary = "Buscar productos por estado activo", description = "Devuelve los productos filtrados segun si estan activos o inactivos")
    @ApiResponse(responseCode = "200", description = "Lista de productos filtrados por estado", content = @Content(schema = @Schema(implementation = ProductoResponseDTO.class)))
    @GetMapping("/activo")
    public List<ProductoResponseDTO> findByActivo(@Parameter(description = "true para productos activos, false para inactivos") @RequestParam boolean activo){
        return service.findByActivo(activo);
    }




}

