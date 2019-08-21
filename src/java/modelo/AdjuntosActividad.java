/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import org.primefaces.model.UploadedFile;

/**
 *
 * @author Andres
 */
public class AdjuntosActividad {

    private Integer codAdjunto;
    private Actividad actividad;
    private String nombre;
    private String direccion;
    private GrupoConcurso grupoConcurso;
    private UploadedFile adj;

    public AdjuntosActividad() {

    }  

    public AdjuntosActividad(Integer codAdjunto, Actividad actividad, String nombre, String direccion, UploadedFile adj) {
        this.codAdjunto = codAdjunto;
        this.actividad = actividad;
        this.nombre = nombre;
        this.direccion = direccion;
        this.adj = adj;
    }

// 

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    public UploadedFile getAdj() {
        return adj;
    }

    public void setAdj(UploadedFile adj) {
        this.adj = adj;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodAdjunto() {
        return codAdjunto;
    }

    public void setCodAdjunto(Integer codAdjunto) {
        this.codAdjunto = codAdjunto;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }    
    
}
