package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends CrudRepository <Producto, Integer> {

    @Query("select p from Producto p where p.activo = true")
    List<Producto> findAll();

    Producto findBySku(String sku);
    List<Producto> findByActivoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Producto> findByActivoTrueAndStockLessThanEqual(int stockIsLessThan);
    List<Producto> findByActivoTrueAndStockGreaterThan(int stock);

    List<Producto> findByActivo(boolean activo);

    boolean existsByCategoria(Categoria categoria);
    boolean existsByProveedor(Proveedor proveedor);
}
