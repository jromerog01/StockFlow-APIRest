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
        if(validarNombre(categoria.getNombre())){
            String nombreCapitalizado = capitalizarPalabra(categoria.getNombre());
            categoria.setNombre(nombreCapitalizado);

            return repository.save(categoria);
        }

        throw new NombreInvalidoException("El nombre que ingresaste es invalido, no puede ser vacio");
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
        findById(id); //verificar que exista la categoria
        if (validarNombre(nombre.getNombre())){
            String nombreCapitalizado = capitalizarPalabra(nombre.getNombre());
            return repository.actualizarCategoria(id, nombreCapitalizado);
        }

        throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
    }


    @Override
    @Transactional
    public Categoria eliminarCategoria(int id) {
        Categoria categoria = findById(id);
        repository.delete(categoria);
        return categoria;
    }

    private boolean validarNombre(String nombre){
        if (nombre.isEmpty() || nombre.equals(" ")){
            throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
        }
        return true;
    }

    private String capitalizarPalabra(String palabra){
        String resto = palabra.substring(1).toLowerCase();
        char primera = Character.toUpperCase(palabra.charAt(0));

        return primera + resto;
    }

}
