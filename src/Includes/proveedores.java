package Includes;

import cuentaspagar.CRUD;
import cuentaspagar.Principal;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class proveedores extends CRUD {

    private final int rif;
    private final String r_social;
    private final String direccionp;
    private final int telefono;
    
    public proveedores() {
        
        this.rif = 0;
        this.r_social = " razon social ";
        this.direccionp = "direccion ";
        this.telefono = 0;
    }

    public proveedores(int rif,String r_social,String direccionp,int telefono) {
        
        this.rif = rif;
        this.r_social = r_social;
        this.direccionp = direccionp;
        this.telefono = telefono;
    }

    @Override
    public void agregar() {

        try {
            String SQL = "INSERT INTO proveedores (rif,razonsocial,direccionp,telefonop,statusp) VALUES (?,?,?,?,'A') ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setInt(1, rif);
            consulta.setString(2, r_social);
            consulta.setString(3, direccionp);
            consulta.setInt(4, telefono);
            consulta.execute();

            JOptionPane.showMessageDialog(null, "Proveedor registrado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    @Override
    public DefaultTableModel mostrar() {
        String[] titulos = {" Rif Proveedor ", " Razon social ", " Direccion ", " Telefono "};
        String[] registros = new String[4];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM proveedores WHERE statusp='A' ORDER BY rif";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("rif");
                registros[1] = resultados.getString("razonsocial");
                registros[2] = resultados.getString("direccionp");
                registros[3] = resultados.getString("telefonop");
                tabla.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al mostrar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

        return tabla;
    }

    @Override
    public void actualizar() {
        
        try {
            String SQL = "UPDATE proveedores SET razonsocial=?,direccionp=?,telefonop=? WHERE rif=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            
            consulta.setString(1, r_social);
            consulta.setString(2, direccionp);
            consulta.setInt(3, telefono);
            consulta.setInt(4, rif);
            consulta.execute();

            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    @Override
    public void eliminar() {
        try {
            
            String SQL = "UPDATE proveedores SET statusp='E' WHERE rif=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            
            consulta.setInt(1, rif);
            consulta.execute();

            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
        
    }

}
