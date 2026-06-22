package com.jesus.stockflow.entities;

import com.jesus.stockflow.entities.enums.MetodoPago;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_venta_seq")
    @SequenceGenerator(name = "id_venta_seq", sequenceName = "id_ventas_seq", allocationSize = 1)
    @Column(name = "id_venta")
    private int idVenta;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    private BigDecimal subtotal;
    private BigDecimal total;

    public Venta(MetodoPago metodoPago, BigDecimal subtotal, BigDecimal total) {
        this.metodoPago = metodoPago;
        this.subtotal = subtotal;
        this.total = total;
    }

    public Venta() {
    }

    @PrePersist
    public void fechaActual(){
        setFecha(LocalDateTime.now());
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
