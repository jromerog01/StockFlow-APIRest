package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.VentaCompletaDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.enums.MetodoPago;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.models.Carrito;
import com.jesus.stockflow.services.interfaces.CarritoService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import com.jesus.stockflow.services.interfaces.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ImplCarritoService implements CarritoService {

    @Autowired
    private Carrito carrito;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @Override
    public List<VentaProductoNombresDTO> agregarProducto(VentaProductoIdDTO nuevoProducto){
        if (nuevoProducto.getCantidad() < 1) throw new CamposInvalidosException("No puedes agregar menos de 1 unidad al carrito");

        Producto productoConsultado = productoService.findById(nuevoProducto.getIdProducto());

        if (productoConsultado.isActivo()){
        // Para ver si tenemos stock del producto
            if(verificarStock(nuevoProducto, productoConsultado)){
                for (VentaProductoNombresDTO p : carrito.getProductos()){
                    // Si ya existe el producto dentro del carrito
                    if (p.getIdProducto() == nuevoProducto.getIdProducto()){
                        if (p.getCantidad() + nuevoProducto.getCantidad() <= productoConsultado.getStock()){
                            p.setCantidad(p.getCantidad() + nuevoProducto.getCantidad());
                            p.setSubtotal(p.getPrecioUnitario().multiply(BigDecimal.valueOf(p.getCantidad())));

                            return carrito.getProductos();
                        }
                        throw new CamposInvalidosException("No puedes agregar mas unidades del producto que quieres, no hay suficiente stock");
                    }
                }
                // No encontro el articulo, es decir, es un nuevo articulo
                VentaProductoNombresDTO nuevo = new VentaProductoNombresDTO(
                                productoConsultado.getIdProducto(),
                                productoConsultado.getSku(),
                                productoConsultado.getNombre(),
                                nuevoProducto.getCantidad(),
                                productoConsultado.getPrecio(),
                                productoConsultado.getPrecio().multiply(BigDecimal.valueOf(nuevoProducto.getCantidad()))
                );

                carrito.getProductos().add(nuevo);
                return carrito.getProductos();
            }
            throw new CamposInvalidosException("No hay suficiente stock del producto solicitado");
        }
        throw new CamposInvalidosException("No es posible agregar al carrito productos desactivados");
    }

    @Override
    public List<VentaProductoNombresDTO> verProductos(){
        return carrito.getProductos();
    }

    @Override
    public VentaProductoNombresDTO eliminarProducto(int idProducto){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == idProducto){
                carrito.getProductos().remove(p);
                return p;
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");
    }

    @Override
    public VentaProductoNombresDTO eliminarUnidadesProducto(int id, VentaProductoIdDTO cantidad){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == id){
                if (cantidad.getCantidad() <= p.getCantidad() && cantidad.getCantidad() >= 1){
                    if(cantidad.getCantidad() == p.getCantidad()){
                        return eliminarProducto(id);
                    }

                    p.setCantidad(p.getCantidad() - cantidad.getCantidad());
                    p.setSubtotal(p.getPrecioUnitario().multiply(BigDecimal.valueOf(p.getCantidad())));

                    return p;
                }
                throw new CamposInvalidosException("Las unidades a eliminar no pueden ser 0 ni mas de las que tienes agregadas al carrito");
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");

    }

    @Override
    public VentaProductoNombresDTO actualizarUnidadesProducto (int id, VentaProductoIdDTO cantidad){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == id){
               if (cantidad.getCantidad() >= 0 && cantidad.getCantidad() <= productoService.findById(id).getStock()){
                   if (cantidad.getCantidad() == 0){
                       return eliminarProducto(id);
                   }
                   p.setCantidad(cantidad.getCantidad());
                   p.setSubtotal(p.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad.getCantidad())));
                   return p;
               }
               throw new CamposInvalidosException("La cantidad a actualizar debe ser menor o igual al stock disponible");
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");
    }

    @Override
    public List<VentaProductoNombresDTO> vaciarCarrito(){
        carrito.getProductos().clear();
        return carrito.getProductos();
    }

    @Override
    @Transactional
    public VentaCompletaDTO confirmarVenta(MetodoPago metodoPago){
        if (!carrito.getProductos().isEmpty()) {
            VentaCompletaDTO venta = ventaService.confirmarVenta(new VentaCompletaDTO(metodoPago, carrito.getProductos()));
            vaciarCarrito();
            return venta;
        }
        throw new CamposInvalidosException("No puedes confirmar una venta con el carrito vacio");
    }

    private boolean verificarStock(VentaProductoIdDTO nuevoProducto, Producto productoConsultado){
        return nuevoProducto.getCantidad() <= productoConsultado.getStock();
    }




}
