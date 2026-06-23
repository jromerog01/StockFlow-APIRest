package com.jesus.stockflow.repositories;

import com.jesus.stockflow.entities.Categoria;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {

    @Query("update Categoria c set c.nombre = ?2 where c.id_categoria = ?1")
    @Modifying
    int update(int id, String nombre);

}
