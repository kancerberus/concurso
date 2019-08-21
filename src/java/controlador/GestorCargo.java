/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.CargoDAO;
import java.util.ArrayList;
import modelo.Cargo;
/**
 *
 * @author Andres
 */
public class GestorCargo extends Gestor{
    public GestorCargo() throws Exception{
        super();
    }
    
    public ArrayList<Cargo> listarCargosPatron(String patron) throws Exception{
       try {
            abrirConexion();
            CargoDAO epsDAO = new CargoDAO(conexion);
            return epsDAO.listarCargosPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Cargo consultarCargoPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            CargoDAO epsDAO = new CargoDAO(conexion);
            return epsDAO.consultarCargoPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}