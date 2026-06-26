package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.ConfirmarVentaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.repositories.VentaRepository;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import com.jesus.stockflow.services.interfaces.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImplVentaService implements VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaProductoService ventaProductoService;

    public ConfirmarVentaDTO confirmarVenta(ConfirmarVentaDTO venta){
        // Precio sin iva
        BigDecimal subtotal = BigDecimal.valueOf(0);
        for (VentaProductoNombresDTO p : venta.getProductos()){
            subtotal = subtotal.add(p.getSubtotal());
        }

        // Precio con iva
        BigDecimal total = subtotal.multiply(BigDecimal.valueOf(1.16));

        Venta ventaGuardada = repository.save(new Venta(venta.getMetodoPago(), subtotal, total));
        registrarProductosVenta(ventaGuardada, venta.getProductos());

        return venta;
    }

    public void registrarProductosVenta(Venta venta, List<VentaProductoNombresDTO> productos){
        ventaProductoService.registrarVenta(venta, productos);
    }




}
