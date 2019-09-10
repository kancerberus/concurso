/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Actividad;
import modelo.AdjuntosActividad;
import modelo.CalificacionActividad;
import modelo.Concurso;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoConcurso;
import modelo.GrupoConcursoParticipantes;
import modelo.SubEmpresa;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import vista.UIConcurso;
/**
 *
 * @author Andres
 */
public class ConcursoDAO {
    private Connection conexion;
    private InputStream logo=new InputStream() {
        @Override
        public int read() throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    public ConcursoDAO(Connection conexion) {
        this.conexion = conexion;
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

        public Integer guardarActividad(Actividad actividad) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        ResultSet rs;
        ArrayList<GrupoConcurso> listaGrupoConcursos = new ArrayList<>();
        GrupoConcurso grupoConcurso;
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.actividad ("
                        + " cod_actividad, nombre, observacion, fecha_limite, fk_cod_concurso) "                        
                        + "VALUES ("
                        + "'" + actividad.getCodActividad()+ "', "
                        + "'" + actividad.getNombre() + "',"
                        + "'" + actividad.getObservacion() + "',"
                        + "'" + actividad.getFechaLimite() + "',"
                        + "'" + actividad.getConcurso().getCodConcurso() + "')";

            resultado = consulta.actualizar(sql);
            
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql1 = " select cod_grupo "
                        + " from campaña.grupo_concurso "
                        + " where cod_concurso='"+actividad.getConcurso().getCodConcurso()+"'";

            rs = consulta.ejecutar(sql1);

            while (rs.next()) {
                grupoConcurso=new GrupoConcurso();
                grupoConcurso.setCodGrupo(rs.getString("cod_grupo"));
                listaGrupoConcursos.add(grupoConcurso);
            }
            
            for(int i=0;i<listaGrupoConcursos.size();i++){
                
                consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql2 = "INSERT INTO campaña.calificacion_actividad ("
                        + " cod_grupo, cod_actividad, calificacion) "                        
                        + "VALUES ("
                        + "'" + listaGrupoConcursos.get(i).getCodGrupo() + "',"
                        + "'" + actividad.getCodActividad() + "', "
                        + "'0' )";
                
            resultado = consulta.actualizar(sql2);
            }
            
            
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer eliminarAdjuntos(Actividad actividad) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = " delete " +
                    " from campaña.adjuntos_actividad " +
                    " where cod_actividad='"+actividad.getCodActividad()+"'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public Integer agregarEmpleado(GrupoConcursoParticipantes grupoParticipantes) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.grupo_concurso_participantes ("
                        + "     cod_grupo, fk_cedula, capitan ) "                        
                        + "VALUES ("
                        + "'" + grupoParticipantes.getGrupoConcurso().getCodGrupo()+ "',"
                        + "'" + grupoParticipantes.getEmpleado().getCedula()+ "',"
                        + "'" +grupoParticipantes.isCapitan()+ "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public Integer guardarConcurso(Concurso concurso) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.concurso ("
                        + " cod_concurso,fk_nitempresa, nombre, participantes, estado, fecha_limite_insc, logo_direccion) "                        
                        + "VALUES ("
                        + "'" + concurso.getCodConcurso() +"',"
                        + "'" + concurso.getEmpresa().getNitempresa()+ "',"
                        + "'" + concurso.getNombre() + "',"
                        + "'" + concurso.getParticipantes() + "',"
                        + "'" + concurso.isEstado() + "',"
                        + "'" + concurso.getFecha_limite_insc() + "',"
                        + "'" + concurso.getLogoDir()+ "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Integer guardarDatosAdjuntos(UploadedFile file, Actividad actividad, String ruta) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.adjuntos_actividad ("
                        + " cod_actividad, nombre, direccion, cod_grupo ) "                        
                        + "VALUES ("
                        + "'" + actividad.getCodActividad() +"',"
                        + "'" + file.getFileName() + "',"
                        + "'" + ruta + "',"                        
                        + "'" + actividad.getGrupoConcurso().getCodGrupo() + "')";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Integer guardarLogo(UploadedFile file, Concurso concurso, String ruta) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = " UPDATE campaña.concurso "
                        + " set ( logo='"+file.getFileName()+"', direcc_logo='' )"
                        + " where cod_concurso='"+concurso.getCodConcurso()+"'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer contarArchivos(Actividad actividad) throws SQLException {
        Consulta consulta = null;        
        ResultSet rs;
        Integer cantArch=0;
        
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = " select count(cod_adjunto) cantidad " +
                            " from campaña.adjuntos_actividad " +
                            " where cod_actividad='"+actividad.getCodActividad()+"' ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                cantArch=rs.getInt("cantidad");
            }
            return cantArch;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public String cargarNombreEmpresa(String nitempresa) throws SQLException {
        Consulta consulta = null;        
        ResultSet rs;
        String nombre="";
        
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = " select nombre " +
                            " from empresa " +
                            " where nitempresa='"+nitempresa+"' ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                nombre=rs.getString("nombre");
                        
            }
            return nombre;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public Integer guardarCalificacion(CalificacionActividad calificacion) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = " UPDATE campaña.calificacion_actividad " +
                            " SET calificacion = '"+calificacion.getCalificacion()+"' "+
                            " WHERE cod_actividad='"+calificacion.getCodActividad()+"' and cod_grupo='"+calificacion.getCodGrupo()+"'";                        
                        

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Long nextval(String secuencia) throws SQLException {

        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder("select nextval('" + secuencia + "')");
            rs = consulta.ejecutar(sql);
            rs.next();
            return rs.getLong(1);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    public String buscarNombreEquipo(String nombre,String codConcurso) throws SQLException {
        String existe="";
        ResultSet rs = null;
        Consulta consulta = null;
        try {
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder("select nombre "
                    + " from campaña.grupo_concurso "
                    + " where nombre='"+nombre+"' and cod_concurso='"+codConcurso+"'");
            rs = consulta.ejecutar(sql);  
            if (rs.next()){
                existe=rs.getString("nombre");
            }
            
            return existe;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (consulta != null) {
                consulta.desconectar();
            }
        }
    }
    
    
    public Integer guardarGrupoConcurso(GrupoConcurso grupoConcurso) throws SQLException {
        Consulta consulta = null;        
        Integer resultado;
        Actividad actividad;
        ArrayList<Actividad> listActividades=new ArrayList<>();
        ResultSet rs;
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO campaña.grupo_concurso ("
                        + " cod_grupo, cod_concurso, nombre, fk_nitsubempresa) "                        
                        + "VALUES ("
                        + "'" + grupoConcurso.getCodGrupo() + "', "                        
                        + "'" + grupoConcurso.getConcurso().getCodConcurso()+ "',"
                        + "'" + grupoConcurso.getNombre() + "', "
                        + "'" + grupoConcurso.getSubempresa().getNitsubempresa()+ "'"
                        + " )";

            resultado = consulta.actualizar(sql);
            
            
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para 
                String sql1 = " select cod_actividad, nombre, observacion, fecha_limite " +
                            " from campaña.actividad " +
                            " where fk_cod_concurso='"+grupoConcurso.getConcurso().getCodConcurso()+"'";

           
            rs = consulta.ejecutar(sql1);

            while (rs.next()) {
                actividad=new Actividad();
                actividad.setCodActividad(rs.getString("cod_actividad"));
                actividad.setNombre(rs.getString("nombre"));
                actividad.setObservacion(rs.getString("observacion"));
                actividad.setFechaLimite(rs.getDate("fecha_limite"));                
                listActividades.add(actividad);                
            }
            
            
            for(int i=0;i<=listActividades.size()-1;i++){
                consulta = new Consulta(getConexion());                

                String sql2 = "INSERT INTO campaña.calificacion_actividad ("
                        + " cod_actividad, cod_grupo, calificacion) "                        
                        + "VALUES ("
                        + "'" + listActividades.get(i).getCodActividad() + "', "                        
                        + "'" + grupoConcurso.getCodGrupo()+ "',"
                        + "0"
                        + " )";

            resultado = consulta.actualizar(sql2);
            }
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Concurso> cargarConcursos() throws SQLException {
        Concurso concurso;
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_concurso, conc.nombre nomconc, em.nombre nombre,fk_nitempresa, participantes, estado, fecha_limite_insc, logo_direccion " +
                        "FROM campaña.concurso conc " +
                        "JOIN empresa em on(em.nitempresa=conc.fk_nitempresa) " +
                        "ORDER BY cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                concurso =new Concurso();    
                concurso.setCodConcurso(rs.getString("cod_concurso"));
                concurso.setNombre(rs.getString("nomconc"));
                concurso.setParticipantes(rs.getInt("participantes"));
                concurso.setEstado(rs.getBoolean("estado"));
                concurso.setFecha_limite_insc(rs.getDate("fecha_limite_insc"));
                concurso.setEmpresa(new Empresa(rs.getString("fk_nitempresa"), rs.getString("nombre")));                
                concurso.setLogoDir(rs.getString("logo_direccion"));                
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }   

    
    public String cargarNitEmpresa(String nitsesion) throws SQLException {
        String nitempresa="";
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT fk_nitempresa "
                    + " from subempresa "
                    + " where nitsubempresa='"+nitsesion+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                nitempresa=rs.getString("fk_nitempresa");
            }
            return nitempresa;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer cargarParticipantes(String codConcurso) throws SQLException {
        Integer participantes=0;
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT participantes "
                    + " from campaña.concurso "
                    + " where cod_concurso='"+codConcurso+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                participantes=rs.getInt("participantes");
            }
            return participantes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<GrupoConcurso> cargarGruposConcursos(String nitsesion, String codConcurso) throws SQLException {
        GrupoConcurso  grupoConcurso;
        SubEmpresa subempresa=new SubEmpresa();
        Empresa empresa=new Empresa();
        ArrayList<GrupoConcurso> listaGrupoConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            
            String nit="";
            consulta = new Consulta(getConexion());
            String sql1
                    = "  select em.nombre nomem, sub.fk_nitempresa nitem, sub.nombre  nombre " +
                    " from subempresa sub "+
                    " JOIN empresa em on (em.nitempresa=sub.fk_nitempresa) " +
                    " where sub.nitsubempresa='"+nitsesion+"'";

            rs = consulta.ejecutar(sql1);
            
            while(rs.next()){                
                nit=rs.getString("nitem");                
                subempresa.setNitsubempresa(nitsesion);
                subempresa.setNombre(rs.getString("nombre"));
                empresa.setNitempresa(nit);
                empresa.setNombre(rs.getString("nomem"));
            }
            
            
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_grupo, gconc.cod_concurso codgconc, conc.nombre nomconc, gconc.nombre nombregconc, conc.participantes participantes " +
                        " FROM campaña.grupo_concurso gconc " +
                        " JOIN campaña.concurso conc on(conc.cod_concurso=gconc.cod_concurso) "+
                        " WHERE gconc.fk_nitsubempresa='"+nitsesion+"' and conc.cod_concurso='"+codConcurso+"'" +
                        " ORDER BY conc.cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {   
                grupoConcurso=new GrupoConcurso();
                grupoConcurso.setCodGrupo(rs.getString("cod_grupo"));
                grupoConcurso.setConcurso(new Concurso(rs.getString("codgconc"), rs.getString("nomconc"), rs.getInt("participantes")));                
                grupoConcurso.setNombre(rs.getString("nombregconc"));                
                grupoConcurso.setSubempresa(subempresa);
                grupoConcurso.getConcurso().setEmpresa(empresa);
                listaGrupoConcursos.add(grupoConcurso);
            }
            return listaGrupoConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<GrupoConcurso> cargarTablaPosiciones(String codConcurso) throws SQLException {
        GrupoConcurso  grupoConcurso;        
        ArrayList<GrupoConcurso> listaTablaPosiciones = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT  row_number() OVER(order by gc.cod_grupo) as pos, gc.cod_grupo codgc, gc.nombre nomgc, sub.nitsubempresa nitsub, sub.nombre nomsub, SUM(calificacion) ptajeTotal  " +
                        " FROM campaña.grupo_concurso gc " +
                        " JOIN campaña.calificacion_actividad ca ON (gc.cod_grupo=ca.cod_grupo) " +
                        " JOIN campaña.concurso con on(gc.cod_concurso=con.cod_concurso) " +
                        " join subempresa sub on (sub.nitsubempresa=gc.fk_nitsubempresa) " +
                        " WHERE gc.cod_concurso='"+codConcurso+"' " +
                        " GROUP BY gc.cod_grupo,sub.nitsubempresa " +
                        " ORDER BY ptajeTotal DESC";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {   
                grupoConcurso=new GrupoConcurso();
                grupoConcurso.setCodGrupo(rs.getString("codgc"));                
                grupoConcurso.setNombre(rs.getString("nomgc"));
                grupoConcurso.setSubempresa(new SubEmpresa(rs.getString("nitsub"), rs.getString("nomsub")));
                grupoConcurso.setPuntajeTotal(rs.getInt("ptajeTotal"));  
                grupoConcurso.setPos(rs.getInt("pos"));
                listaTablaPosiciones.add(grupoConcurso);
            }
            return listaTablaPosiciones;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public ArrayList<AdjuntosActividad> cargarListaAdjuntos(Actividad actividad) throws SQLException {
        AdjuntosActividad adjuntosActividad;
        ArrayList<AdjuntosActividad> listaAdjuntosActividad = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_adjunto, nombre " +
                        " from campaña.adjuntos_actividad " +
                        " where cod_actividad='"+actividad.getCodActividad()+"' and cod_grupo='"+actividad.getGrupoConcurso().getCodGrupo()+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                adjuntosActividad=new AdjuntosActividad();
                adjuntosActividad.setCodAdjunto(rs.getInt("cod_adjunto"));
                adjuntosActividad.setNombre(rs.getString("nombre").trim());
                listaAdjuntosActividad.add(adjuntosActividad);
            }
            return listaAdjuntosActividad;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<AdjuntosActividad> cargarListaAdjuntosCalificaciones(CalificacionActividad calificacionActividad) throws SQLException {        
        AdjuntosActividad adjActividad;
        ArrayList<AdjuntosActividad> listaAdjuntosActividadJueces = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            
            consulta = new Consulta(getConexion());
            String sql
                    = " select nombre, direccion, cod_grupo, cod_adjunto " +
                    "from campaña.adjuntos_actividad " +
                    "where cod_actividad='"+calificacionActividad.getCodActividad()+"' and cod_grupo='"+calificacionActividad.getCodGrupo()+"' ";

            rs = consulta.ejecutar(sql);

                while (rs.next()) {                
                adjActividad=new AdjuntosActividad();
                adjActividad.setCodAdjunto(rs.getInt("cod_adjunto"));
                adjActividad.setNombre(rs.getString("nombre").trim());
                adjActividad.setDireccion(rs.getString("direccion").trim());
                adjActividad.setGrupoConcurso(new GrupoConcurso(rs.getString("cod_grupo"),null, null, null,null,null));
                listaAdjuntosActividadJueces.add(adjActividad);
            }
            return listaAdjuntosActividadJueces;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public ArrayList<Concurso> listarConcursos(String nitsesion) throws SQLException {
        Concurso concurso;        
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            
            String nit="";
            consulta = new Consulta(getConexion());
            String sql1
                    = "  select fk_nitempresa " +
                    " from subempresa " +
                    " where nitsubempresa='"+nitsesion+"'";

            dt = consulta.ejecutar(sql1);
            
            while(dt.next()){
                nit=dt.getString("fk_nitempresa");
            }
            
            consulta = new Consulta(getConexion());
            String sql
                    = "  SELECT conc.cod_concurso, conc.nombre, conc.fk_nitempresa, participantes, estado, fecha_limite_insc, logo_direccion, emp.nombre nomem  " +
                        " FROM campaña.concurso conc " +
                        " JOIN subempresa sub ON (sub.nitsubempresa=conc.fk_nitempresa) " +
                        " JOIN empresa emp ON(emp.nitempresa=sub.fk_nitempresa) " +
                        " WHERE emp.nitempresa='"+nit+"'";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                concurso=new Concurso();
                concurso.setCodConcurso(dt.getString("cod_concurso"));
                concurso.setNombre(dt.getString("nombre"));
                concurso.setParticipantes(dt.getInt("participantes"));
                concurso.setEstado(dt.getBoolean("estado"));
                concurso.setFecha_limite_insc(dt.getDate("fecha_limite_insc"));
                concurso.setLogoDir(dt.getString("logo_direccion"));                
                concurso.setEmpresa(new Empresa(nit, dt.getString("nomem")));
                
                
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    
    public ArrayList<GrupoConcurso> listarGruposConcursos(String codConcurso, String nitsubempresa) throws SQLException {
        GrupoConcurso grupoConcurso;
        ArrayList<GrupoConcurso> listaGrupoConcursos = new ArrayList<>();
        ResultSet dt;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "  SELECT cod_grupo, gconc.nombre, conc.nombre nomconc, conc.participantes participantes  " +
                        " FROM campaña.grupo_concurso gconc " +
                        " JOIN campaña.concurso conc on(conc.cod_concurso=gconc.cod_concurso)  " +
                        " WHERE gconc.cod_concurso='"+codConcurso+"' and gconc.fk_nitsubempresa='"+nitsubempresa+"'  ";

            dt = consulta.ejecutar(sql);

            while (dt.next()) {
                grupoConcurso=new GrupoConcurso();
                grupoConcurso.setCodGrupo(dt.getString("cod_grupo"));
                grupoConcurso.setNombre(dt.getString("nombre"));  
                grupoConcurso.setConcurso(new Concurso(codConcurso, dt.getString("nomconc"), dt.getInt("participantes")));
                
                listaGrupoConcursos.add(grupoConcurso);
            }
            return listaGrupoConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public ArrayList<Concurso> cargarConcursosGerente(String nitsesion) throws SQLException {
        Concurso concurso;
        ArrayList<Concurso> listaConcursos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            
            String nit="";
            consulta = new Consulta(getConexion());
            String sql1
                    = "  select fk_nitempresa " +
                    " from subempresa " +
                    " where nitsubempresa='"+nitsesion+"'";

            rs = consulta.ejecutar(sql1);
            
            while(rs.next()){
                nit=rs.getString("fk_nitempresa");
            }
            
            
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_concurso, conc.nombre nomconc, em.nombre nombre,fk_nitempresa, participantes, estado, fecha_limite_insc " +
                        "FROM campaña.concurso conc " +
                        "JOIN empresa em on(em.nitempresa=conc.fk_nitempresa) "+
                        " WHERE fk_nitempresa='"+nit+"' " +                    
                        "ORDER BY cod_concurso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                concurso =new Concurso();    
                concurso.setCodConcurso(rs.getString("cod_concurso"));
                concurso.setNombre(rs.getString("nomconc"));
                concurso.setParticipantes(rs.getInt("participantes"));
                concurso.setEstado(rs.getBoolean("estado"));
                concurso.setFecha_limite_insc(rs.getDate("fecha_limite_insc"));
                concurso.setEmpresa(new Empresa(rs.getString("fk_nitempresa"), rs.getString("nombre")));
                listaConcursos.add(concurso);
            }
            return listaConcursos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Actividad> cargarActividades(String codConcurso) throws SQLException {
        Actividad actividad;
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_actividad, act.nombre nomact , observacion, fecha_limite, con.cod_concurso codcon, con.nombre nomcon "+                   
                        " FROM campaña.actividad act " +
                        " JOIN campaña.concurso con on(con.cod_concurso=act.fk_cod_concurso) "+                        
                        " WHERE con.cod_concurso='"+codConcurso+"' " +
                        " ORDER BY fecha_limite asc";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                actividad =new Actividad();
                actividad.setCodActividad(rs.getString("cod_actividad"));
                actividad.setNombre(rs.getString("nomact"));
                actividad.setObservacion(rs.getString("observacion"));
                actividad.setFechaLimite(rs.getDate("fecha_limite"));
                actividad.setConcurso(new Concurso(rs.getString("codcon"), rs.getString("nomcon"), null, 0, false, null,"", null));
                listaActividades.add(actividad);
            }
            return listaActividades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<Actividad> cargarActividadesJueces(String codGrupo) throws SQLException {
        Actividad actividad;
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_actividad, act.nombre nomact , observacion,fecha_limite, con.cod_concurso codcon, con.nombre nomcon " +
                        "FROM campaña.actividad act " +
                        "JOIN campaña.concurso con on(con.cod_concurso=act.fk_cod_concurso) "+
                        " WHERE cod_concurso='"+codGrupo+"'" +
                        "ORDER BY cod_actividad";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                actividad =new Actividad();
                actividad.setCodActividad(rs.getString("cod_actividad"));
                actividad.setNombre(rs.getString("nomact"));
                actividad.setObservacion(rs.getString("observacion"));
                actividad.setFechaLimite(rs.getDate("fecha_limite"));
                actividad.setConcurso(new Concurso(rs.getString("codcon"), rs.getString("nomcon"), null, 0, false, null,"",null));
                listaActividades.add(actividad);
            }
            return listaActividades;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<CalificacionActividad> cargarLIstaCalificacionesEquipo(String codGrupo) throws SQLException {
        CalificacionActividad calificacion;
        ArrayList<CalificacionActividad> listaCalificaciones = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select cast(cact.cod_actividad as INTEGER) codact, cact.calificacion calificacion, act.nombre nomact" +
                    " from campaña.calificacion_actividad cact " +
                    " join campaña.actividad act on (act.cod_actividad=cact.cod_actividad) " +
                    " where cod_grupo='"+codGrupo+"' "
                    + " ORDER BY codact asc   ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                calificacion =new CalificacionActividad();
                calificacion.setCodActividad(rs.getString("codact"));
                calificacion.setCalificacion(rs.getInt("calificacion"));
                calificacion.setActividad(new Actividad(rs.getString("codact"), rs.getString("nomact")));
                calificacion.setCodGrupo(codGrupo);
                listaCalificaciones.add(calificacion);
            }
            return listaCalificaciones;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public ArrayList<GrupoConcursoParticipantes> cargarListaGrupoParticipantes(String codConcurso) throws SQLException {
        GrupoConcursoParticipantes grupoParticipantes;
        ArrayList<GrupoConcursoParticipantes> listaGrupoParticipantes = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT fk_cedula, e.nombres nome, e.apellidos apee, capitan " +
                        " FROM  campaña.grupo_concurso_participantes gp " +
                        " JOIN empleado e on(e.cedula=gp.fk_cedula) "+
                        "  WHERE cod_grupo='"+codConcurso+"' " +
                        " ORDER BY cod_grupo_participantes ";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                grupoParticipantes =new GrupoConcursoParticipantes();
                grupoParticipantes.setEmpleado(new Empleado(rs.getString("fk_cedula"), rs.getString("nome"), rs.getString("apee")));
                grupoParticipantes.setCapitan(rs.getBoolean("capitan"));
                
                listaGrupoParticipantes.add(grupoParticipantes);
            }
            return listaGrupoParticipantes;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public  ArrayList<GrupoConcurso> cargarListaEquiposSubempresa(String nitsesion) throws SQLException {
        GrupoConcurso grupoConcurso;
        ArrayList<GrupoConcurso> listaGrupoConcursosSubempresa=new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select cod_grupo, cod_concurso, nombre " +
                        "from campaña.grupo_concurso " +
                        "where fk_nitsubempresa='"+nitsesion+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                grupoConcurso=new GrupoConcurso();                
                grupoConcurso.setCodGrupo(rs.getString("cod_grupo"));
                grupoConcurso.setConcurso(new Concurso(rs.getString("nombre"), "", 0));
                grupoConcurso.setNombre(rs.getString("nombre"));                
                grupoConcurso.setSubempresa(new SubEmpresa(nitsesion, ""));
                listaGrupoConcursosSubempresa.add(grupoConcurso);                
            }
            return listaGrupoConcursosSubempresa;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<SubEmpresa> cargarListaSubempresas(String nitem) throws SQLException {
        SubEmpresa subempresas;
        ArrayList<SubEmpresa> listaSubempresas=new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select nombre, nitsubempresa"
                    + " from subempresa "
                    + " where fk_nitempresa='"+nitem+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                subempresas=new SubEmpresa();
                subempresas.setNombre(rs.getString("nombre"));
                subempresas.setNitsubempresa(rs.getString("nitsubempresa"));
                subempresas.setEmpresa(new Empresa(nitem, ""));
                listaSubempresas.add(subempresas);
                
            }
            return listaSubempresas;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
}

