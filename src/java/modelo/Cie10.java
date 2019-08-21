/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Andres
 */
public class Cie10 {

    private String codCie10;
    private String nombre;
    
    private GrupoCie10 grupoCie10;
    
    
    public Cie10(String codCie10, String nombre) {
      
        this.codCie10 = codCie10;
        this.nombre = nombre;        
    }

    public Cie10() {

    }  

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodCie10() {
        return codCie10;
    }

    public void setCodCie10(String codCie10) {
        this.codCie10 = codCie10;
    }

    public GrupoCie10 getGrupoCie10() {
        return grupoCie10;
    }

    public void setGrupoCie10(GrupoCie10 grupoCie10) {
        this.grupoCie10 = grupoCie10;
    }

    
    
}
