package com.sebastianwebapp.Ejercicio2.domain;

import jakarta.validation.constraints.*;

public class Producto {

    @NotNull(message = "El ID no puede ser nulo")
    private int ID;

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 3, message = "El nombre debe tener al menos 3 letras")
    private String nombre;


    @DecimalMin(value = "0.01", message = "El precio debe ser al menos 0.01")
    @Digits(integer = 10, fraction = 2, message = "El precio no puede tener más de 2 decimales")
    private double precio;

    @NotBlank(message = "La categoria no puede estar vacio")
    @Size(min = 3, message = "La categoria debe tener al menos 3 letras")
    private String categoria;

    public Producto(int ID, String nombre, double precio, String categoria) {
        this.ID = ID;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
