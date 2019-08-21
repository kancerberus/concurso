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
import modelo.AgenteAccidente;
import modelo.Cargo;
import modelo.Ecivil;
import modelo.Empresa;
import modelo.Eps;
import modelo.Municipio;
import modelo.Sexo;
import modelo.SubEmpresa;
import modelo.Mes;
import modelo.Año;
import modelo.CausaBasica;
import modelo.CausaInmediata;
import modelo.Clasificacion;
import modelo.IncapacidadSi;
import modelo.Mecanismo;
import modelo.ParteAfectada;
import modelo.Riesgo;
import modelo.TipoAccidente;
import modelo.TipoEvento;
import modelo.TipoIncapacidad;
import modelo.TipoLesion;

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
    
    public ArrayList<TipoIncapacidad> listarTipoIncapaciadades() throws SQLException {
        TipoIncapacidad tipoIncapacidad;
        ArrayList<TipoIncapacidad> listaTipoIncapacidades = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='3'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                tipoIncapacidad=new TipoIncapacidad();
                tipoIncapacidad.setCodigo(dt.getString("cod_det_lista"));
                tipoIncapacidad.setNombre(dt.getString("nombre"));
                
                listaTipoIncapacidades.add(tipoIncapacidad);
            }
            return listaTipoIncapacidades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<TipoEvento> listarTipoEventos() throws SQLException {
        TipoEvento tipoEvento;
        ArrayList<TipoEvento> listaTipoEventos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='4'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                tipoEvento=new TipoEvento();
                tipoEvento.setCodigo(dt.getString("cod_det_lista"));
                tipoEvento.setNombre(dt.getString("nombre"));
                
                listaTipoEventos.add(tipoEvento);
            }
            return listaTipoEventos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Clasificacion> listarClasificaciones() throws SQLException {
        Clasificacion clasificacion;
        ArrayList<Clasificacion> listaClasificaciones = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='11'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                clasificacion=new Clasificacion();
                clasificacion.setCodigo(dt.getString("cod_det_lista"));
                clasificacion.setNombre(dt.getString("nombre"));
                
                listaClasificaciones.add(clasificacion);
            }
            return listaClasificaciones;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<IncapacidadSi> listarIncapacidadesSis() throws SQLException {
        IncapacidadSi incapacidadSi;
        ArrayList<IncapacidadSi> listaIncapacidadesSi = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='14'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                incapacidadSi=new IncapacidadSi();
                incapacidadSi.setCodigo(dt.getString("cod_det_lista"));
                incapacidadSi.setNombre(dt.getString("nombre"));
                
                listaIncapacidadesSi.add(incapacidadSi);
            }
            return listaIncapacidadesSi;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }


    public ArrayList<TipoAccidente> listarTipoAccidentes() throws SQLException {
        TipoAccidente tipoAccidente;        
        ArrayList<TipoAccidente> listaTipoAccidentes = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='5'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                tipoAccidente=new TipoAccidente();                
                tipoAccidente.setCodigo(dt.getString("cod_det_lista"));
                tipoAccidente.setNombre(dt.getString("nombre"));
                
                listaTipoAccidentes.add(tipoAccidente);
            }
            return listaTipoAccidentes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<ParteAfectada> listarParteAfectadas() throws SQLException {
        ParteAfectada parteAfectada;
        ArrayList<ParteAfectada> listaParteAfectadas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='6'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                parteAfectada=new ParteAfectada();                
                parteAfectada.setCodigo(dt.getString("cod_det_lista"));
                parteAfectada.setNombre(dt.getString("nombre"));
                
                listaParteAfectadas.add(parteAfectada);
            }
            return listaParteAfectadas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<TipoLesion> listarTipoLesiones() throws SQLException {
        TipoLesion tipoLesion;        
        ArrayList<TipoLesion> listaTipoLesion = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='7'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                tipoLesion=new TipoLesion();
                tipoLesion.setCodigo(dt.getString("cod_det_lista"));
                tipoLesion.setNombre(dt.getString("nombre"));
                
                listaTipoLesion.add(tipoLesion);
            }
            return listaTipoLesion;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Riesgo> listarRiesgos() throws SQLException {
        Riesgo riesgo;        
        ArrayList<Riesgo> listaRiesgos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='8'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                riesgo=new Riesgo();
                riesgo.setCodigo(dt.getString("cod_det_lista"));
                riesgo.setNombre(dt.getString("nombre"));
                
                listaRiesgos.add(riesgo);
            }
            return listaRiesgos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Mecanismo> listarMecanismos() throws SQLException {
        Mecanismo mecanismo;        
        ArrayList<Mecanismo> listaMecanismos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='9'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                mecanismo=new Mecanismo();
                mecanismo.setCodigo(dt.getString("cod_det_lista"));
                mecanismo.setNombre(dt.getString("nombre"));
                
                listaMecanismos.add(mecanismo);
            }
            return listaMecanismos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<AgenteAccidente> listarAgenteAccidentes() throws SQLException {
        AgenteAccidente agenteAccidentes;        
        ArrayList<AgenteAccidente> listaAgenteAccidentes = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='10'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                agenteAccidentes=new AgenteAccidente();
                agenteAccidentes.setCodigo(dt.getString("cod_det_lista"));
                agenteAccidentes.setNombre(dt.getString("nombre"));
                
                listaAgenteAccidentes.add(agenteAccidentes);
            }
            return listaAgenteAccidentes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<CausaInmediata> listarCausaInmediatas() throws SQLException {
        CausaInmediata causaInmediatas;        
        ArrayList<CausaInmediata> listaCausaInmediatas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='13'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                causaInmediatas=new CausaInmediata();
                causaInmediatas.setCodigo(dt.getString("cod_det_lista"));
                causaInmediatas.setNombre(dt.getString("nombre"));
                
                listaCausaInmediatas.add(causaInmediatas);
            }
            return listaCausaInmediatas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<CausaBasica> listarCausaBasicas() throws SQLException {
        CausaBasica causaBasicas;        
        ArrayList<CausaBasica> listaCausaBasicas = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_det_lista,nombre "
                    + " FROM det_lista"
                    + " WHERE fk_cod_lista='12'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                causaBasicas=new CausaBasica();
                causaBasicas.setCodigo(dt.getString("cod_det_lista"));
                causaBasicas.setNombre(dt.getString("nombre"));
                
                listaCausaBasicas.add(causaBasicas);
            }
            return listaCausaBasicas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }


    
    public ArrayList<Año> listarAños() throws SQLException {
        Año año;
        ArrayList<Año> listaAños = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT año "
                    + " FROM año";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                año = new Año();                
                año.setAño(dt.getString("año"));                
                listaAños.add(año);
            }
            return listaAños;

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
    
    public ArrayList<Mes> listarMeses() throws SQLException {
        Mes mes;
        ArrayList<Mes> listaMeses = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_mes,nombre "
                    + " FROM mes ";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                mes = new Mes();
                mes.setCodigo(dt.getString("cod_mes"));
                mes.setNombre(dt.getString("nombre"));
                listaMeses.add(mes);
            }
            return listaMeses;

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
