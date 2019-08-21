/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Usuario;
import bd.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import modelo.Menu;
import modelo.Perfil;

/**
 *
 * @author Andres
 */
public class GestorUsuario extends Gestor {

    public GestorUsuario() throws Exception {
        super();
    }

    public Usuario validarUsuario(String usuario, String clave) throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.validarUsuario(usuario, clave);
        } finally {
            cerrarConexion();
        }
    }
    
        
    public Integer modificarUsuario(Usuario usuario) throws Exception{
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.modificarUsuario(usuario);
        } finally {
            cerrarConexion();
        }
    }

    public List<Menu> listarOpcionesMenu(String usuario) throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.listarOpcionesMenu(usuario);
        } finally {
            cerrarConexion();
        }
    }
    
    public Usuario buscarUsuario(String nomusuario) throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.buscarUsuario(nomusuario);
            } finally {
            cerrarConexion();
        }
    } 
    
    public Integer crearUsuario(Usuario usuario) throws Exception{
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.crearUsuario(usuario);
        } finally {
            cerrarConexion();
        }
    }
    
    public ArrayList<Perfil> listarPerfiles() throws Exception {
        try {
            abrirConexion();
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
            return usuarioDAO.listarPerfiles();
        } finally {
            cerrarConexion();
        }        
    }
}
