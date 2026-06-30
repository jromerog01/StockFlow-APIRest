package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
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
            registrarMovimientoInventario(productos);
        }
    }

    @Transactional
    public void reducirStock(Producto p, int cantidad){
        if (cantidad <= 0) throw new CamposInvalidosException("La cantidad no puede ser cero");
        if (cantidad > p.getStock()) throw new CamposInvalidosException("La cantidad no puede ser mayor al stock disponible");

        p.setStock(p.getStock() - cantidad);
        productoService.save(p);
    }

    @Transactional
    public List<VentaProductoNombresDTO> getProductosVenta(int idVenta){
        List<VentaProductoNombresDTO> productos = new ArrayList<>();

        for(VentaProducto v : repository.findAll()){
            if (v.getVenta().getIdVenta() == idVenta){
                productos.add(new VentaProductoNombresDTO(
                        v.getProducto().getIdProducto(),
                        v.getProducto().getSku(),
                        v.getProducto().getNombre(),
                        v.getUnidades(),
                        v.getPrecioUnitario(),
                        v.getSubtotal()
                ));
            }
        }

        return productos;
    }

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



}
