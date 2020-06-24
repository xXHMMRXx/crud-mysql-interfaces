
package crud;

import Interfaces.Read;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hmmr
 */
public class MostrarRegistros implements Read {

    @Override
    public void mostrar(String query, String url, String user, String pwd) {
        
        try {
            
            Connection conn = (Connection) DriverManager.getConnection(url, user, pwd);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while(rs.next()){
                
                System.out.println(
                        
                    "["+rs.getInt("UID")+"] "
                    + rs.getString("NOMBRE") + " "
                    + rs.getString("AP") + " "
                    + rs.getString("AM") + " "
                    + rs.getInt("EDAD") + " "
                    + rs.getString("PT") + " "
                
                );
                
            }
            
        } catch (SQLException ex) {
            
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
               
        }
        
    }
    
}
