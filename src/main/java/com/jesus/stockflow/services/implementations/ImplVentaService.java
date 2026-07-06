package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Venta;
import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.repositories.VentaRepository;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaProductoService;
import com.jesus.stockflow.services.interfaces.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Value("${iva}")
    private double iva;

    @Transactional
    public VentaCompletaDTO confirmarVenta(VentaCompletaDTO venta){
        // Precio sin iva
        BigDecimal subtotal = BigDecimal.valueOf(0);
        for (VentaProductoNombresDTO p : venta.getProductos()){
            subtotal = subtotal.add(p.getSubtotal());
        }

        // Precio con iva
        BigDecimal total = subtotal.multiply(BigDecimal.valueOf(iva)).setScale(2, RoundingMode.HALF_UP);

        venta.setSubtotal(subtotal);
        venta.setTotal(total);

        Venta ventaGuardada = repository.save(new Venta(venta.getMetodoPago(), subtotal, total));
        registrarProductosVenta(ventaGuardada, venta.getProductos());
        venta.setIdVenta(ventaGuardada.getIdVenta());

        return venta;
    }

    @Transactional
    public void registrarProductosVenta(Venta venta, List<VentaProductoNombresDTO> productos){
        ventaProductoService.registrarVenta(venta, productos);
    }

    @Override
    public List<VentaCompletaDTO> findAll(){
        List<VentaCompletaDTO> todos = new ArrayList<>();
        List<Venta> ventas = (List<Venta>) repository.findAll();

        for (Venta v : ventas){
            todos.add(new VentaCompletaDTO(
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
    public VentaCompletaDTO findById(int id) {
        Optional<Venta> venta = repository.findById(id);

        if (venta.isPresent()){
            Venta venta1 = venta.get();
            return new VentaCompletaDTO(
                    venta1.getIdVenta(),
                    venta1.getMetodoPago(),
                    ventaProductoService.getProductosVenta(venta.get().getIdVenta()),
                    venta1.getSubtotal(),
                    venta1.getTotal()
            );
        }

        throw new IdInvalidoException("El id de la venta es invalido, no existe alguna venta con ese id");
    }

    @Override
    public List<VentaCompletaDTO> findByMetodoDePago(MetodoPago metodoPago) {
        List<Venta> ventas = repository.findByMetodoPago(metodoPago);
        List<VentaCompletaDTO> mapeadas = new ArrayList<>();

        for (Venta v : ventas){
            mapeadas.add(new VentaCompletaDTO(
                    v.getIdVenta(),
                    v.getMetodoPago(),
                    ventaProductoService.getProductosVenta(v.getIdVenta()),
                    v.getSubtotal(),
                    v.getTotal()
            ));
        }

        return mapeadas;
    }

    @Override
    public List<VentaCompletaDTO> findByNombreContaining(String nombre) {
        List<VentaCompletaDTO> todos = findAll();
        List<VentaCompletaDTO> encontrados = new ArrayList<>();

        for(VentaCompletaDTO v : todos){
            for (VentaProductoNombresDTO p : v.getProductos()){
                if(p.getNombreProducto().toLowerCase().contains(nombre.toLowerCase())){
                    encontrados.add(v);
                    break;
                }
            }
        }

        return encontrados;
    }

    @Override
    public List<VentaCompletaDTO> findBySku(String sku) {
        List<VentaCompletaDTO> todos = findAll();
        List<VentaCompletaDTO> encontrados = new ArrayList<>();

        for(VentaCompletaDTO v : todos){
            for (VentaProductoNombresDTO p : v.getProductos()){
                if(p.getSku().equals(sku)){
                    encontrados.add(v);
                    break;
                }
            }
        }

        return encontrados;
    }
}
