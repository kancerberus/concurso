/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import controlador.GestorSubempresa;
import controlador.GestorAusentismo;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import modelo.SubEmpresa;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import modelo.Ausentismo;
import modelo.Año;
import modelo.Empresa;
import modelo.Mes;
import util.Utilidades;

/**
 *
 * @author diego
 */
public class UISubempresa implements Serializable {
    
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private GestorSubempresa gestorSubempresa;
    private GestorAusentismo gestorAusentismo;
    private ExpressionFactory sube;
    private SubEmpresa SubEmpresa;
    public Utilidades util = new Utilidades();
    private ExpressionFactory ef;
    Boolean todos;
    private Mes mes;
    private Año ano;
    private List<Ausentismo> listaAusentismoanomes;
    
    private double totarl=0;
    private double toteps=0;
    private double totempleador=0;
    private double total=0;


    public UISubempresa() throws Exception {

        contextoJSF = FacesContext.getCurrentInstance();
        contextoEL = contextoJSF.getELContext();
        sube = contextoJSF.getApplication().getExpressionFactory();
        gestorSubempresa = new GestorSubempresa();
        SubEmpresa = new SubEmpresa();
        
    }
    
    public void guardarSubEmpresa() throws Exception{    

        Boolean invalido = false;

        //ingreso de informacion al gestor
        gestorSubempresa = new GestorSubempresa();

        try {
            //verificar que todas las cajas este llenas
            
            if (SubEmpresa.getNitsubempresa()== null) {
                invalido = true;
            }
            if (SubEmpresa.getNombre()== null){
                invalido = true;
            }
            if (SubEmpresa.getDireccion()== null){
                invalido = true;
            }
            if (SubEmpresa.getMunicipio() == null){
                invalido = true;
            }
            if (SubEmpresa.getTelefono() == null){
                invalido = true;
            }
            if (SubEmpresa.getEmpresa() == null){
                invalido = true;
            }                        
            
            
            if (!invalido) {
                    Integer resultado = gestorSubempresa.guardarSubEmpresa(SubEmpresa);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! La SubEmpresa fue creada de manera exitosa !!");
                        SubEmpresa = new SubEmpresa();                    
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
    
    
    public SubEmpresa getSubEmpresa() {
        return SubEmpresa;
    }

    public void setSubEmpresa(SubEmpresa subEmpresa) {
        this.SubEmpresa = subEmpresa;
    }    

    public List<Ausentismo> getListaAusentismoanomes() {
        return listaAusentismoanomes;
    }

    public void setListaAusentismoanomes(List<Ausentismo> listaAusentismoanomes) {
        this.listaAusentismoanomes = listaAusentismoanomes;
    }   
}
