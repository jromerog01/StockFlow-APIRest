package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends CrudRepository <Producto, Integer> {

    @Query("select p from Producto p where p.activo = true")
    List<Producto> findAll();

    Producto findBySku(String sku);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByStockIsLessThanEqual(int stockIsLessThan);
}
