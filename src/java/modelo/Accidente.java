/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;


public class Accidente {
    
    private Integer codRegAccidente;
    private Empleado empleado;
    private SubEmpresa subempresa;
    private Empresa empresa;
    private Motivo motivo;
    private Date fechaOcurrencia;
    private TipoEvento tipoEvento;
    private Clasificacion clasificacion;
    private boolean investigacion;
    private IncapacidadSi  incapacidadsi;
    private TipoAccidente tipoAccidente;
    private ParteAfectada parteAfectada;
    private TipoLesion tipoLesion;        
    private Riesgo riesgo;
    private Mecanismo mecanismo;
    private AgenteAccidente agenteAccidente;
    private CausaBasica causaBasica;
    private CausaInmediata causaInmediata;
    private String descAccidente;
    
    
    private Integer casos=0;
    private Integer incapacitantes=0;
    private Integer incidentes=0;
    private Integer accidentes=0;
    
    private Integer enfermedades=0;
    private Integer incatels=0;
    private Integer totCbasica=0;
    private Integer totCInmediata=0;
    private Integer totMecanismo=0;
    private Integer totAgAccidente=0;
    private Integer totParteAfectada=0;
    private Integer totTipoLesion=0;
    private Integer totCargos=0;
    private Integer investigados=0;     
    private double porcentaje=0;
    private Cargo cargo;

    public Accidente() {
    }

    public Accidente(TipoEvento tipoEvento, Integer casos, Integer incapacitantes, Integer investigados) {
        this.tipoEvento = tipoEvento;
        this.casos = casos;
        this.incapacitantes = incapacitantes;
        this.investigados = investigados;
        
    }  

    public Accidente(Riesgo riesgo, Integer incidentes, Integer accidentes, Integer enfermedades, Integer incatels) {
        this.riesgo = riesgo;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.enfermedades = enfermedades;
        this.incatels = incatels;
    }

    public Accidente(CausaBasica causaBasica, Integer incidentes, Integer accidentes, Integer enfermedades, Integer totCbasica) {
        this.causaBasica = causaBasica;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.enfermedades = enfermedades;
        this.totCbasica = totCbasica;
    }    
    
    public Accidente(CausaInmediata causaInmediata, Integer incidentes, Integer accidentes, Integer enfermedades, Integer totCInmediata) {
        this.causaInmediata = causaInmediata;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.enfermedades = enfermedades;
        this.totCInmediata = totCInmediata;
    }  

    public Accidente(TipoAccidente tipoAccidente, Integer casos, double porcentaje) {
        this.tipoAccidente = tipoAccidente;
        this.casos = casos;
        this.porcentaje = porcentaje;
    }
    
    public Accidente(Clasificacion clasificacion , Integer casos, double porcentaje) {
        this.clasificacion = clasificacion;
        this.casos = casos;
        this.porcentaje = porcentaje;
    }

    public Accidente(Mecanismo mecanismo, Integer  incidentes, Integer accidentes, Integer totMecanismo, double porcentaje) {
        this.mecanismo = mecanismo;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.totMecanismo = totMecanismo; 
        this.porcentaje = porcentaje;
    }
    
    public Accidente(AgenteAccidente agenteAccidente, Integer  incidentes, Integer accidentes, Integer totAgAccidente, double porcentaje) {
        this.agenteAccidente = agenteAccidente;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.totAgAccidente = totAgAccidente;
        this.porcentaje = porcentaje;
    }
    
    public Accidente(ParteAfectada parteAfectada, Integer  incidentes, Integer accidentes, Integer totParteAfectada, double porcentaje) {
        this.parteAfectada = parteAfectada;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.totParteAfectada = totParteAfectada;
        this.porcentaje = porcentaje;
    }
    
    public Accidente(TipoLesion tipoLesion, Integer  incidentes, Integer accidentes, Integer totTipoLesion, double porcentaje) {
        this.tipoLesion = tipoLesion;
        this.incidentes = incidentes;
        this.accidentes = accidentes;
        this.totTipoLesion = totTipoLesion;
        this.porcentaje = porcentaje;
    }

    public Accidente(Cargo cargo, Integer totCargos) {
        this.cargo = cargo;
        this.totCargos = totCargos;
    }

    public Integer getTotCargos() {
        return totCargos;
    }

