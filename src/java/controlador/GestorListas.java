/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.ListasDAO;
import bd.SubempresaDAO;
import java.util.ArrayList;
import modelo.Accidente;
import modelo.AgenteAccidente;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Empresa;
import modelo.Eps;
import modelo.Municipio;
import modelo.Sexo;
import modelo.SubEmpresa;
import modelo.Mes;
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
    
    public ArrayList<Eps> listarEpss() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarEpss();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<TipoIncapacidad> listarTipoIncapaciadades() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarTipoIncapaciadades();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<TipoEvento> listarTipoEventos() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarTipoEventos();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Clasificacion> listarClasificaciones() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarClasificaciones();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<IncapacidadSi> listarIncapacidadesSi() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarIncapacidadesSis();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<TipoAccidente> listarTipoAccidente() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarTipoAccidentes();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<ParteAfectada> listarParteAfectada() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarParteAfectadas();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<TipoLesion> listarTipoLesiones() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarTipoLesiones();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Riesgo> listarRiesgos() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarRiesgos();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Mecanismo> listarMecanismos() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarMecanismos();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<AgenteAccidente> listarAgentesAccidente() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarAgenteAccidentes();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<CausaInmediata> listarCausaInmediatas() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarCausaInmediatas();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<CausaBasica> listarCausaBasicas() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarCausaBasicas();
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
    
    public ArrayList<Mes> listarMeses() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarMeses();
        } finally {
            cerrarConexion();
        }        
    }
    
    public ArrayList<Año> listarAños() throws Exception {
        try {
            abrirConexion();
            ListasDAO listasDAO = new ListasDAO(conexion);
            return listasDAO.listarAños();
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
