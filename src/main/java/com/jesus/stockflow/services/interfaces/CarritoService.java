package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;

import java.util.List;

public interface CarritoService {
    List<VentaProductoNombresDTO> agregarProducto(VentaProductoIdDTO nuevoProducto);
    List<VentaProductoNombresDTO> verProductos();
    VentaProductoNombresDTO eliminarProducto(int idProducto);
    VentaProductoNombresDTO eliminarUnidadesProducto(int id, VentaProductoIdDTO cantidad);
    VentaProductoNombresDTO actualizarUnidadesProducto (int id, VentaProductoIdDTO cantidad);
    List<VentaProductoNombresDTO> vaciarCarrito();
    VentaCompletaDTO confirmarVenta(MetodoPago metodoPago);




    }
