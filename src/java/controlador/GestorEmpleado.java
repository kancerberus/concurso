/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.AusentismoDAO;
import bd.EmpleadoDAO;
import java.util.List;
import modelo.Empleado;


/**
 *
 * @author Andres
 */
public class GestorEmpleado extends Gestor {

    public GestorEmpleado() throws Exception {
        super();
    }
    public List<Empleado> listarEmpleados(String nitsesion) throws Exception {
       try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.listarEmpleados(nitsesion);
        } finally {
            cerrarConexion();
        }
    }

    
    public Empleado buscarEmpleado(String cedula, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.buscarEmpleado(cedula, nitsesion);
            } finally {
            cerrarConexion();
        }
    }    
    
    public Empleado buscarempleadoAdmin(String cedula) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.buscarempleadoAdmin(cedula);
            } finally {
            cerrarConexion();
        }
    }
    
    public Integer modificarSubempresaempleado(Empleado empleado) throws Exception{
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.modificarSubempresaempleado(empleado);
        } finally {
            cerrarConexion();
        }
    }


    public Empleado validarEmpleado(String cedula, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.validarEmpleado(cedula,nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    
    public Integer validarEmpleadoActualizacion(String cedula, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.validarEmpleadoActualizacion(cedula,nitsesion);
        } finally {
            cerrarConexion();
        }
    }
    public Integer guardarEmpleado(Empleado empleado, String nitsesion) throws Exception {
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.guardarEmpleado(empleado, nitsesion);
           
        } finally {
            cerrarConexion();
        }       
    }
    public Integer modificarEmpleado(Empleado empleado) throws Exception{
        try {
            abrirConexion();
            EmpleadoDAO empleadoDAO = new EmpleadoDAO(conexion);
            return empleadoDAO.modificarEmpleado(empleado);
        } finally {
            cerrarConexion();
        }
    }
}
