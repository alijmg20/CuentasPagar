package Includes;

import cuentaspagar.CRUD;
import cuentaspagar.Principal;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Articulos extends CRUD {

    private final String codigoa;
    private final String nombrea;
    private final String descripciona;
    private final float precio;
    private final int existencia;
    private final String descripcionta;
    
    public Articulos() {

        this.codigoa = "codigo";
        this.nombrea = "nombre";
        this.descripciona = "descripcion";
        this.precio = 0;
        this.existencia = 0;
        this.descripcionta = "descripcionta";
    }

    public Articulos(String codigoa, String nombrea, String descripciona, float precio, int existencia, String descripcionta) {

        this.codigoa = codigoa;
        this.nombrea = nombrea;
        this.descripciona = descripciona;
        this.precio = precio;
        this.existencia = existencia;
        this.descripcionta = descripcionta;
    }

    public void obtenerCategorias(JComboBox cb) {
        try {
            String SQL = "SELECT descripcionta FROM tipo_articulos ORDER BY descripcionta ASC";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Seleccione una opcion");
            while (resultado.next()) {
                cb.addItem(resultado.getString("descripcionta"));
            }
        } catch (Exception ex) {

        }
    }

    public String busquedaCategorias(String descripcionta) {

        String SQL = "SELECT codigota FROM tipo_articulos WHERE descripcionta = '" + descripcionta + "'";
        try {
            String codigoCategoria = "";
            Statement consultaCodigo = Principal.conexion.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return codigoCategoria = resultado.getString("codigota");
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void agregar() {

        String codigota = busquedaCategorias(descripcionta);

        try {
            String SQL = "INSERT INTO articulos (codigoa,nombrea,descripciona,precio,existencia,codigota) VALUES (?,?,?,?,?,?) ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setString(1, codigoa);
            consulta.setString(2, nombrea);
            consulta.setString(3, descripciona);
            consulta.setFloat(4, precio);
            consulta.setInt(5, existencia);
            consulta.setString(6, codigota);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " articulo registrado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public DefaultTableModel mostrar() {
        String[] titulos = {" Codigo de articulo ", " nombre de articulo ", " Descripcion de articulos ", " Precio ", " Existencia ", " nombre categoria "};
        String[] registros = new String[6];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT  art.codigoa,art.nombrea,art.descripciona,art.precio,art.existencia, tipart.descripcionta "
                + "FROM articulos AS art,tipo_articulos AS tipart "
                + "WHERE art.codigota=tipart.codigota "
                + "ORDER BY descripcionta";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("codigoa");
                registros[1] = resultados.getString("nombrea");
                registros[2] = resultados.getString("descripciona");
                registros[3] = resultados.getString("precio");
                registros[4] = resultados.getString("existencia");
                registros[5] = resultados.getString("descripcionta");
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

            String codigota = busquedaCategorias(descripcionta);

            String SQL = "UPDATE articulos SET nombrea=?,descripciona=?,precio=?,existencia=?,codigota=? WHERE codigoa=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            
            consulta.setString(1, nombrea);
            consulta.setString(2, descripciona);
            consulta.setFloat(3, precio);
            consulta.setInt(4, existencia);
            consulta.setString(5, codigota);
            consulta.setString(6, codigoa);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " Articulo actualizado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void eliminar() {

        String SQL = "DELETE FROM articulos WHERE codigoa=?";

        try {
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            consulta.setString(1, codigoa);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "Articulo Eliminado exitosamente ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

    }

}
