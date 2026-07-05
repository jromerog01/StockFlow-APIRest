package com.jesus.stockflow.services.implementations;

import com.jesus.stockflow.entities.MovimientoInventario;
import com.jesus.stockflow.entities.Producto;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioDTO;
import com.jesus.stockflow.entities.dtos.MovimientoInventarioResponseDTO;
import com.jesus.stockflow.entities.enums.TipoMovimiento;
import com.jesus.stockflow.exceptions.CamposInvalidosException;
import com.jesus.stockflow.exceptions.IdInvalidoException;
import com.jesus.stockflow.repositories.MovimientoInventarioRepository;
import com.jesus.stockflow.services.interfaces.MovimientoInventarioService;
import com.jesus.stockflow.services.interfaces.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImplMovimientoInventarioService implements MovimientoInventarioService {

    @Autowired
    private MovimientoInventarioRepository repository;

    @Autowired
    @Lazy
    private ProductoService productoService;

    @Override
    @Transactional
    public MovimientoInventarioResponseDTO registrar(MovimientoInventarioDTO movimiento) {
        if (movimiento.getCantidad() > 0){
            Producto producto = productoService.findById(movimiento.getIdProducto());
            MovimientoInventario movimientoInventario = new MovimientoInventario(
                    producto,
                    movimiento.getTipoMovimiento(),
                    movimiento.getCantidad()
            );

            if (movimiento.getTipoMovimiento().equals(TipoMovimiento.ENTRADA)){
                producto.setStock(producto.getStock() + movimiento.getCantidad());
                productoService.save(producto);
            } else {
                if (producto.getStock() >= movimiento.getCantidad()){
                    producto.setStock(producto.getStock() - movimiento.getCantidad());
                    productoService.save(producto);
                } else {
                    throw new CamposInvalidosException("La cantidad del movimiento supera el stock disponible");
                }
            }
            MovimientoInventario mv = repository.save(movimientoInventario);

            return new MovimientoInventarioResponseDTO(
                    mv.getIdMovimiento(),
                    producto.getNombre(),
                    producto.getSku(),
                    movimiento.getCantidad(),
                    movimiento.getTipoMovimiento(),
                    mv.getFecha()
                    );
        }

        throw new CamposInvalidosException("La cantidad del movimiento debe ser mayor a 0");
    }



    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findAll() {
        return mapear((List<MovimientoInventario>) repository.findAll());
    }


    private List<MovimientoInventarioResponseDTO> mapear(List<MovimientoInventario> lista){
        List<MovimientoInventarioResponseDTO> mapeados = new ArrayList<>();

        for (MovimientoInventario mv : lista){
            Producto p = mv.getProducto();
            mapeados.add(new MovimientoInventarioResponseDTO(
                    mv.getIdMovimiento(),
                    p.getNombre(),
                    p.getSku(),
                    mv.getCantidad(),
                    mv.getTipoMovimiento(),
                    mv.getFecha()
            ));
        }
        return mapeados;
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoInventarioResponseDTO findById(int id) {
        Optional<MovimientoInventario> movimiento = repository.findById(id);

        if(movimiento.isPresent()){
            Producto p = movimiento.get().getProducto();
            MovimientoInventario m = movimiento.get();
            return new MovimientoInventarioResponseDTO(
                    m.getIdMovimiento(),
                    p.getNombre(),
                    p.getSku(),
                    m.getCantidad(),
                    m.getTipoMovimiento(),
                    m.getFecha()
            );
        }
        throw new IdInvalidoException("El id del movimiento que quieres consultar no es valido, " +
                "no existe ningun movimiento con ese id");
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findByNombreContaining(String nombre) {
        return mapear(repository.findByProductoNombreContainingIgnoreCase(nombre));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findBySku(String sku) {
        return mapear(repository.findByProductoSku(sku));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findByCantidadGreaterThan(int cantidad) {
        return mapear(repository.findByCantidadGreaterThanEqual(cantidad));

    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findByCantidadLessThan(int cantidad) {
        return mapear(repository.findByCantidadLessThanEqual(cantidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> findByTipoMovimiento(TipoMovimiento tipoMovimiento) {
        return mapear(repository.findByTipoMovimiento(tipoMovimiento));

    }
}
