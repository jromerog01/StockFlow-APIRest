package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.VentaProductoDescripcionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VentaProductoRepository extends CrudRepository <VentaProducto, Integer> {

    @Query("select v from VentaProducto v where v.venta.idVenta = ?1")
    List<VentaProducto> findByIdVenta(int idVenta);

    @Query("select v from VentaProducto v where v.producto.idProducto = ?1")
    List<VentaProducto> findByIdProducto(int idProducto);

    @Query("select v from VentaProducto v where v.producto.sku = ?1")
    List<VentaProducto> findBySku(String sku);

    @Query("select v from VentaProducto v where v.producto.nombre = ?1")
    List<VentaProducto> findByNombreContaining(String nombre);

    @Query("select v from VentaProducto v where v.unidades >= ?1")
    List<VentaProducto> findByCantidadGreaterThan(int unidades);

    @Query("select v from VentaProducto v where v.unidades <= ?1")
    List<VentaProducto> findByCantidadLessThan(int unidades);


}
