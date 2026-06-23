package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.exceptions.NombreInvalidoException;
import com.jesus.stockflow.repositories.ProveedorRepository;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.capitalizarPalabra;
import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.validarPalabra;

@Service
public class ImplProveedorService implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public Proveedor save(Proveedor proveedor){
        if(validarPalabra(proveedor.getNombre()) && validarTelefono(proveedor.getTelefono()) && validarCorreo(proveedor.getCorreo())){
            proveedor.setNombre(capitalizarPalabra(proveedor.getNombre()));
            return repository.save(proveedor);
        }

        throw new NombreInvalidoException("Alguno o varios de los campos ingresados no son validos, " +
                "no se permiten caracteres vacios");
    }

    @Override
    public List<Proveedor> findAll() {
        return (List<Proveedor>) repository.findAll();
    }

    @Override
    public Proveedor findById(int id) {
        Optional<Proveedor> proveedor = repository.findById(id);

        if(proveedor.isPresent()){
            return proveedor.get();
        }
        throw new IdInvalidoException("No existe un proveedor con el id proporcionado, revisalo" +
                " e intenta nuevamente");
    }

    @Override
    public Proveedor update(int id, Proveedor proveedor) {
        Proveedor buscado = findById(id);

        if(validarPalabra(proveedor.getNombre()) && validarTelefono(proveedor.getTelefono()) && validarCorreo(proveedor.getCorreo())){
            buscado.setNombre(proveedor.getNombre());
            buscado.setTelefono(proveedor.getTelefono());
            buscado.setCorreo(proveedor.getCorreo());

            return repository.save(buscado);
        }

        throw new NombreInvalidoException("Alguno o varios de los campos ingresados no son validos, " +
                "no se permiten caracteres vacios");

    }

    @Override
    public Proveedor delete(int id) {
        Proveedor proveedor = findById(id);
        repository.delete(proveedor);
        return proveedor;
    }

    private boolean validarTelefono(String telefono){
        return telefono.matches("^[0-9]{10}$");
    }

    private boolean validarCorreo(String correo){
        return correo.matches("^[^@]+@[^@]+\\.[^@]{2,}$");
    }
}
