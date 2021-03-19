
package Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    private  Connection con = null;
    private final String jdbc = "jdbc:postgresql://localhost:5432/";
    
    public Connection conectar()
    {
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(jdbc+"cuentaspagar","postgres","password");
            
            System.out.println("Conexion realizada");
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null,"Error: "+ex.getMessage());
        }
        return con;
    }
    
}
