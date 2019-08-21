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
import modelo.Cargo;
/**
 *
 * @author Andres
 */
public class CargoDAO {
    private Connection conexion;

    public CargoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Cargo> listarCargosPatron(String patron) throws SQLException {
        Cargo cargo;
        ArrayList<Cargo> listaCargos = new ArrayList<>();
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
                cargo= new Cargo();
                cargo.setCodigo(rs.getString("codigo"));
                cargo.setNombre(rs.getString("nombre"));
                listaCargos.add(cargo);
            }
            return listaCargos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Cargo consultarCargoPorNombre(String nombre) throws SQLException {
        Cargo cargo= null;
        
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
                cargo= new Cargo();
                cargo.setCodigo(rs.getString("codigo"));
                cargo.setNombre(rs.getString("nombre"));
                
            }
            return cargo;

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