    public void setTotCargos(Integer totCargos) {
        this.totCargos = totCargos;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getTotTipoLesion() {
        return totTipoLesion;
    }

    public void setTotTipoLesion(Integer totTipoLesion) {
        this.totTipoLesion = totTipoLesion;
    }

    public Integer getTotParteAfectada() {
        return totParteAfectada;
    }

    public void setTotParteAfectada(Integer totParteAfectada) {
        this.totParteAfectada = totParteAfectada;
    }

    public Integer getTotAgAccidente() {
        return totAgAccidente;
    }

    public void setTotAgAccidente(Integer totAgAccidente) {
        this.totAgAccidente = totAgAccidente;
    }

    public Integer getTotMecanismo() {
        return totMecanismo;
    }

    public void setTotMecanismo(Integer totMecanismo) {
        this.totMecanismo = totMecanismo;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getTotCInmediata() {
        return totCInmediata;
    }

    public void setTotCInmediata(Integer totCInmediata) {
        this.totCInmediata = totCInmediata;
    }
    
    public Integer getTotCbasica() {
        return totCbasica;
    }

    public void setTotCbasica(Integer totCbasica) {
        this.totCbasica = totCbasica;
    }

    public Integer getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(Integer incidentes) {
        this.incidentes = incidentes;
    }

    public Integer getAccidentes() {
        return accidentes;
    }

    public void setAccidentes(Integer accidentes) {
        this.accidentes = accidentes;
    }

    public Integer getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(Integer enfermedades) {
        this.enfermedades = enfermedades;
    }

    public Integer getIncatels() {
        return incatels;
    }

    public void setIncatels(Integer incatels) {
        this.incatels = incatels;
    }
    
    public Integer getCasos() {
        return casos;
    }

    public void setCasos(Integer casos) {
        this.casos = casos;
    }

    public Integer getIncapacitantes() {
        return incapacitantes;
    }

    public void setIncapacitantes(Integer incapacitantes) {
        this.incapacitantes = incapacitantes;
    }

    public Integer getInvestigados() {
        return investigados;
    }

    public void setInvestigados(Integer investigados) {
        this.investigados = investigados;
    }

    public Integer getCodRegAccidente() {
        return codRegAccidente;
    }

    public void setCodRegAccidente(Integer codRegAccidente) {
        this.codRegAccidente = codRegAccidente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Date getFechaOcurrencia() {
        return fechaOcurrencia;
    }

    public void setFechaOcurrencia(Date fechaOcurrencia) {
        this.fechaOcurrencia = fechaOcurrencia;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public boolean isInvestigacion() {
        return investigacion;
    }

    public void setInvestigacion(boolean investigacion) {
        this.investigacion = investigacion;
    }

    public IncapacidadSi getIncapacidadsi() {
        return incapacidadsi;
    }

    public void setIncapacidadsi(IncapacidadSi incapacidadsi) {
        this.incapacidadsi = incapacidadsi;
    }

    public TipoAccidente getTipoAccidente() {
        return tipoAccidente;
    }

    public void setTipoAccidente(TipoAccidente tipoAccidente) {
        this.tipoAccidente = tipoAccidente;
    }

    public ParteAfectada getParteAfectada() {
        return parteAfectada;
    }

    public void setParteAfectada(ParteAfectada parteAfectada) {
        this.parteAfectada = parteAfectada;
    }

    public TipoLesion getTipoLesion() {
        return tipoLesion;
    }

    public void setTipoLesion(TipoLesion tipoLesion) {
        this.tipoLesion = tipoLesion;
    }

    public Riesgo getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Riesgo riesgo) {
        this.riesgo = riesgo;
    }

    public Mecanismo getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(Mecanismo mecanismo) {
        this.mecanismo = mecanismo;
    }

    public AgenteAccidente getAgenteAccidente() {
        return agenteAccidente;
    }

    public void setAgenteAccidente(AgenteAccidente agenteAccidente) {
        this.agenteAccidente = agenteAccidente;
    }

    public CausaBasica getCausaBasica() {
        return causaBasica;
    }

    public void setCausaBasica(CausaBasica causaBasica) {
        this.causaBasica = causaBasica;
    }

    public CausaInmediata getCausaInmediata() {
        return causaInmediata;
    }

    public void setCausaInmediata(CausaInmediata causaInmediata) {
        this.causaInmediata = causaInmediata;
    }

    public String getDescAccidente() {
        return descAccidente;
    }

    public void setDescAccidente(String descAccidente) {
        this.descAccidente = descAccidente;
    }
}