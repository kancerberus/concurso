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
public class SubEmpresa{
    
    private String nitsubempresa;
    private String nombre;
    private String direccion;
    private String telefono;
    private Municipio municipio; 
    private Empresa empresa;

    public SubEmpresa() {
        this.empresa=new Empresa();
        this.municipio = new Municipio();
    }    

    public SubEmpresa(String nitsubempresa, String nombre) {
        this.nitsubempresa = nitsubempresa;
        this.nombre = nombre;
    }

    public String getNitsubempresa() {
        return nitsubempresa;
    }

    public void setNitsubempresa(String nitsubempresa) {
        this.nitsubempresa = nitsubempresa;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }   

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }    
}