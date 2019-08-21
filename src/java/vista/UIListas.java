/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorEmpleado;
import controlador.GestorEps;
import controlador.GestorCargo;
import controlador.GestorEcivil;
import controlador.GestorMes;
import controlador.GestorSexo;
import controlador.GestorMunicipio;
import controlador.GestorListas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Empleado;
import modelo.Municipio;
import modelo.Eps;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Sexo;
import modelo.Mes;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.AgenteAccidente;
import modelo.Empresa;
import modelo.SubEmpresa;
import modelo.Año;
import modelo.CausaBasica;
import modelo.CausaInmediata;
import modelo.Clasificacion;
import modelo.IncapacidadSi;
import modelo.Mecanismo;
import modelo.ParteAfectada;
import modelo.Riesgo;
import modelo.TipoAccidente;
import modelo.TipoEvento;
import modelo.TipoIncapacidad;
import modelo.TipoLesion;
/**
 *
 * @author Andres
 */
public class UIListas implements Serializable {

    public String cedula;
    public Empleado empleado;
    public Empresa empresa;
    public SubEmpresa subempresa;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory empl;
    private GestorEmpleado gestorEmpleado; 
    private GestorEps gestorEps;
    private GestorEcivil gestorEcivil;
    private GestorCargo gestorCargo;
    private GestorSexo gestorSexo;
    private GestorMunicipio gestorMunicipio;
    private GestorListas gestorListas;
    private GestorMes gestorMes;
    private ArrayList<SelectItem> itemsEpss = new ArrayList<>();
    private ArrayList<SelectItem> itemsCargos = new ArrayList<>();
    private ArrayList<SelectItem> itemsEciviles = new ArrayList<>();
    private ArrayList<SelectItem> itemsSexos = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoIncapacidades = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoEventos = new ArrayList<>();
    private ArrayList<SelectItem> itemsClasificaciones = new ArrayList<>();
    private ArrayList<SelectItem> itemsIncapacidadesSi = new ArrayList<>();
    private ArrayList<SelectItem> itemsParteAfectada = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoLesiones = new ArrayList<>();
    private ArrayList<SelectItem> itemsRiesgos = new ArrayList<>();
    private ArrayList<SelectItem> itemsMecanismos = new ArrayList<>();
    private ArrayList<SelectItem> itemsAgentesAccidente = new ArrayList<>();
    private ArrayList<SelectItem> itemsSubempresas = new ArrayList<>();
    private ArrayList<SelectItem> itemsCausaBasicas = new ArrayList<>();
    private ArrayList<SelectItem> itemsCausaInmediatas = new ArrayList<>();
    private ArrayList<SelectItem> itemsTipoAccidente = new ArrayList<>();
    private ArrayList<SelectItem> itemsEmpresas = new ArrayList<>();
    private ArrayList<SelectItem> itemsMeses = new ArrayList<>();
    

