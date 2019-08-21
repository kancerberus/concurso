/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import controlador.GestorAusentismo;
import controlador.GestorEmpresa;
import java.io.Serializable;
import java.util.List;

import javax.el.ELContext;
import modelo.Empresa;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import modelo.Ausentismo;
import modelo.Año;
import modelo.Mes;
import util.Utilidades;


/**
 *
 * @author diego
 */
public class UIEmpresa implements Serializable{
    
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private GestorEmpresa gestorempresa;
    private GestorAusentismo gestorAusentismo;
    
    private ExpressionFactory empre;
    private Empresa Empresa;
    public Utilidades util = new Utilidades();   


    public UIEmpresa() throws Exception {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        empre = contextoJSF.getApplication().getExpressionFactory();
        gestorempresa = new GestorEmpresa();
        Empresa = new Empresa();
        
    }
        
    public void guardarEmpresa() throws Exception{    

        Boolean invalido = false;

        //ingreso de informacion al gestor
        gestorempresa = new GestorEmpresa();

        try {
            //verificar que todas las cajas este llenas
            
            if (Empresa.getNitempresa()== null) {
                invalido = true;
            }
            if (Empresa.getNombre()== null){
                invalido = true;
            }
            if (Empresa.getDireccion()== null){
                invalido = true;
            }
            if (Empresa.getTelefono()== null){
                invalido = true;
            }
            if (Empresa.getMunicipio()== null){
                invalido = true;
            }                      
            
            
            if (!invalido) {
                    Integer resultado = gestorempresa.guardarEmpresa(Empresa);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! La Empresa fue creada de manera exitosa !!");
                        Empresa = new Empresa();                    
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
    
    
    public Empresa getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(Empresa Empresa) {
        this.Empresa = Empresa;
    } 
}