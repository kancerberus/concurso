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
import java.util.List;
import modelo.Menu;
import modelo.Perfil;
import modelo.SubMenu;
import modelo.Usuario;
import modelo.SubEmpresa;


/**
 *
 * @author Andres
 */
public class UsuarioDAO {

    private Connection conexion;

    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Usuario validarUsuario(String usuario, String clave) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Usuario u = null;
        Perfil p = null;   
        SubEmpresa e = null;
        try {
            consulta = new Consulta(getConexion());
            sql = "select u.usuario,u.nombre,u.cod_perfil,p.nombre perfil,e.nombre nom_empresa,u.fk_nitsubempresa, u.estado as estado from "
                    + " usuario u "
                    + " inner join perfil p using (cod_perfil) "  
                    + " inner join subempresa e on (nitsubempresa=fk_nitsubempresa) "
                    + " where usuario='" + usuario.trim() + "' and clave=md5('" + clave.trim() + "');";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                u = new Usuario();
                u.setNomusuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEstado(rs.getBoolean("estado"));
                
                p = new Perfil();
                p.setCodigo(rs.getInt("cod_perfil"));
                p.setNombre(rs.getString("perfil"));                
                u.setPerfil(p);
                
                e = new SubEmpresa();
                e.setNombre(rs.getString("nom_empresa"));  
                e.setNitsubempresa(rs.getString("fk_nitsubempresa"));
                u.setSubEmpresa(e);                
                
            }
            return u;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Usuario buscarUsuario(String nomusuario) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Usuario us = null;

        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select us.usuario as nomusuario, us.nombre as nombre, pe.cod_perfil as cod_perfil, su.nitsubempresa as nitsubem, us.estado as estado " +
                "from usuario us " +
                "inner join perfil pe on (pe.cod_perfil=us.cod_perfil) " +
                "inner join subempresa su on (su.nitsubempresa=us.fk_nitsubempresa) " +
                "where us.usuario = '"+nomusuario.trim()+"' ";
                    
                      
            rs = consulta.ejecutar(sql);
            
            if (rs.next()) {
                us = new Usuario();                
                us.setNomusuario(rs.getString("nomusuario"));
                us.setNombre(rs.getString("nombre"));
                us.getPerfil().setCodigo(rs.getInt("cod_perfil"));
                us.getSubEmpresa().setNitsubempresa(rs.getString("nitsubem"));
                us.setEstado(rs.getBoolean("estado"));
            }
            return us;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer modificarUsuario(Usuario usuario) throws SQLException{
        Consulta consulta = null;
        Integer resultado;  
        //Sentencia SQL para guardar el registro
        String sql ;    
        
   
        try {
            consulta = new Consulta(getConexion());
            
                sql = "UPDATE usuario "                         
                    +"set estado = "+usuario.isEstado()+" " 
                    +"where usuario = '"+usuario.getNomusuario()+"'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }    
    
    public Integer crearUsuario(Usuario usuario) throws SQLException{
        Consulta consulta = null;
        Integer resultado;
        ResultSet rs;
        String md5 = null;

        //Sentencia SQL para guardar el usuario
        String sql = "";
        try {
            consulta = new Consulta(getConexion());

            //Guardado del usuario
                sql = "INSERT INTO usuario ("
                        + "nombre, usuario, clave, cod_perfil, fk_nitsubempresa,estado) "
                        + "VALUES ("                    
                        + "'" + usuario.getNombre() + "',"
                        + "'" + usuario.getNomusuario()+ "',"
                        + "(select md5('" + usuario.getClave()+ "')),"                    
                        + "'" + usuario.getPerfil().getCodigo() + "', "                       
                        + "'" + usuario.getSubEmpresa().getNitsubempresa()+ "',true)";                    

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
    
    /*public Integer modificarUsuario(Usuario usuario) throws SQLException{
        Consulta consulta = null;
        Integer resultado;        

        //Sentencia SQL para guardar el registro
        String sql = "";
        ResultSet rs;    
   
        try {
            consulta = new Consulta(getConexion());
            
            sql = "SELECT  from usuario where usuario = '" + empleado.getResidencia().getNombre() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                empleado.getResidencia().setCodigo(rs.getString("cod_municipio"));                   
            }                        
            
                sql = "UPDATE empleado "
                        +" set nombres = '" + empleado.getNombres() + "', apellidos = '" + empleado.getApellidos() + "', cod_municipio = '" + empleado.getResidencia().getCodigo() + "', cod_eps = '" + empleado.getEps().getCodigo() + "',"
                        +" cod_det_lista_sexo='" + empleado.getSexo().getCodigo() + "',cod_det_lista_ecivil='" + empleado.getEcivil().getCodigo() + "',fecha_nacimiento='" + empleado.getFecha_nac() + "',"
                        +" cargo = '" + empleado.getCargo().getCodigo() + "', sueldo_mes = '" + empleado.getSueldo_mes() + "',aux_transporte='" + empleado.getAux_transporte() + "'"
                        +" where cedula = '" + empleado.getCedula() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }*/
    
    public ArrayList<Perfil> listarPerfiles() throws SQLException {
        Perfil perfil;
        ArrayList<Perfil> listaPerfiles = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_perfil,nombre "
                    + " FROM perfil";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                perfil = new Perfil();
                perfil.setCodigo(dt.getInt("cod_perfil"));
                perfil.setNombre(dt.getString("nombre"));
                listaPerfiles.add(perfil);
            }
            return listaPerfiles;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public List<Menu> listarOpcionesMenu(String usuario) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql, menuAnt = "";
        List<Menu> listaMenu = new ArrayList<>();
        try {
            Menu m;
            consulta = new Consulta(getConexion());
            sql = " select "
                    + " m.nombre nombre_menu,s.nombre nombre_submenu,imagen,s.opcion nombre_opcion"
                    + " from "
                    + " usuario u "
                    + " inner join rel_perfil_submenu r using (cod_perfil) "
                    + " inner join menu m using (cod_menu) "
                    + " inner join submenu s using (cod_menu,cod_submenu) "
                    + " where m.cod_menu='2' and "
                    + " usuario='" + usuario + "' "
                    + " order by m.orden,s.orden ";
            rs = consulta.ejecutar(sql);
            if (rs.next()) {

                SubMenu sm = new SubMenu();
                sm.setEtiqueta(rs.getString("nombre_submenu"));
                sm.setImagen(rs.getString("imagen"));
                sm.setOpcion(rs.getString("nombre_opcion"));
                //fredy
                //sm.setOpcion("nuevo_residente");

                m = new Menu();
                m.setEtiqueta(rs.getString("nombre_menu"));
                m.getListaSubMenu().add(sm);
                menuAnt = rs.getString("nombre_menu");

                while (rs.next()) {
                    sm = new SubMenu();
                    sm.setEtiqueta(rs.getString("nombre_submenu"));
                    sm.setImagen(rs.getString("imagen"));
                    sm.setOpcion(rs.getString("nombre_opcion"));
                    //fredy
                    //sm.setOpcion("nuevo_residente");
                    //si pertenece al mismo menu
                    if (rs.getString("nombre_menu").equalsIgnoreCase(menuAnt)) {
                        m.getListaSubMenu().add(sm);
                    } else {
                        listaMenu.add(m);
                        m = new Menu();
                        m.setEtiqueta(rs.getString("nombre_menu"));
                        m.getListaSubMenu().add(sm);
                        menuAnt = rs.getString("nombre_menu");
                    }
                }
                listaMenu.add(m);
            }

            return listaMenu;
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
