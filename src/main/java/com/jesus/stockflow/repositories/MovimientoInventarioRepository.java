package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovimientoInventarioRepository extends CrudRepository <MovimientoInventario, Integer> {

    List<MovimientoInventario> findByProductoNombreContainingIgnoreCase(String nombre);
    List<MovimientoInventario> findByProductoSku(String sku);
    List<MovimientoInventario> findByCantidadGreaterThanEqual(int cantidadIsGreaterThan);
    List<MovimientoInventario> findByCantidadLessThanEqual(int cantidadIsGreaterThan);
    List<MovimientoInventario> findByTipoMovimiento(TipoMovimiento tipoMovimiento);

}
