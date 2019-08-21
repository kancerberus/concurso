/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.EpsDAO;
import java.util.ArrayList;
import modelo.Eps;
/**
 *
 * @author Andres
 */
public class GestorEps extends Gestor{
    public GestorEps() throws Exception{
        super();
    }
    
    public ArrayList<Eps> listarEpssPatron(String patron) throws Exception{
       try {
            abrirConexion();
            EpsDAO epsDAO = new EpsDAO(conexion);
            return epsDAO.listarEpssPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Eps consultarEpsPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            EpsDAO epsDAO = new EpsDAO(conexion);
            return epsDAO.consultarEpsPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}