    public UIListas()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       empl = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       gestorMunicipio = new GestorMunicipio();
       gestorListas = new GestorListas();
       empleado.setResidencia(new Municipio()); 
       gestorEmpleado = new GestorEmpleado();
       empresa = new Empresa();
       subempresa = new SubEmpresa();
       
    }
  
    public List<String> listarMunicipiosPatron(String query) throws Exception {
        
        ArrayList<Municipio> listaMunicipiosLocal;
        listaMunicipiosLocal = getGestorListas().listarMunicipiosPatron(query);
        List<String> listaMun = new ArrayList<>();        
        for (Municipio m : listaMunicipiosLocal) {
            listaMun.add(m.getNombre());
        }        
        return listaMun;
    }     
       
    public ArrayList<SelectItem> getItemsEpss() throws Exception{
            
            try {
                gestorEps = new GestorEps();
                ArrayList<Eps> listaEpss;
                listaEpss = gestorListas.listarEpss();
                itemsEpss.clear();
                
                for (int i = 0; i < listaEpss.size(); i++) {                    
                        itemsEpss.add(new SelectItem(listaEpss.get(i).getCodigo(), listaEpss.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsEpss;    
    }

    public ArrayList<SelectItem> getItemsCargos() throws Exception{
            
            try {
                gestorCargo = new GestorCargo();
                ArrayList<Cargo> listaCargos;
                listaCargos = gestorListas.listarCargos();
                itemsCargos.clear();
                for (int i = 0; i < listaCargos.size(); i++) {                    
                        itemsCargos.add(new SelectItem(listaCargos.get(i).getCodigo(), listaCargos.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsCargos;    
    }
    
    public ArrayList<SelectItem> getItemsMeses() throws Exception{
            
            try {
                gestorMes = new GestorMes();
                ArrayList<Mes> listaMeses;
                listaMeses = gestorListas.listarMeses();
                itemsMeses.clear();
                for (int i = 0; i < listaMeses.size(); i++) {                    
                        itemsMeses.add(new SelectItem(listaMeses.get(i).getCodigo(), listaMeses.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMeses;    
    }
    
    public ArrayList<SelectItem> getItemsAños() throws Exception{
            
            try {
                gestorListas = new GestorListas();
                ArrayList<Año> listaAños;
                listaAños = gestorListas.listarAños();
                itemsMeses.clear();
                for (int i = 0; i < listaAños.size(); i++) {                    
                        itemsMeses.add(new SelectItem(listaAños.get(i).getAño()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMeses;    
    }



    public ArrayList<SelectItem> getItemsEciviles() throws Exception{
            
            try {
                gestorEcivil = new GestorEcivil();
                ArrayList<Ecivil> listaEciviles;
                listaEciviles = gestorListas.listarEciviles();
                itemsEciviles.clear();
                for (int i = 0; i < listaEciviles.size(); i++) {                    
                        itemsEciviles.add(new SelectItem(listaEciviles.get(i).getCodigo(), listaEciviles.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsEciviles;    
    }

    public ArrayList<SelectItem> getItemsSexos() throws Exception{
            
            try {
                gestorSexo = new GestorSexo();
                ArrayList<Sexo> listaSexos;
                listaSexos = gestorListas.listarSexos();
                itemsSexos.clear();
                for (int i = 0; i < listaSexos.size(); i++) {                    
                        itemsSexos.add(new SelectItem(listaSexos.get(i).getCodigo(), listaSexos.get(i).getNombre()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsSexos;    
    }
    
    public ArrayList<SelectItem> getItemsTipoIncapacidades() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoIncapacidad> listaTipoIncapacidades;
            listaTipoIncapacidades = gestorListas.listarTipoIncapaciadades();
            itemsTipoIncapacidades.clear();
            for (int i = 0; i < listaTipoIncapacidades.size(); i++) {                    
                    itemsTipoIncapacidades.add(new SelectItem(listaTipoIncapacidades.get(i).getCodigo(), listaTipoIncapacidades.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoIncapacidades;    
    }
    
    public ArrayList<SelectItem> getItemsTipoEvento() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoEvento> listaTipoEventos;
            listaTipoEventos = gestorListas.listarTipoEventos();
            itemsTipoEventos.clear();
            for (int i = 0; i < listaTipoEventos.size(); i++) {                    
                    itemsTipoEventos.add(new SelectItem(listaTipoEventos.get(i).getCodigo(), listaTipoEventos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoEventos;    
    }
    
    public ArrayList<SelectItem> getItemsClasificaiones() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Clasificacion> listaClasificaciones;
            listaClasificaciones = gestorListas.listarClasificaciones();
            itemsClasificaciones.clear();
            for (int i = 0; i < listaClasificaciones.size(); i++) {                    
                    itemsClasificaciones.add(new SelectItem(listaClasificaciones.get(i).getCodigo(), listaClasificaciones.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsClasificaciones;    
    }
    
    public ArrayList<SelectItem> getItemsIncapacidadesSi() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<IncapacidadSi> listaIncapacidadSis;
            listaIncapacidadSis = gestorListas.listarIncapacidadesSi();
            itemsIncapacidadesSi.clear();
            for (int i = 0; i < listaIncapacidadSis.size(); i++) {                    
                    itemsIncapacidadesSi.add(new SelectItem(listaIncapacidadSis.get(i).getCodigo(), listaIncapacidadSis.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsIncapacidadesSi;    
    }

    public ArrayList<SelectItem> getItemsTipoAccidentes() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoAccidente> listaTipoAccidentes;
            listaTipoAccidentes = gestorListas.listarTipoAccidente();
            itemsTipoAccidente.clear();
            for (int i = 0; i < listaTipoAccidentes.size(); i++) {                    
                    itemsTipoAccidente.add(new SelectItem(listaTipoAccidentes.get(i).getCodigo(), listaTipoAccidentes.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

            return itemsTipoAccidente;    
    }
    
    public ArrayList<SelectItem> getItemsParteAfectadas() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<ParteAfectada> listaParteAfectadas;
            listaParteAfectadas = gestorListas.listarParteAfectada();
            itemsParteAfectada.clear();
            for (int i = 0; i < listaParteAfectadas.size(); i++) {                    
                    itemsParteAfectada.add(new SelectItem(listaParteAfectadas.get(i).getCodigo(), listaParteAfectadas.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsParteAfectada;    
    }
    
    public ArrayList<SelectItem> getItemsTipoLesiones() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<TipoLesion> listaTipoLesiones;
            listaTipoLesiones = gestorListas.listarTipoLesiones();
            itemsTipoLesiones.clear();
            for (int i = 0; i < listaTipoLesiones.size(); i++) {                    
                    itemsTipoLesiones.add(new SelectItem(listaTipoLesiones.get(i).getCodigo(), listaTipoLesiones.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsTipoLesiones;    
    }
    
    public ArrayList<SelectItem> getItemsRiesgos() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Riesgo> listaRiesgos;
            listaRiesgos = gestorListas.listarRiesgos();
            itemsRiesgos.clear();
            for (int i = 0; i < listaRiesgos.size(); i++) {                    
                    itemsRiesgos.add(new SelectItem(listaRiesgos.get(i).getCodigo(), listaRiesgos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsRiesgos;    
    }
    
    public ArrayList<SelectItem> getItemsMecanismos() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<Mecanismo> listaMecanismos;
            listaMecanismos = gestorListas.listarMecanismos();
            itemsMecanismos.clear();
            for (int i = 0; i < listaMecanismos.size(); i++) {                    
                    itemsMecanismos.add(new SelectItem(listaMecanismos.get(i).getCodigo(), listaMecanismos.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsMecanismos;    
    }
    
    public ArrayList<SelectItem> getItemsAgentesAccidente() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<AgenteAccidente> listaAgenteAccidentes;
            listaAgenteAccidentes = gestorListas.listarAgentesAccidente();
            itemsAgentesAccidente.clear();
            for (int i = 0; i < listaAgenteAccidentes.size(); i++) {                    
                    itemsAgentesAccidente.add(new SelectItem(listaAgenteAccidentes.get(i).getCodigo(), listaAgenteAccidentes.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsAgentesAccidente;    
    }
    
    public ArrayList<SelectItem> getItemsCausaBasicas() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<CausaBasica> listaCausaBasicas;
            listaCausaBasicas = gestorListas.listarCausaBasicas();
            itemsCausaBasicas.clear();
            for (int i = 0; i < listaCausaBasicas.size(); i++) {                    
                    itemsCausaBasicas.add(new SelectItem(listaCausaBasicas.get(i).getCodigo(), listaCausaBasicas.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsCausaBasicas;    
    }
    
    public ArrayList<SelectItem> getItemsCausaInmediata() throws Exception{
            
        try {
            gestorListas= new GestorListas();
            ArrayList<CausaInmediata> listaCausaInmediata;
            listaCausaInmediata = gestorListas.listarCausaInmediatas();
            itemsCausaInmediatas.clear();
            for (int i = 0; i < listaCausaInmediata.size(); i++) {                    
                    itemsCausaInmediatas.add(new SelectItem(listaCausaInmediata.get(i).getCodigo(), listaCausaInmediata.get(i).getNombre()));
                }                        
            }
        catch (Exception ex) {
                    Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
            return itemsCausaInmediatas;    
    }

    
    public ArrayList<SelectItem> getItemsEmpresas() throws Exception{
            
            try {
                gestorListas = new GestorListas();
                ArrayList<Empresa> listaEmpresa;
                listaEmpresa = gestorListas.listarEmpresas();
                itemsEmpresas.clear();
                for (int i = 0; i < listaEmpresa.size(); i++) {
                        itemsEmpresas.add(new SelectItem(listaEmpresa.get(i).getNitempresa(), listaEmpresa.get(i).getNombre()));                        
                    }
                }
            catch (Exception ex) {
                        
                }
            
                return itemsEmpresas;    
    }
    
        public ArrayList<SelectItem> getItemsSubEmpresas() throws Exception{
            
            String nitempresa = empresa.getNitempresa();
            
            if ( nitempresa == null) {
                
                try {
                    gestorListas = new GestorListas();
                    ArrayList<SubEmpresa> listaSubEmpresa;
                    listaSubEmpresa = gestorListas.listarSubempresas();
                    itemsSubempresas.clear();
                    for (int i = 0; i < listaSubEmpresa.size(); i++) {                    
                            itemsSubempresas.add(new SelectItem(listaSubEmpresa.get(i).getNitsubempresa(), listaSubEmpresa.get(i).getNombre()));
                        }                        
                    }
                catch (Exception ex) {
                            Logger.getLogger(UIListas.class.getName()).log(Level.SEVERE, null, ex);
                    }

            }else{
                
                try {
                    gestorListas = new GestorListas();
                    ArrayList<SubEmpresa> listaSubEmpresa;
                    listaSubEmpresa = gestorListas.listarSubempresas(nitempresa);
                    itemsSubempresas.clear();
                    for (int i = 0; i < listaSubEmpresa.size(); i++) {                    
                            itemsSubempresas.add(new SelectItem(listaSubEmpresa.get(i).getNitsubempresa(), listaSubEmpresa.get(i).getNombre()));
                        }                        
                    }
                catch (Exception ex) {
                            Logger.getLogger(UIListas.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            
                return itemsSubempresas;    
    }
        
    public String convertirMeses(String fecha){
        
        String nomfecha = "";
        
                switch (fecha) {
            case "01":
                nomfecha="ENERO";
                break;
            case "02":
                nomfecha="FEBRERO";
                break;
            case "03":
                nomfecha="MARZO";
                break;
            case "04":
                nomfecha="ABRIL";
                break;
            case "05":
                nomfecha="MAYO";
                break;
            case "06":
                nomfecha="JUNIO";    
                break;
            case "07":
                nomfecha="JULIO";
                break;
            case "08":
                nomfecha="AGOSTO";
                break;
            case "09":
                nomfecha="SEPTIEMBRE";
                break;
            case "10":
                nomfecha="OCTUBRE";
                break;
            case "11":
                nomfecha="NOVIEMBRE";
                break;
            case "12":
                nomfecha="DICIEMBRE";                            
                break;
        } 
                
                return nomfecha;
    }  

    public void setItemsMecanismos(ArrayList<SelectItem> itemsMecanismos) {
        this.itemsMecanismos = itemsMecanismos;
    }   

    public void setItemsTipoIncapacidades(ArrayList<SelectItem> itemsTipoIncapacidades) {
        this.itemsTipoIncapacidades = itemsTipoIncapacidades;
    }

    public GestorMunicipio getGestorMunicipio() {
        return gestorMunicipio;
    }

    public void setGestorMunicipio(GestorMunicipio gestorMunicipio) {
        this.gestorMunicipio = gestorMunicipio;
    }

    public GestorListas getGestorListas() {
        return gestorListas;
    }

    public void setGestorListas(GestorListas gestorListas) {
        this.gestorListas = gestorListas;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
}