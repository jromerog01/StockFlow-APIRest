package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoUpdateCantidadDTO;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.models.Carrito;
import com.jesus.stockflow.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private Carrito carrito;

    @Autowired
    private ProductoService productoService;

    // Pendiente: ver como optimizar para no iterar dos veces con el findById y en la segunda parte donde si existe
    public List<VentaProductoNombresDTO> agregarProducto(VentaProductoIdDTO nuevoProducto){
        if (nuevoProducto.getCantidad() < 1) throw new CamposInvalidosException("No puedes agregar menos de 1 unidad al carrito");

        Producto productoConsultado = productoService.findById(nuevoProducto.getIdProducto());

        // Para ver si tenemos stock del producto
        if(verificarStock(nuevoProducto, productoConsultado)){
            for (VentaProductoNombresDTO p : carrito.getProductos()){
                // Si ya existe el producto dentro del carrito
                if (p.getIdProducto() == nuevoProducto.getIdProducto()){
                    if (p.getCantidad() + nuevoProducto.getCantidad() <= productoConsultado.getStock()){
                        p.setCantidad(p.getCantidad() + nuevoProducto.getCantidad());
                        p.setSubtotal(BigDecimal.valueOf(p.getPrecioUnitario().doubleValue() * p.getCantidad()));
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
                            BigDecimal.valueOf(productoConsultado.getPrecio().doubleValue() * nuevoProducto.getCantidad()));

            carrito.getProductos().add(nuevo);
            return carrito.getProductos();
        }
        throw new CamposInvalidosException("No hay suficiente stock del producto solicitado");
    }


    public List<VentaProductoNombresDTO> verProductos(){
        return carrito.getProductos();
    }

    public VentaProductoNombresDTO eliminarProducto(int idProducto){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == idProducto){
                carrito.getProductos().remove(p);
                return p;
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");
    }

    public VentaProductoNombresDTO eliminarUnidadesProducto(int id, VentaProductoUpdateCantidadDTO cantidad){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == id){
                if (cantidad.getCantidad() <= p.getCantidad() && cantidad.getCantidad() >= 0){
                    if(cantidad.getCantidad() == p.getCantidad()){
                        return eliminarProducto(id);
                    }

                    p.setCantidad(p.getCantidad() - cantidad.getCantidad());
                    p.setSubtotal(BigDecimal.valueOf(p.getPrecioUnitario().doubleValue() * cantidad.getCantidad()));

                    return p;
                }
                throw new CamposInvalidosException("Las unidades a eliminar no pueden ser 0 ni mas de las que tienes agregadas al carrito");
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");

    }

    public VentaProductoNombresDTO actualizarUnidadesProducto (int id, VentaProductoUpdateCantidadDTO cantidad){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if (p.getIdProducto() == id){
               if (cantidad.getCantidad() >= 0 && cantidad.getCantidad() <= productoService.findById(id).getStock()){
                   if (cantidad.getCantidad() == 0){
                       return eliminarProducto(id);
                   }
                   p.setCantidad(cantidad.getCantidad());
                   p.setSubtotal(BigDecimal.valueOf(p.getPrecioUnitario().doubleValue() * cantidad.getCantidad()));

                   return p;
               }
               throw new CamposInvalidosException("La cantidad a actualizar debe ser menor o igual al stock disponible");
            }
        }
        throw new IdInvalidoException("El id del producto que ingresaste no existe en tu carrito");
    }

    public List<VentaProductoNombresDTO> vaciarCarrito(){
        carrito.getProductos().clear();
        return carrito.getProductos();
    }

    // es para no agregar n veces el mismo producto si es que ya se agrego antes al carrito
    private VentaProductoNombresDTO buscarProducto(int id){
        for (VentaProductoNombresDTO p : carrito.getProductos()){
            if(p.getIdProducto() == id){
                return p;
            }
        }
        return null;
    }


    private boolean verificarStock(VentaProductoIdDTO nuevoProducto, Producto productoConsultado){
        return nuevoProducto.getCantidad() <= productoConsultado.getStock();
    }


}
