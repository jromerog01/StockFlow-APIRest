package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioResponseDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoInventarioService {

    MovimientoInventario registrar(MovimientoInventarioDTO movimiento);
    List<MovimientoInventarioResponseDTO> findAll();
    MovimientoInventarioResponseDTO findById(int id);
    List<MovimientoInventarioResponseDTO> findByNombreContaining(String nombre);
    List<MovimientoInventarioResponseDTO> findBySku(String sku);
    List<MovimientoInventarioResponseDTO> findByCantidadGreaterThan(int cantidad);
    List<MovimientoInventarioResponseDTO> findByCantidadLessThan(int cantidad);
    List<MovimientoInventarioResponseDTO> findByTipoMovimiento(TipoMovimiento tipoMovimiento);


}
