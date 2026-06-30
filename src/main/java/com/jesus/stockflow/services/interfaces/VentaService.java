package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.dtos.ConfirmarVentaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;

import java.util.List;

public interface VentaService {

    ConfirmarVentaDTO confirmarVenta(ConfirmarVentaDTO venta);
    List<ConfirmarVentaDTO> findAll();
    ConfirmarVentaDTO findById(int id);
    List<ConfirmarVentaDTO> findByMetodoDePago(MetodoPago metodoPago);
}
