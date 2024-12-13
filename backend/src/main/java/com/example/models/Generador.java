package com.example.models;

public class Generador {
    private int id;
    private Double coste;
    private Double consumoPorHora;
    private Double capacidadGeneracion;
    private String uso;
    private String modelo;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getCoste() {
        return this.coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public Double getConsumoPorHora() {
        return this.consumoPorHora;
    }

    public void setConsumoPorHora(Double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public Double getCapacidadGeneracion() {
        return this.capacidadGeneracion;
    }

    public void setCapacidadGeneracion(Double capacidadGeneracion) {
        this.capacidadGeneracion = capacidadGeneracion;
    }

    public String getUso() {
        return this.uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}


