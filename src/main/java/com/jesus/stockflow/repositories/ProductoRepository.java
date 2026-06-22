package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository <Producto, Integer> {


}
