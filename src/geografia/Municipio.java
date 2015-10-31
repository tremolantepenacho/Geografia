/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geografia;

/**
 *
 * @author hector
 */
public class Municipio {

  
    int provincia,cod,poblacion,hombres,mujeres;
    String nombre;
    float superficie;

    public Municipio(int provincia, int cod, int poblacion, int hombres, int mujeres, String Nombre) {
        this.provincia = provincia;
        this.cod = cod;
        this.poblacion = poblacion;
        this.hombres = hombres;
        this.mujeres = mujeres;
        this.nombre = Nombre;
    }
    

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(int poblacion) {
        this.poblacion = poblacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public float getSuperficie() {
        return superficie;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.cod;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Municipio other = (Municipio) obj;
        if (this.cod != other.cod) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Municipio{" + "provincia=" + provincia + ", cod=" + cod + ", poblacion=" + poblacion + ", hombres=" + hombres + ", mujeres=" + mujeres + ", Nombre=" + nombre + ", superficie=" + superficie + '}';
    }

   public String insertLocalidad(){
       
       String aux="INSERT INTO \"localidad\" (\"nombre\") VALUES ('"+nombre+"');";
    //   "INSERT INTO "localidad" ("nombre", "cod", "provincia") VALUES ('torrent', '100','46');
       return aux;
   }


    
    
}
