/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Empresa;

/**
 *
 * @author diego
 */
public class EmpresaDAO {
    private Connection conexion;

    public EmpresaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    public Connection getConexion() {
        return conexion;
    }
    
    public Integer guardarEmpresa(Empresa empresa) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    

        //Sentencia SQL para guardar el registro
        String sql = "";
        try {
            consulta = new Consulta(getConexion());
            ResultSet rs;

                sql = "SELECT cod_municipio from municipio where municipio = '" + empresa.getMunicipio().getNombre() + "'";
                    rs = consulta.ejecutar(sql); 
                    if (rs.next()) {
                        empresa.getMunicipio().setCodigo(rs.getString("cod_municipio"));                   
                    }

                sql = "INSERT INTO empresa ("
                        + "nitempresa,nombre, direccion, "
                        + "telefono, fk_codmunicipio)"
                        + "VALUES ("                                                                                                    
                        + "'" + empresa.getNitempresa()+ "',"
                        + "'" + empresa.getNombre()+ "',"
                        + "'" + empresa.getDireccion()+ "',"
                        + "'" + empresa.getTelefono()+ "',"
                        + "'" + empresa.getMunicipio().getCodigo()+"')";
            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
}
