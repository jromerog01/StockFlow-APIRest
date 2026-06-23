package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Proveedor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProveedorRepository extends CrudRepository <Proveedor, Integer> {


}
