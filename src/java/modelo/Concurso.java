/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Andres
 */
public class Concurso implements Serializable{

    
    
    private String codConcurso;
    private String nombre;
    private Empresa empresa;
    private Integer participantes;
    private boolean estado=true;
    private Date fecha_limite_insc;    
    private String logoDir;
    

    public Concurso(String codConcurso, String nombre, Empresa empresa, Integer participantes, boolean estado, Date fecha_limite_insc, String logoNom, String logoDir) {
        this.codConcurso = codConcurso;
        this.nombre = nombre;
        this.empresa = empresa;
        this.participantes = participantes;
        this.estado = estado;
        this.fecha_limite_insc = fecha_limite_insc;        
        this.logoDir=logoDir;
    }

    public Concurso(String codConcurso, String nombre, Integer participantes) {
        this.codConcurso = codConcurso;
        this.nombre = nombre;
        this.participantes= participantes;
    }



    public Concurso() {

    }      

    public String getLogoDir() {
        return logoDir;
    }

    public void setLogoDir(String logoDir) {
        this.logoDir = logoDir;
    }

    public boolean isEstado() {
        return estado;
    } 

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha_limite_insc() {
        return fecha_limite_insc;
    }

    public void setFecha_limite_insc(Date fecha_limite_insc) {
        this.fecha_limite_insc = fecha_limite_insc;
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

    public String getCodConcurso() {
        return codConcurso;
    }

    public void setCodConcurso(String codConcurso) {
        this.codConcurso = codConcurso;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Integer getParticipantes() {
        return participantes;
    }

    public void setParticipantes(Integer participantes) {
        this.participantes = participantes;
    }    
    
}
