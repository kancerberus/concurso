/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.Date;


public class Incapacidad {
    private String cod_reg_Incapacidad;
    private Cie10 cie10;
    private GrupoCie10 grupoCie10;
    private Empleado empleado;
    private SubEmpresa subempresa;
    private Empresa empresa;    
    private Date fecha_registro;
    private Integer tiempoDias;  
    private String tiempoHoras;
    private Date fecha_inicial;
    private Date fecha_final;
    private String motivoincapacidad; 
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

    public Incapacidad() {
    }

    public Incapacidad(String cod_reg_Incapacidad, Cie10 cie10, GrupoCie10 grupoCie10, Empleado empleado, SubEmpresa subempresa, Empresa empresa, Date fecha_registro, String tiempoHoras,Integer tiempoDias, Date fecha_inicial, Date fecha_final, String motivoincapacidad, String tipo_permiso, Motivo motivo, String observaciones, String mes, String cargo, String estado, Double arl, Double eps, Double hs, Double empleador, Double trabajador, Double total, TipoIncapacidad tipoIncapacidad, Double totalsube) {
        this.cod_reg_Incapacidad = cod_reg_Incapacidad;
        this.cie10 = cie10;
        this.grupoCie10 = grupoCie10;
        this.empleado = empleado;
        this.subempresa = subempresa;
        this.empresa = empresa;
        this.fecha_registro = fecha_registro;
        this.tiempoHoras = tiempoHoras;
        this.tiempoDias = tiempoDias;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
        this.motivoincapacidad = motivoincapacidad;
        this.tipo_permiso = tipo_permiso;
        this.motivo = motivo;
        this.observaciones = observaciones;
        this.mes = mes;
        this.cargo = cargo;
        this.estado = estado;
        this.arl = arl;
        this.eps = eps;
        this.hs = hs;
        this.empleador = empleador;
        this.trabajador = trabajador;
        this.total = total;
        this.tipoIncapacidad = tipoIncapacidad;
        this.totalsube = totalsube;
    }

    public String getTiempoHoras() {
        return tiempoHoras;
    }

    public void setTiempoHoras(String tiempoHoras) {
        this.tiempoHoras = tiempoHoras;
    }

    public GrupoCie10 getGrupoCie10() {
        return grupoCie10;
    }

    public void setGrupoCie10(GrupoCie10 grupoCie10) {
        this.grupoCie10 = grupoCie10;
    }

    public Date getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(Date fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }

    public Cie10 getCie10() {
        return cie10;
    }

    public void setCie10(Cie10 cie10) {
        this.cie10 = cie10;
    }

    public TipoIncapacidad getTipoIncapacidad() {
        return tipoIncapacidad;
    }

    public void setTipoIncapacidad(TipoIncapacidad tipoIncapacidad) {
        this.tipoIncapacidad = tipoIncapacidad;
    }

    public String getCod_reg_Incapacidad() {
        return cod_reg_Incapacidad;
    }

    public void setCod_reg_Incapacidad(String cod_reg_Incapacidad) {
        this.cod_reg_Incapacidad = cod_reg_Incapacidad;
    }

    public String getMotivoincapacidad() {
        return motivoincapacidad;
    }

    public void setMotivoincapacidad(String motivoincapacidad) {
        this.motivoincapacidad = motivoincapacidad;
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

    public Integer getTiempoDias() {
        return tiempoDias;
    }

    public void setTiempoDias(Integer tiempoDias) {
        this.tiempoDias = tiempoDias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    } 

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
