
package com.mycompany.proyectofinalpoo;

public class Venta
{
    private int IDVenta;
    private String metodoDePago;
    private String nombreCliente;
    private int dineroRecibido;
    private int dineroCambio;
    private int subtotalVenta;
    private int totalVenta;
    
    /**
     * Constructor de la clase Venta.
     * @param IDVenta > (int)Codigo de la venta realizada.
     * @param metodoDePago > (String) Metodo/medio de pago por el cual se pago la venta.
     * @param nombreCliente > (String) Nombre del cliente que paga.
     * @param dineroRecibido > (int) Cantidad de dinero que recibe el cajero/tendero por parte del cliente.
     * @param dineroCambio > (int) Cantidad de dinero a devolver al cliente.
     * @param subtotalVenta (int) Valor parcial de la venta.
     * @param totalVenta > (int) Valor total de la venta.
     */
    public Venta(int IDVenta, String metodoDePago, String nombreCliente, int dineroRecibido, int dineroCambio, int subtotalVenta, int totalVenta) {
        this.IDVenta = IDVenta;
        this.metodoDePago = metodoDePago;
        this.nombreCliente = nombreCliente;
        this.dineroRecibido = dineroRecibido;
        this.dineroCambio = dineroCambio;
        this.subtotalVenta = subtotalVenta;
        this.totalVenta = totalVenta;
    }
    
    /**
     * Constructor vacio de la clase Ventas
     */
    public Venta() 
    {
        this.IDVenta = 0;
        this.metodoDePago = "";
        this.nombreCliente = "";
        this.dineroRecibido = 0;
        this.dineroCambio = 0;
        this.subtotalVenta = 0;
        this.totalVenta = 0;
    }

    public int getIDVenta() 
    {
        return IDVenta;
    }

    public void setIDVenta(int IDVenta) 
    {
        this.IDVenta = IDVenta;
    }
    
    public String getMetodoDePago() 
    {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) 
    {
        this.metodoDePago = metodoDePago;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getDineroRecibido() {
        return dineroRecibido;
    }

    public void setDineroRecibido(int dineroRecibido) {
        this.dineroRecibido = dineroRecibido;
    }

    public int getDineroCambio() {
        return dineroCambio;
    }

    public void setDineroCambio(int dineroCambio) {
        this.dineroCambio = dineroCambio;
    }

    public int getSubtotalVenta() {
        return subtotalVenta;
    }

    public void setSubtotalVenta(int subtotalVenta) {
        this.subtotalVenta = subtotalVenta;
    }
    
    
    public int getTotalVenta() 
    {
        return totalVenta;
    }

    public void setTotalVenta(int totalVenta) 
    {
        this.totalVenta = totalVenta;
    }

    @Override
    public String toString() {
        return "Venta{" + "IDVenta=" + IDVenta + ", metodoDePago=" + metodoDePago + ", nombreCliente=" + nombreCliente + ", dineroRecibido=" + dineroRecibido + ", dineroCambio=" + dineroCambio + ", subtotalVenta=" + subtotalVenta + ", totalVenta=" + totalVenta + '}';
    }

    
}
