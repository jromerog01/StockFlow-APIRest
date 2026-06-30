package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.VentaProducto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VentaProductoRepository extends CrudRepository <VentaProducto, Integer> {
    List<VentaProducto> findByVentaIdVenta(int IdVenta);

}
