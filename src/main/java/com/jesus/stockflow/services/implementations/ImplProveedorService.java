package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.entities.dtos.ProveedorDTO;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.exceptions.NombreInvalidoException;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.repositories.ProveedorRepository;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.capitalizarPalabra;
import static com.jesus.stockflow.services.implementations.MetodosAuxiliares.validarPalabra;

@Service
public class ImplProveedorService implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public ProveedorDTO save(ProveedorDTO proveedor){
        if(validarPalabra(proveedor.getNombre()) && validarTelefono(proveedor.getTelefono()) && validarCorreo(proveedor.getCorreo())){
            String nombre = capitalizarPalabra(proveedor.getNombre());
            return mapear(repository.save(new Proveedor(
                    nombre,
                    proveedor.getTelefono(),
                    proveedor.getCorreo()
            )));
        }

        throw new NombreInvalidoException("Alguno o varios de los campos ingresados no son validos, " +
                "no se permiten caracteres vacios");
    }

    @Override
    public List<ProveedorDTO> findAll() {
        List<Proveedor> proveedores = (List<Proveedor>) repository.findAll();
        List<ProveedorDTO> mapeados = new ArrayList<>();

        for (Proveedor p : proveedores){
            mapeados.add(new ProveedorDTO(
                    p.getIdProveedor(),
                    p.getNombre(),
                    p.getTelefono(),
                    p.getCorreo()
            ));
        }

        return mapeados;
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
    public ProveedorDTO findByIdMapeado(int id){
        return mapear(findById(id));
    }

    @Override
    @Transactional
    public ProveedorDTO update(int id, ProveedorDTO proveedor) {
        Proveedor buscado = findById(id);

        if(validarPalabra(proveedor.getNombre()) && validarTelefono(proveedor.getTelefono()) && validarCorreo(proveedor.getCorreo())){
            buscado.setNombre(capitalizarPalabra(proveedor.getNombre()));
            buscado.setTelefono(proveedor.getTelefono());
            buscado.setCorreo(proveedor.getCorreo());

            return mapear(repository.save(buscado));
        }

        throw new NombreInvalidoException("Alguno o varios de los campos ingresados no son validos, " +
                "no se permiten caracteres vacios");

    }

    @Override
    @Transactional
    public ProveedorDTO delete(int id) {
        Proveedor proveedor = findById(id);

        if (productoRepository.existsByProveedor(proveedor)){
            throw new CamposInvalidosException("No se puede eliminar el proveedor '" + proveedor.getNombre() +
                    "' porque tiene productos asociados");
        }

        repository.delete(proveedor);
        return mapear(proveedor);
    }

    private ProveedorDTO mapear(Proveedor p){
        return new ProveedorDTO(
                p.getIdProveedor(),
                p.getNombre(),
                p.getTelefono(),
                p.getCorreo()
        );
    }

    private boolean validarTelefono(String telefono){
        return telefono != null && telefono.matches("^[0-9]{10}$");
    }

    private boolean validarCorreo(String correo){
        return correo != null && correo.matches("^[^@]+@[^@]+\\.[^@]{2,}$");
    }
}
