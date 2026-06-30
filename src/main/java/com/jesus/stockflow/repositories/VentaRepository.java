package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.enums.MetodoPago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VentaRepository extends CrudRepository <Venta, Integer> {
    List<Venta> findByMetodoPago(MetodoPago metodoPago);

}
