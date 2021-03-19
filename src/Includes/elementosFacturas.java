package Includes;

import cuentaspagar.Principal;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class elementosFacturas {

    public elementosFacturas() {
    }

    public DefaultListModel listarArticulos() {

        DefaultListModel lista = new DefaultListModel();

        try {

            String SQL = "SELECT nombrea FROM articulos ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                lista.addElement(resultado.getString("nombrea"));
            }

        } catch (Exception e) {

        }

        return lista;
    }

    public DefaultListModel agregarALista(JList j1, String nombrea) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        boolean agregado = true;
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals(nombrea)) {
                agregado = false;
            }

        }

        if (agregado) {
            lista.addElement(nombrea);
        }
        return lista;
    }

    public DefaultListModel EliminarDeLista(JList j1, String nombrea) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals(nombrea)) {
                lista.removeElement(nombrea);
                return lista;
            }

        }
        return lista;

    }

    public int obtenernrofactura() {

        String SQL = "SELECT n_factura FROM facturas ORDER BY n_factura DESC LIMIT 1   ";
        try {
            int nrofactura = 0;
            Statement consultaCodigo = Principal.conexion.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return nrofactura = resultado.getInt("n_factura");
        } catch (Exception e) {

        }
        return -1;

    }

    public String obtenerCodigoArticulo(String nombrea) {

        String SQL = "SELECT codigoa FROM articulos WHERE nombrea = '" + nombrea + "'";
        try {
            String codigoa = "";
            Statement consultaCodigo = Principal.conexion.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return codigoa = resultado.getString("codigoa");
        } catch (Exception e) {

        }
        return null;

    }

    public float obtenerPrecioArticulo(String codigoa) {
        float precio = 0;
        String SQL = "SELECT precio FROM articulos WHERE codigoa = '" + codigoa + "'";
        try {

            Statement consultaCodigo = Principal.conexion.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return precio = resultado.getFloat("precio");
        } catch (Exception e) {

        }
        return precio;
    }

    public void agregarElementosFacturas(JList j1, int cantidad) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        int nrofactura = this.obtenernrofactura();
        String SQL = "INSERT INTO elementos_facturas (n_factura,codigoa,cantidad,precio) VALUES (?,?,?,?) ";

        for (int i = 0; i < arreglo.length; i++) {
            try {

                String codigoa = obtenerCodigoArticulo(arreglo[i].toString());
                float precio = obtenerPrecioArticulo(codigoa);
                PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
                consulta.setInt(1, nrofactura);
                consulta.setString(2, codigoa);
                consulta.setInt(3, cantidad);
                consulta.setFloat(4, precio);
                consulta.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, " Error al añadir un elementos: ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(null, " Elementos ingresados exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);
    }

    public DefaultTableModel mostrarElementosFacturas(int nrofactura) {
        String[] titulos = {" Nro Factura ", " codigo Producto ", " Nombre Producto ", " Cantidad ", " Precio "};
        String[] registros = new String[5];

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = " SELECT elef.n_factura,elef.codigoa,elef.cantidad,elef.precio, art.nombrea "
                + " FROM elementos_facturas elef,articulos art "
                + "WHERE art.codigoa=elef.codigoa AND n_factura="+nrofactura+" ORDER BY n_factura,elef.codigoa";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                registros[0] = resultados.getString("n_factura");
                registros[1] = resultados.getString("codigoa");
                registros[2] = resultados.getString("nombrea");
                registros[3] = resultados.getString("cantidad");
                registros[4] = resultados.getString("precio");
                tabla.addRow(registros);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al mostrar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

        return tabla;
    }

    public void eliminarElementoFactura(int nrofactura, String codigoa) {

        String SQL = "DELETE FROM elementos_facturas where n_factura=? AND codigoa=?";

        try {
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            consulta.setInt(1, nrofactura);
            consulta.setString(2, codigoa);
            consulta.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar un elemento de factura: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public void actualizarElementoFactura(int nrofactura,String codigoa,int cantidad){
        
                try {

            String SQL = "UPDATE elementos_facturas SET cantidad=? WHERE n_factura=? AND codigoa=? ";
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);

            consulta.setInt(1, cantidad);
            consulta.setInt(2, nrofactura);
            consulta.setString(3, codigoa);
            consulta.execute();

            JOptionPane.showMessageDialog(null, " elemento de Factura actualizado exitosamente", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    

    public void eliminarElementosFactura(int nrofactura) {

        String SQL = "DELETE FROM elementos_facturas where n_factura=?";

        try {
            PreparedStatement consulta = Principal.conexion.prepareStatement(SQL);
            consulta.setInt(1, nrofactura);
            consulta.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar los elementos de factura: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    
    
    

    public String MostrarFactura(int nrofactura) {
        String factura = "\tFACTURA NRO:  " + nrofactura + " \n";
        String SQL = " SELECT  elef.cantidad,elef.precio, art.nombrea, elef.codigoa "
                + "FROM elementos_facturas elef,articulos art "
                + "  WHERE elef.codigoa=art.codigoa AND n_factura=" + nrofactura + " ORDER BY art.codigoa ";

        try {
            Statement consulta = Principal.conexion.createStatement();
            ResultSet resultados = consulta.executeQuery(SQL);
            while (resultados.next()) {
                factura += "codigo del articulo: ";
                factura += resultados.getString("codigoa");
                factura += "\n";
                factura += "nombre del articulo: ";
                factura += resultados.getString("nombrea");
                factura += "\n";
                factura += "cantidad del articulo: ";
                factura += resultados.getString("cantidad");
                factura += "\n";
                factura += "Precio del articulo: ";
                factura += resultados.getString("precio");
                factura += "\n----------------------------\n";

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al mostrar datos: " + e.getMessage(), "Accion no realizada", JOptionPane.ERROR_MESSAGE);
        }

        return factura;

    }

}
