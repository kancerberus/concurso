/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.Date;


public class Ausentismo {
    private String cod_registro;
    private Empleado empleado;
    private SubEmpresa subempresa;
    private Empresa empresa;
    private Date fecha_permiso;
    private Date fecha_registro;
    private Date hora_salida;
    private Date hora_llegada;
    private String tiempo_horas;  
    
    private String motivoausentismo; 
    private String tipo_permiso;
    private Motivo motivo;
    private String observaciones;
    private String mes;
    private String cargo;
    private String estado;
    private Double arl;
    private Double eps;
    private Double hs;
    private Double empleador;
    private Double trabajador;
    private Double total;
    private TipoIncapacidad tipoIncapacidad;
    
    
    private Double totalsube;
    
    
    public Ausentismo() {   
        this.fecha_registro = new Date();
        this.fecha_permiso = new Date();             
        this.empleado = new Empleado();
        this.motivo = new Motivo();   
        this.subempresa = new SubEmpresa();
        this.empresa = new Empresa();
    } 

    public TipoIncapacidad getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(TipoIncapacidad tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }    

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getTiempo_horas() {
        return tiempo_horas;
    }

    public void setTiempo_horas(String tiempo_horas) {
        this.tiempo_horas = tiempo_horas;
    }

    public String getMotivoausentismo() {
        return motivoausentismo;
    }

    public void setMotivoausentismo(String motivoausentismo) {
        this.motivoausentismo = motivoausentismo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    } 

    public Date getFecha_permiso() {
        return fecha_permiso;
    }

    public void setFecha_permiso(Date fecha_permiso) {
        this.fecha_permiso = fecha_permiso;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCod_registro() {
        return cod_registro;
    }

    public void setCod_registro(String cod_registro) {
        this.cod_registro = cod_registro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Date hora_salida) {
        this.hora_salida = hora_salida;
    }

    public Date getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(Date hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public String getTipo_permiso() {
        return tipo_permiso;
    }

    public void setTipo_permiso(String tipo_permiso) {
        this.tipo_permiso = tipo_permiso;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Double getArl() {
        return arl;
    }

    public void setArl(Double arl) {
        this.arl = arl;
    }

    public Double getEps() {
        return eps;
    }

    public void setEps(Double eps) {
        this.eps = eps;
    }

    public Double getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Double empleador) {
        this.empleador = empleador;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }   

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getTotalsube() {
        return totalsube;
    }

    public void setTotalsube(Double totalsube) {
        this.totalsube = totalsube;        
    }

    public Double getHs() {
        return hs;
    }

    public void setHs(Double hs) {
        this.hs = hs;
    }   

    public Double getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Double trabajador) {
        this.trabajador = trabajador;
    }
}
