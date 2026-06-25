package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.repositories.MovimientoInventarioRepository;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImplMovimientoInventarioService implements MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository repository;

    @Autowired
    @Lazy
    private ProductoService productoService;

    @Override
    public MovimientoInventario registrar(MovimientoInventarioDTO movimiento) {
        if (movimiento.getCantidad() > 0){
            Producto producto = productoService.findById(movimiento.getIdProducto());
            MovimientoInventario movimientoInventario = new MovimientoInventario(
                    producto,
                    movimiento.getTipoMovimiento(),
                    movimiento.getCantidad()
            );

            repository.save(movimientoInventario);
            return movimientoInventario;
        }

        throw new CamposInvalidosException("La cantidad del movimiento debe ser mayor a 0");
    }



}
