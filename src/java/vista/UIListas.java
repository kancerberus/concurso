/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorEmpleado;
import controlador.GestorCargo;
import controlador.GestorEcivil;
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
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Sexo;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Empresa;
import modelo.SubEmpresa;
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
    private GestorEcivil gestorEcivil;
    private GestorCargo gestorCargo;
    private GestorSexo gestorSexo;
    private GestorMunicipio gestorMunicipio;
    private GestorListas gestorListas;
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
                        Logger.getLogger(UIListas.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(UIListas.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsSexos;    
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