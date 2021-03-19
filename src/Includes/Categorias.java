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

public class Categorias extends CRUD  {

    private final String codigota;
    private final String descripcionta;
    private final char categoriata;

    public Categorias() {
        
        this.codigota = " codigo ";
        this.descripcionta = " Descripcion ";
        this.categoriata = 'A';
    }
    
    
    
    public Categorias(String codigota, String descripcionta,char categoriata) {
        
        this.codigota = codigota;
        this.descripcionta = descripcionta;
        this.categoriata = categoriata;
        
    }
    
    

    
    @Override
    public void agregar() {
        try {
            String SQL = "INSERT INTO tipo_articulos (codigota,descripcionta,categoriata) VALUES (?,?,?) ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setString(1, codigota);
            consulta.setString(2, descripcionta);
            consulta.setString(3, String.valueOf(categoriata));
            consulta.execute();

            JOptionPane.showMessageDialog(null, " categoria registrado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

    
    @Override
    public DefaultTableModel mostrar() {
        String[] titulos = {" Codigo de categoria ", " Descripcion Categoria ", " tipo de categoria "};
        String[] registros = new String[3];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM tipo_articulos ORDER BY codigota";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("codigota");
                registros[1] = resultados.getString("descripcionta");
                registros[2] = resultados.getString("categoriata");
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
            String SQL = "UPDATE tipo_articulos SET descripcionta=?,categoriata=? WHERE codigota=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setString(1, descripcionta);
            consulta.setString(2, String.valueOf(categoriata));
            consulta.setString(3, codigota);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " categoria actualizada exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    @Override
    public void eliminar() {

        String SQL = "DELETE FROM tipo_articulos where codigota=?";

        try {
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            consulta.setString(1, codigota);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria Eliminada exitosamente ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

}
