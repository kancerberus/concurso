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
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.context.FacesContext;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Empresa;
import modelo.Eps;
import modelo.Municipio;
import modelo.Sexo;
import modelo.SubEmpresa;

/**
 *
 * @author Andres
 */
public class ListasDAO {
    private Connection conexion;

    public ListasDAO(Connection conexion) {
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
    
    public ArrayList<Eps> listarEpss() throws SQLException {
        Eps eps;
        ArrayList<Eps> listaEpss = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_eps,eps "
                    + " FROM eps";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                eps = new Eps();                
                eps.setCodigo(dt.getString("cod_eps"));
                eps.setNombre(dt.getString("eps"));
                listaEpss.add(eps);
            }
            return listaEpss;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
           
     public ArrayList<Cargo> listarCargos() throws SQLException {
        Cargo cargo;
        ArrayList<Cargo> listaCargos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_cargo,cargo "
                    + " FROM cargo order by cargo";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                cargo = new Cargo();
                cargo.setCodigo(dt.getString("cod_cargo"));
                cargo.setNombre(dt.getString("cargo"));
                listaCargos.add(cargo);
            }
            return listaCargos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Ecivil> listarEciviles() throws SQLException {        
        Ecivil ecivil;
        ArrayList<Ecivil> listaEciviles = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "select cod_det_lista,nombre "
                    + "from det_lista inner join lista on"
                    + " (cod_lista = fk_cod_lista) where cod_lista='2'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                ecivil = new Ecivil();                
                ecivil.setCodigo(dt.getString("cod_det_lista"));                
                ecivil.setNombre(dt.getString("nombre"));
                listaEciviles.add(ecivil);
            }
            return listaEciviles;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Sexo> listarSexos() throws SQLException {
        Sexo sexo;
        ArrayList<Sexo> listaSexos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "select cod_det_lista,nombre "
                    + "from det_lista inner join lista on"
                    + " (cod_lista = fk_cod_lista) where cod_lista='1'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                sexo = new Sexo();
                sexo.setCodigo(dt.getString("cod_det_lista"));
                sexo.setNombre(dt.getString("nombre"));                
                listaSexos.add(sexo);
            }
            return listaSexos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    public ArrayList<Empresa> listarEmpresas() throws SQLException {
        
        FacesContext contextoJSF = FacesContext.getCurrentInstance();
        ELContext contextoEL = contextoJSF.getELContext();
        ExpressionFactory ef = contextoJSF.getApplication().getExpressionFactory();
        
        Empresa empresa;
        ArrayList<Empresa> listaempresas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        String sql;
        String usuario = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.nomusuario}", String.class).getValue(contextoEL);       
        String nitsesion = (String) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.subEmpresa.nitsubempresa}", String.class).getValue(contextoEL);
        Integer cod_perfil = (Integer) ef.createValueExpression(contextoEL, "#{loginBean.sesion.usuario.perfil.codigo}", int.class).getValue(contextoEL);
        
        try {
            consulta = new Consulta(getConexion());
            

                        if  (cod_perfil.equals(4) || cod_perfil.equals(2)) {

                            sql = "SELECT e.nitempresa,e.nombre from subempresa sub "
                                    + "inner join empresa e on (e.nitempresa=sub.fk_nitempresa) "
                                    + "where nitsubempresa ='" + nitsesion + "'";                     
                        }else{
                            sql
                                = " SELECT nitempresa,nombre "
                                    + " FROM empresa";
                        }
                    
                     dt = consulta.ejecutar(sql);

            while (dt.next()) {
                empresa = new Empresa();
                empresa.setNitempresa(dt.getString("nitempresa"));
                empresa.setNombre(dt.getString("nombre"));
                listaempresas.add(empresa);
                
            }
            return listaempresas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<SubEmpresa> listarSubEmpresas() throws SQLException {
        SubEmpresa subempresa;
        ArrayList<SubEmpresa> listaSubempresas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT nitsubempresa,nombre "
                    + " FROM subempresa order by nombre";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                subempresa = new SubEmpresa();
                subempresa.setNitsubempresa(dt.getString("nitsubempresa"));
                subempresa.setNombre(dt.getString("nombre"));
                listaSubempresas.add(subempresa);
                
            }
            return listaSubempresas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<SubEmpresa> listarSubEmpresas(String nitempresa) throws SQLException {
        SubEmpresa subempresa;
        ArrayList<SubEmpresa> listaSubempresas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT nitsubempresa, nombre"
                    + " FROM subempresa "
                    + " where fk_nitempresa = '" + nitempresa + "'"  
                    + " order by nombre";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                subempresa = new SubEmpresa();
                subempresa.setNitsubempresa(dt.getString("nitsubempresa"));
                subempresa.setNombre(dt.getString("nombre"));
                listaSubempresas.add(subempresa);
                
            }
            return listaSubempresas;

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
