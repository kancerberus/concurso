/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorMunicipio;
import java.io.Serializable;
import controlador.GestorEmpleado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Municipio;
import util.Utilidades;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
/**
 *
 * @author Andres
 */
public class UIEmpleado implements Serializable {

    public Empleado empleado;    
    private Ausentismo ausentismo;
    public Utilidades util = new Utilidades();
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory empl;
    private GestorEmpleado gestorEmpleado; 
    private GestorMunicipio gestorMunicipio;       
    private ExpressionFactory ef;
    private List<Empleado> listaEmpleado;
    private List<Empleado> filteredlistaEmpleado; 
    

    public UIEmpleado()  throws Exception {
       contextoJSF = FacesContext.getCurrentInstance();
       contextoEL = contextoJSF.getELContext(); 
       empl = contextoJSF.getApplication().getExpressionFactory();
       empleado = new Empleado();
       ausentismo = new Ausentismo();
       gestorMunicipio = new GestorMunicipio();
       empleado.setResidencia(new Municipio()); 
       gestorEmpleado = new GestorEmpleado();
       
    }
            
    public void guardarEmpleado() throws Exception{    

        Boolean invalido = false;        

        //ingreso de informacion al gestor
        gestorEmpleado = new GestorEmpleado();
        
        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);

        try {
            //verificar que todas las cajas este llenas
            
            
            
            if (empleado.getCedula() == null) {
                invalido = true;
            }
            if (empleado.getNombres() == null){
                invalido = true;
            }
            if (empleado.getApellidos() == null){
                invalido = true;
            }
            if (empleado.getFecha_nac() == null){
                invalido = true;
            }
            if (empleado.getMunicipio() == null){
                invalido = true;
            }
            if (empleado.getEps() == null){
                invalido = true;
            }            
            if (empleado.getEps() == null){
                invalido = true;
            }         
            if (empleado.getCargo() == null){
                invalido = true;
            }       
            if (empleado.getEcivil() == null){
                invalido = true;
            }         
            if (empleado.getSexo() == null){
                invalido = true;
            }            
            
            
            if (!invalido) {
                    Integer resultado = gestorEmpleado.guardarEmpleado(empleado,nitsesion);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El empleado fue creado de manera exitosa !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! El registro no pudo ser almacenado !!");
                    }
            } else {
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
            }
    }
    
    public List<Empleado> getListaEmpleado() {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);          

        try {
            listaEmpleado = gestorEmpleado.listarEmpleados(nitsesion);
        } catch (Exception ex) {
            Logger.getLogger(UIEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return (listaEmpleado);
    }
    
    public void buscarempleadoAdmin() throws Exception {
    
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        String cedula = empleado.getCedula();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        
        
        empleado = gestorEmpleado.buscarempleadoAdmin(cedula); 
        
        if(empleado == null){                                                
            util.mostrarMensaje("La cedula no existe");
            empleado = new Empleado();
        }     
    }
    
    public void modificarSubempresaempleado() throws Exception{    

        String msg = null;
        
        //ingreso de informacion al gestor
        //gestorAusentismo = new GestorAusentismo();               

        try {
                    Integer resultado=gestorEmpleado.modificarSubempresaempleado(empleado);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! cambio guardado !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! El cambio no pudo ser guardado !!");
                    }

            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! El cambio no pudo ser guardado !!");               
            }
    }
    
    public void buscarEmpleado() throws Exception {
    
        GestorEmpleado gestorEmpleado = new GestorEmpleado();
        String cedula = empleado.getCedula();

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        ef = contextoJSF.getApplication().getExpressionFactory();
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);        
        
        empleado = gestorEmpleado.buscarEmpleado(cedula, nitsesion); 
        
        if(empleado == null){                                                
            util.mostrarMensaje("La cedula no existe");
            empleado = new Empleado();
        }
    }
    
    public void modificarEmpleado() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        //gestorAusentismo = new GestorAusentismo();               

        try {
            //verificar que todas las cajas este llenas
            if (empleado.getNombres() == null) {
                msg = "El nombre esta vacio!";
                invalido = true;                
            }
            if (empleado.getApellidos() == null){
                msg = "Los apellidos estan vacios!";
                invalido = true;
            }

            if (invalido == false) {
                    Integer resultado=gestorEmpleado.modificarEmpleado(empleado);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! Modificacion guardada !!");
                        empleado = new Empleado();                    
                    } else {
                        util.mostrarMensaje("!! La modificacion no pudo ser guardada !!");
                    }
            } else {
                    util.mostrarMensaje("Hay campos requeridos sin diligenciar.");                
                }
            } catch (Exception ex) {
                util.mostrarMensaje(ex.getMessage());
                util.mostrarMensaje("!! La modificacion no pudo ser guardada !!");               
            }
    }    
    
    
    public void limpiarEmpleado() { 
        empleado = new Empleado();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }     

    public List<Empleado> getFilteredlistaEmpleado() {
        return filteredlistaEmpleado;
    }

    public void setFilteredlistaEmpleado(List<Empleado> filteredlistaEmpleado) {
        this.filteredlistaEmpleado = filteredlistaEmpleado;
    }
    
}
