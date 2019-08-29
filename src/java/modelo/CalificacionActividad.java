/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class CalificacionActividad implements Serializable{

    private String codActividad;
    private String codGrupo;    
    private Integer calificacion=0;
    
    private Actividad actividad;
    private GrupoConcurso grupoConcurso;
    

    public CalificacionActividad(String codActividad, String codGrupo, Integer calificacion) {
        this.codActividad = codActividad;
        this.codGrupo = codGrupo;
        this.calificacion = calificacion;
    }


    public CalificacionActividad() {

    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }   

    public String getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }


}
