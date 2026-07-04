package com.jesus.stockflow.entities.dtos;

import com.jesus.stockflow.entities.enums.MetodoPago;

public class MetodoPagoDTO {
    private MetodoPago metodoPago;

    public MetodoPagoDTO(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
}
