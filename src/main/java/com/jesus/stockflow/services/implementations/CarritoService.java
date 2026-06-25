package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.VentaProductoIdDTO;
import com.jesus.stockflow.entities.dtos.VentaProductoNombresDTO;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.models.Carrito;
import com.jesus.stockflow.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService {

    @Autowired
    private Carrito carrito;

    @Autowired
    private ProductoService productoService;

    // Pendiente: ver como optimizar para no iterar dos veces con el findById y en la segunda parte donde si existe
    public List<VentaProductoIdDTO> agregarProducto(VentaProductoIdDTO nuevoProducto){

        Producto productoConsultado = productoService.findById(nuevoProducto.getIdProducto());

        if(verificarStock(nuevoProducto, productoConsultado)){
            VentaProductoIdDTO productoExistente = buscarProducto(nuevoProducto.getIdProducto());
            if (productoExistente == null){
                carrito.getProductos().add(nuevoProducto);
                return carrito.getProductos();
            }

            // Pendiente: Verificar que la cantidad que hay en el carrito de un cierto producto
            // no sobrepase el stock en la base de datos de dicho producto
            for (VentaProductoIdDTO p : carrito.getProductos()){
                if(p.getIdProducto() == nuevoProducto.getIdProducto()){
                    p.setCantidad(p.getCantidad() + nuevoProducto.getCantidad());
                }
            }
            return carrito.getProductos();
        }
        throw new CamposInvalidosException("No hay suficiente stock del producto solicitado");
    }


    public List<VentaProductoNombresDTO> verProductos(){
        List<VentaProductoNombresDTO> nombres = new ArrayList<>();
        for (VentaProductoIdDTO p : carrito.getProductos()){
            Producto producto = productoService.findById(p.getIdProducto());
            nombres.add(new VentaProductoNombresDTO(producto.getNombre(), p.getCantidad()));
        }

        return nombres;
    }


    // es para no agregar n veces el mismo producto si es que ya se agrego antes al carrito
    private VentaProductoIdDTO buscarProducto(int id){
        for (VentaProductoIdDTO p : carrito.getProductos()){
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
