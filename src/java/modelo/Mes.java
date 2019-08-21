/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
/**
 *
 * @author diego
 */
public class Mes {
    
    private String codigo;
    private String nombre;
    private String desde;
    private String hasta;

    public Mes(String codigo, String nombre, String desde, String hasta) {
        this.codigo = codigo;
        this.nombre = nombre; 
        this.desde = desde;
        this.hasta = hasta;
    }
    public Mes() {

    }  

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    

}
