/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Ausentismo;
import modelo.InformeAusentismo;
import modelo.Motivo;
import modelo.Configuracion;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import modelo.Cie10;
import modelo.Empleado;
import modelo.Empresa;
import modelo.GrupoCie10;
import modelo.Incapacidad;
import org.primefaces.model.chart.PieChartModel;
import vista.UIListas;

/**
 *
 * @author diego
 */
public class IncapacidadDAO {    
    private Connection conexion;
    private UIListas uilistas;
    
    
    public IncapacidadDAO(Connection conexion) throws Exception{
        this.conexion = conexion;
        this.uilistas = new UIListas();
    }
    
    
    public ArrayList<Ausentismo> listarAusentismos(String nitsesion) throws SQLException {
        Ausentismo au;
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;           
        
        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "select e.nitsubempresa, ra.cod_regausentismo codigo, ra.tiempohoras, ra.fk_cedula cedula,e.nombres nombres,e.apellidos apellidos,ra.fechapermiso fechaper,mo.nombre_motivo motivo " 
                    + "from registro_ausentismo ra " 
                    + "inner join empleado e on (cedula=fk_cedula) " 
                    + "inner join motivopermiso mo on (cod_motivo=fk_cod_motivo) "                    
                    + "where ra.estado = 'A' and e.nitsubempresa ='" + nitsesion + "' "
                    + "order by cod_regausentismo";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                au = new Ausentismo();
                au.setCod_registro(rs.getString("codigo"));
                au.getEmpleado().setCedula(rs.getString("cedula"));
                au.getEmpleado().setNombres(rs.getString("nombres"));
                au.getEmpleado().setApellidos(rs.getString("apellidos"));
                au.setFecha_permiso(rs.getDate("fechaper"));
                au.setMotivoausentismo(rs.getString("motivo"));
                au.setTiempo_horas(rs.getString("tiempohoras"));

                listaAusentismo.add(au);
            }
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public ArrayList<Ausentismo> ausentismoAnomes(String nitsesion,String selmesano, String selano) throws SQLException {

        Ausentismo au;            
        ArrayList<Ausentismo> listaAusentismo = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String queryfecha = null;
        Double totaleps = 0.00;

        if (selmesano == null){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')";
        }else{
            selfecha = selmesano;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }


        try {
            consulta = new Consulta(getConexion());
            String sql
                    = "select fechapermiso, e.nombres nombres, e.apellidos apellidos, e.sueldo_mes sueldo, e.nitsubempresa,tiempohoras, m.nombre_motivo nom_motivo, m.tipo tipo,"
                    + "ra.eps eps, ra.arl arl, ra.empleador empleador, ra.trabajador trabajador, ra.total total " 
                    + "from registro_ausentismo ra " 
                    + "inner join empleado e on (cedula=fk_cedula) " 
                    + "inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                    + "where e.nitsubempresa ='" + nitsesion + "' and " + queryfecha + "='" + selfecha + "' "
                    + "order by fechapermiso";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                au = new Ausentismo();


                String nomfecha = "";
                String formato = "MM";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
                String fecha = simpleDateFormat.format(rs.getDate("fechapermiso"));

                //trae el nombre del mes
                nomfecha = uilistas.convertirMeses(fecha);
                au.setFecha_permiso(rs.getDate("fechapermiso"));
                au.getEmpleado().setNombres(rs.getString("nombres"));
                au.getEmpleado().setApellidos(rs.getString("apellidos"));
                au.getEmpleado().setSueldo_mes(rs.getInt("sueldo"));
                au.getEmpleado().setNitsubempresa(rs.getString("nitsubempresa"));
                au.setTiempo_horas(rs.getString("tiempohoras"));
                au.getMotivo().setNombrem(rs.getString("nom_motivo"));
                au.setMes(nomfecha);
                au.getMotivo().setTipo(rs.getString("tipo"));
                au.setEps(rs.getDouble("eps"));
                au.setArl(rs.getDouble("arl"));
                au.setEmpleador(rs.getDouble("empleador"));
                au.setTrabajador(rs.getDouble("trabajador"));
                au.setTotal(rs.getDouble("total"));                                         

                listaAusentismo.add(au);
            }      
            return listaAusentismo;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    } 
    
