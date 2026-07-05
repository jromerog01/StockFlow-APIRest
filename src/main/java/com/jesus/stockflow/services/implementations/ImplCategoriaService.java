package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.dtos.CategoriaDTO;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.exceptions.NombreInvalidoException;
import com.jesus.stockflow.repositories.CategoriaRepository;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.capitalizarPalabra;
import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.validarPalabra;

@Service
public class ImplCategoriaService implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public List<CategoriaDTO> findAll() {
        List<Categoria> original = (List<Categoria>) repository.findAll();
        List<CategoriaDTO> mapeadas = new ArrayList<>();

        for (Categoria c : original){
            mapeadas.add(new CategoriaDTO(
                    c.getIdCategoria(),
                    c.getNombre()
            ));
        }

        return mapeadas;
    }

    @Override
    @Transactional
    public CategoriaDTO save(CategoriaDTO categoria) {
        if(validarPalabra(categoria.getNombre())){
            String nombreCapitalizado = capitalizarPalabra(categoria.getNombre());
            categoria.setNombre(nombreCapitalizado);

            return mapear(repository.save(new Categoria(categoria.getNombre())));
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

    @Transactional
    @Override
    public CategoriaDTO findByIdMapeada(int id){
        return mapear(findById(id));
    }

    @Override
    @Transactional
    public CategoriaDTO update(int id, CategoriaDTO nombre) {
        Categoria buscada = findById(id); //verificar que exista la categoria
        if (validarPalabra(nombre.getNombre())){
            String nombreCapitalizado = capitalizarPalabra(nombre.getNombre());
            buscada.setNombre(nombreCapitalizado);
            return mapear(repository.save(buscada));
        }

        throw new NombreInvalidoException("El nombre ingresado no puede estar vacio");
    }


    @Override
    @Transactional
    public CategoriaDTO delete(int id) {
        Categoria categoria = findById(id);

        if (productoRepository.existsByCategoria(categoria)){
            throw new CamposInvalidosException("No se puede eliminar la categoria '" + categoria.getNombre() +
                    "' porque tiene productos asociados");
        }

        repository.delete(categoria);
        return mapear(categoria);
    }

    public CategoriaDTO mapear(Categoria categoria){
        return new CategoriaDTO(categoria.getIdCategoria(), categoria.getNombre());
    }



}
