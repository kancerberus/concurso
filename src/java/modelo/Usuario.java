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
public class Usuario {
    private String nomusuario;
    private String nombre;
    private String clave;
    private Perfil perfil;
    private SubEmpresa subempresa;
    private boolean estado;

    public Usuario() {
        this.perfil=new Perfil(); 
        this.subempresa=new SubEmpresa();
    }

    public String getNomusuario() {
        return nomusuario;
    }

    public void setNomusuario(String nomusuario) {
        this.nomusuario = nomusuario;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    /**
     * @return the perfil
     */
    public Perfil getPerfil() {
        return perfil;
    }

    /**
     * @param perfil the perfil to set
     */
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public SubEmpresa getSubEmpresa() {
        return subempresa;
    }

    public void setSubEmpresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }        

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
