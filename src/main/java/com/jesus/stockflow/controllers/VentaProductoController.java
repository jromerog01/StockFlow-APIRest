package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoDescripcionDTO;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
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
@RequestMapping("/api/venta-productos")
@Tag(name = "Venta-Productos", description = "Consulta del detalle de productos incluidos en las ventas")
public class VentaProductoController {

    @Autowired
    private VentaProductoService service;

    @Operation(summary = "Listar detalle de venta-productos", description = "Devuelve todos los registros de productos vendidos")
    @ApiResponse(responseCode = "200", description = "Lista de detalles de venta-productos", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping
    public List<VentaProductoDescripcionDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Buscar detalle por id", description = "Devuelve un registro de venta-producto segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Detalle encontrado", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un detalle con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public VentaProductoDescripcionDTO findById(@Parameter(description = "Id del detalle de venta-producto") @PathVariable int id){
        return service.findById(id);
    }

    @Operation(summary = "Buscar detalles por id de venta", description = "Devuelve los productos vendidos asociados a una venta especifica")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/venta")
    public List<VentaProductoDescripcionDTO> findByIdVenta(@Parameter(description = "Id de la venta") @RequestParam int idVenta){
        return service.findByIdVenta(idVenta);
    }

    @Operation(summary = "Buscar detalles por id de producto", description = "Devuelve las ventas registradas de un producto especifico")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/producto")
    public List<VentaProductoDescripcionDTO> findByIdProducto(@Parameter(description = "Id del producto") @RequestParam int idProducto){
        return service.findByIdProducto(idProducto);
    }

    @Operation(summary = "Buscar detalles por SKU", description = "Devuelve los detalles de venta-producto asociados al SKU indicado")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/sku")
    public List<VentaProductoDescripcionDTO> findBySku(@Parameter(description = "SKU del producto") @RequestParam String sku){
        return service.findBySku(sku);
    }

    @Operation(summary = "Buscar detalles por nombre de producto", description = "Devuelve los detalles de venta-producto cuyo producto contenga el nombre indicado")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/nombre")
    public List<VentaProductoDescripcionDTO> findByNombreContaining(@Parameter(description = "Texto a buscar dentro del nombre del producto") @RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @Operation(summary = "Buscar detalles con cantidad mayor a un valor", description = "Devuelve los detalles de venta-producto cuya cantidad sea mayor al valor indicado")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/cantidad-minima")
    public List<VentaProductoDescripcionDTO> findByCantidadGreaterThan(@Parameter(description = "Cantidad minima (exclusiva)") @RequestParam int cantidadMin){
        return service.findByCantidadGreaterThan(cantidadMin);
    }

    @Operation(summary = "Buscar detalles con cantidad menor a un valor", description = "Devuelve los detalles de venta-producto cuya cantidad sea menor al valor indicado")
    @ApiResponse(responseCode = "200", description = "Lista de detalles encontrados", content = @Content(schema = @Schema(implementation = VentaProductoDescripcionDTO.class)))
    @GetMapping("/cantidad-maxima")
    public List<VentaProductoDescripcionDTO> findByCantidadLessThan(@Parameter(description = "Cantidad maxima (exclusiva)") @RequestParam int cantidadMax){
        return service.findByCantidadLessThan(cantidadMax);
    }



}
