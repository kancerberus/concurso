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
public class Motivo {
    
    private String codigo;
    private String nombrem;
    private String tipo;

    public String getNombrem() {
        return nombrem;
    }

    public void setNombrem(String nombrem) {
        this.nombrem = nombrem;
    } 

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
}
