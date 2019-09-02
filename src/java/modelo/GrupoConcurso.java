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
public class GrupoConcurso implements Serializable{

    private String codGrupo;
    private Concurso concurso;
    private String nombre;    
    private SubEmpresa subempresa;
    private Integer puntajeTotal;
    private Integer pos;

    public GrupoConcurso(String codGrupo, Concurso concurso, String nombre, SubEmpresa subempresa, Integer puntajeTotal, Integer pos) {
        this.codGrupo = codGrupo;
        this.concurso = concurso;
        this.nombre = nombre;     
        this.subempresa = subempresa;
        this.puntajeTotal= puntajeTotal;
        this.pos=pos;
    }      

    public GrupoConcurso() {

    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(Integer puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
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

    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }    
    
}
