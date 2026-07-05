package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Categoria;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Proveedor;
import com.jesus.stockflow.entities.dtos.*;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.services.interfaces.CategoriaService;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.ProveedorService;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    @Value("${umbralBajoStock}")
    private int umbralBajoStock;

    @Override
    @Transactional
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
    @Transactional
    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    @Override
    @Transactional
    public List<ProductoResponseDTO> findAll() {
        List<Producto> normales = repository.findAll();
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
    @Transactional
    public ProductoResponseDTO findByIdMapeado(int id) {
        Optional<Producto> producto = repository.findById(id);

        if (producto.isPresent()){
            return mapear(producto.get());
        }
        throw new IdInvalidoException("El id del producto ingresado no existe");
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
    @Transactional
    public ProductoResponseDTO findBySku(String sku) {
        if (validarSku(sku)){
            return mapear(repository.findBySku(sku));
        }
        throw new CamposInvalidosException("El sku ingresado no tiene el formato correcto");
    }

    @Override
    public List<ProductoResponseDTO> findByNombreContainingIgnoreCase(String nombre) {
        return mapearLista(repository.findByActivoTrueAndNombreContainingIgnoreCase(nombre));
    }

    @Override
    @Transactional
    public ProductoResponseDTO update(int id, ProductoUpdateRequestDTO producto) {

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
            return mapear(buscado);
        }

        throw new CamposInvalidosException("ALguno de los campos ingresados es invalido");
    }

    @Override
    @Transactional
    public ProductoResponseDTO activarDesactivarProducto(int id, boolean estado) {
        Producto buscado = findById(id);
        buscado.setActivo(estado);
        repository.save(buscado);
        return mapear(buscado);
    }

    @Override
    @Transactional
    public List<ProductoResponseDTO> findByStockIsLessThanEqual(boolean bajoStock) {
        if (bajoStock){
            return mapearLista(repository.findByActivoTrueAndStockLessThanEqual(umbralBajoStock));
        }
        return mapearLista(repository.findByActivoTrueAndStockGreaterThan(umbralBajoStock));
    }

    @Override
    public List<ProductoResponseDTO> findByActivo(boolean activo) {
        return mapearLista(repository.findByActivo(activo));
    }


    private boolean validarSku(String sku){
        if (sku.matches("^[A-Z0-9-]{3,50}$")){
            return true;
        }
        throw new CamposInvalidosException("El sku no tiene el formato requerido");
    }

    private ProductoResponseDTO mapear(Producto producto){
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getCategoria().getNombre(),
                producto.getProveedor().getNombre(),
                producto.getSku(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.isActivo());
    }

    private List<ProductoResponseDTO> mapearLista(List<Producto> productos){

        List<ProductoResponseDTO> mapeados = new ArrayList<>();

        for (Producto producto : productos){
            mapeados.add(mapear(producto));
        }

        return mapeados;


    }
}
