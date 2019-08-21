/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import modelo.SubEmpresa;
import bd.SubempresaDAO;

/**
 *
 * @author diego
 */
public class GestorSubempresa extends Gestor{
    public GestorSubempresa() throws Exception{
        super();
    }
    
    public Integer guardarSubEmpresa(SubEmpresa SubEmrpesa) throws Exception {
        try {
            abrirConexion();
            SubempresaDAO subempresaDAO = new SubempresaDAO(conexion);
            return subempresaDAO.guardarSubEmpresa(SubEmrpesa);
           
        } finally {
            cerrarConexion();
        }       
    }           
}
