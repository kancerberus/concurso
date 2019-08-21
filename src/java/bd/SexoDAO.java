/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Sexo;
/**
 *
 * @author Andres
 */
public class SexoDAO {
    private Connection conexion;

    public SexoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Sexo> listarSexosPatron(String patron) throws SQLException {
        Sexo sexo;
        ArrayList<Sexo> listaSexos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " nombre "
                    + " from det_lista inner join lista on "
                    + "(cod_lista = fk_cod_lista) where cod_lista='1' ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                sexo = new Sexo();      
                sexo.setNombre(rs.getString("nombre"));
                listaSexos.add(sexo);
            }
            return listaSexos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Sexo consultarSexoPorNombre(String nombre) throws SQLException {
        Sexo municipio = null;
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre "
                    + " from det_lista inner join lista on "
                    + "(cod_lista = fk_cod_lista) where cod_lista='1''" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                municipio = new Sexo();
                municipio.setNombre(rs.getString("nombre"));
                
            }
            return municipio;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}

