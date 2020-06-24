
package crud;

import Interfaces.Update;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author hmmr
 */
public class Actualizar implements Update{

    @Override
    public void update(String query, String url, String user, String pwd) {
        
        try {
            
            Connection conn = (Connection) DriverManager.getConnection(url, user, pwd);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
            conn.close();
            
            System.out.println("\n*** Usuario modificado ***\n");
            System.out.println("*** Usuarios registrados ***\n");
            
        } catch (SQLException ex) {
            
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
               
        }
        
    }
    
}
