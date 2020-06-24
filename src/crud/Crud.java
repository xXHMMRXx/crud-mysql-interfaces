
package crud;

import Interfaces.Delete;
import Interfaces.Insert;
import Interfaces.Read;
import Interfaces.Update;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 *
 * @author hmmr
 */
public class Crud {
    
    private static String url = "jdbc:mysql://localhost:3306/crud?autoReconnect=true&serverTimezone=UTC";
    private static String user = "root";
    private static String pwd = "toor"; 
    
    private static void Udt(int uid, int opt) {
        
        Update up = new Actualizar();
        Scanner sc = new Scanner(System.in);
        String n, ap, am, pt, query;
        int ed;
        
        switch(opt) {
            
            case 1: System.out.print("\nIngrese nombre: ");
                    n = sc.next();
                    System.out.print("\nIngrese apellido paterno: ");
                    ap = sc.next();
                    System.out.print("\nIngrese apellido materno: ");
                    am = sc.next();
                    System.out.print("\nIngrese edad: ");
                    ed = sc.nextInt();
                    System.out.print("\nIngrese pasatiempo: ");
                    pt = sc.next();
                    query = "UPDATE USERS SET NOMBRE = \""+n
                            +"\", AP = \""+ap+"\", AM = \""+am
                            +"\", EDAD = "+ed+", PT = \""+pt+"\" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
                    
            case 2: System.out.print("\nIngrese nombre: ");
                    n = sc.next();
                    query = "UPDATE USERS SET NOMBRE = \""+n+"\" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
                    
                    /****/
                    
            case 3: System.out.print("\nIngrese apellido paterno: ");
                    ap = sc.next();
                    query = "UPDATE USERS SET AP = \""+ap+"\" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
                    
            case 4: System.out.print("\nIngrese apellido materno: ");
                    am = sc.next();
                    query = "UPDATE USERS SET AM = \""+am+"\" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
                    
            case 5: System.out.print("\nIngrese edad: ");
                    ed = sc.nextInt();
                    query = "UPDATE USERS SET EDAD = "+ed+" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
            
            case 6: System.out.print("\nIngrese pasatiempo: ");
                    pt = sc.next();
                    query = "UPDATE USERS SET PT = \""+pt+"\" WHERE (UID = "+uid+");";
                    up.update(query, url, user, pwd);
                    break;
                    
            case 7: inicio();
            
            default: inicio();
            
        }
        
    }
    
