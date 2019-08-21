/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import bd.IncapacidadDAO;
import bd.EmpleadoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart;
import modelo.Ausentismo;
import modelo.Cie10;
import modelo.Empleado;
import modelo.Incapacidad;
import modelo.Mes;
import modelo.Motivo;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author diego
 */
public class GestorIncapacidad extends Gestor implements Serializable{
    
    private IncapacidadDAO incapacidadDAO;
    
    public GestorIncapacidad() throws Exception{
        super();
    }
    
    
    public List<Ausentismo> listarAusentismos(String nitsesion) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.listarAusentismos(nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> ausentismoAnomes(String nitsesion,String selmesano,String selano) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.ausentismoAnomes(nitsesion, selmesano, selano);
        } finally {
            cerrarConexion();
        }
    }    

    
    public List<Ausentismo> ausentismoanomesEmpresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano,String selmotivo) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.ausentismoanomesEmpresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> ausentismoanomesEmpleado(String cedula,String nitem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.ausentismoanomesEmpleado(cedula, nitem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    } 
    
    public List<Ausentismo> pieausentismoanomesEmpresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano,String selmotivo) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.pieausentismoanomesEmpresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }
    
    public List<Ausentismo> pieausentismoanomesEmpleado(String cedula, String nitem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.pieanomesEmpleado(cedula, nitem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    }   
    
    
    
    public List<Ausentismo> pieporSubempresa(String nitem,String nitsubem, String selmesdesde, String selmeshasta,String selano, String selmotivo) throws Exception {
       try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.pieporSubempresa(nitem, nitsubem,selmesdesde, selmeshasta, selano, selmotivo);
        } finally {
            cerrarConexion();
        }
    } 
    
    public Ausentismo buscarAusentismo(String cod_registro, String nitsesion) throws Exception {
        try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.buscarAusentismo(cod_registro, nitsesion );
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer guardarIncapacidad(Incapacidad incapacidad) throws Exception{
        try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.guardarIncapacidad(incapacidad);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer horasanoAcumuladas(String cedula) throws Exception{
        try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.horasAcumuladas(cedula);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer modificarAusentismo(Ausentismo ausentismo) throws Exception{
        try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.modificarAusentismo(ausentismo);
        } finally {
            cerrarConexion();
        }
    }    
    
        
    /*public Integer actualizarAusentismo(Ausentismo ausentismo) throws Exception{
        try {
            abrirConexion();
            IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
            return incapacidadDAO.actualizarAusentismo(ausentismo);
        } finally {
            cerrarConexion();
        }
    }*/
    
    public ArrayList<Motivo> listarMotivosIncapacidad() throws Exception {
        try {
                abrirConexion();
                IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
                return incapacidadDAO.listarMotivosIncapacidad();
            } finally {
                cerrarConexion();
            }
    }
    
    public Cie10 cargarCie10(String codCie10) throws Exception {
        try {
                abrirConexion();
                IncapacidadDAO incapacidadDAO = new IncapacidadDAO(conexion);
                return incapacidadDAO.cargarCie10(codCie10);
            } finally {
                cerrarConexion();
            }
    }
    
}
