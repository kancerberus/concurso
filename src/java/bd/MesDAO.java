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
import modelo.Mes;
/**
 *
 * @author Andres
 */
public class MesDAO {
    private Connection conexion;

    public MesDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Mes> listarMesesPatron(String patron) throws SQLException {
        Mes mes;
        ArrayList<Mes> listaMeses = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " codigo,nombre "
                    + " from "
                    + " mes where nombre ilike '%" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                mes= new Mes();
                mes.setCodigo(rs.getString("codigo"));
                mes.setNombre(rs.getString("nombre"));
                listaMeses.add(mes);
            }
            return listaMeses;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Mes consultarMesPorNombre(String nombre) throws SQLException {
        Mes mes= null;
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " cod_mes,nombre "
                    + " from "
                    + " mes where nombre = '" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                mes= new Mes();
                mes.setCodigo(rs.getString("cod_mes"));
                mes.setNombre(rs.getString("nombre"));
                
            }
            return mes;

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

