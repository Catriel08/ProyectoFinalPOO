
package com.mycompany.proyectofinalpoo;

public class Producto {

    private int IDProducto;
    private String nombre;
    private int cantidad;
    private int precio;
    private boolean estado;

    
    public Producto(int IDProducto, String nombre, int cantidad, int precio, boolean estado) {
        this.IDProducto = IDProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    public Producto() {
        this.IDProducto = 0;
        this.nombre = "";
        this.cantidad = 0;
        this.precio = 0;
        this.estado = false;
    }
    
    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "IdProducto=" + IDProducto +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                ", estado=" + estado +
                '}';
    }
}
