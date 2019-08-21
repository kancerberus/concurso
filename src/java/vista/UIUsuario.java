/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.GestorUsuario;
import java.io.Serializable;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import javax.el.ExpressionFactory;
import util.Utilidades;


/**
 *
 * @author francisco
 */
public class UIUsuario implements Serializable{
    private String nombre;
    private String clave;    
    private Usuario usuario;
    private FacesContext contextoJSF;
    private ELContext contextoEL;
    private ExpressionFactory reg;
    private GestorUsuario gestorUsuario;
    
    public Utilidades util = new Utilidades();



    public UIUsuario()  throws Exception {          
        gestorUsuario = new GestorUsuario();
        usuario = new Usuario(); 
       //llamar la funcion        
    }
    

    public void buscarUsuario() throws Exception {
           
        GestorUsuario gestorUsuario = new GestorUsuario();
        String nomusuario = usuario.getNomusuario();


        
        usuario = gestorUsuario.buscarUsuario(nomusuario); 
        
        if(usuario == null){                                                
            util.mostrarMensaje("El usuario no existe");
            usuario = new Usuario();
        }
    }
    
    public void modificarUsuario() throws Exception{    

        Boolean invalido = false;
        String msg = null;

        //ingreso de informacion al gestor
        //gestorAusentismo = new GestorAusentismo();               

        try {
            //verificar que todas las cajas este llenas
            if (usuario.getNombre() == null ) {
                msg = "El nombre esta vacio!";
                invalido = true;                
            }

            if (invalido == false) {
                
                    Integer resultado=gestorUsuario.modificarUsuario(usuario);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! Modificacion guardada !!");
                        usuario = new Usuario();                    
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
    
    
    public void crearUsuario() throws Exception{    

        Boolean invalido = false;

        //ingreso de informacion al gestor
        gestorUsuario = new GestorUsuario();

        try {
            //verificar que todas las cajas este llenas
            
            if (usuario.getNombre() == null) {
                invalido = true;
            }
            if (usuario.getNomusuario()== null){
                invalido = true;
            }
            if (usuario.getClave() == null){
                invalido = true;
            }
            if (usuario.getPerfil() == null){
                invalido = true;
            }
            
            
            if (!invalido) {
                    Integer resultado = gestorUsuario.crearUsuario(usuario);

                    if (resultado > 0) {
                        util.mostrarMensaje("!! El login fue creado de manera exitosa !!");
                        usuario = new Usuario();                   
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
    
    public void limpiarUsuario() { 
        usuario = new Usuario();
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Object getContextoJSF() {
        return contextoJSF;
    }   
    
    public GestorUsuario getGestorUsuario() {
        return gestorUsuario;
    }

    public void setGestorUsuario(GestorUsuario gestorUsuario) {
        this.gestorUsuario = gestorUsuario;
    }

    public Utilidades getUtil() {
        return util;
    }

    public void setUtil(Utilidades util) {
        this.util = util;
    }   
    
}
