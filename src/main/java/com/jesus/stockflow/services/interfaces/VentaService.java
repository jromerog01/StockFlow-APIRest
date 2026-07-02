package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;

import java.util.List;

public interface VentaService {

    VentaCompletaDTO confirmarVenta(VentaCompletaDTO venta);

    List<VentaCompletaDTO> findAll();

    VentaCompletaDTO findById(int id);

    List<VentaCompletaDTO> findByMetodoDePago(MetodoPago metodoPago);

    List<VentaCompletaDTO> findByNombreContaining(String nombre);

    List<VentaCompletaDTO> findBySku(String sku);

    }
