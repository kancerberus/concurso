/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.EcivilDAO;
import java.util.ArrayList;
import modelo.Ecivil;
/**
 *
 * @author Andres
 */
public class GestorEcivil extends Gestor{
    public GestorEcivil() throws Exception{
        super();
    }
    
    public ArrayList<Ecivil> listarEcivilesPatron(String patron) throws Exception{
       try {
            abrirConexion();
            EcivilDAO epsDAO = new EcivilDAO(conexion);
            return epsDAO.listarEcivilsPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Ecivil consultarEcivilPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            EcivilDAO ecivilDAO = new EcivilDAO(conexion);
            return ecivilDAO.consultarEcivilPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}