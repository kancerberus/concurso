/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import bd.UtilJSF;
import controlador.GestorConcurso;
import controlador.GestorEmpleado;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import modelo.Actividad;
import modelo.AdjuntosActividad;
import modelo.CalificacionActividad;
import util.Utilidades;
import modelo.Concurso;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoConcurso;
import modelo.GrupoConcursoParticipantes;
import modelo.SubEmpresa;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.UtilArchivo;




/**
 *
 * @author Andres
 */
@ManagedBean(name = "concursoBean")
@SessionScoped
public class UIConcurso implements Serializable {
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private String cedula;
    private Concurso concurso= new Concurso();    
    private GrupoConcurso grupoConcurso=new GrupoConcurso();    
    private GrupoConcursoParticipantes grupoParticipantes=new GrupoConcursoParticipantes();
    private String codGrupo;    
    private Empleado empleado=new Empleado();
    private Actividad actividad;   
    private AdjuntosActividad adjActividad;
    private Empresa empresa=new Empresa();  
    private CalificacionActividad calificacionActividad;    
    private Boolean captain;        
    private SubEmpresa subempresa;
    private Integer puntajeAcum;
    private UploadedFile file;  
    private StreamedContent fileDownload;
    private Date hoy=new Date();
    
    
    
    private ArrayList<SelectItem> listActividades=new ArrayList<>();
    private ArrayList<Actividad> listActividadess=new ArrayList<>();
    private ArrayList<AdjuntosActividad> listaAdjuntos=new ArrayList<>();
    private ArrayList<SelectItem> listActividadesJueces=new ArrayList<>();
    private ArrayList<Actividad> listActividadesJuecess=new ArrayList<>();
    private ArrayList<SelectItem> listaConcurso=new ArrayList<>();    
    private ArrayList<Concurso> listaConcursoss =new ArrayList<>();
    private ArrayList<SelectItem> listaConcursosGerente=new ArrayList<>();
    private ArrayList<SelectItem> listaConcursosEmpresas=new ArrayList<>();
    private ArrayList<SelectItem> listGruposConcurso=new ArrayList<>();
    private ArrayList<SelectItem> listConcursosSubempresa=new ArrayList<>();
    private ArrayList<SelectItem> listaGruposConcursos=new ArrayList<>();
    private ArrayList<GrupoConcurso> listaGrupoConcursoss=new ArrayList<>();
    private ArrayList<SelectItem> listaGruposConcursosSubempresa=new ArrayList<>();    
    private ArrayList<SelectItem> listaSubEmpresas=new ArrayList<>();
    private ArrayList<SubEmpresa> listaSubEmpresasnit=new ArrayList<>();
    private ArrayList<SelectItem> listGruposParticipantes=new ArrayList<>();
    private ArrayList<GrupoConcursoParticipantes> listGrupoParticipantess=new ArrayList<>();
    private ArrayList<CalificacionActividad> listCalificacionesActividad=new ArrayList<>();
    private ArrayList<CalificacionActividad> listCalificacionesActividadJueces=new ArrayList<>();
    private ArrayList<GrupoConcurso> listTablaPosiciones=new ArrayList<>();
    
    private GestorConcurso gestorConcurso;
    
    public Utilidades util = new Utilidades();    
    private ExpressionFactory ef;
    public UIConcurso()  throws Exception { 
       adjActividad=new AdjuntosActividad();
       concurso=new Concurso();
       concurso.setEmpresa(new Empresa());      
       grupoConcurso=new GrupoConcurso();
       grupoConcurso.setSubempresa(new SubEmpresa());
       grupoParticipantes.setEmpleado(new Empleado());       
       grupoParticipantes.setGrupoConcurso(new GrupoConcurso());
       actividad=new Actividad();     
       listaAdjuntos=new ArrayList<>();
       listaConcursosEmpresas=new ArrayList<>();
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext();        
       listCalificacionesActividadJueces=new ArrayList<>();
       subempresa=new SubEmpresa();
       calificacionActividad=new CalificacionActividad();
       this.limpiarActividad();
       
    }  
    
