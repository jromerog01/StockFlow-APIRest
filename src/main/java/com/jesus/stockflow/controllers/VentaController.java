package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.services.interfaces.VentaService;
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
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Consulta de ventas ya confirmadas")
public class VentaController {

    @Autowired
    private VentaService service;

    @Operation(summary = "Listar ventas", description = "Devuelve todas las ventas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class)))
    @GetMapping
    public List<VentaCompletaDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Buscar venta por id", description = "Devuelve una venta segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe una venta con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public VentaCompletaDTO findById(@Parameter(description = "Id de la venta") @PathVariable int id){
        return service.findById(id);
    }

    @Operation(summary = "Buscar ventas por metodo de pago", description = "Devuelve las ventas filtradas por metodo de pago (TARJETA o EFECTIVO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ventas encontradas", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El metodo de pago indicado no es valido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/metodo-pago")
    public List<VentaCompletaDTO> findByMetodoDePago(@Parameter(description = "Metodo de pago: TARJETA o EFECTIVO") @RequestParam MetodoPago metodoPago){
        return service.findByMetodoDePago(metodoPago);
    }

    @Operation(summary = "Buscar ventas por nombre de producto", description = "Devuelve las ventas que incluyan un producto cuyo nombre contenga el texto indicado")
    @ApiResponse(responseCode = "200", description = "Lista de ventas encontradas", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class)))
    @GetMapping("/nombre")
    public List<VentaCompletaDTO> findByNombreContaining(@Parameter(description = "Texto a buscar dentro del nombre del producto") @RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @Operation(summary = "Buscar ventas por SKU", description = "Devuelve las ventas que incluyan el producto con el SKU indicado")
    @ApiResponse(responseCode = "200", description = "Lista de ventas encontradas", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class)))
    @GetMapping("/sku")
    public List<VentaCompletaDTO> findBySku(@Parameter(description = "SKU del producto") @RequestParam String sku){
        return service.findBySku(sku);
    }




}