    public static void Del(int UID) {
        
       String query = "DELETE FROM USERS WHERE (UID = "+UID+");"; 
       String query2 = "SELECT * FROM USERS;";
       
        try {
            
            Connection conn = (Connection) DriverManager.getConnection(url, user, pwd);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            conn.close();
            
            System.out.println("\n*** Registro eliminado ***\n");
            System.out.println("Usuarios registrados\n");
            
            List<Delete> l = new ArrayList<>();
            
            Connection con = (Connection) DriverManager.getConnection(url, user, pwd);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query2);
            
            while(rs.next()){
                
                l.add(new Delete(rs.getInt("UID"), rs.getString("NOMBRE"), 
                        rs.getString("AP"), rs.getString("AM"), rs.getInt("EDAD"), 
                        rs.getString("PT")));

            }
            
            List<Delete> result = dMostrar(l, (Delete d) -> d.getUid() >= 0 );
            
            for(Delete d : result){
                   
                System.out.println("["+d.getUid()+"] "
                        +d.getNombre()+" "
                        +d.getAp()+" "
                        +d.getAm()+" "
                        +d.getEdad()+" "
                        +d.getPt()+" "
                
                );
                   
            } 
            
            
        } catch (SQLException ex) {
            
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
               
        }

    }
    
    public static <T> List<T>  dMostrar(List<T> registros, Predicate<T> predicate) {
        
        List<T> reg = new ArrayList<>();
        
        for(T t : registros) {
            
            if(predicate.test(t)) {
                
                reg.add(t);
                
            }
            
        }
        
        return reg;
        
    }
    
    private static void Select(boolean tmp) {
        
        String query = "SELECT * FROM USERS;";
        
        try {
            
            Read mr = new MostrarRegistros();
            mr.mostrar(query, url, user, pwd);
            
        } catch (Exception e) {
            
            System.out.println(e);
               
        }
        
        if(!tmp) inicio();
        
    }
    
    private static String queryCreate(Insert insertar) {
        
        return insertar.insertar();
        
    }
    
    private static void create(String n, String ap, String am, int e, String pt) {
        
        String query = "INSERT INTO USERS(NOMBRE, AP, AM, EDAD, PT)" +" VALUES(?,?,?,?,?)";
        
        queryCreate(() -> {
            
            try {
                
                Connection con = (Connection) DriverManager.getConnection(url, user, pwd);
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, n);
                ps.setString(2, ap);
                ps.setString(3, am);
                ps.setInt(4, e);
                ps.setString(5, pt);
                
                ps.execute();
                ps.close();
                
            } catch (SQLException ex) {
            
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
               
            }
            
            return null;
            
        });
        
        inicio();
        
    }
    
    public static void inicio() {
        
        int option, edad, uid, opt;
        String nombre, ap, am, pt;
        boolean temp = false;
        
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n**** CRUD ****\n");
        System.out.println("Seleccione una opción:\n");
        System.out.println("[1] Insertar");
        System.out.println("[2] Mostrar");
        System.out.println("[3] Actualizar");
        System.out.println("[4] Eliminar");
        System.out.println("[5] Salir\n");
        System.out.print("xXHMMRXx:~$ ");
        option = sc.nextInt();
        
        do {
            
            switch(option) {
                
                //Insertar
                case 1: System.out.println("\n*** Insertar ***\n");
                        System.out.print("Ingrese nombre: ");
                        nombre = sc.next();
                        System.out.print("\nIngrese apellido paterno: ");
                        ap = sc.next();
                        System.out.print("\nIngrese apellido materno: ");
                        am = sc.next();
                        System.out.print("\nIngrese edad: ");
                        edad = sc.nextInt();
                        System.out.print("\nIngrese un pasatiempo: ");
                        pt = sc.next();
                        Crud.create(nombre, ap, am, edad, pt);
                        break;
                
                //Mostrar
                case 2: System.out.println("\n*** Usuarios registrados ***\n");
                        Crud.Select(temp);
                        break;
                
                //Actualizar
                case 3: System.out.println("\n*** Actualizar ***\n");
                        System.out.println("Seleccione un usuario\n");
                        temp = true;
                        Crud.Select(temp);
                        System.out.print("\nxXHMMRXx:~$ ");
                        uid = sc.nextInt();
                        System.out.println("\nSeleccione que desea modificar\n");
                        System.out.println("[1] Todo el registro");
                        System.out.println("[2] Nombre");
                        System.out.println("[3] Apellido paterno");
                        System.out.println("[4] Apellido materno");
                        System.out.println("[5] Edad");
                        System.out.println("[6] Pasatiempo");
                        System.out.println("[7] Volver");
                        System.out.print("\nxXHMMRXx:~$ ");
                        opt = sc.nextInt();
                        Udt(uid, opt);
                        Crud.Select(temp);
                        inicio();
                        break;
                
                //Eliminar
                case 4: System.out.println("\n*** Eliminar ***\n");
                        System.out.println("Seleccione un usuario\n");
                        temp = true;
                        Crud.Select(temp);
                        System.out.print("\nxXHMMRXx:~$ ");
                        uid = sc.nextInt();
                        Crud.Del(uid);
                        inicio();
                        break;  
                        
                //Salir
                case 5: System.exit(0); 
                        break; 
                
                default: System.out.println("\n### Opción no valida ###\n"); 
                         inicio();
                         break;
                
            }
            
        } while(option > 6);
        
    }
    
    public static void main(String[] args) {
        
        inicio();

    }
    
}