    @PostConstruct
    public void init() {
        try {            
            this.getListaConcurso();            
            this.limpiarActividad();
        } catch (Exception ex) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleClose(CloseEvent event) throws Exception {                        
    }     
    
    public void crearConcurso() throws Exception{        
        Boolean invalido = false;
        String msg = null;        

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();
        concurso.setEmpresa(empresa);
        try {
            //verificar que todas las cajas este llenas           
            if (concurso.getNombre().equals("") ) {
                msg = "Concurso sin nombre!";
                invalido = true;              
            }
         

            if (invalido == false) {       
                    Long codConcurso=gestorConcurso.nextval(GestorConcurso.CAMPAÑA_CONCURSO_COD_CONCURSO_SEQ);
                    concurso.setCodConcurso(codConcurso.toString());
                    concurso.getEmpresa().setNombre(gestorConcurso.cargarNombreEmpresa(empresa.getNitempresa()));
                    
                    
                    
                    
                    
                    Integer resultado = gestorConcurso.guardarConcurso(concurso);
                    

                    if (resultado > 0) {
                        util.mostrarMensaje("!! Concurso guardado !!");
                        this.getListaConcurso();
                        concurso = new Concurso();                        
                    } else {
                        util.mostrarMensaje("!! El concurso no pudo ser almacenado !!");
                    }
            } else {                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! El concurso no pudo ser almacenado !!");               
        }
    }     

    public void crearGrupoConcurso() throws Exception{        
        Boolean invalido = false;
        String msg = null;
        File carpeta;

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();
        this.getListaGruposConcursos();
         
        Date hoy =new Date();
        try {
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            
            grupoConcurso.setSubempresa(new SubEmpresa());
            grupoConcurso.setConcurso(concurso);
            grupoConcurso.getSubempresa().setNitsubempresa(nitsesion);
            //verificar que todas las cajas este llenas           
            if (grupoConcurso.getNombre().equals("") || grupoConcurso.getConcurso().getCodConcurso().equals("")) {
                util.mostrarMensaje("Grupo sin nombre!");
                invalido = true;              
            }
            concurso.getEmpresa();
            if (hoy.after(concurso.getFecha_limite_insc())) {
                util.mostrarMensaje("Fecha Limite De inscripcion Vencida!");                
                concurso=new Concurso();
                this.getListaConcursosEmpresas();                       
                invalido = true;              
            }
         

            if (invalido == false) {  
                    Long codGrupoSeq= gestorConcurso.nextval(GestorConcurso.CAMPAÑA_GRUPO_CONCURSO_COD_GRUPO_SEQ);
                    grupoConcurso.setCodGrupo(codGrupoSeq.toString());
                    Integer resultado = gestorConcurso.guardarGrupoConcurso(grupoConcurso);
                            
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Equipo guardado !!");
                        this.getListaGruposConcursos();
                        grupoConcurso = new GrupoConcurso();
                        this.getListaConcursosEmpresas();
                    } else {
                        util.mostrarMensaje("!! El Equipo no pudo ser almacenado !!");
                    }
            } else {                
                util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                     
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);            
            util.mostrarMensaje("!! El Equipo no pudo ser almacenado !!");               
        }
    }
    
    
    public void subirItemConcurso() throws Exception {         
        concurso = (Concurso) UtilJSF.getBean("varConcursos");            
        this.getListActividades();
    }
    
    public void subirItemGrupoConcurso() throws Exception {         
        grupoParticipantes.setGrupoConcurso((GrupoConcurso) UtilJSF.getBean("varEquipos")); 
        codGrupo=grupoParticipantes.getGrupoConcurso().getCodGrupo();
        concurso=grupoParticipantes.getGrupoConcurso().getConcurso();
        this.getListGruposParticipantes();
        
    }    
    
    public void subirItemActividad() {         
        actividad= (Actividad) UtilJSF.getBean("varActividades");               
    }          
    
    public void subirItemActividadAdjuntos() {    
        try {
            actividad= (Actividad) UtilJSF.getBean("varActividadAdjuntos");         
            for(int i=0;i<getListaGruposConcursos().size();i++){
                if(grupoConcurso.getCodGrupo().equals(listaGrupoConcursoss.get(i).getCodGrupo())){
                    grupoConcurso.setSubempresa(new SubEmpresa(listaGrupoConcursoss.get(i).getSubempresa().getNitsubempresa(),listaGrupoConcursoss.get(i).getSubempresa().getNombre() ));
                    grupoConcurso.setConcurso(new Concurso("", "", new Empresa(listaGrupoConcursoss.get(i).getConcurso().getEmpresa().getNitempresa(), listaGrupoConcursoss.get(i).getConcurso().getEmpresa().getNombre()) , null, true,null));
                    grupoConcurso.setNombre(listaGrupoConcursoss.get(i).getNombre());
                }
            }
            actividad.setGrupoConcurso(grupoConcurso);
            this.cargarAdjuntos();
        
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }   
    
    
    
    public void eliminarAdjuntos(){
        try {
            String ruta = "C:/Concursos/"+actividad.getGrupoConcurso().getConcurso().getEmpresa().getNombre()+"/"+
                    actividad.getConcurso().getNombre()+"/"+grupoConcurso.getSubempresa().getNombre()+"/"+
                    grupoConcurso.getNombre()+"/"+actividad.getNombre();
            
            gestorConcurso=new GestorConcurso();
            
            gestorConcurso.eliminarAdjuntos(actividad);
            File f = new File(ruta);
             File[] ficheros = f.listFiles();
             if(ficheros != null){
                for(int x=0;x<ficheros.length;x++){
                    ficheros[x].delete();                 
                }             
                f.delete();
             }
            
            util.mostrarMensaje("!! Adjuntos Eliminados !!");
            this.cargarAdjuntos();
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void guardarCalificacion() throws Exception{        
        Boolean invalido = false;
        
        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();
        
        try {
            //verificar que todas las cajas este llenas           
                    

            if (invalido == false) {       
                                        
                    Integer resultado = gestorConcurso.guardarCalificacion(calificacionActividad);
                    
                    if (resultado > 0) {                                               
                        util.mostrarMensaje("!! Calificacion guardada !!");
                        cargarCalificaciones();
                        
                      
                    } else {
                        util.mostrarMensaje("!! El Calificacion no pudo ser almacenado !!");
                    }
            } else {                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! El concurso no pudo ser almacenado !!");               
        }
    }
    
    public void subirItemCalificacion() {         
        calificacionActividad= (CalificacionActividad) UtilJSF.getBean("varCalificaciones");       
    }
    
    public void subirItemActividadAdjuntosJueces() {            
         try {
             calificacionActividad= (CalificacionActividad) UtilJSF.getBean("varCalificaciones");
             getListConcursosSubempresa();
            this.cargarAdjuntosCalificaciones();
         } catch (Exception e) {
             Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
         }
         
    }
    
    public void agregarEmpleado() throws Exception{        
        Boolean invalido = false;
        String msg = null;        
        Date hoy=new Date();        
        
        try {            
            
            //verificar que todas las cajas este llenas           
            if (empleado.getCedula() == null || codGrupo==null || empleado.getNombres().equals("")) {
                msg = "Ingrese Cedula!";
                invalido = true;
            }           
            
            
            this.grupoParticipantes.setEmpleado(empleado);
            this.grupoParticipantes.getGrupoConcurso().setCodGrupo(codGrupo);
            
            Integer participantes = gestorConcurso.cargarParticipantes(concurso.getCodConcurso());
            
            if(listGruposParticipantes.size()>=(participantes)){
                util.mostrarMensaje("Cupo Completado.");                
                invalido = true;
            }
            
            if (invalido == false) {
                    Integer resultado = gestorConcurso.agregarEmpleado(grupoParticipantes);
                    if(grupoParticipantes.isCapitan()==true){
                        grupoParticipantes.setCapitan(false);
                        captain=false;
                    }
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Participante agregado !!");
                        empleado = new Empleado();
                        this.getListGruposParticipantes();
                    } else {
                        util.mostrarMensaje("!! El participante no se pudo agregar !!");
                    }
            } else {
                
                    util.mostrarMensaje("El participante no se pudo agregar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }
    }   
    
    public ArrayList<SelectItem> getListaConcurso() throws Exception{    
        try {            
            listaConcurso=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            listaConcursoss=new ArrayList<>();
            listaConcursoss = gestorConcurso.cargarConcursos();
            listaConcurso.clear();
            
            for (int i = 0; i < listaConcursoss.size(); i++) {                    
                        listaConcurso.add(new SelectItem(listaConcursoss.get(i).getCodConcurso(), listaConcursoss.get(i).getNombre()));
                    }                                   
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
        return listaConcurso;
    }
    
    public ArrayList<SelectItem> getListaGruposConcursos() throws Exception{
        
        try {            
            listaGrupoConcursoss.clear();
            listaGruposConcursos=new ArrayList<>();
            gestorConcurso=new GestorConcurso();                        
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);  
            listaGrupoConcursoss=new ArrayList<>();
            listaGrupoConcursoss=gestorConcurso.cargarGruposConcursos(nitsesion, concurso.getCodConcurso());           
            
            listaGruposConcursos.clear();
            
            for (int i = 0; i < listaGrupoConcursoss.size(); i++) {                    
                        listaGruposConcursos.add(new SelectItem(listaGrupoConcursoss.get(i).getCodGrupo(), listaGrupoConcursoss.get(i).getNombre(),listaGrupoConcursoss.get(i).getSubempresa().getNombre()));
                    }
            
                if(concurso.getCodConcurso()!=null){
                    for(int i=0;i<listaConcurso.size();i++){
                        if(concurso.getCodConcurso().equals(listaConcursoss.get(i).getCodConcurso())){                    
                            concurso.setNombre(listaConcursoss.get(i).getNombre());                        
                            concurso.setCodConcurso(listaConcursoss.get(i).getCodConcurso());
                            concurso.setEmpresa(listaConcursoss.get(i).getEmpresa());
                            concurso.setFecha_limite_insc(listaConcursoss.get(i).getFecha_limite_insc());
                            concurso.setParticipantes(listaConcursoss.get(i).getParticipantes());                                                    
                        }
                    }
                }
            
                }
         catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);            
        } 
        return listaGruposConcursos;
    }

    
    
    
    
    public ArrayList<SelectItem> getListaConcursosGerente() throws Exception{
        
        try {
            
            listaConcursosGerente=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            
            ArrayList<Concurso> listaConcursosG;
            listaConcursosG=gestorConcurso.cargarConcursosGerente(nitsesion);
            listaConcursosGerente.clear();
            for (int i = 0; i < listaConcursosG.size(); i++) {                    
                        listaConcursosGerente.add(new SelectItem(listaConcursosG.get(i).getCodConcurso(), listaConcursosG.get(i).getNombre()));
                    }                        
                
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }
        return listaConcursosGerente;
    }
    
    public ArrayList<SelectItem> getListActividades() throws Exception{
        try {
            actividad=new Actividad();
            this.limpiarActividad();
            gestorConcurso=new GestorConcurso();     
            listActividadess=new ArrayList<>();
            
            if(concurso==null){
                concurso=new Concurso();
                concurso=grupoConcurso.getConcurso();
            }
            
            listActividadess=gestorConcurso.cargarActividades(concurso.getCodConcurso());
            listActividades.clear();
            
            for (int i = 0; i < listActividadess.size(); i++) {                    
                        listActividades.add(new SelectItem(listActividadess.get(i).getCodActividad(), listActividadess.get(i).getNombre()));
                    }
            this.cargarCalificacionesJueces();
            this.listCalificacionesActividadJueces.clear();
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
        return listActividades;
        
    }
    
    
    
    
    public ArrayList<SelectItem> getListActividadesJueces() throws Exception{
        try {
            
            listActividadesJueces=new ArrayList<>();
            gestorConcurso=new GestorConcurso();            
            listActividadesJuecess=new ArrayList<>();
            listActividadesJuecess=gestorConcurso.cargarActividadesJueces(grupoConcurso.getCodGrupo());
            listActividadesJueces.clear();
            for (int i = 0; i < listActividadesJueces.size(); i++) {                    
                listActividadesJueces.add(new SelectItem(listActividadesJuecess.get(i).getCodActividad(), listActividadesJuecess.get(i).getNombre()));
            }
            
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }  
        return listActividadesJueces;
    }
    
    public void cargarCalificaciones(){
        try {
            puntajeAcum=0;
            listCalificacionesActividadJueces=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            
            listCalificacionesActividadJueces.addAll(gestorConcurso.cargarLIstaCalificacionesEquipo(grupoConcurso.getCodGrupo()));
            
            for(int i=0; i<listCalificacionesActividadJueces.size();i++){
                puntajeAcum += listCalificacionesActividadJueces.get(i).getCalificacion();
            }
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
        }
    }
    
    public void cargarCalificacionesJueces(){
        try {
            puntajeAcum=0;
            listCalificacionesActividadJueces=new ArrayList<>();
            gestorConcurso=new GestorConcurso();
            
            listCalificacionesActividadJueces.addAll(gestorConcurso.cargarLIstaCalificacionesEquipo(grupoConcurso.getCodGrupo()));
            
            for(int i=0; i<listCalificacionesActividadJueces.size();i++){
                puntajeAcum += listCalificacionesActividadJueces.get(i).getCalificacion();
            }
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
        }
    }
    
    public void cargarTablaPosiciones(){
        try {
            
            listTablaPosiciones=new ArrayList<>();            
            gestorConcurso=new GestorConcurso();
            
            listTablaPosiciones.addAll(gestorConcurso.cargarTablaPosiciones(concurso.getCodConcurso()));
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public ArrayList<SelectItem> getListGruposParticipantes() throws Exception{
        try {
            captain=false;
            listGruposParticipantes=new ArrayList<>();
            gestorConcurso=new GestorConcurso();            
            grupoConcurso.setCodGrupo(codGrupo);
            listGrupoParticipantess=new ArrayList<>();
            listGrupoParticipantess=gestorConcurso.cargarListaGrupoParticipantes(grupoConcurso.getCodGrupo());
            listGruposParticipantes.clear();
            
            for (int i = 0; i < listGrupoParticipantess.size(); i++) {                    
                        listGruposParticipantes.add(new SelectItem(listGrupoParticipantess.get(i).getCodGrupoParticipantes()));
                    }
                        
            for(int i=0;i<=listGrupoParticipantess.size()-1;i++){
                if(listGrupoParticipantess.get(i).isCapitan()==true){
                    captain=true;
                }
                        
            }
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }   
        return listGruposParticipantes;
    }
    
    public void addMessage() {
        String summary = concurso.isEstado() ? "Activo" : "Inactivo";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }
    
    public void crearActividad() throws Exception{        
        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        gestorConcurso = new GestorConcurso();

        try {
            actividad.setConcurso(concurso);
            //verificar que todas las cajas este llenas           
            if (actividad.getNombre().equals("")) {
                msg = "Actividad sin nombre!";
                invalido = true;              
            }
            if(actividad.getConcurso().getCodConcurso()==null){
                msg = "Cree Concurso!";
                invalido = true;              
            }
         

            if (invalido == false) {   
                    Long codActividad=gestorConcurso.nextval(GestorConcurso.CAMPAÑA_ACTIIVIDAD_COD_ACTIVIDAD_SEQ);
                    actividad.setCodActividad(codActividad.toString());
                    actividad.getNombre().trim();
                    Integer resultado = gestorConcurso.guardarActividad(actividad);
                    this.getListActividades();
                    if (resultado > 0) {
                        util.mostrarMensaje("!! Activdad guardada !!");
                        actividad = new Actividad();                        
                    } else {
                        util.mostrarMensaje("!! La actividad no pudo ser almacenada !!");
                    }
            } else {
                
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
            }            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
            util.mostrarMensaje(e.getMessage());
            util.mostrarMensaje("!! La actividad no pudo ser almacenado !!");               
        }
    }
    
    
    public void buscarEmpleado() throws Exception{
        try {
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
            GestorEmpleado gestorEmpleado = new GestorEmpleado();
            Empleado em = gestorEmpleado.validarEmpleado(cedula,nitsesion);


            if(em!=null && em.isEstado() == true){
                empleado=em; 
                grupoParticipantes.setGrupoConcurso(grupoConcurso);
            }
            else {
                util.mostrarMensaje("La cedula no existe....");
                empleado =new Empleado();
            }
            this.getListGruposParticipantes();
        } catch (Exception ex) {
        Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    public ArrayList<SelectItem> getListaConcursosEmpresas() throws Exception{
            
            try {
                this.listaConcursosEmpresas= new ArrayList<>();
                grupoConcurso=new GrupoConcurso();
                concurso=new Concurso();
                listActividadess=new ArrayList<>();
                gestorConcurso = new GestorConcurso();
                contextoJSF = FacesContext.getCurrentInstance();
                contextoEL = contextoJSF.getELContext();
                ef = contextoJSF.getApplication().getExpressionFactory();
                listActividadess=new ArrayList<>();

                String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);                        
                
                
                listaConcursoss=new ArrayList<>();
                listaConcursoss = gestorConcurso.listarConcursos(nitsesion);
                listaConcursosEmpresas.clear();                
                
                for (int i = 0; i < listaConcursoss.size(); i++) {
                        listaConcursosEmpresas.add(new SelectItem(listaConcursoss.get(i).getCodConcurso(), listaConcursoss.get(i).getNombre(), listaConcursoss.get(i).getFecha_limite_insc().toString()));                        
                    }
                    
                
                }
            catch (Exception ex) {                        
                    Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
                }          
                return listaConcursosEmpresas;                
                
    }
    
    public void limpiarActividad(){
        this.listaConcursosEmpresas=new ArrayList<>();
        this.listActividades=new ArrayList<>();
        this.listActividadess=new ArrayList<>();
        this.listCalificacionesActividad=new ArrayList<>();
        this.listaGruposConcursos= new ArrayList<>();
    }
    
    public ArrayList<SelectItem> getListaGruposConcursosSubempresa() throws Exception{
        try {
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            gestorConcurso = new GestorConcurso();
            listaGruposConcursosSubempresa = new ArrayList<>(); 
            ArrayList<GrupoConcurso> listaEquiposSubempresas;
            listaEquiposSubempresas=gestorConcurso.cargarListaEquiposSubempresa(subempresa.getNitsubempresa());
            listaGruposConcursos.clear();
            
            for (int i = 0; i < listaEquiposSubempresas.size(); i++) {                    
                        listaGruposConcursosSubempresa.add(new SelectItem(listaEquiposSubempresas.get(i).getCodGrupo(), listaEquiposSubempresas.get(i).getNombre()));
                    } 
            this.cargarCalificaciones();
        } catch (Exception ex) {
              Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaGruposConcursosSubempresa;
    }
    
    
    
    public ArrayList<SelectItem> getListaSubEmpresas() throws Exception{
        try {                                   
            contextoJSF = FacesContext.getCurrentInstance();
            contextoEL = contextoJSF.getELContext();
            ef = contextoJSF.getApplication().getExpressionFactory();
            gestorConcurso = new GestorConcurso();
            
            String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);                                    
            
            String nitem=gestorConcurso.cargarNitEmpresa(nitsesion);  
            
            listaSubEmpresasnit=new ArrayList<>();
            listaSubEmpresasnit=gestorConcurso.cargarListaSubempresas(nitem);
            listaSubEmpresas.clear();              
            
            
            
            for (int i = 0; i < listaSubEmpresasnit.size(); i++) {
                listaSubEmpresas.add(new SelectItem(listaSubEmpresasnit.get(i).getNitsubempresa(), listaSubEmpresasnit.get(i).getNombre()));                        
            }            
        } catch (Exception ex) {
              Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listaSubEmpresas;
        
    }
    
    public ArrayList<SelectItem> getListaGrupoConcurso() throws Exception{
            
            try {
                this.listActividades= new ArrayList<>();
                gestorConcurso = new GestorConcurso();                
                
                listaGrupoConcursoss=new ArrayList<>();                
                listaGrupoConcursoss = gestorConcurso.listarGrupoConcursos(concurso.getCodConcurso(), subempresa.getNitsubempresa());
                listGruposConcurso.clear();
                for (int i = 0; i < listaGrupoConcursoss.size(); i++) {                    
                        listGruposConcurso.add(new SelectItem(listaGrupoConcursoss.get(i).getCodGrupo(), listaGrupoConcursoss.get(i).getNombre()));
                    }
                }
            catch (Exception ex) {
                 Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);       
                }
            
                return listGruposConcurso;
                
    }
    
    
    public void upload(FileUploadEvent event) throws Exception {
        String msg=null;
        try {            
            
            String ruta = "C:/Concursos/"+actividad.getGrupoConcurso().getConcurso().getEmpresa().getNombre()+"/"+
                    actividad.getConcurso().getNombre()+"/"+grupoConcurso.getSubempresa().getNombre()+"/"+
                    grupoConcurso.getNombre()+"/"+actividad.getNombre();
            
            gestorConcurso=new GestorConcurso();
            
            File carpeta = new File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
            
            Integer contArch=gestorConcurso.contarArchivos(actividad);            
            
            if(contArch>=5){
                util.mostrarMensaje("Numero de archivos permitido");
            }else{
                UtilArchivo.guardarStream(ruta.trim() + File.separator + event.getFile().getFileName(), event.getFile().getInputstream());
                this.file = event.getFile();
                gestorConcurso.guardarDatosAdjuntos(file, actividad, ruta);
            }
            this.cargarAdjuntos();
        } catch (IOException ex) {            
        }

    }

    public StreamedContent getFileDownload() {        
        try {                     
            adjActividad= (AdjuntosActividad) UtilJSF.getBean("varVerAdjuntos");
            
            String archivo=adjActividad.getDireccion()+"/"+adjActividad.getNombre();
            InputStream stream = new FileInputStream(archivo);
            fileDownload = new DefaultStreamedContent(stream, null, adjActividad.getNombre());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileDownload;
    }
    
    public void limpiarActividades() throws Exception{          
        listCalificacionesActividadJueces=new ArrayList<>();
        listCalificacionesActividadJueces.clear();
        calificacionActividad.setCodGrupo("");
        this.guardarCalificacion();
    }

    public ArrayList<SelectItem> getListConcursosSubempresa() {
        try {                            
            
                concurso=new Concurso();
                gestorConcurso = new GestorConcurso();                
                listActividadess=new ArrayList<>();
                
                listaConcursoss=new ArrayList<>();
                listaConcursoss = gestorConcurso.listarConcursos(subempresa.getNitsubempresa());
                listConcursosSubempresa=new ArrayList<>();
                
                
                for (int i = 0; i < listaConcursoss.size(); i++) {
                        listConcursosSubempresa.add(new SelectItem(listaConcursoss.get(i).getCodConcurso(), listaConcursoss.get(i).getNombre(), listaConcursoss.get(i).getFecha_limite_insc().toString()));                        
                    }
                
                if(concurso.getCodConcurso()!=null){
                    for(int i=0;i<=listaConcurso.size()-1;i++){
                        if(concurso.getCodConcurso().equals(listaConcursoss.get(i).getCodConcurso())){                    
                        concurso.setCodConcurso(listaConcursoss.get(i).getCodConcurso());
                        concurso.setEmpresa(listaConcursoss.get(i).getEmpresa());
                        concurso.setFecha_limite_insc(listaConcursoss.get(i).getFecha_limite_insc());
                        concurso.setParticipantes(listaConcursoss.get(i).getParticipantes());
                        concurso.setNombre(listaConcursoss.get(i).getNombre());
                        this.getListaGrupoConcurso();                        
                        }
                    }
                } 
                
                }
            catch (Exception e) {                        
                Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
                }          
                
                return listConcursosSubempresa;
    }

    
    public void cargarAdjuntos(){
        try {
            gestorConcurso=new GestorConcurso();
            listaAdjuntos=new ArrayList<>();
            
            listaAdjuntos.addAll(gestorConcurso.cargarListaAdjuntos(actividad));
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void cargarAdjuntosCalificaciones(){
        try {  
            
            gestorConcurso=new GestorConcurso();
            listaAdjuntos=new ArrayList<>();
            
            listaAdjuntos.addAll(gestorConcurso.cargarListaAdjuntosCalificaciones(calificacionActividad));
            
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void uploadLogo(FileUploadEvent event) throws Exception {
        String msg=null;
        try {            
            this.getConcurso();
            gestorConcurso=new GestorConcurso();
            
            String ruta = "C:/Concursos/"+concurso.getEmpresa().getNombre()+"/"+
                    concurso.getNombre();
            
            //gestorConcurso.guardarLogo(file, concurso, ruta);
            
            
            File carpeta = new File(ruta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }
                       
            UtilArchivo.guardarStream(ruta + File.separator + event.getFile().getFileName(), event.getFile().getInputstream());
            this.file = event.getFile();
            
        } catch (IOException ex) {            
        }

    }

    public ArrayList<GrupoConcurso> getListTablaPosiciones() {
        return listTablaPosiciones;
    }

    public void setListTablaPosiciones(ArrayList<GrupoConcurso> listTablaPosiciones) {
        this.listTablaPosiciones = listTablaPosiciones;
    }

    public ArrayList<SubEmpresa> getListaSubEmpresasnit() {        
        return listaSubEmpresasnit;
    }
    
   /*public StreamedContent mostrarImagen(String logonom){        
        concurso= (Concurso) UtilJSF.getBean("varConcursos");
        ByteArrayOutputStream out=null;        
        InputStream logo=null;
        
        
        if(!logonom.equals("")){
            out=traerArchivo(concurso.getLogo(), logonom);
            logo=new ByteArrayInputStream(out.toByteArray());
            return new DefaultStreamedContent(logo);                        
        }
        if(logonom.equals("")){            
            out=traerArchivo("C:\\Concursos\\COBIENESTAR - RIOSUCIO\\concurso2\\", "a.png");
            logo=new ByteArrayInputStream(out.toByteArray());
            return new DefaultStreamedContent(logo);                        
        }        
        else{        
            out=traerArchivo("C:\\Concursos\\COBIENESTAR - RIOSUCIO\\concurso1\\", "logo.png");
            logo=new ByteArrayInputStream(out.toByteArray());
            return new DefaultStreamedContent(logo);                        
        
        }
        
        
    }*/
    
    public ByteArrayOutputStream traerArchivo(String ruta, String nom){
        ByteArrayOutputStream out=null;
        String path=ruta+nom;
        
        if(path.equals(null)){
            out=new ByteArrayOutputStream();
        }
        InputStream in=null;
        try {
            File remoteFile=new File(path);
            in=new BufferedInputStream(new FileInputStream(remoteFile));
            out=new  ByteArrayOutputStream((int) remoteFile.length());
            byte[] buffer=new byte [4096];
            int len=0;
            while ((len=in.read(buffer,0,buffer.length))!=-1){
                out.write(buffer,0,len);
            }
        out.flush();
        } catch (Exception e) {
            Logger.getLogger(UIConcurso.class.getName()).log(Level.SEVERE, null, e);
        }
        return out;
    }
    
    public void setListaSubEmpresasnit(ArrayList<SubEmpresa> listaSubEmpresasnit) {
        this.listaSubEmpresasnit = listaSubEmpresasnit;
    }

    public SubEmpresa getSubempresa() {        
        return subempresa;
    }

    public void setSubempresa(SubEmpresa subempresa) {                        
        this.subempresa = subempresa;                        
    }

    public ArrayList<CalificacionActividad> getListCalificacionesActividadJueces() {
        return listCalificacionesActividadJueces;
    }

    public void setListCalificacionesActividadJueces(ArrayList<CalificacionActividad> listCalificacionesActividadJueces) {
        this.listCalificacionesActividadJueces = listCalificacionesActividadJueces;
    }

    public AdjuntosActividad getAdjActividad() {
        return adjActividad;
    }

    public void setAdjActividad(AdjuntosActividad adjActividad) {
        this.adjActividad = adjActividad;
    }
    
    public ArrayList<AdjuntosActividad> getListaAdjuntos() {
        return listaAdjuntos;
    }

    public void setListaAdjuntos(ArrayList<AdjuntosActividad> listaAdjuntos) {
        this.listaAdjuntos = listaAdjuntos;
    }

    public void setListConcursosSubempresa(ArrayList<SelectItem> listConcursosSubempresa) {
        this.listConcursosSubempresa = listConcursosSubempresa;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Integer getPuntajeAcum() {
        return puntajeAcum;
    }

    public void setPuntajeAcum(Integer puntajeAcum) {
        this.puntajeAcum = puntajeAcum;
    }

    public ArrayList<CalificacionActividad> getListCalificacionesActividad() {
        return listCalificacionesActividad;
    }

    public void setListCalificacionesActividad(ArrayList<CalificacionActividad> listCalificacionesActividad) {
        this.listCalificacionesActividad = listCalificacionesActividad;
    }

    public ArrayList<Concurso> getListaConcursoss() throws Exception {
        this.getListaConcurso();
        return listaConcursoss;
    } 

    public void setListaConcursoss(ArrayList<Concurso> listaConcursoss) {
        this.listaConcursoss = listaConcursoss;
    }

    public void setListaGruposConcursosSubempresa(ArrayList<SelectItem> listaGruposConcursosSubempresa) {
        this.listaGruposConcursosSubempresa = listaGruposConcursosSubempresa;
    }

    public void setListaSubEmpresas(ArrayList<SelectItem> listaSubEmpresas) {
        this.listaSubEmpresas = listaSubEmpresas;
    }

    public ArrayList<GrupoConcursoParticipantes> getListGrupoParticipantess() {
        return listGrupoParticipantess;
    }

    public void setListGrupoParticipantess(ArrayList<GrupoConcursoParticipantes> listGrupoParticipantess) {
        this.listGrupoParticipantess = listGrupoParticipantess;
    }
    
    public Boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    public ArrayList<Actividad> getListActividadesJuecess() {
        return listActividadesJuecess;
    }

    public void setListActividadesJuecess(ArrayList<Actividad> listActividadesJuecess) {
        this.listActividadesJuecess = listActividadesJuecess;
    }

    public ArrayList<GrupoConcurso> getListaGrupoConcursoss() {
        return listaGrupoConcursoss;
    }

    public void setListaGrupoConcursoss(ArrayList<GrupoConcurso> listaGrupoConcursoss) {
        this.listaGrupoConcursoss = listaGrupoConcursoss;
    }

    public CalificacionActividad getCalificacionActividad() {
        return calificacionActividad;
    }

    public void setCalificacionActividad(CalificacionActividad calificacionActividad) {
        this.calificacionActividad = calificacionActividad;
    }    

    public FacesContext getContextoJSF() {
        return contextoJSF;
    }

    public void setContextoJSF(FacesContext contextoJSF) {
        this.contextoJSF = contextoJSF;
    }

    public ELContext getContextoEL() {
        return contextoEL;
    }

    public void setContextoEL(ELContext contextoEL) {
        this.contextoEL = contextoEL;
    }

    public ArrayList<Actividad> getListActividadess() {
        return listActividadess;
    }

    public void setListActividadess(ArrayList<Actividad> listActividadess) {
        this.listActividadess = listActividadess;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }

    public ExpressionFactory getEf() {
        return ef;
    }

    public void setEf(ExpressionFactory ef) {
        this.ef = ef;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setListaConcursosEmpresas(ArrayList<SelectItem> listaConcursosEmpresas) {
        this.listaConcursosEmpresas = listaConcursosEmpresas;
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

    public ArrayList<SelectItem> getListGruposConcurso() {
        return listGruposConcurso;
    }

    public void setListGruposConcurso(ArrayList<SelectItem> listGruposConcurso) {
        this.listGruposConcurso = listGruposConcurso;
    }

    public GrupoConcursoParticipantes getGrupoParticipantes() {
        return grupoParticipantes;
    }

    public void setGrupoParticipantes(GrupoConcursoParticipantes grupoParticipantes) {
        this.grupoParticipantes = grupoParticipantes;
    }
    
    public GrupoConcurso getGrupoConcurso() {
        return grupoConcurso;
    }

    public void setGrupoConcurso(GrupoConcurso grupoConcurso) {
        this.grupoConcurso = grupoConcurso;
    }

    public GestorConcurso getGestorConcurso() {
        return gestorConcurso;
    }

    public void setGestorConcurso(GestorConcurso gestorConcurso) {
        this.gestorConcurso = gestorConcurso;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Concurso getConcurso() {
        if(concurso == null){
            concurso = new Concurso();
        }
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }
    
}