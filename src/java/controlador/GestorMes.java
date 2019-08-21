/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.MesDAO;
import java.util.ArrayList;
import modelo.Mes;
/**
 *
 * @author Andres
 */
public class GestorMes extends Gestor{
    public GestorMes() throws Exception{
        super();
    }   

    
    public ArrayList<Mes> listarCargosPatron(String patron) throws Exception{
       try {
            abrirConexion();
            MesDAO mesDAO = new MesDAO(conexion);
            return mesDAO.listarMesesPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Mes consultarMesPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            MesDAO mesDAO = new MesDAO(conexion);
            return mesDAO.consultarMesPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}