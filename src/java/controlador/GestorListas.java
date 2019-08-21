/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.ListasDAO;
import bd.SubempresaDAO;
import java.util.ArrayList;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Empresa;
import modelo.Municipio;
import modelo.Sexo;
import modelo.SubEmpresa;

/**
 *
 * @author Andres
 */

public class GestorListas extends Gestor{
    
    public GestorListas() throws Exception{
        super();
    }
    
    public ArrayList<Municipio> listarMunicipiosPatron(String patron) throws Exception{
       try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarMunicipiosPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Municipio consultarMunicipioPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.consultarMunicipioPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
        
    public ArrayList<Cargo> listarCargos() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarCargos();
        } finally {
            cerrarConexion();
        }        
    }
      
    public ArrayList<Ecivil> listarEciviles() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarEciviles();
        } finally {
            cerrarConexion();
        }        
    }

    public ArrayList<Sexo> listarSexos() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarSexos();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Empresa> listarEmpresas() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarEmpresas();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<SubEmpresa> listarSubempresas() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarSubEmpresas();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<SubEmpresa> listarSubempresas(String nitempresa) throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarSubEmpresas(nitempresa);
        } finally {
            cerrarConexion();
        }        
    }    
    
}
