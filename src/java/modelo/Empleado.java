/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.Date;


public class Empleado {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String nitsubempresa;
    private Date fecha_nac;
    private int edad;
    private Cargo cargo;
    private int sueldo_mes;
    private int aux_transporte;
    private Ecivil ecivil;    
    private Municipio municipio; 
    private Sexo sexo; 
    private int thacum;
    private String anoacum;
    private Eps eps;    
    private Municipio Residencia;
    private boolean estado;

    
    public Empleado() {
        this.municipio = new Municipio();
        this.fecha_nac = new Date();
        this.Residencia = new Municipio();
        this.cargo = new Cargo();
        this.ecivil = new Ecivil();
        this.sexo = new Sexo();
        this.eps = new Eps();
    } 

    public Empleado(String cedula, String nombres, String apellidos) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    
    

    public String getAnoacum() {
        return anoacum;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setAnoacum(String anoacum) {
        this.anoacum = anoacum;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio() {
        this.municipio = municipio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }   

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Municipio getResidencia() {
        return Residencia;
    }

    public void setResidencia(Municipio Residencia) {
        this.Residencia = Residencia;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Ecivil getEcivil() {
        return ecivil;
    }

    public void setEcivil(Ecivil ecivil) {
        this.ecivil = ecivil;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Eps getEps() {
        return eps;
    }

    public void setEps(Eps eps) {
        this.eps = eps;
    }      

    public int getSueldo_mes() {
        return sueldo_mes;
    }

    public void setSueldo_mes(int sueldo_mes) {
        this.sueldo_mes = sueldo_mes;
    } 

    public int getAux_transporte() {
        return aux_transporte;
    }

    public void setAux_transporte(int aux_transporte) {
        this.aux_transporte = aux_transporte;
    }  

    public String getNitsubempresa() {
        return nitsubempresa;
    }

    public void setNitsubempresa(String nitsubempresa) {
        this.nitsubempresa = nitsubempresa;
    } 

    public int getThacum() {
        return thacum;
    }

    public void setThacum(int thacum) {
        this.thacum = thacum;
    }
    
}
