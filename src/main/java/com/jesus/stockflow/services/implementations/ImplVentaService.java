package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.VentaProducto;
import com.jesus.stockflow.entities.dtos.ConfirmarVentaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.repositories.ProductoRepository;
import com.jesus.stockflow.repositories.VentaRepository;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import com.jesus.stockflow.services.interfaces.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImplVentaService implements VentaService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaProductoService ventaProductoService;

    @Transactional
    public ConfirmarVentaDTO confirmarVenta(ConfirmarVentaDTO venta){
        // Precio sin iva
        BigDecimal subtotal = BigDecimal.valueOf(0);
        for (VentaProductoNombresDTO p : venta.getProductos()){
            subtotal = subtotal.add(p.getSubtotal());
        }

        // Precio con iva
        BigDecimal total = subtotal.multiply(BigDecimal.valueOf(1.16));

        venta.setSubtotal(subtotal);
        venta.setTotal(total);

        Venta ventaGuardada = repository.save(new Venta(venta.getMetodoPago(), subtotal, total));
        registrarProductosVenta(ventaGuardada, venta.getProductos());

        return venta;
    }

    @Transactional
    public void registrarProductosVenta(Venta venta, List<VentaProductoNombresDTO> productos){
        ventaProductoService.registrarVenta(venta, productos);
    }

    @Override
    public List<ConfirmarVentaDTO> findAll(){
        List<ConfirmarVentaDTO> todos = new ArrayList<>();
        List<Venta> ventas = (List<Venta>) repository.findAll();

        for (Venta v : ventas){
            todos.add(new ConfirmarVentaDTO(
                    v.getIdVenta(),
                    v.getMetodoPago(),
                    ventaProductoService.getProductosVenta(v.getIdVenta()),
                    v.getSubtotal(),
                    v.getTotal()
            ));
        }

        return todos;
    }

    @Override
    public ConfirmarVentaDTO findById(int id) {
        Optional<Venta> venta = repository.findById(id);

        if (venta.isPresent()){
            Venta venta1 = venta.get();
            return new ConfirmarVentaDTO(
                    venta1.getIdVenta(),
                    venta1.getMetodoPago(),
                    ventaProductoService.getProductosVenta(venta.get().getIdVenta()),
                    venta1.getSubtotal(),
                    venta1.getTotal()
            );
        }

        throw new CamposInvalidosException("El id de la venta es invalido, no existe alguna venta con ese id");
    }

    @Override
    public List<ConfirmarVentaDTO> findByMetodoDePago(MetodoPago metodoPago) {
        List<Venta> ventas = repository.findByMetodoPago(metodoPago);
        List<ConfirmarVentaDTO> mapeadas = new ArrayList<>();

        for (Venta v : ventas){
            mapeadas.add(new ConfirmarVentaDTO(
                    v.getIdVenta(),
                    v.getMetodoPago(),
                    ventaProductoService.getProductosVenta(v.getIdVenta()),
                    v.getSubtotal(),
                    v.getTotal()
            ));
        }

        return mapeadas;
    }
}
