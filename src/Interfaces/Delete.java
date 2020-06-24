
package Interfaces;

/**
 *
 * @author hmmr
 */
public class Delete {
    
    //Aqui utilice Expression Lambda con la clase crud
    
    private int edad, uid;
    private String nombre, ap, am, pt;

    public Delete(int uid, String nombre, String ap, String am, int edad, String pt) {
        this.uid = uid;
        this.nombre = nombre;
        this.ap = ap;
        this.am = am;
        this.edad = edad;
        this.pt = pt;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

}
