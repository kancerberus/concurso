/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Motivo;;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import modelo.Accidente;
import modelo.AgenteAccidente;
import modelo.Cargo;
import modelo.CausaBasica;
import modelo.CausaInmediata;
import modelo.Cie10;
import modelo.Clasificacion;
import modelo.GrupoCie10;
import modelo.Mecanismo;
import modelo.ParteAfectada;
import modelo.Riesgo;
import modelo.TipoAccidente;
import modelo.TipoEvento;
import modelo.TipoLesion;
import vista.UIListas;

/**
 *
 * @author diego
 */
public class AccidenteDAO {    
    private Connection conexion;
    private UIListas uilistas;
    
    
    public AccidenteDAO(Connection conexion) throws Exception{
        this.conexion = conexion;
        this.uilistas = new UIListas();
    }  
    
    public Integer guardarAccidente(Accidente accidente) throws SQLException{
        Consulta consulta = null;        
        Integer resultado;
        
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate ahora = LocalDate.now();
        
        SimpleDateFormat ffecha= new SimpleDateFormat("yyyy/MM/dd");        
        
              
        try {
            consulta = new Consulta(getConexion());                
            
            //Sentencia SQL para guardar el registro
                String sql = "INSERT INTO registro_accidente ("
                        + "fk_cedula, fecha_registro, fecha_ocurrencia, cod_det_lista_tipo_evento, "
                        + " cod_det_lista_clasif, investigacion, cod_det_lista_tipo_incapacidad, cod_det_lista_tipo_accidente, "
                        + " cod_det_lista_parte_afectada, cod_det_lista_tipo_lesion, cod_det_lista_riesgo, cod_det_lista_mecanismo, "
                        + " cod_det_lista_agente_acc, cod_det_lista_causa_bas, cod_det_lista_causa_inm, descripcion_acc) "                        
                        + "VALUES ("
                        + "'" + accidente.getEmpleado().getCedula() + "',"
                        + "'" + formatoFecha.format(ahora) + "',"                                                                                                    
                        + "'" + ffecha.format(accidente.getFechaOcurrencia()) + "',"
                        + "'" + accidente.getTipoEvento().getCodigo() + "',"
                        + "'" + accidente.getClasificacion().getCodigo() + "',"
                        + "'" + accidente.isInvestigacion() + "',"
                        + "'" + accidente.getIncapacidadsi().getCodigo() + "',"
                        + "'" + accidente.getTipoAccidente().getCodigo() + "',"
                        + "'" + accidente.getParteAfectada().getCodigo() + "',"
                        + "'" + accidente.getTipoLesion().getCodigo()+ "',"
                        + "'" + accidente.getRiesgo().getCodigo()+ "',"
                        + "'" + accidente.getMecanismo().getCodigo()+ "',"
                        + "'" + accidente.getAgenteAccidente().getCodigo()+ "',"
                        + "'" + accidente.getCausaBasica().getCodigo()+ "',"
                        + "'" + accidente.getCausaInmediata().getCodigo()+ "',"
                        + "'" + accidente.getDescAccidente()+ "')";
            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }   
    
    
    public ArrayList<Motivo> listarMotivosIncapacidad() throws SQLException {
        Motivo motivo;
        ArrayList<Motivo> listaMotivos = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " SELECT cod_motivo,nombre_motivo,tipo " +
                        "FROM motivopermiso " +
                        "WHERE cod_motivo='2' or cod_motivo='1' " +
                        "ORDER BY cod_motivo";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                motivo = new Motivo();
                motivo.setCodigo(rs.getString("cod_motivo"));
                motivo.setNombrem(rs.getString("nombre_motivo"));
                motivo.setTipo(rs.getString("tipo"));
                listaMotivos.add(motivo);
            }
            return listaMotivos;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Cie10 cargarCie10(String Cie10) throws SQLException {
        Cie10 cie10=null;
        ResultSet rs;
        Consulta consulta = null;
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = " select c10.cod_cie10 codCie10, c10.nombre nomCie10, gc10.cod_grupo_cie10 codGcie10, gc10.nombre nomGCie10 " +
                    " FROM cie10 c10 " +
                    " JOIN grupo_cie10 gc10 using (cod_grupo_cie10) " +
                    " WHERE cod_cie10='"+Cie10+"' ";
                        

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                cie10= new Cie10();
                cie10.setCodCie10(rs.getString("codCie10"));
                cie10.setNombre(rs.getString("nomCie10"));
                cie10.setGrupoCie10(new GrupoCie10(rs.getString("codGcie10"), rs.getString("nomGCie10")));
            }
            return cie10;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    //Acumulador de horas de ausentismo tipo EM-TR
    public Integer horasAcumuladas(String cedula) throws SQLException {
        int thacum =0 ;         
        ResultSet rs;
        Consulta consulta = null;  
        
        
        LocalDate ahora = LocalDate.now();
        String ano = ahora.format(DateTimeFormatter.ofPattern("yyyy"));
        
        try {
            String sql;
            
            consulta = new Consulta(getConexion());
            sql = "select sum(cast(tiempohoras as double precision)) as thacum "
                   +"from registro_ausentismo re "
                   +"inner join motivopermiso mo on (mo.cod_motivo=re.fk_cod_motivo) " 
                   +"where fk_cedula='"+cedula+"' "
                   +"and mo.tipo = 'EM-TR' and to_char (fechapermiso,'yyyy')= '"+ano+"'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                thacum=rs.getInt("thacum");
            }
            
            return thacum;
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    private void setNormal(boolean b) {        
    }   

    public Connection getConexion() {
        return conexion;
    }

    public Collection<? extends Accidente> cargarDistribucionTipoEventos(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        
        Integer casos=0;        
        Integer incapacitantes=0;        
        Integer investigados=0;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {          
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT dl.nombre nombre, dl.cod_det_lista cod_det_lista_tipo_evento " +
                " FROM det_lista  dl " +
                " JOIN lista l on(l.cod_lista=dl.fk_cod_lista) " +
                " WHERE cod_lista='4' " +
                " GROUP BY dl.nombre, cod_lista, dl.cod_det_lista " + 
                " ORDER BY dl.cod_det_lista "    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new 
                TipoEvento(rs.getString("cod_det_lista_tipo_evento"), rs.getString("nombre")),0, 0, 0);                                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            
            if(nitem != null && selano != null  && nitsubem==null){
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"'"+
                        " and "+queryfecha+"='"+selano+"'"
                        + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                        
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) { 
                    casos=rs.getInt("cantidad");                    
                }
                
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                        + " cod_det_lista_tipo_incapacidad='25'"+
                        " and "+queryfecha+"='"+selano+"'"
                        + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) { 
                    incapacitantes=rs.getInt("cantidad");                                    
                }

                    
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                        + " investigacion='"+true+"' "+
                        " AND "+queryfecha+"='"+selano+"'"
                        + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) { 
                    investigados=rs.getInt("cantidad");                                        
                }

                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoEvento(), casos, incapacitantes, investigados));
            }
            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){

                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql1 = new StringBuilder(
                            "SELECT count(cod_regaccidente) cantidad " +
                            " FROM registro_accidente " +
                            " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"'"+
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql1);
                    while (rs.next()) { 
                        casos=rs.getInt("cantidad");                    
                    }

                    consulta = new Consulta(this.conexion);
                    StringBuilder sql2 = new StringBuilder(
                            "SELECT count(cod_regaccidente) cantidad " +
                            " FROM registro_accidente " +
                            " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                            + " cod_det_lista_tipo_incapacidad='25'"+
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql2);
                    while (rs.next()) { 
                        incapacitantes=rs.getInt("cantidad");                                            
                    }

                    consulta = new Consulta(this.conexion);
                    StringBuilder sql3 = new StringBuilder(
                            "SELECT count(cod_regaccidente) cantidad " +
                            " FROM registro_accidente " +
                            " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                            + " investigacion='"+true+"' "+
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql3);
                    while (rs.next()) { 
                        investigados=rs.getInt("cantidad");                         
                    }
                    listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoEvento(), casos, incapacitantes, investigados));
                }           
            }      
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"'"+
                        " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                        
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) { 
                    casos=rs.getInt("cantidad");                    
                }
                
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                        + " cod_det_lista_tipo_incapacidad='25'"+
                        " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) { 
                    incapacitantes=rs.getInt("cantidad");                                    
                }

                    
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                        "SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " WHERE cod_det_lista_tipo_evento='"+listaDistribucionTiposAccidente.get(i).getTipoEvento().getCodigo()+"' AND "
                        + " investigacion='"+true+"' "+
                        " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) { 
                    investigados=rs.getInt("cantidad");                                        
                }

                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoEvento(), casos, incapacitantes, investigados));
            }
            }
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    ////
    
    ////
    public Collection<? extends Accidente> cargarDistribucionRiesgos(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer enfermedad=0;
        Integer incatel=0;
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre " +
                " FROM det_lista " +
                " WHERE fk_cod_lista='8' "    
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new Riesgo(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , incidentes, accidentes, enfermedad, incatel);                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+    
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +                    
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) "
                    +"inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                incatel=incidentes+accidentes;                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getRiesgo(), incidentes, accidentes, enfermedad, incatel));
                
            }
                
            }
            
            
            if(nitem != null && selano != null){
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+    
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +                    
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) "
                    +"inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                incatel=incidentes+accidentes;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getRiesgo(), incidentes, accidentes, enfermedad, incatel));
                
            }
            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){                
                
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+    
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_riesgo) " +
                    " WHERE ra.cod_det_lista_riesgo='"+listaDistribucionTiposAccidente.get(i).getRiesgo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                incatel=incidentes+accidentes;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getRiesgo(), incidentes, accidentes, enfermedad, incatel));
                
            }           
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Collection<? extends Accidente> cargarDistribucionCausaBasica(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer enfermedad=0;
        Integer total=0;
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {       
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='12' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new CausaBasica(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , incidentes, accidentes, enfermedad, total);                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                total=incidentes+accidentes+enfermedad;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaBasica(), incidentes, accidentes, enfermedad, total));                
            }
            }
            
            if(nitem != null && selano != null){
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                    " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                total=incidentes+accidentes+enfermedad;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaBasica(), incidentes, accidentes, enfermedad, total));                
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){

                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql1 = new StringBuilder(
                        " SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente ra " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                        " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                        " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql1);
                    while (rs.next()) {
                        incidentes=rs.getInt("cantidad");
                    }

                    consulta = new Consulta(this.conexion);
                    StringBuilder sql2 = new StringBuilder(
                        " SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente ra "+                            
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                        " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                        " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql2);
                    while (rs.next()) {
                        accidentes=rs.getInt("cantidad");
                    }

                    consulta = new Consulta(this.conexion);
                    StringBuilder sql3 = new StringBuilder(
                        " SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente ra " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_bas) " +
                        " WHERE ra.cod_det_lista_causa_bas='"+listaDistribucionTiposAccidente.get(i).getCausaBasica().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                        " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql3);
                    while (rs.next()) {
                        enfermedad=rs.getInt("cantidad");
                    }
                    total=incidentes+accidentes+enfermedad;

                    listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaBasica(), incidentes, accidentes, enfermedad, total));                
                }
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionCausaInmediata(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer enfermedad=0;
        Integer total=0;
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='13' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new CausaInmediata(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , incidentes, accidentes, enfermedad, total);                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                total=incidentes+accidentes+enfermedad;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaInmediata(), incidentes, accidentes, enfermedad, total));                
            }
            }
            
            if(nitem != null && selano != null){            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and "+queryfecha+"='"+selano+"'"                            
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                total=incidentes+accidentes+enfermedad;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaInmediata(), incidentes, accidentes, enfermedad, total));                
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11'"+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_causa_inm) " +
                    " WHERE ra.cod_det_lista_causa_inm='"+listaDistribucionTiposAccidente.get(i).getCausaInmediata().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='13' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    enfermedad=rs.getInt("cantidad");
                }
                total=incidentes+accidentes+enfermedad;
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getCausaInmediata(), incidentes, accidentes, enfermedad, total));                
            }
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionTipoAccidente(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer casos=0;        
        double porcentaje=0;
        double total=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {

            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='5' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new TipoAccidente(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0 );                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                "SELECT count(cod_regaccidente) total " +
                " FROM registro_accidente ra " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                " WHERE dl.fk_cod_lista='5'"+
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {                   
                total=rs.getInt("total");
            }
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+                            
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                    " WHERE ra.cod_det_lista_tipo_accidente='"+listaDistribucionTiposAccidente.get(i).getTipoAccidente().getCodigo()+"'"+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    casos=rs.getInt("cantidad");
                }                
                
                porcentaje=(casos/total)*100;
                
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoAccidente(), casos, porcentaje));                
            }
            }            
            
            if(nitem != null && selano != null){
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                "SELECT count(cod_regaccidente) total " +
                " FROM registro_accidente ra " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                " WHERE dl.fk_cod_lista='5'"+
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {                   
                total=rs.getInt("total");
            }
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+                            
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                    " WHERE ra.cod_det_lista_tipo_accidente='"+listaDistribucionTiposAccidente.get(i).getTipoAccidente().getCodigo()+"'"+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    casos=rs.getInt("cantidad");
                }                
                
                porcentaje=(casos/total)*100;
                
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoAccidente(), casos, porcentaje));                
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql1 = new StringBuilder(
                    "SELECT count(cod_regaccidente) total " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                    " WHERE dl.fk_cod_lista='5'"+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql1);
                while (rs.next()) {                   
                    total=rs.getInt("total");
                }


                for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                    consulta = new Consulta(this.conexion);
                    StringBuilder sql2 = new StringBuilder(
                        " SELECT count(cod_regaccidente) cantidad " +
                        " FROM registro_accidente ra " +
                        "inner join empleado e on (e.cedula=fk_cedula) "+
                        " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_accidente) " +
                        " WHERE ra.cod_det_lista_tipo_accidente='"+listaDistribucionTiposAccidente.get(i).getTipoAccidente().getCodigo()+"'"+
                        " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                    );
                    rs = consulta.ejecutar(sql2);
                    while (rs.next()) {
                        casos=rs.getInt("cantidad");
                    }                

                    porcentaje=(casos/total)*100;


                    listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getTipoAccidente(), casos, porcentaje));                
                }           
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }


    public Collection<? extends Accidente> cargarDistribucionClasificaciones(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer casos=0;        
        double porcentaje=0;
        double total=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {     
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='11' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new Clasificacion(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0 );                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                "SELECT count(cod_regaccidente) total " +
                " FROM registro_accidente ra " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                " WHERE dl.fk_cod_lista='11'"+
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {                   
                total=rs.getInt("total");
            }
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                    " WHERE ra.cod_det_lista_clasif='"+listaDistribucionTiposAccidente.get(i).getClasificacion().getCodigo()+"'"+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    casos=rs.getInt("cantidad");
                }                
                
                porcentaje=(casos/total)*100;               
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getClasificacion(), casos, porcentaje));                
            }
            }
            
            if(nitem != null && selano != null){
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                "SELECT count(cod_regaccidente) total " +
                " FROM registro_accidente ra " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                " WHERE dl.fk_cod_lista='11'"+
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {                   
                total=rs.getInt("total");
            }
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                    " WHERE ra.cod_det_lista_clasif='"+listaDistribucionTiposAccidente.get(i).getClasificacion().getCodigo()+"'"+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    casos=rs.getInt("cantidad");
                }                
                
                porcentaje=(casos/total)*100;               
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getClasificacion(), casos, porcentaje));                
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
            consulta = new Consulta(this.conexion);
            StringBuilder sql1 = new StringBuilder(
                "SELECT count(cod_regaccidente) total " +
                " FROM registro_accidente ra " +
                " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                " WHERE dl.fk_cod_lista='11'"+
                " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
            );
            rs = consulta.ejecutar(sql1);
            while (rs.next()) {                   
                total=rs.getInt("total");
            }
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_clasif) " +
                    " WHERE ra.cod_det_lista_clasif='"+listaDistribucionTiposAccidente.get(i).getClasificacion().getCodigo()+"'"+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    casos=rs.getInt("cantidad");
                }                
                
                porcentaje=(casos/total)*100;               
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getClasificacion(), casos, porcentaje));                
            }             
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }    
    
    public Collection<? extends Accidente> cargarDistribucionMecanismos(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTiposAccidentes = new ArrayList<>();
        List<Accidente> listaDistribucionTiposAccidente = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer totales=0;
        double totalPorcentaje=0;
        double porcentaje=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {        
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='9' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new Mecanismo(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0,0,0 );                
                listaDistribucionTiposAccidente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='9' "+
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getMecanismo(), incidentes, accidentes,totales,porcentaje));
            }
            }
            
            if(nitem != null && selano != null){            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='9' "+
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getMecanismo(), incidentes, accidentes,totales,porcentaje));
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='9' "+
                " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTiposAccidente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_mecanismo) " +
                    " WHERE ra.cod_det_lista_mecanismo='"+listaDistribucionTiposAccidente.get(i).getMecanismo().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTiposAccidentes.add(new Accidente(listaDistribucionTiposAccidente.get(i).getMecanismo(), incidentes, accidentes,totales,porcentaje));
            }           
            }           
            
            return listaDistribucionTiposAccidentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionAgente(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionAgentes = new ArrayList<>();
        List<Accidente> listaDistribucionAgente = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer totales=0;
        double totalPorcentaje=0;
        double porcentaje=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='10' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new AgenteAccidente(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0,0,0 );                
                listaDistribucionAgente.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
               consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='10' "   +
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionAgente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionAgentes.add(new Accidente(listaDistribucionAgente.get(i).getAgenteAccidente(), incidentes, accidentes,totales,porcentaje));
            } 
            }
            
            
            if(nitem != null && selano != null){            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='10' "   +
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionAgente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionAgentes.add(new Accidente(listaDistribucionAgente.get(i).getAgenteAccidente(), incidentes, accidentes,totales,porcentaje));
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='10' "   +
                " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionAgente.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_agente_acc) " +
                    " WHERE ra.cod_det_lista_agente_acc='"+listaDistribucionAgente.get(i).getAgenteAccidente().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionAgentes.add(new Accidente(listaDistribucionAgente.get(i).getAgenteAccidente(), incidentes, accidentes,totales,porcentaje));
            }   
            }           
            
            return listaDistribucionAgentes;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionParteAfectada(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionParteAfectadas = new ArrayList<>();
        List<Accidente> listaDistribucionParteAfectada = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer totales=0;
        double totalPorcentaje=0;
        double porcentaje=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {       
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='6' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new ParteAfectada(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0,0,0 );                
                listaDistribucionParteAfectada.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='6' "+
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionParteAfectada.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionParteAfectadas.add(new Accidente(listaDistribucionParteAfectada.get(i).getParteAfectada(), incidentes, accidentes,totales,porcentaje));
            }
            }
            
            if(nitem != null && selano != null){            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='6' "+
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionParteAfectada.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionParteAfectadas.add(new Accidente(listaDistribucionParteAfectada.get(i).getParteAfectada(), incidentes, accidentes,totales,porcentaje));
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='6' "+
                " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionParteAfectada.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_parte_afectada) " +
                    " WHERE ra.cod_det_lista_parte_afectada='"+listaDistribucionParteAfectada.get(i).getParteAfectada().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionParteAfectadas.add(new Accidente(listaDistribucionParteAfectada.get(i).getParteAfectada(), incidentes, accidentes,totales,porcentaje));
            }           
            }           
            
            return listaDistribucionParteAfectadas;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    public Collection<? extends Accidente> cargarDistribucionTipoLesion(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionTipoLesiones = new ArrayList<>();
        List<Accidente> listaDistribucionTipoLesion = new ArrayList<>();
        
        Integer incidentes=0;        
        Integer accidentes=0;
        Integer totales=0;
        double totalPorcentaje=0;
        double porcentaje=0;
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {    
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql = new StringBuilder(
                " SELECT cod_det_lista, nombre"+
                " FROM det_lista " +
                " WHERE fk_cod_lista='7' "
            );
            rs = consulta.ejecutar(sql);
            while (rs.next()) {                   
                Accidente accidente=new Accidente(new TipoLesion(rs.getString("cod_det_lista"), rs.getString("nombre"))
                , 0,0,0,0 );                
                listaDistribucionTipoLesion.add(accidente);
            }
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='7' "+
                " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTipoLesion.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                        " and " + queryfecha + " = '" + selfecha + "'"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTipoLesiones.add(new Accidente(listaDistribucionTipoLesion.get(i).getTipoLesion(), incidentes, accidentes,totales,porcentaje));
            }
            }
            
            
            if(nitem != null && selano != null){            
            
            consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                "inner join empleado e on (e.cedula=fk_cedula) "+
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='7' "+
                " and "+queryfecha+"='"+selano+"'"
                + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTipoLesion.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    "inner join empleado e on (e.cedula=fk_cedula) "+
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTipoLesiones.add(new Accidente(listaDistribucionTipoLesion.get(i).getTipoLesion(), incidentes, accidentes,totales,porcentaje));
            }            
                
            }
            
            if(selmesdesde!=null && selmeshasta!=null){
                
                consulta = new Consulta(this.conexion);
            StringBuilder sql4 = new StringBuilder(
                "SELECT count(cod_regaccidente) total  " +
                " FROM registro_accidente ra  " +
                " JOIN det_lista dl ON(dl.cod_det_lista=ra.cod_det_lista_tipo_evento) " +
                " JOIN det_lista d ON(d.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                " WHERE ra.cod_det_lista_tipo_evento='11' OR ra.cod_det_lista_tipo_evento='12' AND " +
                " d.fk_cod_lista='7' "+
                " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
            );
            rs = consulta.ejecutar(sql4);
            while (rs.next()) {
                totalPorcentaje=rs.getInt("total");
            }
            
            
            
            for(int i=0; i<=listaDistribucionTipoLesion.size()-1;i++){
                consulta = new Consulta(this.conexion);
                StringBuilder sql2 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='11' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql2);
                while (rs.next()) {
                    incidentes=rs.getInt("cantidad");
                }
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql3 = new StringBuilder(
                    " SELECT count(cod_regaccidente) cantidad " +
                    " FROM registro_accidente ra " +
                    " JOIN det_lista dl on(dl.cod_det_lista=ra.cod_det_lista_tipo_lesion) " +
                    " WHERE ra.cod_det_lista_tipo_lesion='"+listaDistribucionTipoLesion.get(i).getTipoLesion().getCodigo()+"' AND ra.cod_det_lista_tipo_evento='12' "+
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                );
                rs = consulta.ejecutar(sql3);
                while (rs.next()) {
                    accidentes=rs.getInt("cantidad");
                }
                
                totales=incidentes+accidentes;
                double tottt=totales;
                porcentaje=(tottt/totalPorcentaje)*100;
                
                
                listaDistribucionTipoLesiones.add(new Accidente(listaDistribucionTipoLesion.get(i).getTipoLesion(), incidentes, accidentes,totales,porcentaje));
            }
            }           
            
            return listaDistribucionTipoLesiones;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Collection<? extends Accidente> cargarDistribucionCargos(String nitem, String nitsubem, String selmesdesde, String selmeshasta, String selano ) throws SQLException {
        ResultSet rs = null;
        Consulta consulta = null;
        List<Accidente> listaDistribucionCargos = new ArrayList<>();        
        
        
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;        

         if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fecha_registro,'yyyy')";                         
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fecha_registro,'yyyy/mm')";            
        }
        
        try {                
            
            if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regaccidente) accidentes, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_accidente ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and e.nitsubempresa = '"+nitsubem+"'"+
                    " and " + queryfecha + " = '" + selfecha + "'"+
                    " GROUP BY e.cargo, car.cargo "
                    
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Accidente accidente=new Accidente(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("accidentes"));
                    listaDistribucionCargos.add(accidente);
                }
            }
            
            if(nitem != null && selano != null){
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regaccidente) accidentes, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_accidente ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and "+queryfecha+"='"+selano+"'"
                    + " and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"')"+
                    " GROUP BY e.cargo, car.cargo "
                    
                );
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Accidente accidente=new Accidente(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("accidentes"));
                    listaDistribucionCargos.add(accidente);
                }
            
            }            
            
            if(selmesdesde!=null && selmeshasta!=null){
                
                consulta = new Consulta(this.conexion);
                StringBuilder sql = new StringBuilder(
                    " SELECT count(cod_regaccidente) accidentes, e.cargo cargo, car.cargo nomcargo " +
                    " FROM registro_accidente ra " +                    
                    " JOIN empleado e ON (e.cedula=ra.fk_cedula) " +
                    " JOIN cargo car ON (car.cod_cargo=e.cargo) " +
                    " WHERE e.cargo<>'0' " +
                    " and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "+
                    " GROUP BY e.cargo, car.cargo ");
                    
                rs = consulta.ejecutar(sql);
                while (rs.next()) {                   
                    Accidente accidente=new Accidente(new Cargo(rs.getString("cargo"), rs.getString("nomcargo")), rs.getInt("accidentes"));
                    listaDistribucionCargos.add(accidente);
                }          
            }           
            
            return listaDistribucionCargos;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
}
