/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
/**
 *
 * @author Andres
 */
public class GestorPrincipal implements Serializable {    
    
    private boolean crearConcurso=false;
    private boolean concursos=false;
    private boolean equipos=false;
    private boolean jueces=false;    
    private boolean tablaPosiciones=false;

    public String selGeneral(String opcion) {
        switch (opcion) {            
                
            case "crear_concurso":                
                setCrearConcurso(true);                
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setTablaPosiciones(false);
                break;
                
            case "concursos":                
                setCrearConcurso(false); 
                setConcursos(true);
                setEquipos(false);
                setJueces(false);
                setTablaPosiciones(false);
                break;
                
            case "equipos":                
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(true);
                setJueces(false);
                setTablaPosiciones(false);
                break;
                
            case "jueces":
                
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(true);
                setTablaPosiciones(false);
                break;
                
            case "tabla_posiciones":
                setCrearConcurso(false); 
                setConcursos(false);
                setEquipos(false);
                setJueces(false);
                setTablaPosiciones(true);
                break;    
                
        }
        return "";
    }

    public boolean isTablaPosiciones() {
        return tablaPosiciones;
    }

    public void setTablaPosiciones(boolean tablaPosiciones) {
        this.tablaPosiciones = tablaPosiciones;
    }

    public boolean isJueces() {
        return jueces;
    }

    public void setJueces(boolean jueces) {
        this.jueces = jueces;
    }

    public boolean isEquipos() {
        return equipos;
    }

    public void setEquipos(boolean equipos) {
        this.equipos = equipos;
    }

    public boolean isConcursos() {
        return concursos;
    }

    public void setConcursos(boolean concursos) {
        this.concursos = concursos;
    }

    public boolean isCrearConcurso() {
        return crearConcurso;
    }

    public void setCrearConcurso(boolean crearConcurso) {
        this.crearConcurso = crearConcurso;
    }
}
