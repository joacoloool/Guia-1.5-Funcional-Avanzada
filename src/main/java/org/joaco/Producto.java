package org.joaco;

public class Producto {
    private String nombre;
    private double precio;
    private String categoria;
    private int stock;
    // Constructor, Getters y Setters
    public Producto(String nombre, double precio, String categoria, int
            stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
    }

    public Producto(Producto producto,double precio) {
        nombre = producto.nombre;
       this.precio = precio;
        categoria = producto.categoria;
        stock = producto.stock;
    }

    //Generar Getters, Setters y metodo toString.


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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", stock=" + stock +
                '}';
    }
}
