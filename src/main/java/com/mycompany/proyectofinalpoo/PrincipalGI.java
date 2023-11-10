
package com.mycompany.proyectofinalpoo;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PrincipalGI extends javax.swing.JFrame 
{
    //formatos
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    String formato;
    
    //Busqueda de algun producto
    TableRowSorter<DefaultTableModel> sorter;
    
    //Diccionarios.
    Map<String, Producto> dicInventario;
    Map<String, Producto> dicVentas;
    
    //modelo de tabals
    DefaultTableModel modeloTablaInventario, modeloTablaVentas, modeloTablaHistorial;
    
    //Variables de clase
    private String contadorFormateado;
    private int IDProducto=0;
    private int numeroProducto = 0;
    private int subtotal = 0;
    int montoTotal = 0, IDVenta=0;
    private String montoTotalFormateado;


    public PrincipalGI() 
    {
        this.formato = "%03d";
        initComponents();
        
        //Creado los diccionarios
        dicInventario = new HashMap<>();
        dicVentas = new HashMap<>();
        
        //Seteando las fechas a los txt
        txt_fecha_tab_ventas.setText(fecha());
        txt_fecha_tab_inventario.setText(fecha());
        txt_fecha_tab_historial.setText(fecha());
        
        //Modelo y especificaciones de la Tabla de Historial
        modeloTablaHistorial = (DefaultTableModel) tablaHistorial.getModel();
        
        //Modelo y especificaciones de la Tabla de ventas
        modeloTablaVentas = (DefaultTableModel) tablaVentas.getModel();
        tablaVentas.getColumnModel().getColumn(1).setMaxWidth(100);
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaVentas.getColumnModel().getColumn(2).setMaxWidth(280);
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(280);
        
        //Modelo y especificaciones de la Tabla de Inventario
        modeloTablaInventario = (DefaultTableModel) tablaInventario.getModel();
        tablaInventario.getColumnModel().getColumn(0).setMaxWidth(100);
        tablaInventario.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaInventario.getColumnModel().getColumn(1).setMaxWidth(280);
        tablaInventario.getColumnModel().getColumn(1).setPreferredWidth(280);
        
        sorter = new TableRowSorter<>(modeloTablaInventario);
        tablaInventario.setRowSorter(sorter);
        
        //Cargando los productos predeterminados
        cargarProductosPredeterminados();
    }
    
    public String fecha()
    {
        Date fecha = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        return df.format(fecha);
    }
    
    //Metodos relacionados con el TabbedPane de Ventas.
    public int retornaNumeracionProductoVentas()
    {
        numeroProducto++;
        return numeroProducto;
    }
    
    public int retornaIDVenta()
    {
        IDVenta++;
        return IDVenta;
    }
    
    public void limpiarVenta() {
        montoTotal=0;
        txt_monto_total.setText(nf.format(montoTotal));
        modeloTablaVentas.setRowCount(0);
        txtNombreProducto.setText("");
        txt_cantidad.setText("");
        modeloTablaVentas.fireTableDataChanged();
    }
    
    //Metodos relacionados con el TabbedPane de Inventario.
    
    /**
     * Metodo que carga 4 productos predeterminados a la Tabla de Inventario.
     */
     //Metodos relacionados con el TabbedPane de Inventario.
    /**
     * Metodo que carga 4 productos predeterminados a la Tabla de Inventario.
     */
     public void cargarProductosPredeterminados()
     {
         while ( modeloTablaInventario.getRowCount()>0){
            modeloTablaInventario.removeRow(0);
         }
        agregarProductoDicInventario(retornaIDProducto(),"Frijol Bola Roja 500gr",100,10_500,true);
        agregarProductoDicInventario(retornaIDProducto(),"Lentejas Maritza premium 500gr",250,4_300,true);
        agregarProductoDicInventario(retornaIDProducto(),"Arroz del llano 500gr",301,2_300,true);
        agregarProductoDicInventario(retornaIDProducto(),"Azucar Incauca blanca 500gr",58,3_000,true);
        
     }
     
     public void agregarProductoDicInventario(int IDProducto, String nombre, int cantidad, int precio, boolean estado)
     {
        String contadorFormateado; 
        Producto pro = new Producto(IDProducto,  nombre,  cantidad,  precio,  estado);
        dicInventario.put(pro.getNombre(),pro);
        Object[] producto0 =  {contadorFormateado = String.format(formato, pro.getIDProducto()), pro.getNombre(), pro.getCantidad(), nf.format(pro.getPrecio()), retornarDisponibilidadProducto(pro.isEstado())};
        modeloTablaInventario.addRow(producto0);
        modeloTablaInventario.fireTableDataChanged();
     
     }
     
     public void buscarProducto(String buscar){
        sorter.setRowFilter(RowFilter.regexFilter(buscar));
    }
     
     /**
     * Metodo que permite el incremento del atributo IDProducto de la clase Producto.
     * @return > IDProducto incrementado en 1.
     */
    public int retornaIDProducto()
    {
        IDProducto++;
        return IDProducto;
    }
    
    /**
     * Metodo que cambia el visualmente el estado de la clase producto
     * @param estado > Estado del producto
     * @return > Si el estado es true, retorna un String que contiene la palabra "Disponible",  si estado es false, retona un String que contiene la palabra "No Disponible".
     */
    public String retornarDisponibilidadProducto(boolean estado)
    {
        String disponibilidad;
        
        if (estado)
        {
            disponibilidad = "Disponible";
        }
        else
        {
            disponibilidad = "No Disponible";
        }
        return disponibilidad;
    }
     
     public boolean actualizarTablaInventario(Map<String, Producto> diccionario)
     {
         boolean resultado =  true;
         Producto pinv;
         
         while (modeloTablaInventario.getRowCount()>0){
             modeloTablaInventario.removeRow(0);
         }
         
         for (Map.Entry<String, Producto> entry : diccionario.entrySet()) {
            pinv = entry.getValue();
            Object[] producto2 =  {contadorFormateado = String.format(formato, pinv.getIDProducto()), pinv.getNombre(), pinv.getCantidad(), pinv.getPrecio(), retornarDisponibilidadProducto(pinv.isEstado())};
            modeloTablaInventario.addRow(producto2);
            modeloTablaInventario.fireTableDataChanged();
        }
         return resultado;
     }
    
    
    /**
     * Metodo que verifica si el producto a añadir ya existe o no.
     * @param nombreProductoNuevo > Nombre del producto a añadir.
     * @return > true si el producto se encuentra en el diccionario de productos (existe), false si el producto no se encuentra en el diccionario de productos (no existe).
     */
    public boolean verificaProductoDuplicado(String nombreProductoNuevo)
    {
        boolean productoExistente = false;
        
        for (Map.Entry<String, Producto> entry : dicInventario.entrySet())
        {
            productoExistente = entry.getKey().equals(nombreProductoNuevo);
            
            if (productoExistente)
             {
                 break;
             }
        }
        
        return productoExistente;
    }
    
    public int cantidadExistencia(int idProducto)
    {
        int total =0;
        for (int i = 0; i < modeloTablaInventario.getRowCount(); i++) {
            
            if (Integer.parseInt(modeloTablaInventario.getValueAt(i, 0).toString())==idProducto) {
                total=Integer.parseInt(modeloTablaInventario.getValueAt(i, 2).toString());
            }
            
        }
        
        return total;
    }
    
    public void actualizarDiccionario(Map<String, Producto> diccionario)
    {
        this.dicInventario = diccionario;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_fondo = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnl_ventas = new javax.swing.JPanel();
        panel_parte_superior = new javax.swing.JPanel();
        nombre_negocio = new javax.swing.JLabel();
        imagenNegocio = new javax.swing.JLabel();
        panel_fecha = new javax.swing.JPanel();
        txt_fecha_tab_ventas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        btt_agregar_codigo_del_producto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_cantidad = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txt_monto_total = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        bttCobrar = new javax.swing.JButton();
        bttCierreCaja = new javax.swing.JButton();
        bttEliminarP = new javax.swing.JButton();
        pnl_inventario = new javax.swing.JPanel();
        panel_parte_superior1 = new javax.swing.JPanel();
        nombre_negocio1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panel_fecha1 = new javax.swing.JPanel();
        txt_fecha_tab_inventario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaInventario = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        bttAddProducto = new javax.swing.JButton();
        bttEliminarProducto = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        panel_parte_superior2 = new javax.swing.JPanel();
        nombre_negocio2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        panel_fecha2 = new javax.swing.JPanel();
        txt_fecha_tab_historial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaHistorialVentas = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_buscar_venta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_fondo.setBackground(new java.awt.Color(255, 255, 255));
        pnl_fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        pnl_ventas.setBackground(new java.awt.Color(255, 255, 255));

        panel_parte_superior.setBackground(new java.awt.Color(255, 255, 255));
        panel_parte_superior.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        nombre_negocio.setFont(new java.awt.Font("AR CENA", 1, 36)); // NOI18N
        nombre_negocio.setText("PLACITA EL MONO");

        panel_fecha.setBackground(new java.awt.Color(255, 255, 255));
        panel_fecha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14))); // NOI18N
        panel_fecha.setLayout(new java.awt.GridLayout(1, 0));

        txt_fecha_tab_ventas.setEditable(false);
        txt_fecha_tab_ventas.setBackground(new java.awt.Color(0, 0, 153));
        txt_fecha_tab_ventas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txt_fecha_tab_ventas.setForeground(new java.awt.Color(255, 255, 255));
        txt_fecha_tab_ventas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha_tab_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fecha_tab_ventasActionPerformed(evt);
            }
        });
        panel_fecha.add(txt_fecha_tab_ventas);

        jLabel2.setFont(new java.awt.Font("AR CENA", 0, 18)); // NOI18N
        jLabel2.setText("Carrera 43 #83-03 Sur brr. Villa Juliana");

        javax.swing.GroupLayout panel_parte_superiorLayout = new javax.swing.GroupLayout(panel_parte_superior);
        panel_parte_superior.setLayout(panel_parte_superiorLayout);
        panel_parte_superiorLayout.setHorizontalGroup(
            panel_parte_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenNegocio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addGroup(panel_parte_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombre_negocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        panel_parte_superiorLayout.setVerticalGroup(
            panel_parte_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superiorLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_parte_superiorLayout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(panel_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(panel_parte_superiorLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(nombre_negocio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel_parte_superiorLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(imagenNegocio, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 102, 255));

        jLabel7.setFont(new java.awt.Font("Roboto", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("VENTA DE PRODUCTOS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setText("Nombre del producto");
        jPanel4.add(jLabel3);

        txtNombreProducto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyPressed(evt);
            }
        });
        jPanel4.add(txtNombreProducto);

        btt_agregar_codigo_del_producto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btt_agregar_codigo_del_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btt_agregar_codigo_del_productoActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(207, 23));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Cantidad del producto:  ");
        jPanel2.add(jLabel4);

        txt_cantidad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jPanel2.add(txt_cantidad);

        jPanel13.setBackground(new java.awt.Color(0, 0, 153));
        jPanel13.setPreferredSize(new java.awt.Dimension(980, 30));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        tablaVentas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. de producto", "Codigo del producto", "Nombre del producto", "Cantidad del producto", "Valor unitario", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablaVentas);

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        txt_monto_total.setEditable(false);
        txt_monto_total.setBackground(new java.awt.Color(153, 153, 255));
        txt_monto_total.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        txt_monto_total.setForeground(new java.awt.Color(0, 0, 153));
        txt_monto_total.setText("$");
        txt_monto_total.setBorder(null);
        txt_monto_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_monto_totalActionPerformed(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.setBackground(new java.awt.Color(153, 153, 255));
        jTextField3.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 0, 153));
        jTextField3.setText("COP");
        jTextField3.setBorder(null);

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(153, 153, 255));
        jTextField4.setFont(new java.awt.Font("Roboto", 3, 24)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setText("TOTAL A PAGAR:");
        jTextField4.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txt_monto_total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(61, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_monto_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        bttCobrar.setBackground(new java.awt.Color(255, 102, 0));
        bttCobrar.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        bttCobrar.setText("Cobrar");
        bttCobrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttCobrarActionPerformed(evt);
            }
        });

        bttCierreCaja.setText("Cierre de caja");
        bttCierreCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttCierreCajaActionPerformed(evt);
            }
        });

        bttEliminarP.setText("Eliminar Producto");
        bttEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttEliminarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_ventasLayout = new javax.swing.GroupLayout(pnl_ventas);
        pnl_ventas.setLayout(pnl_ventasLayout);
        pnl_ventasLayout.setHorizontalGroup(
            pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_parte_superior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
            .addGroup(pnl_ventasLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btt_agregar_codigo_del_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnl_ventasLayout.createSequentialGroup()
                .addGroup(pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_ventasLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(bttEliminarP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bttCierreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(bttCobrar)
                        .addGap(46, 46, 46)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ventasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnl_ventasLayout.setVerticalGroup(
            pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_ventasLayout.createSequentialGroup()
                .addComponent(panel_parte_superior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btt_agregar_codigo_del_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_ventasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_ventasLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_ventasLayout.createSequentialGroup()
                                .addComponent(bttEliminarP)
                                .addGap(45, 45, 45))))
                    .addGroup(pnl_ventasLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnl_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bttCierreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bttCobrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", pnl_ventas);

        pnl_inventario.setBackground(new java.awt.Color(255, 255, 255));

        panel_parte_superior1.setBackground(new java.awt.Color(255, 255, 255));
        panel_parte_superior1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        nombre_negocio1.setFont(new java.awt.Font("AR CENA", 1, 36)); // NOI18N
        nombre_negocio1.setText("PLACITA EL MONO");

        panel_fecha1.setBackground(new java.awt.Color(255, 255, 255));
        panel_fecha1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14))); // NOI18N
        panel_fecha1.setLayout(new java.awt.GridLayout(1, 0));

        txt_fecha_tab_inventario.setEditable(false);
        txt_fecha_tab_inventario.setBackground(new java.awt.Color(0, 0, 153));
        txt_fecha_tab_inventario.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txt_fecha_tab_inventario.setForeground(new java.awt.Color(255, 255, 255));
        txt_fecha_tab_inventario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha_tab_inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fecha_tab_inventarioActionPerformed(evt);
            }
        });
        panel_fecha1.add(txt_fecha_tab_inventario);

        jLabel6.setFont(new java.awt.Font("AR CENA", 0, 18)); // NOI18N
        jLabel6.setText("Carrera 43 #83-03 Sur brr. Villa Juliana");

        javax.swing.GroupLayout panel_parte_superior1Layout = new javax.swing.GroupLayout(panel_parte_superior1);
        panel_parte_superior1.setLayout(panel_parte_superior1Layout);
        panel_parte_superior1Layout.setHorizontalGroup(
            panel_parte_superior1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superior1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel_parte_superior1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(nombre_negocio1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(136, 136, 136)
                .addComponent(panel_fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );
        panel_parte_superior1Layout.setVerticalGroup(
            panel_parte_superior1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superior1Layout.createSequentialGroup()
                .addGroup(panel_parte_superior1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nombre_negocio1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_parte_superior1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(panel_fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel9.setBackground(new java.awt.Color(0, 102, 255));

        jLabel8.setFont(new java.awt.Font("Roboto", 3, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("INVENTARIO DE PRODUCTOS");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setText("Buscar producto:  ");
        jPanel1.add(jLabel9);

        txt_buscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel1.add(txt_buscar);

        jPanel14.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 992, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        tablaInventario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tablaInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre del producto", "Cantidad del producto", "Valor unitario", "Estado del producto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaInventario.setSelectionBackground(new java.awt.Color(51, 51, 255));
        tablaInventario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaInventario.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablaInventario);
        if (tablaInventario.getColumnModel().getColumnCount() > 0) {
            tablaInventario.getColumnModel().getColumn(0).setResizable(false);
            tablaInventario.getColumnModel().getColumn(1).setResizable(false);
            tablaInventario.getColumnModel().getColumn(2).setResizable(false);
            tablaInventario.getColumnModel().getColumn(3).setResizable(false);
            tablaInventario.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel15.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 986, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto Black", 1, 14))); // NOI18N
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        bttAddProducto.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        bttAddProducto.setText("Añadir producto");
        bttAddProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttAddProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttAddProductoMouseClicked(evt);
            }
        });
        bttAddProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttAddProductoActionPerformed(evt);
            }
        });
        jPanel5.add(bttAddProducto);

        bttEliminarProducto.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        bttEliminarProducto.setText("Eliminar Producto");
        bttEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttEliminarProductoActionPerformed(evt);
            }
        });
        jPanel5.add(bttEliminarProducto);

        javax.swing.GroupLayout pnl_inventarioLayout = new javax.swing.GroupLayout(pnl_inventario);
        pnl_inventario.setLayout(pnl_inventarioLayout);
        pnl_inventarioLayout.setHorizontalGroup(
            pnl_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_parte_superior1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_inventarioLayout.createSequentialGroup()
                .addGroup(pnl_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_inventarioLayout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_inventarioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_inventarioLayout.setVerticalGroup(
            pnl_inventarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_inventarioLayout.createSequentialGroup()
                .addComponent(panel_parte_superior1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 70, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventario", pnl_inventario);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        panel_parte_superior2.setBackground(new java.awt.Color(255, 255, 255));
        panel_parte_superior2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        nombre_negocio2.setFont(new java.awt.Font("AR CENA", 1, 36)); // NOI18N
        nombre_negocio2.setText("PLACITA EL MONO");

        panel_fecha2.setBackground(new java.awt.Color(255, 255, 255));
        panel_fecha2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 14))); // NOI18N
        panel_fecha2.setLayout(new java.awt.GridLayout(1, 0));

        txt_fecha_tab_historial.setEditable(false);
        txt_fecha_tab_historial.setBackground(new java.awt.Color(0, 0, 153));
        txt_fecha_tab_historial.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txt_fecha_tab_historial.setForeground(new java.awt.Color(255, 255, 255));
        txt_fecha_tab_historial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha_tab_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fecha_tab_historialActionPerformed(evt);
            }
        });
        panel_fecha2.add(txt_fecha_tab_historial);

        jLabel11.setFont(new java.awt.Font("AR CENA", 0, 18)); // NOI18N
        jLabel11.setText("Carrera 43 #83-03 Sur brr. Villa Juliana");

        javax.swing.GroupLayout panel_parte_superior2Layout = new javax.swing.GroupLayout(panel_parte_superior2);
        panel_parte_superior2.setLayout(panel_parte_superior2Layout);
        panel_parte_superior2Layout.setHorizontalGroup(
            panel_parte_superior2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superior2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addGroup(panel_parte_superior2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombre_negocio2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panel_fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        panel_parte_superior2Layout.setVerticalGroup(
            panel_parte_superior2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_parte_superior2Layout.createSequentialGroup()
                .addGroup(panel_parte_superior2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_parte_superior2Layout.createSequentialGroup()
                        .addComponent(nombre_negocio2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(panel_parte_superior2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panel_fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 102, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 3, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("HISTORIAL DE VENTAS");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel12))
        );

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        tablaHistorialVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "No. de productos", "Cantidad de produtos", "Nombre del cliente", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaHistorialVentas.setEnabled(false);
        jScrollPane3.setViewportView(tablaHistorialVentas);

        jPanel16.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 986, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(0, 0, 153));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 992, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setText("Buscar Venta:  ");
        jPanel7.add(jLabel13);

        txt_buscar_venta.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txt_buscar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscar_ventaActionPerformed(evt);
            }
        });
        txt_buscar_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscar_ventaKeyReleased(evt);
            }
        });
        jPanel7.add(txt_buscar_venta);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_parte_superior2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(304, 304, 304)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(356, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(panel_parte_superior2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(129, 129, 129)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(547, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Historial", jPanel6);

        pnl_fondo.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 705, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_fecha_tab_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fecha_tab_ventasActionPerformed

    }//GEN-LAST:event_txt_fecha_tab_ventasActionPerformed

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed

    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void txtNombreProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyPressed

    }//GEN-LAST:event_txtNombreProductoKeyPressed

    private void btt_agregar_codigo_del_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btt_agregar_codigo_del_productoActionPerformed
        String nombreProducto = txtNombreProducto.getText();
        int cantidad = Integer.parseInt(txt_cantidad.getText());
        Producto productoInventario = dicInventario.get(nombreProducto);
        
         if(nombreProducto.equals("") || txt_cantidad.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Verifique el nombre y la cantidad.","ERROR",JOptionPane.WARNING_MESSAGE);
            txtNombreProducto.setText("");
            txt_cantidad.setText("");
        }
        else if(!dicInventario.containsKey(nombreProducto))
        {
            JOptionPane.showMessageDialog(null, "El producto no existe en el inventario","ERROR",JOptionPane.WARNING_MESSAGE);
        }
        else if (cantidad > productoInventario.getCantidad())
        {
            JOptionPane.showMessageDialog(null, "La cantidad excede a las unidades existentes","ERROR",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            if(dicVentas.containsKey(nombreProducto))
            {
                Producto p = dicVentas.get(nombreProducto);
                p.setCantidad(p.getCantidad()+cantidad);
                subtotal = p.getPrecio()*p.getCantidad();
                
            
            for (int i = 0; i < modeloTablaVentas.getRowCount(); i++) 
                {
                    if (Integer.parseInt(modeloTablaVentas.getValueAt(i, 1).toString())==p.getIDProducto()) 
                    {
                        modeloTablaVentas.removeRow(i);
                        Object[] productoVenta = {i+1, contadorFormateado = String.format(formato,p.getIDProducto()), 
                            p.getNombre(), p.getCantidad(), nf.format(p.getPrecio()), subtotal};
                        modeloTablaVentas.insertRow(i,productoVenta);
                        modeloTablaVentas.fireTableDataChanged();
                        
                        break;
                    }
                }
            }
            else
            {
                Producto productoModificado = new Producto(productoInventario.getIDProducto(),productoInventario.getNombre(), cantidad,
                    productoInventario.getPrecio(), productoInventario.isEstado()); 
                
                dicVentas.put(nombreProducto, productoModificado);
                Producto p = dicVentas.get(nombreProducto);;
                subtotal = p.getPrecio()*cantidad;
                
                Object[] productoVenta = {retornaNumeracionProductoVentas(),contadorFormateado = String.format(formato,p.getIDProducto()), 
                    p.getNombre(), cantidad, nf.format(p.getPrecio()), subtotal};
                
                modeloTablaVentas.addRow(productoVenta);
                modeloTablaVentas.fireTableDataChanged();
                
            }
                Producto pinv = dicInventario.get(nombreProducto);
                pinv.setCantidad(pinv.getCantidad()-cantidad);
                        
                for (int i = 0; i < modeloTablaInventario.getRowCount(); i++) {
                    if  (modeloTablaInventario.getValueAt(i, 1).toString().equals(pinv.getNombre()))
                    {
                        modeloTablaInventario.removeRow(i);
                        Object[] producto0 =  {contadorFormateado = String.format(formato, pinv.getIDProducto()), pinv.getNombre(), pinv.getCantidad(),nf.format(pinv.getPrecio()), retornarDisponibilidadProducto(pinv.isEstado())};
                        modeloTablaInventario.addRow(producto0);
                        modeloTablaInventario.fireTableDataChanged();
                    }
                }
                
                for (int i = 0; i < modeloTablaVentas.getRowCount(); i++)
                {
                    montoTotal += Integer.parseInt(modeloTablaVentas.getValueAt(i, 5).toString());
                }
                
                txt_monto_total.setText(nf.format(montoTotal));
        }
    }//GEN-LAST:event_btt_agregar_codigo_del_productoActionPerformed

    private void txt_monto_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_monto_totalActionPerformed
        
    }//GEN-LAST:event_txt_monto_totalActionPerformed

    private void bttCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttCobrarActionPerformed
        bttCobrar.setText(txt_monto_total.getText());
        JOptionPane.showMessageDialog(null, "El valor total de la compra es de: " + bttCobrar.getText());
    }//GEN-LAST:event_bttCobrarActionPerformed

    private void txt_fecha_tab_inventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fecha_tab_inventarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fecha_tab_inventarioActionPerformed

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed

    }//GEN-LAST:event_txt_buscarActionPerformed

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased

    }//GEN-LAST:event_txt_buscarKeyReleased

    private void bttAddProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttAddProductoActionPerformed
        addProducto add = new addProducto();
        add.copiarDiccionario(dicInventario);
        add.setVisible(true);
    }//GEN-LAST:event_bttAddProductoActionPerformed

    private void bttEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttEliminarProductoActionPerformed
       
    }//GEN-LAST:event_bttEliminarProductoActionPerformed

    private void txt_fecha_tab_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fecha_tab_historialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fecha_tab_historialActionPerformed

    private void txt_buscar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscar_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscar_ventaActionPerformed

    private void txt_buscar_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_ventaKeyReleased
        
    }//GEN-LAST:event_txt_buscar_ventaKeyReleased

    private void bttCierreCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttCierreCajaActionPerformed
       
    }//GEN-LAST:event_bttCierreCajaActionPerformed

    private void bttAddProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttAddProductoMouseClicked
        
    }//GEN-LAST:event_bttAddProductoMouseClicked

    private void bttEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttEliminarPActionPerformed

         String nombreProducto = txtNombreProducto.getText();
         int cantidad = Integer.parseInt(txt_cantidad.getText());
        //Producto productoInventario = dicInventario.get(nombreProducto);
         
         Producto pinv = dicInventario.get(nombreProducto);
         pinv.setCantidad(pinv.getCantidad()+cantidad);
                
            for (int i = 0; i < modeloTablaInventario.getRowCount(); i++) {
                    if  (Integer.parseInt(modeloTablaInventario.getValueAt(i, 0).toString())==pinv.getIDProducto())
                    {
                        modeloTablaInventario.removeRow(i);
                        Object[] producto0 =  {contadorFormateado = String.format(formato, pinv.getIDProducto()), pinv.getNombre(), pinv.getCantidad(), pinv.getPrecio(), retornarDisponibilidadProducto(pinv.isEstado())};
                        modeloTablaInventario.addRow(producto0);
                    }
                }
            dicVentas.remove(nombreProducto);
            modeloTablaVentas.removeRow(tablaVentas.getSelectedRow());
    }//GEN-LAST:event_bttEliminarPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalGI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalGI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalGI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalGI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalGI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttAddProducto;
    private javax.swing.JButton bttCierreCaja;
    private javax.swing.JButton bttCobrar;
    private javax.swing.JButton bttEliminarP;
    private javax.swing.JButton bttEliminarProducto;
    public javax.swing.JButton btt_agregar_codigo_del_producto;
    private javax.swing.JLabel imagenNegocio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel nombre_negocio;
    private javax.swing.JLabel nombre_negocio1;
    private javax.swing.JLabel nombre_negocio2;
    private javax.swing.JPanel panel_fecha;
    private javax.swing.JPanel panel_fecha1;
    private javax.swing.JPanel panel_fecha2;
    private javax.swing.JPanel panel_parte_superior;
    private javax.swing.JPanel panel_parte_superior1;
    private javax.swing.JPanel panel_parte_superior2;
    private javax.swing.JPanel pnl_fondo;
    private javax.swing.JPanel pnl_inventario;
    private javax.swing.JPanel pnl_ventas;
    public javax.swing.JTable tablaHistorialVentas;
    public javax.swing.JTable tablaInventario;
    public javax.swing.JTable tablaVentas;
    public javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_buscar_venta;
    public javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_fecha_tab_historial;
    private javax.swing.JTextField txt_fecha_tab_inventario;
    private javax.swing.JTextField txt_fecha_tab_ventas;
    public javax.swing.JTextField txt_monto_total;
    // End of variables declaration//GEN-END:variables
}
