package com.mycompany.proyectofinalpoo;

public class Factura
{
    private String idFactura;
    
    public Factura(String idFactura)
    {
        this.idFactura = idFactura;
    }
    
    public Factura() 
    {
        this.idFactura = "";
    }

    public String getIdFactura() 
    {
        return idFactura;
    }

    public void setIdFactura(String idFactura) 
    {
        this.idFactura = idFactura;
    }
}
