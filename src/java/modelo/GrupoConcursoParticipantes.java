/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author FRANCISCO
 */
public class GrupoConcursoParticipantes implements Serializable{

    private String codGrupoParticipantes;
    private GrupoConcurso grupoConcurso;    
    private Empleado empleado;
    private boolean capitan=false;

    public GrupoConcursoParticipantes(String codGrupoParticipantes, GrupoConcurso grupoConcurso, Empleado empleado, boolean capitan) {
        this.codGrupoParticipantes = codGrupoParticipantes;
        this.grupoConcurso = grupoConcurso;
        this.empleado = empleado;
        this.capitan = capitan;
    }         

    public GrupoConcursoParticipantes() {
        
    }  

    public String getCodGrupoParticipantes() {
        return codGrupoParticipantes;
    }

    public void setCodGrupoParticipantes(String codGrupoParticipantes) {
        this.codGrupoParticipantes = codGrupoParticipantes;
    }

    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isCapitan() {
        return capitan;
    }

    public void setCapitan(boolean capitan) {
        this.capitan = capitan;
    }    
    
}
