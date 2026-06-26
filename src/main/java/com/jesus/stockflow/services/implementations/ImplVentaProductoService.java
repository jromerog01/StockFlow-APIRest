package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.repositories.VentaProductoRepository;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplVentaProductoService implements VentaProductoService {

    @Autowired
    private VentaProductoRepository repository;

    @Autowired
    private ProductoService productoService;

    @Override
    public void registrarVenta(Venta venta, List<VentaProductoNombresDTO> productos) {
        for (VentaProductoNombresDTO p : productos){
            repository.save(new VentaProducto(
                    productoService.findById(p.getIdProducto()),
                    venta,
                    p.getCantidad(),
                    p.getPrecioUnitario(),
                    p.getSubtotal()
            ));
        }
    }
}
