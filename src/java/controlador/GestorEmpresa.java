/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import modelo.Empresa;
import bd.EmpresaDAO;

/**
 *
 * @author diego
 */
public class GestorEmpresa extends Gestor{
    public GestorEmpresa() throws Exception{
        super();
    }
    
    public Integer guardarEmpresa(Empresa Empresa) throws Exception {
        try {
            abrirConexion();
            EmpresaDAO empresaDAO = new EmpresaDAO(conexion);
            return empresaDAO.guardarEmpresa(Empresa);
           
        } finally {
            cerrarConexion();
        }       
    }           
}
