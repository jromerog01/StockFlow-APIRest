package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioResponseDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
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
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos de Inventario", description = "Consulta y registro de movimientos de entrada y salida de inventario")
public class MovimientoInventarioController {

    @Autowired
    private MovimientoInventarioService service;

    @Operation(summary = "Listar movimientos", description = "Devuelve todos los movimientos de inventario registrados")
    @ApiResponse(responseCode = "200", description = "Lista de movimientos", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class)))
    @GetMapping
    public List<MovimientoInventarioResponseDTO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Buscar movimiento por id", description = "Devuelve un movimiento de inventario segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No existe un movimiento con ese id", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public MovimientoInventarioResponseDTO findById(@Parameter(description = "Id del movimiento de inventario") @PathVariable int id){
        return service.findById(id);
    }

    @Operation(summary = "Registrar movimiento", description = "Registra un nuevo movimiento de entrada o salida de inventario, actualizando el stock del producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimiento registrado correctamente", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del movimiento invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "El producto indicado no existe", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public MovimientoInventarioResponseDTO registrar(@RequestBody MovimientoInventarioDTO movimiento){
        return service.registrar(movimiento);
    }

    @Operation(summary = "Buscar movimientos por nombre de producto", description = "Devuelve los movimientos cuyo producto contenga el nombre indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos encontrados", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/nombre")
    public List<MovimientoInventarioResponseDTO> findByNombreContaining(@Parameter(description = "Texto a buscar dentro del nombre del producto") @RequestParam String nombre){
        return service.findByNombreContaining(nombre);
    }

    @Operation(summary = "Buscar movimientos por SKU", description = "Devuelve los movimientos asociados al producto con el SKU indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos encontrados", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/sku")
    public List<MovimientoInventarioResponseDTO> findBySku(@Parameter(description = "SKU del producto") @RequestParam String sku){
        return service.findBySku(sku);
    }

    @Operation(summary = "Buscar movimientos con cantidad mayor a un valor", description = "Devuelve los movimientos cuya cantidad sea mayor al valor indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos encontrados", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/cantidad-minima")
    public List<MovimientoInventarioResponseDTO> findByCantidadGreaterThan(@Parameter(description = "Cantidad minima (exclusiva) del movimiento") @RequestParam int cantidadMin){
        return service.findByCantidadGreaterThan(cantidadMin);
    }

    @Operation(summary = "Buscar movimientos con cantidad menor a un valor", description = "Devuelve los movimientos cuya cantidad sea menor al valor indicado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos encontrados", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parametro invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/cantidad-maxima")
    public List<MovimientoInventarioResponseDTO> findByCantidadLessThan(@Parameter(description = "Cantidad maxima (exclusiva) del movimiento") @RequestParam int cantidadMax){
        return service.findByCantidadLessThan(cantidadMax);
    }

    @Operation(summary = "Buscar movimientos por tipo", description = "Devuelve los movimientos filtrados por tipo (ENTRADA o SALIDA)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos encontrados", content = @Content(schema = @Schema(implementation = MovimientoInventarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "El tipo de movimiento indicado no es valido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/tipo")
    public List<MovimientoInventarioResponseDTO> findByTipoMovimiento(@Parameter(description = "Tipo de movimiento: ENTRADA o SALIDA") @RequestParam TipoMovimiento tipoMovimiento){
        return service.findByTipoMovimiento(tipoMovimiento);
    }

}
