package com.jesus.stockflow.services.interfaces;

import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;

public interface MovimientoInventarioService {

    MovimientoInventario registrar(MovimientoInventarioDTO movimiento);
}
