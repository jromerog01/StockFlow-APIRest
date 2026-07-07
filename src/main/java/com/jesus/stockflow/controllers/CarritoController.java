package com.jesus.stockflow.controllers;

import com.jesus.stockflow.entities.dtos.ErrorResponseDTO;
import com.jesus.stockflow.entities.dtos.MetodoPagoDTO;
import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.services.implementations.ImplCarritoService;
import com.jesus.stockflow.services.interfaces.CarritoService;
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
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "Gestion del carrito de compras: agregar, actualizar, eliminar productos y confirmar la venta")
public class CarritoController {

    @Autowired
    private CarritoService service;

    @Operation(summary = "Agregar producto al carrito", description = "Agrega un producto (por id y cantidad) al carrito de compras actual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto agregado correctamente", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del producto invalidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "El producto indicado no existe", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping
    public List<VentaProductoNombresDTO> agregarProducto(@RequestBody VentaProductoIdDTO producto){
        return service.agregarProducto(producto);
    }

    @Operation(summary = "Ver productos del carrito", description = "Devuelve la lista de productos que se encuentran actualmente en el carrito de compras")
    @ApiResponse(responseCode = "200", description = "Lista de productos en el carrito", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class)))
    @GetMapping
    public List<VentaProductoNombresDTO> verProductos(){
        return service.verProductos();
    }

    @Operation(summary = "Eliminar producto del carrito", description = "Elimina por completo un producto del carrito de compras segun su id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado del carrito", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en el carrito", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @DeleteMapping("/{id}")
    public VentaProductoNombresDTO eliminarProducto(@Parameter(description = "Id del producto dentro del carrito") @PathVariable int id){
        return service.eliminarProducto(id);
    }

    @Operation(summary = "Restar unidades de un producto del carrito", description = "Disminuye la cantidad de unidades de un producto ya agregado al carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unidades restadas correctamente", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class))),
            @ApiResponse(responseCode = "400", description = "Cantidad invalida", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en el carrito", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PatchMapping("/{id}")
    public VentaProductoNombresDTO eliminarUnidadesProducto(@Parameter(description = "Id del producto dentro del carrito") @PathVariable int id, @RequestBody VentaProductoIdDTO cantidad){
        return service.eliminarUnidadesProducto(id, cantidad);
    }

    @Operation(summary = "Actualizar unidades de un producto del carrito", description = "Actualiza la cantidad de unidades de un producto ya agregado al carrito")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Unidades actualizadas correctamente", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class))),
            @ApiResponse(responseCode = "400", description = "Cantidad invalida", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "El producto no existe en el carrito", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PutMapping("/{id}")
    public VentaProductoNombresDTO actualizarUnidadesProducto (@Parameter(description = "Id del producto dentro del carrito") @PathVariable int id, @RequestBody VentaProductoIdDTO cantidad){
        return service.actualizarUnidadesProducto(id, cantidad);
    }

    @Operation(summary = "Vaciar el carrito", description = "Elimina todos los productos actualmente presentes en el carrito de compras")
    @ApiResponse(responseCode = "200", description = "Carrito vaciado correctamente", content = @Content(schema = @Schema(implementation = VentaProductoNombresDTO.class)))
    @DeleteMapping
    public List<VentaProductoNombresDTO> vaciarCarrito(){
        return service.vaciarCarrito();
    }

    @Operation(summary = "Confirmar venta", description = "Confirma la venta con los productos del carrito actual usando el metodo de pago indicado, generando el registro de venta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta confirmada correctamente", content = @Content(schema = @Schema(implementation = VentaCompletaDTO.class))),
            @ApiResponse(responseCode = "400", description = "El carrito esta vacio o el metodo de pago es invalido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "No se pudo completar la venta por un conflicto de datos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PostMapping("/confirmar")
    public VentaCompletaDTO confirmarVenta(@RequestBody MetodoPagoDTO metodoPago){
        return service.confirmarVenta(metodoPago.getMetodoPago());
    }



}
