package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.exceptions.NombreInvalidoException;
import com.jesus.stockflow.repositories.CategoriaRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.capitalizarPalabra;
import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.validarPalabra;

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
    public Categoria save(Categoria categoria) {
        if(validarPalabra(categoria.getNombre())){
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

        throw new IdInvalidoException("El id de la categoria que quieres buscar es invalido, no hay" +
                "ninguna categoria con ese id");
    }

    @Override
    @Transactional
    public int update(int id, Categoria nombre) {
        findById(id); //verificar que exista la categoria
        if (validarPalabra(nombre.getNombre())){
            String nombreCapitalizado = capitalizarPalabra(nombre.getNombre());
            return repository.update(id, nombreCapitalizado);
        }

        throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
    }


    @Override
    @Transactional
    public Categoria delete(int id) {
        Categoria categoria = findById(id);
        repository.delete(categoria);
        return categoria;
    }



}
