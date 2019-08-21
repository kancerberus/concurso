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
import modelo.Municipio;

/**
 *
 * @author Andres
 */
public class MunicipioDAO {
    private Connection conexion;

    public MunicipioDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Municipio> listarMunicipiosPatron(String patron) throws SQLException {
        Municipio municipio;
        ArrayList<Municipio> listaMunicipios = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " cod_municipio,municipio "
                    + " from "
                    + " municipio where municipio ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                municipio = new Municipio();
                municipio.setCodigo(rs.getString("cod_municipio"));
                municipio.setNombre(rs.getString("municipio"));
                listaMunicipios.add(municipio);
            }
            return listaMunicipios;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Municipio consultarMunicipioPorNombre(String nombre) throws SQLException {
        Municipio municipio = null;
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " cod_municipio,municipio "
                    + " from "
                    + " municipio where municipio = '" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                municipio = new Municipio();
                municipio.setCodigo(rs.getString("cod_municipio"));
                municipio.setNombre(rs.getString("municipio"));
                
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