    public ArrayList<Ausentismo> ausentismoanomesEmpresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;        
        String thconv;
        ArrayList<Ausentismo> listaausentismoEmpresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";
        

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = "select  se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) "                             
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " = '" + selfecha + "' " 
                            +"group by se.nombre, to_char(fechapermiso,'mm')";                    
                }
                //Valida que llegue año y empresa y motivopermiso
                if (nitem != null && selano != null && selmotivo!=null) {
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, "
                            +"sum(cast(tiempohoras as float)) as tothoras, sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') and fk_cod_motivo='"+selmotivo+"' "
                            +"and " + queryfecha + " = '" + selfecha + "' "
                            +"group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo";
                }
                
                //Valida que llegue nitsubempresa nitempresa y año
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'"+
                            "and " + queryfecha + " = '" + selfecha + "'"+
                            "group by se.nombre, to_char(fechapermiso,'mm')";                    
                }
                //Valida que llegue nitsubempresa nitempresa año y motivo
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null && selmotivo!=null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)  "
                            +"where e.nitsubempresa = '"+nitsubem+"' and fk_cod_motivo='"+selmotivo+"' "
                            + "and " + queryfecha + " = '" + selfecha + "'"
                            +"group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo"; 
                    
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año
                if (selmesdesde != null && selmeshasta != null && nitsubem != null) {                                        
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "group by se.nombre, to_char(fechapermiso,'mm')";                    
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año y motivo
                if (selmesdesde != null && selmeshasta != null && nitsubem != null && selmotivo!=null) {
                    sql= "select se.nombre,to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "+
                            "sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "+
                            "round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "+
                            "from registro_ausentismo "+
                            "inner join empleado e on (e.cedula=fk_cedula) "+
                            "inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "+
                            "where e.nitsubempresa = '"+nitsubem+"'" +
                            "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " +
                            "and fk_cod_motivo='"+selmotivo+"'"+
                            "group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo";                    
                }
                //valida que llegue nitempresa mes desde y mes hasta
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null){
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, to_char(fechapermiso,'mm')";                 

                }
                //Valida que llegue nitempresa mes desde mes hasta y motivo
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null && selmotivo!=null){
                    sql = "select se.nombre as nombre, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, "
                            +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, "
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo= '"+selmotivo+"'"
                            +"group by se.nombre, to_char(fechapermiso,'mm'), fk_cod_motivo";
                }
                
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {
                        String fecha = rs.getString("fechaper");
                        String mes = uilistas.convertirMeses(fecha);                        
                        //convertir tiempo horas en float
                        float thcon = Float.parseFloat(rs.getString("tothoras"));                        
                        String threg = Float.toString(thcon);                        
                        au = new Ausentismo();
                        au.getSubempresa().setNombre(rs.getString("nombre"));
                        au.setMes(mes);       
                        
                        au.setTiempo_horas(threg);                        
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        au.setTotalsube(rs.getDouble("totalsube"));  
                        
                        

                        listaausentismoEmpresa.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return listaausentismoEmpresa;
    }
    
    public ArrayList<Ausentismo> ausentismoanomesEmpleado(String cedula,String nitem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;            
        ArrayList<Ausentismo> listausentismoEmpleado = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";

        //formato fecha recibida
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
        
        //Valida y muestra la seleccion de empresa año y motivo de la aucensia
        if (nitem != null && selano != null && selmotivo != null) {
                sql = "select cedula , e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfecha+" = '"+selfecha+"' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo";
        }
        //Valida y muestra la seleccion de empresa y año
        if (nitem != null && selano != null && selmotivo == null) {
                sql = "select cedula, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and "+queryfecha+" = '"+selfecha+"' "
                        +"group by e.nombres, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo";
        }
        //valida y muestra la seleccion de empresa mesdesde y meshasta y año correspondiente
        if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null ) {
                sql = "select cedula, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                        +"group by e.nombres, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo";                        
        }
        //valida y muestra la seleccion de empresa mesdesde meshasta motivo y año
        if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null && selmotivo!=null ) {
                sql = "select cedula, e.nombres as nombre, apellidos, to_char(fechapermiso,'mm') as fechaper, sum(cast(tiempohoras as float)) as tothoras, " 
                        +"sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube, m.nombre_motivo as motivo  " 
                        +"from registro_ausentismo re " 
                        +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                        +"where fk_cedula='"+cedula+"' and e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') "
                        +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' and fk_cod_motivo='"+selmotivo+"' "
                        +"group by e.nombres, e.cedula, e.apellidos, to_char(fechapermiso,'mm'), m.nombre_motivo";                        
        }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {
                        String fecha = rs.getString("fechaper");
                        String mes = uilistas.convertirMeses(fecha);
                        au = new Ausentismo();
                        au.getEmpleado().setCedula(rs.getString("cedula"));
                        au.getEmpleado().setNombres(rs.getString("nombre"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.setMes(mes);  
                        au.setTiempo_horas(rs.getString("tothoras"));
                        au.setMotivoausentismo(rs.getString("motivo"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        au.setTotalsube(rs.getDouble("totalsube"));                                 

                        listausentismoEmpleado.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }
        return listausentismoEmpleado;
    }
    public ArrayList<Ausentismo> pieanomesEmpleado(String cedula,String nitem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {
        
        Ausentismo au;       
        ArrayList<Ausentismo> pieausentismoEmpleado = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
                            
                //Valida que llegue año cedula y empresa
                if (nitem != null && selano != null) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' and "+queryfecha+" = '"+selfecha+"' " 
                            +"group by nombres,apellidos";                 
                }
                
                //valida y muestra la seleccion de empresa mesdesde y meshasta y año correspondiente
                 if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null ) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "'  " 
                            +"group by nombres,apellidos";  
                 }
                 
                 if (nitem != null &&  selmesdesde != null && selmeshasta != null && queryfecha!=null && selmotivo!=null ) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' "
                            + "and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "'  " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by nombres, apellidos, m.nombre_motivo";                                            
                 }
                 
                 if (nitem != null && selano != null && selmotivo != null) {
                    sql = "select nombres , apellidos, sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador " 
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) "
                            +"inner join motivopermiso m on (cod_motivo=fk_cod_motivo) "
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +"where fk_cedula= '"+cedula+"' and fk_nitempresa = '"+nitem+"' "
                            + "and "+queryfecha+" = '"+selfecha+"' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by nombres, apellidos, m.nombre_motivo";                     
                 }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpleado().setNombres(rs.getString("nombres"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        pieausentismoEmpleado.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieausentismoEmpleado;
    }
    
    
    public ArrayList<Ausentismo> pieausentismoanomesEmpresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;       
        ArrayList<Ausentismo> pieausentismoEmpresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " 
                            +"group by em.nombre";                 
                }
                
                //Valida que llegue año y empresa
                if (nitem != null && selano != null && selmotivo !=null) {
                    sql = " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"' and " + queryfecha + " = '" + selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by em.nombre, fk_cod_motivo";                 
                }
                //valida que llegue nitsubempresa y fecha
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null) {
                    sql = " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_ausentismo "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfecha + " = '"+ selfecha + "' " 
                            +" group by se.nombre";
                 
                }
                
                //valida que llegue nitsubempresa fecha y motivo
                if(nitem != null && nitsubem != null && selfecha != null && selfecha2==null && selmotivo!=null) {
                    sql = " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) as totempleador, sum(arl) "
                            +" as totarl, sum(trabajador) as tottrabajador " 
                            +" from registro_ausentismo "
                            +" inner join empleado e on (e.cedula=fk_cedula)"
                            +" inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +" inner join empresa em on(em.nitempresa=se.fk_nitempresa) "
                            +" where se.nitsubempresa = '"+nitsubem+"' and  " + queryfecha + " = '"+ selfecha + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +" group by se.nombre, fk_cod_motivo";
                 
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta y año
                if (selmesdesde != null && selmeshasta != null && nitsubem != null) {                                        
                    sql = " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"group by se.nombre";
                    
                   
                }
                
                //Valida que llegue nitsubempresa, mes desde, mes hasta año y motivo
                if (selmesdesde != null && selmeshasta != null && nitsubem != null) {                                        
                    sql = " select se.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) "
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "
                            +"where se.nitsubempresa = '"+nitsubem+"' "
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' "
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by se.nombre, fk_cod_motivo";
                    
                   
                }
                //valida que llegue nitempresa mes desde y mes hasta
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null){
                    sql = " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by em.nombre";
                }
                
                //valida que llegue nitempresa mes desde  mes hasta y motivo
                if(selmesdesde != null && selmeshasta != null && nitem != null && nitsubem==null && selmotivo!=null){
                    sql = " select em.nombre as nombre,sum(eps) as toteps, sum(empleador) " 
                            +"as totempleador, sum(arl) as totarl, sum(trabajador) as tottrabajador "                              
                            +"from registro_ausentismo "
                            +"inner join empleado e on (e.cedula=fk_cedula)" 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa)"
                            +"inner join empresa em on(em.nitempresa=se.fk_nitempresa)"
                            +"where fk_nitempresa = '"+nitem+"'" 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"' "
                            +"group by em.nombre, fk_cod_motivo";
                }
                
                
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpresa().setNombre(rs.getString("nombre"));
                        au.setEps(rs.getDouble("toteps"));
                        au.setEmpleador(rs.getDouble("totempleador"));
                        au.setArl(rs.getDouble("totarl"));
                        au.setTrabajador(rs.getDouble("tottrabajador"));
                        pieausentismoEmpresa.add(au);
                    }                             

            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieausentismoEmpresa;
        
    }
        
    public ArrayList<Ausentismo> pieporSubempresa(String nitem,String nitsubem,String selmesdesde, String selmeshasta, String selano, String selmotivo) throws SQLException, Exception {

        Ausentismo au;       
        ArrayList<Ausentismo> pieporSubempresa = new ArrayList<>();
        ResultSet rs;
        Consulta consulta = null;
        String selfecha = null;
        String selfecha2 = null;
        String queryfecha = null;
        Double totaleps = 0.00;
        String sql="";

        
        if ((selmesdesde == null) && (selmeshasta == null)){
            selfecha = selano;
            queryfecha = "to_char(fechapermiso,'yyyy')"; 
        }else{
            selfecha = selmesdesde;
            selfecha2 = selmeshasta;
            queryfecha = "to_char(fechapermiso,'yyyy/mm')";
        }
                            
                //Valida que llegue año y empresa
                if (nitem != null && selano != null) {
                    sql = "Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_ausentismo " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfecha + " = '" + selfecha + "' " 
                        +"group by se.nombre, em.nombre";
                }
                
                //Valida que llegue año empresa y motivo
                if (nitem != null && selano != null && selmotivo!=null) {
                    sql = "Select em.nombre as nombreem, se.nombre as nombre, " 
                        +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube " 
                        +"from registro_ausentismo " 
                        +"inner join empleado e on (e.cedula=fk_cedula) " 
                        +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                        +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) "    
                        +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '"+nitem+"') " 
                        +"and " + queryfecha + " = '" + selfecha + "' " 
                        +"and fk_cod_motivo='"+selmotivo+"' "  
                        +"group by se.nombre, em.nombre, fk_cod_motivo";
                }
                
                if(selmesdesde != null && selmeshasta != null && nitem != null){
                    sql = " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"group by se.nombre, em.nombre";
                }
                
                if(selmesdesde != null && selmeshasta != null && nitem != null && selmotivo!=null){
                    sql = " Select em.nombre as nombreem, se.nombre as nombre, " 
                            +"round(CAST(sum(eps)+sum(empleador)+sum(arl)+sum(trabajador) as numeric), '2') as totalsube "
                            +"from registro_ausentismo " 
                            +"inner join empleado e on (e.cedula=fk_cedula) " 
                            +"inner join subempresa se on (se.nitsubempresa=e.nitsubempresa) " 
                            +"inner join empresa em on (em.nitempresa=se.fk_nitempresa) " 
                            +"where e.nitsubempresa in (select nitsubempresa from subempresa where fk_nitempresa = '" +nitem+ "') " 
                            +"and " + queryfecha + " between '" + selfecha + "' and '" + selfecha2 + "' " 
                            +"and fk_cod_motivo='"+selmotivo+"'"
                            +"group by se.nombre, em.nombre, fk_cod_motivo";
                }
                
            try {
                consulta = new Consulta(getConexion());  
                    rs = consulta.ejecutar(sql);                              

                    while (rs.next()) {                     
                        au = new Ausentismo();
                        au.getEmpresa().setNombre(rs.getString("nombreem"));
                        au.getSubempresa().setNombre(rs.getString("nombre"));                        
                        au.setTotalsube(rs.getDouble("totalsube"));
                        pieporSubempresa.add(au);
                    }                  
            } catch (SQLException ex) {
                throw ex;
            } finally {
                consulta.desconectar();
            }

        return pieporSubempresa;
        
    }
    
    
    
    public Ausentismo buscarAusentismo(String cod_registro, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Ausentismo au = null;
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");
        String hora_salida = null;
        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select re.*, e.nombres,e.apellidos,e.fecha_nacimiento,e.cargo,cargo.cargo ncargo, "
                    + "e.cod_det_lista_ecivil,civil.nombre necivil, " 
                    + "cod_det_lista_sexo,sexo.nombre nsexo, "
                    + "(SELECT date_part('year',age(e.fecha_nacimiento)) AS edad) " 
                    + "from registro_ausentismo re " 
                    + "inner join empleado e on (fk_cedula=cedula) " 
                    + "inner join det_lista civil on (civil.cod_det_lista=e.cod_det_lista_ecivil) " 
                    + "inner join det_lista sexo on (sexo.cod_det_lista=e.cod_det_lista_sexo) " 
                    + "inner join cargo on (cargo.cod_cargo=e.cargo) " 
                    + " where cod_regausentismo='" + cod_registro.trim() +"'";
                      
            rs = consulta.ejecutar(sql);
            
            if (rs.next()) {
                String estado = rs.getString("estado");
                if (estado.equals("A")) {                                                                       
                        au = new Ausentismo(); 
                        au.setCod_registro(cod_registro);
                        au.setMotivoausentismo(rs.getString("fk_cod_motivo"));                                
                        au.setHora_salida(rs.getTime("horasalida"));                                                               
                        au.setHora_llegada(rs.getTime("horallegada"));                                                
                        au.setTiempo_horas(rs.getString("tiempohoras"));                        
                        au.setObservaciones(rs.getString("observaciones"));
                        au.getEmpleado().setCedula(rs.getString("fk_cedula"));
                        au.setFecha_permiso(rs.getDate("fechapermiso"));
                        au.getEmpleado().setNombres(rs.getString("nombres"));
                        au.getEmpleado().setApellidos(rs.getString("apellidos"));
                        au.getEmpleado().getCargo().setCodigo(rs.getString("cargo"));
                        au.getEmpleado().getCargo().setNombre(rs.getString("ncargo"));
                        au.getEmpleado().getSexo().setCodigo(rs.getString("cod_det_lista_sexo"));
                        au.getEmpleado().getSexo().setNombre(rs.getString("nsexo"));
                        au.getEmpleado().getEcivil().setCodigo(rs.getString("cod_det_lista_ecivil"));
                        au.getEmpleado().getEcivil().setNombre(rs.getString("necivil"));
                        au.getEmpleado().setEdad(rs.getInt("edad"));                
                }
            }                                    
            return au;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer modificarAusentismo(Ausentismo ausentismo) throws SQLException{
        Consulta consulta = null;
        Integer resultado;   
        ResultSet rs;
        String sql = "";
        String tipo = "";       
        double salmin = 0.00;
        float th = 0;        
        double salhoras = 0.00;
        double thd = 0;
        double tha = 0;
        

        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");    
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");   
        

        

        //Sentencia SQL para guardar el registro        
        try {
            consulta = new Consulta(getConexion());            
                
            
            sql = "SELECT tipo from motivopermiso where cod_motivo = '" + ausentismo.getMotivoausentismo() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {                
                ausentismo.setTipo_permiso(rs.getString("tipo"));    
                tipo = ausentismo.getTipo_permiso();                
                    
            }
            
            //Búsqueda del sueldo de Empleado
            sql = "SELECT sueldo_mes from empleado where cedula = '" + ausentismo.getEmpleado().getCedula() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                ausentismo.getEmpleado().setSueldo_mes(rs.getInt("sueldo_mes"));

            }
            
            sql = "SELECT sal_min from configuracion";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
               salmin = rs.getDouble("sal_min");
            }

            salhoras = (ausentismo.getEmpleado().getSueldo_mes()/240.00);
            th = Float.parseFloat(ausentismo.getTiempo_horas());
            double minhoras = (salmin/240);
            tha = 16;
            thd = th - tha;
           
            
            double empleador=0;
            double arl=0;
            double eps=0;
            double trabajador=0;
            double total=0;
            double porcent=0.6667;
            double normal=0;
            
            DecimalFormat formato1= new DecimalFormat("#.00");
            formato1.format(arl);
            formato1.format(eps);
            formato1.format(empleador);
            formato1.format(total);
            List thorasHibrido = new ArrayList();

            //Revisar cada tipo
            switch(tipo){
                
                case "EMPLEADOR":
                        empleador = salhoras * th;
                    break;
                    
                case "EPS":
                       //Calculo en valor del mínimo
                       double minimo = (th-16) * minhoras;
                       
                       //Calculo de los primeros dias
                      if(th <= 16 ){
                           empleador = tha * salhoras;    
                      }else{
                           //Calculo de los primeros dias                         
                           empleador = tha * salhoras; 
                           //Calculo de los días restantes
                           double epsxemp = ((salhoras * thd) * porcent);
                           
                           //Se compara con el mínimo el valor adquirido                           
                           if (epsxemp > minimo) {
                               eps = epsxemp;
                           }else{
                               eps = minimo;
                           }
                       }  
                    break; 
                    
                case "ARL":                
                    arl = salhoras*th;  
                    break;
                    
                case "TRABAJADOR":
                    trabajador = salhoras*th;
                    break;
                    
                case "EPSN":
                    eps = salhoras*th;                    
                    break;
                    
                case "EM-TR":
                    //Calcula sumatoria para tipo EM-TR con acumulador de horas de empleado y tiempo horas
                    int thacumA= ausentismo.getEmpleado().getThacum();                   
                    float thacumD = thacumA + th;
                    
                    if (thacumA > 24){
                        trabajador= th*salhoras;
                    }else if(thacumA <= 24 && thacumD>24){
                        double  thdes=thacumD-24;
                        double  thant=24-thacumA;
                        trabajador=thdes*salhoras;
                        empleador=thant*salhoras;
                    }else{
                        empleador=th*salhoras;
                    }
                    break;
            }  
            total = empleador + eps + arl + trabajador;

            
                sql = "UPDATE registro_ausentismo "
                        + "set horasalida = '" + formatoHora.format(ausentismo.getHora_salida()) + "', horallegada = '" + formatoHora.format(ausentismo.getHora_llegada()) + "', "
                        + "fk_cod_motivo = '" + ausentismo.getMotivoausentismo() + "', tiempohoras = '" +ausentismo.getTiempo_horas()+ "', "
                        + "observaciones = '" + ausentismo.getObservaciones() + "', "
                        + "eps = '"+eps+"', arl='"+arl+"',trabajador = '"+trabajador+"', empleador = '"+empleador+"', total = '"+total+"', "
                        + "fechapermiso = '" + formatoFecha.format(ausentismo.getFecha_permiso()) + "', estado = 'F' "
                        + "where cod_regausentismo = '" + ausentismo.getCod_registro() + "' ";                        

            resultado = consulta.actualizar(sql);
            return resultado;
            
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    } 
    
    public Integer guardarIncapacidad(Incapacidad incapacidad) throws SQLException{
        Consulta consulta = null;        
        Integer resultado;
        ResultSet rs;
        String sql = "";
        String tipo = "";       
        double salmin = 0.00;
        Integer horasdia = 0;
        float th = 0;        
        double salhoras = 0.00;
        double thd = 0;
        double tha = 0;
        
        
        //Adquirir la fecha de hoy y darle formato
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate ahora = LocalDate.now();
        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha1 = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        
        
              
        try {
            consulta = new Consulta(getConexion());          
            
            //Búsqueda del tipo de motivo
            sql = "SELECT tipo from motivopermiso where cod_motivo = '" + incapacidad.getMotivoincapacidad() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {                
                incapacidad.setTipo_permiso(rs.getString("tipo"));    
                tipo = incapacidad.getTipo_permiso();                
                    
            }
            
            //Búsqueda del sueldo de Empleado
            sql = "SELECT sueldo_mes from empleado where cedula = '" + incapacidad.getEmpleado().getCedula() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                incapacidad.getEmpleado().setSueldo_mes(rs.getInt("sueldo_mes"));

            }
            
            sql = "SELECT sal_min from configuracion";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
               salmin = rs.getDouble("sal_min");
            }
            
            sql = "SELECT horasdia from subempresa";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
               horasdia = rs.getInt("horasdia");
            }

            salhoras = (incapacidad.getEmpleado().getSueldo_mes()/240.00);
            
            incapacidad.setTiempoHoras(String.valueOf(incapacidad.getTiempoDias()*horasdia));
            
            th = Float.parseFloat(incapacidad.getTiempoHoras());
            double minhoras = (salmin/240);
            tha = 16;
            thd = th - tha;
           
            boolean finalizado=false;
            double empleador=0;
            double arl=0;
            double eps=0;
            double trabajador=0;
            double total=0;
            double porcent=0.6667;
            double normal=0;
            
            DecimalFormat formato1= new DecimalFormat("#.00");
            formato1.format(arl);
            formato1.format(eps);
            formato1.format(empleador);
            formato1.format(total);
            List thorasHibrido = new ArrayList();

            //Revisar cada tipo
            switch(tipo){
                
                case "EMPLEADOR":
                        empleador = salhoras * th;
                    break;
                    
                case "EPS":
                       //Calculo en valor del mínimo
                       double minimo = (th-16) * minhoras;
                       
                       //Calculo de los primeros dias
                      if(th <= 16 ){
                           empleador = tha * salhoras;    
                      }else{
                           //Calculo de los primeros dias                         
                           empleador = tha * salhoras; 
                           //Calculo de los días restantes
                           double epsxemp = ((salhoras * thd) * porcent);
                           
                           //Se compara con el mínimo el valor adquirido                           
                           if (epsxemp > minimo) {
                               eps = epsxemp;
                           }else{
                               eps = minimo;
                           }
                       }  
                    break; 
                    
                case "ARL":                
                    arl = salhoras*th;  
                    break;
                
            }  
            total = empleador + eps + arl + trabajador;
        

            
            
            //Sentencia SQL para guardar el registro
                sql = "INSERT INTO registro_incapacidad ("
                        + "fecha_registro, fk_cod_motivo, "
                        + " tiempo_dias, tiempohoras, observaciones, cod_cie10, cod_det_lista_tipo_incapacidad, "
                        + "fk_cedula, fecha_inicial, fecha_final, empleador, eps, arl,trabajador, total )"
                        + "VALUES ("
                        + "'" + formatoFecha.format(ahora) + "',"                                                                                                    
                        + "'" + incapacidad.getMotivoincapacidad() + "',"
                        + "'" + incapacidad.getTiempoDias() + "',"
                        + "'" + incapacidad.getTiempoHoras() + "',"
                        + "'" + incapacidad.getObservaciones() + "',"
                        + "'" + incapacidad.getCie10().getCodCie10() + "',"
                        + "'" + incapacidad.getTipoIncapacidad().getCodigo() + "',"
                        + "'" + incapacidad.getEmpleado().getCedula() + "',"
                        + "'" + formatoFecha1.format(incapacidad.getFecha_inicial())+"',"
                        + "'" + formatoFecha1.format(incapacidad.getFecha_final())+"',"                        
                        + " round(CAST(('"+empleador+"') as numeric), '3'), "
                        + " round(CAST(('"+eps+"') as numeric), '3'), "
                        + " round(CAST(('"+arl+"') as numeric), '3'), "
                        + " round(CAST(('"+trabajador+"') as numeric), '3'), "
                        + " round(CAST(('"+total+"') as numeric), '3'))";

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   

    public Connection getConexion() {
        return conexion;
    }
}
