package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.dtos.CategoriaDTO;
import com.jesus.stockflow.exceptions.IdCategoriaInvalid;
import com.jesus.stockflow.exceptions.NombreInvalidoException;
import com.jesus.stockflow.repositories.CategoriaRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplCategoriaService implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Override
    @Transactional
    public List<Categoria> findAll() {
        return (List<Categoria>) repository.findAll();
    }

    @Override
    @Transactional
    public Categoria crearCategoria(Categoria categoria) {
        String nombre = categoria.getNombre().toLowerCase();


        return repository.save(categoria);
    }

    @Override
    @Transactional
    public Categoria findById(int id) {
        Optional<Categoria> categoria = repository.findById(id);

        if (categoria.isPresent()){
            return categoria.get();
        }

        throw new IdCategoriaInvalid("El id de la categoria que quieres buscar es invalido, no hay" +
                "ninguna categoria con ese id");
    }

    @Override
    @Transactional
    public int actualizarCategoria(int id, CategoriaDTO nombre) {
        findById(id);
        if (validarNombre(nombre.getNombre())){
            String nombreMinuscualas = nombre.getNombre().toLowerCase();
            return repository.actualizarCategoria(id, nombreMinuscualas);
        }

        throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
    }

    private boolean validarNombre(String nombre){
        if (nombre.isEmpty() || nombre.equals(" ")){
            throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
        }
        return true;
    }

    @Override
    @Transactional
    public Categoria eliminarCategoria(int id) {
        Categoria categoria = findById(id);
        repository.delete(categoria);
        return categoria;
    }
}
