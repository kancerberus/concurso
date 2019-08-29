/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Andres
 */
public class Actividad implements Serializable{

    private String codActividad;
    private String nombre;
    private String observacion;
    private Date fechaLimite;    
    private Concurso concurso;    
    private GrupoConcurso grupoConcurso;

    public Actividad(String codActividad, String nombre, String observacion, Date fechaLimite, Concurso concurso) {
        this.codActividad = codActividad;
        this.nombre = nombre;
        this.observacion = observacion;
        this.fechaLimite = fechaLimite;        
        this.concurso = concurso;
    }

    public Actividad(String codActividad, String nombre) {
        this.codActividad = codActividad;
        this.nombre = nombre;
    }

    

    public Actividad() {

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

    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    public String getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

   
    
}
