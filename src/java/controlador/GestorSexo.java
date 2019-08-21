/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import bd.SexoDAO;
import java.util.ArrayList;
import modelo.Sexo;
/**
 *
 * @author Andres
 */
public class GestorSexo extends Gestor{
    public GestorSexo() throws Exception{
        super();
    }
    
    public ArrayList<Sexo> listarSexosPatron(String patron) throws Exception{
       try {
            abrirConexion();
            SexoDAO epsDAO = new SexoDAO(conexion);
            return epsDAO.listarSexosPatron(patron);
        } finally {
            cerrarConexion();
        }
    }
    
    public Sexo consultarSexoPorNombre(String nombre) throws Exception{
       try {
            abrirConexion();
            SexoDAO sexoDAO = new SexoDAO(conexion);
            return sexoDAO.consultarSexoPorNombre(nombre);
        } finally {
            cerrarConexion();
        }
    }
    
}