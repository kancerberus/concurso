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
public class IncapacidadSi {

    private String codigo;
    private String nombre;
    
    
    public IncapacidadSi(String codigo, String nombre) {
      
        this.codigo = codigo;
        this.nombre = nombre;        
    }

    public IncapacidadSi() {

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }        
}
