package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoDescripcionDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.repositories.VentaProductoRepository;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImplVentaProductoService implements VentaProductoService {

    @Autowired
    private VentaProductoRepository repository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MovimientoInventarioService movimientoInventarioService;

    @Override
    @Transactional
    public void registrarVenta(Venta venta, List<VentaProductoNombresDTO> productos) {
        for (VentaProductoNombresDTO p : productos){
            Producto productoById = productoService.findById(p.getIdProducto());
            repository.save(new VentaProducto(
                    productoById,
                    venta,
                    p.getCantidad(),
                    p.getPrecioUnitario(),
                    p.getSubtotal()
            ));
            reducirStock(productoById, p.getCantidad());
        }
        registrarMovimientoInventario(productos);
    }

    @Override
    @Transactional
    public void reducirStock(Producto p, int cantidad){
        if (cantidad <= 0) throw new CamposInvalidosException("La cantidad no puede ser cero");
        if (cantidad > p.getStock()) throw new CamposInvalidosException("La cantidad no puede ser mayor al stock disponible");

        p.setStock(p.getStock() - cantidad);
        productoService.save(p);
    }

    @Transactional
    public List<VentaProductoNombresDTO> getProductosVenta(int idVenta){
        List<VentaProductoDescripcionDTO> todos = findByIdVenta(idVenta);
        List<VentaProductoNombresDTO> mapeados = new ArrayList<>();

        for (VentaProductoDescripcionDTO v : todos){
            mapeados.add(new VentaProductoNombresDTO(
                    v.getIdProducto(),
                    v.getSku(),
                    v.getNombreProducto(),
                    v.getCantidad(),
                    v.getPrecioUnitario(),
                    v.getSubtotal()
            ));
        }
        return mapeados;
    }

    @Override
    @Transactional
    public void registrarMovimientoInventario(List<VentaProductoNombresDTO> productos){
        for (VentaProductoNombresDTO p : productos){
            movimientoInventarioService.registrar(new MovimientoInventarioDTO(
                    p.getIdProducto(),
                    TipoMovimiento.SALIDA,
                    p.getCantidad()
            ));
        }
    }

    @Override
    public List<VentaProductoDescripcionDTO> findAll() {
        List<VentaProducto> todos = (List<VentaProducto>) repository.findAll();
        return mapear(todos);

    }

    @Override
    public VentaProductoDescripcionDTO findById(int id) {
        Optional<VentaProducto> ventaProducto = repository.findById(id);

        if (ventaProducto.isPresent()){
            VentaProducto v = ventaProducto.get();
            Venta venta = v.getVenta();
            Producto producto = v.getProducto();
            return new VentaProductoDescripcionDTO(
                    v.getIdVentaProducto(),
                    venta.getIdVenta(),
                    producto.getIdProducto(),
                    producto.getSku(),
                    producto.getNombre(),
                    v.getUnidades(),
                    v.getPrecioUnitario(),
                    v.getSubtotal()
            );
        }

        throw new IdInvalidoException("El id de la venta del producto que quieres no es valido");

    }

    @Override
    public List<VentaProductoDescripcionDTO> findByIdVenta(int idVenta) {
        List<VentaProducto> todos = repository.findByIdVenta(idVenta);
        return mapear(todos);

    }

    @Override
    public List<VentaProductoDescripcionDTO> findByIdProducto(int idProducto) {
        List<VentaProducto> todos = repository.findByIdProducto(idProducto);
        return mapear(todos);
    }

    @Override
    public List<VentaProductoDescripcionDTO> findBySku(String sku) {
        List<VentaProducto> todos = repository.findBySku(sku);
        return mapear(todos);
    }

    @Override
    public List<VentaProductoDescripcionDTO> findByNombreContaining(String nombre) {
        List<VentaProducto> todos = repository.findByNombreContaining(nombre);
        return mapear(todos);
    }

    @Override
    public List<VentaProductoDescripcionDTO> findByCantidadGreaterThan(int cantidad) {
        List<VentaProducto> todos = repository.findByCantidadGreaterThan(cantidad);
        return mapear(todos);
    }

    @Override
    public List<VentaProductoDescripcionDTO> findByCantidadLessThan(int cantidad) {
        List<VentaProducto> todos = repository.findByCantidadLessThan(cantidad);
        return mapear(todos);
    }

    private List<VentaProductoDescripcionDTO> mapear (List<VentaProducto> lista){
        List<VentaProductoDescripcionDTO> mapeado = new ArrayList<>();

        for (VentaProducto v : lista){
            Venta venta = v.getVenta();
            Producto producto = v.getProducto();
            mapeado.add(new VentaProductoDescripcionDTO(
                    v.getIdVentaProducto(),
                    venta.getIdVenta(),
                    producto.getIdProducto(),
                    producto.getSku(),
                    producto.getNombre(),
                    v.getUnidades(),
                    v.getPrecioUnitario(),
                    v.getSubtotal()

            ));
        }

        return mapeado;
    }
}
