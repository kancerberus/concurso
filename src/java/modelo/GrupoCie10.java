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
public class GrupoCie10 {

    private String cod_grupo_cie10;
    private String nombre;
    
    
    public GrupoCie10(String cod_grupo_cie10, String nombre) {
      
        this.cod_grupo_cie10 = cod_grupo_cie10;
        this.nombre = nombre;        
    }

    
    
    public GrupoCie10() {

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

    public String getCod_grupo_cie10() {
        return cod_grupo_cie10;
    }

    public void setCod_grupo_cie10(String cod_grupo_cie10) {
        this.cod_grupo_cie10 = cod_grupo_cie10;
    }    
    
}
