/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import bd.AccidenteDAO;
import bd.IncapacidadDAO;
import bd.EmpleadoDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.chart.PieChart;
import modelo.Accidente;
import modelo.Ausentismo;
import modelo.Año;
import modelo.Cie10;
import modelo.Empleado;
import modelo.Incapacidad;
import modelo.Mes;
import modelo.Motivo;
import modelo.SubEmpresa;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author diego
 */
public class GestorAccidente extends Gestor implements Serializable{
    
    private IncapacidadDAO incapacidadDAO;
    
    public GestorAccidente() throws Exception{
        super();
    }
        
    
    public Integer guardarAccidente(Accidente accidente) throws Exception{
        try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.guardarAccidente(accidente);
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

    public Collection<? extends Accidente> cargarDistribucionTipoEventos(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionTipoEventos(nitem, nitsubem, mesdesde, meshasta,ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionRiesgos(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionRiesgos(nitem, nitsubem, mesdesde, meshasta,ano);
        } finally {
            cerrarConexion();
        }
    }
    public Collection<? extends Accidente> cargarDistribucionCausaBasica(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionCausaBasica(nitem, nitsubem, mesdesde, meshasta,ano);
        } finally {
            cerrarConexion();
        }
    }
    public Collection<? extends Accidente> cargarDistribucionCausaInmediata(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionCausaInmediata(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionTipoAccidente(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionTipoAccidente(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    
    public Collection<? extends Accidente> cargarDistribucionClasificaciones(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionClasificaciones(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionMecanismos(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionMecanismos(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionAgente(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionAgente(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionParteAfectada(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionParteAfectada(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionTipoLesion(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionTipoLesion(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionCargos(String nitem, String nitsubem, String  mesdesde,String meshasta,String ano) throws Exception {
       try {
            abrirConexion();
            AccidenteDAO accidenteDAO = new AccidenteDAO(conexion);
            return accidenteDAO.cargarDistribucionCargos(nitem, nitsubem, mesdesde, meshasta, ano);
        } finally {
            cerrarConexion();
        }
    }
    
}
