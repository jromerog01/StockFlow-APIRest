package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.entities.dtos.ProductoRequestDTO;
import com.jesus.stockflow.entities.dtos.ProductoResponseDTO;
import com.jesus.stockflow.entities.dtos.ProductoUpdateRequestDTO;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.management.ThreadDumpEndpoint;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImplProductoService implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private CategoriaService categoriaService;


    @Override
    public ProductoResponseDTO save(ProductoRequestDTO producto) {
        if (validarSku(producto.getSku()) &&
                producto.getPrecio().compareTo(BigDecimal.ZERO) > 0 &&
                producto.getStock() >= 0 &&
                MetodosAuxiliares.validarPalabra(producto.getNombre())
            ){

            Proveedor proveedor = proveedorService.findById(producto.getIdProveedor());
            Categoria categoria = categoriaService.findById(producto.getIdCategoria());

            Producto nuevoProducto = new Producto(
                    categoria,
                    proveedor,
                    producto.getSku(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getStock()
            );
            repository.save(nuevoProducto);

            ProductoResponseDTO responseDTO = new ProductoResponseDTO(
                    nuevoProducto.getIdProducto(),
                    categoria.getNombre(),
                    proveedor.getNombre(),
                    producto.getSku(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getStock(),
                    nuevoProducto.isActivo()
            );

            return responseDTO;
        }

        throw new CamposInvalidosException("Alguno de los campos ingresados no tiene el formato correcto");
    }

    @Override
    public List<ProductoResponseDTO> findAll() {
        List<Producto> normales = (List<Producto>) repository.findAll();
        List<ProductoResponseDTO> mapeados = new ArrayList<>();

        for (Producto p : normales){
            mapeados.add(new ProductoResponseDTO(
                    p.getIdProducto(),
                    p.getCategoria().getNombre(),
                    p.getProveedor().getNombre(),
                    p.getSku(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock(),
                    p.isActivo()
            ));
        }

        return mapeados;
    }

    @Override
    public Producto findById(int id) {
        Optional<Producto> producto = repository.findById(id);

        if (producto.isPresent()){
            return producto.get();
        }
        throw new IdInvalidoException("El id del producto ingresado no existe");
    }

    @Override
    public Producto findBySku(String sku) {
        if (validarSku(sku)){
            return repository.findBySku(sku);
        }
        throw new CamposInvalidosException("El sku ingresado no tiene el formato correcto");
    }

    @Override
    public List<Producto> findByNombreContainingIgnoreCase(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Producto update(int id, ProductoUpdateRequestDTO producto) {

        if (validarSku(producto.getSku()) &&
                MetodosAuxiliares.validarPalabra(producto.getNombre()) &&
                producto.getPrecio().compareTo(BigDecimal.ZERO) > 0
        ) {
            Producto buscado = findById(id);
            Categoria categoria = categoriaService.findById(producto.getIdCategoria());
            Proveedor proveedor = proveedorService.findById(producto.getIdProveedor());

            buscado.setCategoria(categoria);
            buscado.setProveedor(proveedor);
            buscado.setSku(producto.getSku());
            buscado.setNombre(producto.getNombre());
            buscado.setPrecio(producto.getPrecio());

            repository.save(buscado);
            return buscado;
        }

        throw new CamposInvalidosException("ALguno de los campos ingresados es invalido");


    }

    private boolean validarSku(String sku){
        if (sku.matches("^[A-Z0-9-]{3,50}$")){
            return true;
        }
        throw new CamposInvalidosException("El sku no tiene el formato requerido");
    }
}
