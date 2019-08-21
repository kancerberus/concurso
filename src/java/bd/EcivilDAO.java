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
import modelo.Ecivil;
/**
 *
 * @author Andres
 */
public class EcivilDAO {
    private Connection conexion;

    public EcivilDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Ecivil> listarEcivilsPatron(String patron) throws SQLException {
        Ecivil cargo;
        ArrayList<Ecivil> listaEcivils = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " nombre "
                    + " from "
                    + " det_lista inner join lista on (cod_lista = fk_cod_lista)" + patron + "%';";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                cargo= new Ecivil();               
                cargo.setNombre(rs.getString("nombre"));
                listaEcivils.add(cargo);
            }
            return listaEcivils;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Ecivil consultarEcivilPorNombre(String nombre) throws SQLException {
        Ecivil ecivil= null;
        
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select "
                    + " nombre "
                    + " from "
                    + " det_lista inner join lista on (cod_lista = fk_cod_lista)" + nombre + "';";

            rs = consulta.ejecutar(sql);

            if (rs.next()) {
                ecivil= new Ecivil();                
                ecivil.setNombre(rs.getString("nombre"));
                
            }
            return ecivil;

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

