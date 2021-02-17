package com.example.bravewoman;

public class ListProductos {
    public String nombreProducto;
    public String nombreTienda;

    public ListProductos() {
    }
    public ListProductos(String mNombreProducto, String mNombreNegocio) {
        this.nombreProducto = mNombreProducto;
        this.nombreTienda = mNombreNegocio;
    }

    public String getmNombreProducto() {
        return nombreProducto;
    }

    public void setmNombreProducto(String mNombreProducto) {
        this.nombreProducto = mNombreProducto;
    }

    public String getmNombreNegocio() {
        return nombreTienda;
    }

    public void setmNombreNegocio(String mNombreNegocio) {
        this.nombreTienda = mNombreNegocio;
    }
}
