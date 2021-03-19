package Includes;

import cuentaspagar.CRUD;
import cuentaspagar.Principal;
import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Facturas extends CRUD {

    private int n_factura;
    private final String r_social;
    private final Date fecha_creada;
    private final Date fecha_vencimiento;
    private final char tipopago;
    private final char statusf;
    private float total;

    public Facturas() {
        this.r_social = "Razon social";
        this.fecha_creada = null;
        this.fecha_vencimiento = null;
        this.tipopago = 'P';
        this.statusf = 'A';
        this.total = 0;
    }

    //Para accion de agregar
    public Facturas(String r_social, Date fecha_creada, Date fecha_vencimiento, char tipopago, char statusf) {
        this.r_social = r_social;
        this.fecha_creada = fecha_creada;
        this.fecha_vencimiento = fecha_vencimiento;
        this.tipopago = tipopago;
        this.statusf = statusf;
        this.total = 0;
    }

    //Para demas acciones Principalmente eliminar
    public Facturas(int n_factura, String r_social, Date fecha_creada, Date fecha_vencimiento, char tipopago, char statusf) {
        this.n_factura = n_factura;
        this.r_social = r_social;
        this.fecha_creada = fecha_creada;
        this.fecha_vencimiento = fecha_vencimiento;
        this.tipopago = tipopago;
        this.statusf = statusf;
    }

    public void obtenerProveedores(JComboBox cb) {
        try {
            String SQL = "SELECT razonsocial FROM proveedores ORDER BY rif ASC";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("razonsocial"));
            }
        } catch (Exception ex) {

        }
    }

    public int busquedaProveedor(String r_social) {

        String SQL = "SELECT rif FROM proveedores WHERE razonsocial = '" + r_social + "'";
        try {
            int rifproveedor = 0;
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);
            resultado.next();
            return rifproveedor = resultado.getInt("rif");
        } catch (Exception e) {

        }
        return -1;
    }

    @Override
    public void agregar() {

        int rifproveedor = this.busquedaProveedor(r_social);

        try {
            String SQL = "INSERT INTO facturas (rifproveedor, fecha_creada, fecha_vencimiento, tipopago, statusf, total) VALUES (?,?,?,?,?,?) ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setInt(1, rifproveedor);
            consulta.setDate(2, fecha_creada);
            consulta.setDate(3, fecha_vencimiento);
            consulta.setString(4, String.valueOf(tipopago));
            consulta.setString(5, String.valueOf(statusf));
            consulta.setFloat(6, total);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " Factura registrada exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public DefaultTableModel mostrar() {
        String[] titulos = {" Nro Factura ", " Fecha Creada ", " Fecha de Vencimiento ", " Tipo de Pago ", " Status de Factura "," Razon social "};
        String[] registros = new String[6];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = " SELECT  f.*, pro.razonsocial FROM facturas f, proveedores pro  "
                + " WHERE  pro.rif=f.rifproveedor AND f.statusf<>'P' "
                + " GROUP BY n_Factura,rif ORDER BY n_factura,rif ";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("n_factura");
                String FormatoFecha1 = resultados.getString("fecha_creada");
                String fecha1 = FormatoFecha1.replace("-", "/");
                registros[1] = fecha1;
                String FormatoFecha2 = resultados.getString("fecha_vencimiento");
                String fecha2 = FormatoFecha2.replace("-", "/");
                registros[2] = fecha2;
                registros[3] = resultados.getString("tipopago");
                registros[4] = resultados.getString("statusf");
                registros[5] = resultados.getString("razonsocial");
                tabla.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al mostrar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

        return tabla;
    }

    public DefaultTableModel mostrarFacturasPagadas() {
        String[] titulos = {" Nro Factura ", " Fecha Creada ", " Fecha de Vencimiento ", " Tipo de Pago ", " Status de Factura "," Razon social "};
        String[] registros = new String[6];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = " SELECT  f.*, pro.razonsocial FROM facturas f, proveedores pro  "
                + " WHERE  pro.rif=f.rifproveedor AND f.statusf='P' "
                + " GROUP BY n_Factura,rif ORDER BY n_factura,rif   ";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("n_factura");
                String FormatoFecha1 = resultados.getString("fecha_creada");
                String fecha1 = FormatoFecha1.replace("-", "/");
                registros[1] = fecha1;
                String FormatoFecha2 = resultados.getString("fecha_vencimiento");
                String fecha2 = FormatoFecha2.replace("-", "/");
                registros[2] = fecha2;
                registros[3] = resultados.getString("tipopago");
                registros[4] = resultados.getString("statusf");
                registros[5] = resultados.getString("razonsocial");
                tabla.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al mostrar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

        return tabla;
    }

    @Override
    public void actualizar() {

        int rifproveedor = this.busquedaProveedor(r_social);

        try {

            String SQL = "UPDATE facturas SET rifproveedor=?,fecha_creada=?,fecha_vencimiento=?,tipopago=? WHERE n_factura=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setInt(1, rifproveedor);
            consulta.setDate(2, fecha_creada);
            consulta.setDate(3, fecha_vencimiento);
            consulta.setString(4, String.valueOf(tipopago));
            consulta.setInt(5, n_factura);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " Factura actualizado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void actualizarPagada() {

        try {

            String SQL = "UPDATE facturas SET statusf='P' WHERE n_factura=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            
            consulta.setInt(1, n_factura);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " Factura Pagada con exito ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void eliminar() {
        String SQL = "DELETE FROM facturas WHERE n_factura=?";

        try {
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            consulta.setInt(1, n_factura);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Factura Eliminada exitosamente ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
    }

}
