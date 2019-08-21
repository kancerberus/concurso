/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.SubEmpresa;

/**
 *
 * @author diego
 */
public class SubempresaDAO {
    private Connection conexion;

    public SubempresaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    public Connection getConexion() {
        return conexion;
    }
    
        public Integer guardarSubEmpresa(SubEmpresa subempresa) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    

        //Sentencia SQL para guardar el registro
        String sql = "";
        try {
            consulta = new Consulta(getConexion());
            ResultSet rs;

                sql = "SELECT cod_municipio from municipio where municipio = '" + subempresa.getMunicipio().getNombre() + "'";
                    rs = consulta.ejecutar(sql); 
                    if (rs.next()) {
                        subempresa.getMunicipio().setCodigo(rs.getString("cod_municipio"));                   
                    }

                sql = "INSERT INTO subempresa ("
                        + "nitsubempresa,nombre, direccion, "
                        + "telefono, fk_cod_municipio, fk_nitempresa)"
                        + "VALUES ("                                                                                                    
                        + "'" + subempresa.getNitsubempresa()+ "',"
                        + "'" + subempresa.getNombre()+ "',"
                        + "'" + subempresa.getDireccion()+ "',"
                        + "'" + subempresa.getTelefono()+ "',"
                        + "'" + subempresa.getMunicipio().getCodigo()+ "',"
                        + "'" + subempresa.getEmpresa().getNitempresa()+ "')";
            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
}
