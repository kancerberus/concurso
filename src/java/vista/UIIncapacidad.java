/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorEmpleado;
import controlador.GestorIncapacidad;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Empleado;
import modelo.Motivo;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Año;
import modelo.Cie10;
import modelo.GrupoCie10;
import modelo.Incapacidad;
import modelo.Mes;
import modelo.SubEmpresa;
import modelo.TipoIncapacidad;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author Andres
 */
public class UIIncapacidad implements Serializable {

    private String cedula;
    private Empleado empleado;
    private Incapacidad incapacidad;
    private Mes mes;
    private Año ano;
    private Motivo motivo;
    private SubEmpresa subempresa;
    private String cie10;
    
    DecimalFormat formato1 = new DecimalFormat("#,###.00");   
    private float totarl=0;
    private float toteps=0;
    private float totempleador=0;
    private float tottrabajador=0;
    private float total=0;
    private float totalhs=0;
    private float totaleps=0;
    private float totalarl=0;
    private float totaltrabajador=0;
    private float totalem=0;    
    private float totalsubt=0;
    
    Boolean todos;
    Boolean selec;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory reg;
    private GestorIncapacidad gestorIncapacidad;
    private ArrayList<SelectItem> itemsMotivos = new ArrayList<>();
    private List<Incapacidad> listaIncapacidad;
    private List<Incapacidad> listaIncapacidadanomes;
    private List<Incapacidad> listaincapacidadEmpresa;
    private List<Incapacidad> listIncapacidadEmpleado;
    private List<Incapacidad> pieincapacidadEmpresa;
    private List<Incapacidad> pieincapacidadEmpleado;
    
    private List<Incapacidad> pieporSubempresa;
    private List<Incapacidad> filteredlistaIncapacidad;
    private PieChartModel pieincapacidadanomesEmpresa;
    private PieChartModel pieincapacidadanomesEmpleado;
    
    private PieChartModel pieSubempresa;
    private ExpressionFactory ef;    
    private String minutos;

