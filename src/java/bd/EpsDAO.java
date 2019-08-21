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
import modelo.Eps;
/**
 *
 * @author Andres
 */
public class EpsDAO {
    private Connection conexion;

    public EpsDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Eps> listarEpssPatron(String patron) throws SQLException {
        Eps municipio;
        ArrayList<Eps> listaEpss = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre "
                    + " from "
                    + " municipios where nombre ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                municipio = new Eps();
                municipio.setCodigo(rs.getString("codigo"));
                municipio.setNombre(rs.getString("nombre"));
                listaEpss.add(municipio);
            }
            return listaEpss;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Eps consultarEpsPorNombre(String nombre) throws SQLException {
        Eps municipio = null;
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre "
                    + " from "
                    + " municipios where nombre = '" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                municipio = new Eps();
                municipio.setCodigo(rs.getString("codigo"));
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

