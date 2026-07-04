package com.jesus.stockflow.entities.dtos;

public class ActivarDesactivarDTO {
    private boolean activo;

    public ActivarDesactivarDTO(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