    public UIIncapacidad()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       reg = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       incapacidad = new Incapacidad();
       incapacidad.setTipoIncapacidad(new TipoIncapacidad());
       mes = new Mes();
       ano = new Año();
       gestorIncapacidad = new GestorIncapacidad();
       pieincapacidadanomesEmpresa = new PieChartModel();
       pieSubempresa = new PieChartModel();
       pieincapacidadanomesEmpleado = new PieChartModel();
       
    }

    public void BuscarEmpleado() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);        
        
        if(em!=null && em.isEstado() == true){
           incapacidad.setEmpleado(em);           
        }
        else {
            util.mostrarMensaje("La cedula no existe....");
            empleado =new Empleado();
        }
    }
    
    public void buscarEActualizacion() throws Exception {
    
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        Integer resultado = gestorEmpleado.validarEmpleadoActualizacion(cedula,nitsesion); 
        
        //mensaje si resultado viene con 0 error en la consulta

    }
    

        
    /*public void buscarIncapacidad() throws Exception {
    
        GestorIncapacidad gestorIncapacidad = new GestorIncapacidad();
        String cod_registro = incapacidad.getCod_registro();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        
        incapacidad = gestorIncapacidad.buscarIncapacidad(cod_registro, nitsesion); 
        
        if(incapacidad == null){                                                
            util.mostrarMensaje("El registro no existe o se encuentra cerrado");
            incapacidad = new Incapacidad();
        }        
    }*/
    
    public void dialogoIncapacidad(){        
        if(incapacidad.getMotivoincapacidad().equals("2") || incapacidad.getMotivoincapacidad().equals("1")){
            RequestContext req= RequestContext.getCurrentInstance();
            req.execute("PF('dlg2').show();");
        }
        
    }

    /*public void guardarRegistro() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorIncapacidad = new GestorIncapacidad();

        try {
            //verificar que todas las cajas este llenas           
            if (incapacidad.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;              
            }
            if (incapacidad.getMotivoincapacidad().equals("Seleccione...")) {
                msg = "Seleccione un Motivo";
                invalido = true;
            }            

            if (invalido == false) {                
                    float min=0;
                    min=Float.parseFloat(minutos);
                    float horas= Float.parseFloat(incapacidad.getTiempo_horas());
                    horas+= min;
                    incapacidad.setTiempo_horas(Float.toString(horas));
                    Integer resultado = gestorIncapacidad.guardarIncapacidad(incapacidad);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro fue realizado de manera exitosa !!");
                        incapacidad = new Incapacidad();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
            }
    }*/
    
    /*public void modificarIncapacidad() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorIncapacidad = new GestorIncapacidad();               

        try {
            //verificar que todas las cajas este llenas
            if (incapacidad.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;                
            }
            if (incapacidad.getMotivoincapacidad().equals("Seleccione...")) {
                msg = "Seleccione un Motivo";
                invalido = true;
            }

            if (invalido == false) {
                    Integer resultado = gestorIncapacidad.modificarIncapacidad(incapacidad);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro ha sido cerrado !!");
                        incapacidad = new Incapacidad();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
            }
    }*/
    
    public void limpiarIncapacidad() {       
        incapacidad = new Incapacidad(); 
        empleado = new Empleado();        
        itemsMotivos.clear();
    }
    
    /*public List<Incapacidad> getListaIncapacidad() {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);          

        try {
            listaIncapacidad = gestorIncapacidad.listarIncapacidads(nitsesion);
        } catch (Exception ex) {
            Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return (listaIncapacidad);
    }*/
  
    
    /*public void incapacidadAnomes() { 
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);
        
        //revisar checkbox
        String selmesano = null;
        String selano = null;
        
        if(todos == true){
           selano = ano.getAño();   
        }else{            
           selmesano = (ano.getAño())+"/"+(mes.getCodigo());            
        }

        try {            
            listaIncapacidadanomes = gestorIncapacidad.incapacidadAnomes(nitsesion,selmesano,selano);
            setListaIncapacidadanomes(listaIncapacidadanomes);                                  
            totarl=0; toteps=0; totempleador=0; tottrabajador=0; total=0;
            
            
            //Carga totales incapacidad ano y mes 
            for(int i=0; i < listaIncapacidadanomes.size(); i++){
                totarl +=  listaIncapacidadanomes.get(i).getArl();                
                toteps += listaIncapacidadanomes.get(i).getEps();
                totempleador += listaIncapacidadanomes.get(i).getEmpleador();
                tottrabajador += listaIncapacidadanomes.get(i).getTrabajador();
                total += listaIncapacidadanomes.get(i).getTotal();                               
            }   

            
        } catch (Exception ex) {
            Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }*/
    //muestra reporte de incapacidads por cedula de empleado
    /*public void incapacidadAnomesEmpleado() {             
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);          
        //revisar checkbox
        String selmesdesde = null;
        String selmeshasta = null;
        String selano = null; 
        String selmotivo=null;
        
            
        if(todos == true){
            selano = ano.getAño();   
        }else{
           selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
           selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
        }        
        selmotivo = incapacidad.getMotivoincapacidad();
        
        try {       

            listIncapacidadEmpleado = gestorIncapacidad.incapacidadanomesEmpleado(cedula, nitem, selmesdesde, selmeshasta, selano, selmotivo);
            pieincapacidadEmpleado = gestorIncapacidad.pieincapacidadanomesEmpleado(cedula, nitem, selmesdesde, selmeshasta, selano, selmotivo);
            setListIncapacidadEmpleado(listIncapacidadEmpleado);
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0;
            //calculo de totales eps,arl,empleador,trabajador y total 
            
            
            for(int i=0; i < listIncapacidadEmpleado.size(); i++){
                totaleps += listIncapacidadEmpleado.get(i).getEps();
                totalarl += listIncapacidadEmpleado.get(i).getArl();
                totalem += listIncapacidadEmpleado.get(i).getEmpleador();
                totaltrabajador += listIncapacidadEmpleado.get(i).getTrabajador();
                totalsubt += listIncapacidadEmpleado.get(i).getTotalsube();
                
            }
            
            
            
            //Carga Pie Totales Incapacidad Vs Tipo
            pieincapacidadanomesEmpleado.clear();
            pieincapacidadanomesEmpleado.set("EPS", pieincapacidadEmpleado.get(0).getEps());
            pieincapacidadanomesEmpleado.set("ARL", pieincapacidadEmpleado.get(0).getArl());
            pieincapacidadanomesEmpleado.set("TRABAJADOR", pieincapacidadEmpleado.get(0).getTrabajador());
            pieincapacidadanomesEmpleado.set("EMPLEADOR", pieincapacidadEmpleado.get(0).getEmpleador());
            pieincapacidadanomesEmpleado.setTitle(pieincapacidadEmpleado.get(0).getEmpleado().getNombres()+" "+ pieincapacidadEmpleado.get(0).getEmpleado().getApellidos()+"   TOTAL AUSENTISMOS ");
            pieincapacidadanomesEmpleado.setLegendPosition("w");
            pieincapacidadanomesEmpleado.setShowDataLabels(true);
            pieincapacidadanomesEmpleado.setDiameter(300);
            
    
        } catch (Exception ex) {
            Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }*/

    
    /*public void incapacidadAnomesEmpresa() { 
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitem = (String) ef.createValueExpression(contextoEL, "#{listasBean.empresa.nitempresa}", String.class).getValue(contextoEL);  
        String nitsubem = (String) ef.createValueExpression(contextoEL, "#{listasBean.subempresa.nitsubempresa}", String.class).getValue(contextoEL);  
        //revisar checkbox
        String selmesdesde = null;
        String selmeshasta = null;
        String selano = null; 
        DecimalFormat formato2 = new DecimalFormat("#.##");
        String selmotivo=null;
            
        if(todos == true){
            selano = ano.getAño();   
        }else{
           selmesdesde = (ano.getAño())+"/"+(mes.getDesde());
           selmeshasta = (ano.getAño()+"/"+(mes.getHasta()));           
        }        
        if(nitsubem == ""){
         nitsubem = null;         
        }
        
        selmotivo = incapacidad.getMotivoincapacidad();
        
        try {       

            listaincapacidadEmpresa = gestorIncapacidad.incapacidadanomesEmpresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            pieincapacidadEmpresa = gestorIncapacidad.pieincapacidadanomesEmpresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            pieporSubempresa = gestorIncapacidad.pieporSubempresa(nitem, nitsubem, selmesdesde, selmeshasta, selano, selmotivo);
            setListaincapacidadEmpresa(listaincapacidadEmpresa); 
            
            
            
            totaleps=0;totalarl=0;totalem=0; totaltrabajador=0; totalsubt=0;
            
            for(int i=0; i < listaincapacidadEmpresa.size(); i++){                
                totaleps += listaincapacidadEmpresa.get(i).getEps();
                totalarl += listaincapacidadEmpresa.get(i).getArl();
                totalem += listaincapacidadEmpresa.get(i).getEmpleador();
                totaltrabajador += listaincapacidadEmpresa.get(i).getTrabajador();
                totalsubt += listaincapacidadEmpresa.get(i).getTotalsube();                
                
            }            
            
            //Carga Pie Totales Incapacidad Vs Tipo
            pieincapacidadanomesEmpresa.clear();
            pieincapacidadanomesEmpresa.set("EPS", pieincapacidadEmpresa.get(0).getEps());
            pieincapacidadanomesEmpresa.set("ARL", pieincapacidadEmpresa.get(0).getArl());
            pieincapacidadanomesEmpresa.set("TRABAJADOR", pieincapacidadEmpresa.get(0).getTrabajador());
            pieincapacidadanomesEmpresa.set("EMPLEADOR", pieincapacidadEmpresa.get(0).getEmpleador());
            pieincapacidadanomesEmpresa.setTitle(pieincapacidadEmpresa.get(0).getEmpresa().getNombre()+"   TOTALES AUSENTISMO VS TIPO");
            pieincapacidadanomesEmpresa.setLegendPosition("w");
            pieincapacidadanomesEmpresa.setShowDataLabels(true);
            pieincapacidadanomesEmpresa.setDiameter(300);
            
            
            //Carga Totales Incapacidad vs Subemrpesa
                pieSubempresa.clear();
                for(int i=0; i < pieporSubempresa.size(); i++){    

                    pieSubempresa.setTitle(pieporSubempresa.get(i).getEmpresa().getNombre()+"   TOTALES AUSENTISMO VS SUBEMPRESA");
                    pieSubempresa.set(pieporSubempresa.get(i).getSubempresa().getNombre(),pieporSubempresa.get(i).getTotalsube());
                    pieSubempresa.setDiameter(300);
                    pieSubempresa.setLegendPosition("w");
                    pieSubempresa.setShowDataLabels(true);
                }
            
            
        } catch (Exception ex) {
            Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    */
    
    public void cargarDiferenciaDias() throws Exception{
        try {
            
            
            
        } catch (Exception ex) {
        Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void onDateSelect(SelectEvent event) {        
        
        if(incapacidad.getFecha_final()==null){
            incapacidad.setFecha_final(new Date());
        }
        int dias=(int) ((incapacidad.getFecha_final().getTime()-incapacidad.getFecha_inicial().getTime())/86400000);
        incapacidad.setTiempoDias(dias+1);                            
        
        
    }
    
    public void limpiarAusentismo() {       
        incapacidad = new Incapacidad(); 
        empleado = new Empleado();        
        itemsMotivos.clear();
    }
        
     
    public void guardarRegistro() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorIncapacidad = new GestorIncapacidad();

        try {
            //verificar que todas las cajas este llenas           
            if (incapacidad.getEmpleado().getCedula() == null) {
                msg = "La cédula esta vacía!";
                invalido = true;              
            }
            if (incapacidad.getMotivoincapacidad().equals("Seleccione...")) {
                msg = "Seleccione un Motivo";
                invalido = true;
            }            

            if (invalido == false) {                                    
                    Integer resultado = gestorIncapacidad.guardarIncapacidad(incapacidad);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El registro fue realizado de manera exitosa !!");
                        incapacidad = new Incapacidad();
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El registro no pudo ser almacenado !!");               
            }
    }   
    
 
    public void cargarCie10() throws Exception{
        try {
            gestorIncapacidad=new GestorIncapacidad();
            incapacidad.setCie10(new Cie10());         
            cie10=cie10.toUpperCase();
            incapacidad.setCie10(gestorIncapacidad.cargarCie10(cie10));
            if(incapacidad.getCie10()==null){                                
                incapacidad.setCie10(new Cie10());           
                incapacidad.getCie10().setNombre("Cie10 no encontrado");
                incapacidad.getCie10().setGrupoCie10(new GrupoCie10());
                incapacidad.getCie10().getGrupoCie10().setNombre("Cie10 no encontrado");
            }
            
            
        } catch (Exception ex) {
        Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public ArrayList<SelectItem> getItemsMotivos() throws Exception{
            
            try {
                gestorIncapacidad = new GestorIncapacidad();
                ArrayList<Motivo> listaMotivos;
                itemsMotivos.clear();
                listaMotivos = gestorIncapacidad.listarMotivosIncapacidad();                                
                
                for (int i = 0; i < listaMotivos.size(); i++) {                    
                        itemsMotivos.add(new SelectItem(listaMotivos.get(i).getCodigo(), listaMotivos.get(i).getNombrem(), listaMotivos.get(i).getTipo()));
                    }                        
                }
            catch (Exception ex) {
                        Logger.getLogger(UIIncapacidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                return itemsMotivos;    
    }    

    public String getCie10() {
        return cie10;
    }

    public void setCie10(String cie10) {
        this.cie10 = cie10;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public GestorIncapacidad getGestorIncapacidad() {
        return gestorIncapacidad;
    }

    public void setGestorIncapacidad(GestorIncapacidad gestorIncapacidad) {
        this.gestorIncapacidad = gestorIncapacidad;
    }

    public List<Incapacidad> getListaIncapacidad() {
        return listaIncapacidad;
    }

    public void setListaIncapacidad(List<Incapacidad> listaIncapacidad) {
        this.listaIncapacidad = listaIncapacidad;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }    

    public PieChartModel getPieincapacidadanomesEmpleado() {
        return pieincapacidadanomesEmpleado;
    }

    public void setPieincapacidadanomesEmpleado(PieChartModel pieincapacidadanomesEmpleado) {
        this.pieincapacidadanomesEmpleado = pieincapacidadanomesEmpleado;
    }   
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public List<Incapacidad> getListIncapacidadEmpleado() {
        return listIncapacidadEmpleado;
    }

    public void setListIncapacidadEmpleado(List<Incapacidad> listIncapacidadEmpleado) {
        this.listIncapacidadEmpleado = listIncapacidadEmpleado;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

    public Incapacidad getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(Incapacidad incapacidad) {
        this.incapacidad = incapacidad;
    }

    public List<Incapacidad> getFilteredlistaIncapacidad() {
        return filteredlistaIncapacidad;
    }

    public void setFilteredlistaIncapacidad(List<Incapacidad> filteredlistaIncapacidad) {
        this.filteredlistaIncapacidad = filteredlistaIncapacidad;
    }
    
    public List<Incapacidad> getListaincapacidadEmpresa() {
        return listaincapacidadEmpresa;
    }

    public void setListaincapacidadEmpresa(List<Incapacidad> listaincapacidadEmpresa) {
        this.listaincapacidadEmpresa = listaincapacidadEmpresa;
    }

    public List<Incapacidad> getPieporSubempresa() {
        return pieporSubempresa;
    }

    public void setPieporSubempresa(List<Incapacidad> pieporSubempresa) {
        this.pieporSubempresa = pieporSubempresa;
    }

    public Mes getMes() {
        return mes;
    }

    public void setMes(Mes mes) {
        this.mes = mes;
    }

    public Año getAno() {
        return ano;
    }

    public void setAno(Año ano) {
        this.ano = ano;
    } 

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }

    public List<Incapacidad> getListaIncapacidadanomes() {
        return listaIncapacidadanomes;
    }

    public void setListaIncapacidadanomes(List<Incapacidad> listaIncapacidadanomes) {
        this.listaIncapacidadanomes = listaIncapacidadanomes;
    }    

    public String getTotarl() {
        return formato1.format(totarl);
    }

    public void setTotarl(float totarl) {
        this.totarl = totarl;
    }

    public String getToteps() {
        return formato1.format(toteps);
    }

    public void setToteps(float toteps) {
        this.toteps = toteps;
    }

    public String getTotempleador() {
        return formato1.format(totempleador);
    }

    public void setTotempleador(float totempleador) {
        this.totempleador = totempleador;
    }

    public String getTottrabajador() {
        return formato1.format(tottrabajador);
    }

    public void setTottrabajador(float tottrabajador) {
        this.tottrabajador = tottrabajador;
    }

    public String getTotal() {
        return formato1.format(total);
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getTotalhs() {
        return formato1.format(totalhs);
    }

    public void setTotalhs(float totalhs) {
        this.totalhs = totalhs;
    }

    public String getTotaleps() {
        return formato1.format(totaleps);
    }

    public void setTotaleps(float totaleps) {
        this.totaleps = totaleps;
    }

    public String getTotalarl() {
        return formato1.format(totalarl);
    }

    public void setTotalarl(float totalarl) {
        this.totalarl = totalarl;
    }

    public String getTotaltrabajador() {
        return formato1.format(totaltrabajador);
    }

    public void setTotaltrabajador(float totaltrabajador) {
        this.totaltrabajador = totaltrabajador;
    }

    public String getTotalem() {
        return formato1.format(totalem);
    }

    public void setTotalem(float totalem) {
        this.totalem = totalem;
    }

    public List<Incapacidad> getPieincapacidadEmpresa() {
        return pieincapacidadEmpresa;
    }

    public void setPieincapacidadEmpresa(List<Incapacidad> pieincapacidadEmpresa) {
        this.pieincapacidadEmpresa = pieincapacidadEmpresa;
    }

    public List<Incapacidad> getPieincapacidadEmpleado() {
        return pieincapacidadEmpleado;
    }

    public void setPieincapacidadEmpleado(List<Incapacidad> pieincapacidadEmpleado) {
        this.pieincapacidadEmpleado = pieincapacidadEmpleado;
    }
    public SubEmpresa getSubempresa() {
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {
        this.subempresa = subempresa;
    }
    
    public String getTotalsubt() {        
        
        return formato1.format(totalsubt);
    }

    public void setTotalsubt(float totalsubt) {
        this.totalsubt = totalsubt;
    }
    public PieChartModel getPieincapacidadanomesEmpresa() {
        return pieincapacidadanomesEmpresa;
    }

    public void setPieincapacidadanomesEmpresa(PieChartModel pieincapacidadanomesEmpresa) {
        this.pieincapacidadanomesEmpresa = pieincapacidadanomesEmpresa;
    } 

    public PieChartModel getPieSubempresa() {
        return pieSubempresa;
    }

    public void setPieSubempresa(PieChartModel pieSubempresa) {
        this.pieSubempresa = pieSubempresa;
    }
}