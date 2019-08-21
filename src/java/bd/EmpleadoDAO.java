/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import modelo.Ausentismo;
import modelo.Empleado;
import modelo.Municipio;
/**
 *
 * @author Andres
 */
public class EmpleadoDAO {

    private Connection conexion;

    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;        
    }

    public Empleado validarEmpleado(String cedula, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        
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
            
            sql = "SELECT em.nombres nombres,em.apellidos apellidos,em.fecha_nacimiento fecha_nac, "
                    + "m.municipio municipio,c.cargo cargo,det1.nombre sexo,det2.nombre e_civil,estado "
                    + "FROM empleado em "
                    + "INNER JOIN municipio m using (cod_municipio) "
                    + "INNER JOIN cargo c on (c.cod_cargo=em.cargo) "
                    + "INNER JOIN det_lista det1 on (det1.cod_det_lista=em.cod_det_lista_sexo) "
                    + "INNER JOIN det_lista det2 on (det2.cod_det_lista=em.cod_det_lista_ecivil) "
                    + " WHERE nitsubempresa ='" + nit +"' and cedula='" + cedula.trim() +"'";

            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                em = new Empleado();  
                em.setCedula(cedula);
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));                
                em.getEcivil().setNombre(rs.getString("e_civil"));               
                em.getSexo().setNombre(rs.getString("sexo"));
                em.getCargo().setNombre(rs.getString("cargo"));                 
                em.setEstado(rs.getBoolean("estado"));

                //calculo de la edad
                fechaNac = rs.getDate("fecha_nac");
                
                Calendar fechaNacimiento = Calendar.getInstance();
                //Se crea un objeto con la fecha actual
                Calendar fechaActual = Calendar.getInstance();
                //Se asigna la fecha recibida a la fecha de nacimiento.
                fechaNacimiento.setTime(fechaNac);
                //Se restan la fecha actual y la fecha de nacimiento
                int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
                //int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
                //int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);  
                
                //Asignamos la edad al atributo de la clase Empleados
                em.setEdad(año);   
                
                mu = new Municipio();
                mu.setNombre(rs.getString("municipio"));            
            }            
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer validarEmpleadoActualizacion(String cedula, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        Ausentismo au=null;  
        String cod_registro="";
        
        try {
            consulta = new Consulta(getConexion());            
            
                double thoras=0;
                
                double trabajador=0;
                double total=0;
                boolean finalizado=false;
                float salhoras=0;                
                Integer resultado=0;               
                double thacum=0;
                double tp=24;
                                               
            
                //Consulta la información a actualizar item a item
                sql= "select cod_regausentismo, re.tiempohoras as thoras,round(CAST((e.sueldo_mes/240.000) as numeric), '3') as sueldohora  "                        
                        +"from registro_ausentismo re "
                        +"inner join empleado e on (e.cedula=re.fk_cedula) "
                        +"inner join motivopermiso mo on (mo.cod_motivo=re.fk_cod_motivo) "
                        +"where fk_cedula = '"+cedula.trim()+"' and mo.tipo = 'EM-TR' order by cod_regausentismo";
                
                rs = consulta.ejecutar(sql);
                                
                while (rs.next()) {            
                    double empleador=0; 
                    trabajador=0;
                    thoras = rs.getDouble("thoras");
                    thacum= thoras+thacum;
                    salhoras= rs.getFloat("sueldohora");
                    cod_registro = rs.getString("cod_regausentismo");                                           



                        if(thacum <= tp){
                            empleador=thoras*salhoras;                            
                        }
                        total=trabajador+empleador;
                        if(thacum > tp){                             
                            if(finalizado==false){                                
                                double thorastrabajador= thacum-tp;
                                trabajador=thorastrabajador*salhoras;
                                double  thorasempleador= thoras-thorastrabajador;
                                empleador=thorasempleador*salhoras;
                                finalizado=true;                                      
                                  
                            }                               
                            else{
                                empleador=0;                                
                                trabajador = thoras * salhoras;                        
                                                               
                            }
                            total=trabajador+empleador;
                            
                        }                        
                        //Ingresa la actualización en BD  
                        consulta = new Consulta(getConexion());
                        
                        
                        sql = "UPDATE registro_ausentismo re "
                                +"set trabajador =  round(CAST(('"+trabajador+"') as numeric), '3')  , empleador = round(CAST(('"+empleador+"') as numeric),'3') , total= round(CAST(('"+total+"') as numeric),'3') " 
                                +"FROM motivopermiso mo "                                                
                                +"where re.fk_cedula= '"+cedula.trim()+"' and mo.tipo = 'EM-TR' and cod_regausentismo ='" + cod_registro + "' ";

                        resultado = consulta.actualizar(sql);
                
                    }//termina while                 
                
                return resultado;
                
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    public Integer modificarEmpleado(Empleado empleado) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    

        //Dar formato a la fecha y a la hora
        DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");    
        DateFormat formatoHora = new SimpleDateFormat("HH:mm");    

        //Sentencia SQL para guardar el registro
        String sql = "";
        ResultSet rs;    
   
        try {
            consulta = new Consulta(getConexion());
            
            sql = "SELECT cod_municipio from municipio where municipio = '" + empleado.getResidencia().getNombre() + "'";
            rs = consulta.ejecutar(sql); 
            if (rs.next()) {
                empleado.getResidencia().setCodigo(rs.getString("cod_municipio"));                   
            }                        
            
                sql = "UPDATE empleado "
                        +" set nombres = '" + empleado.getNombres() + "', apellidos = '" + empleado.getApellidos() + "', cod_municipio = '" + empleado.getResidencia().getCodigo() + "', cod_eps = '" + empleado.getEps().getCodigo() + "',"
                        +" cod_det_lista_sexo='" + empleado.getSexo().getCodigo() + "',cod_det_lista_ecivil='" + empleado.getEcivil().getCodigo() + "',fecha_nacimiento='" + empleado.getFecha_nac() + "',"
                        +" cargo = '" + empleado.getCargo().getCodigo() + "', sueldo_mes = '" + empleado.getSueldo_mes() + "',aux_transporte='" + empleado.getAux_transporte() + "', estado = "+empleado.isEstado()+"  "
                        +" where cedula = '" + empleado.getCedula() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }    
    
    public Empleado buscarEmpleado(String cedula, String nitsesion) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Empleado em = null;

        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select e.nombres,e.apellidos,m.municipio nom_municipio,ep.cod_eps eps,\n" +
                    "e.cod_det_lista_ecivil,cod_det_lista_sexo,\n" +
                    "e.fecha_nacimiento, c.cod_cargo ,e.sueldo_mes, e.aux_transporte, e.estado\n" +
                    "from empleado e\n" +
                    "inner join municipio m on (m.cod_municipio=e.cod_municipio)\n" +
                    "inner join eps ep on(ep.cod_eps=e.cod_eps)\n" +
                    "inner join det_lista s on (s.cod_det_lista=e.cod_det_lista_sexo)\n" +
                    "inner join det_lista ci on (ci.cod_det_lista=e.cod_det_lista_ecivil)\n" +
                    "inner join cargo c on (c.cod_cargo=e.cargo)\n"+
                    "where e.nitsubempresa='" + nitsesion +"' and cedula='" + cedula.trim() +"'";
                    
                      
            rs = consulta.ejecutar(sql);
            
            if (rs.next()) {               
                    
                em=new Empleado();      

                
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                
                em.getResidencia().setNombre(rs.getString("nom_municipio"));
                
                
                em.getEps().setCodigo(rs.getString("eps"));
                em.getEcivil().setCodigo(rs.getString("cod_det_lista_ecivil"));
                 
                
                em.getCargo().setCodigo(rs.getString("cod_cargo"));
                em.setSueldo_mes(rs.getInt("sueldo_mes"));
                em.setAux_transporte(rs.getInt("aux_transporte")); 
                
                em.getSexo().setCodigo(rs.getString("cod_det_lista_sexo")); 
                em.setFecha_nac(rs.getDate("fecha_nacimiento"));
                em.setEstado(rs.getBoolean("estado"));
            }                                    
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
            //Modifica la subempresa en la que esta registrado el empleado
    public Integer modificarSubempresaempleado(Empleado empleado) throws SQLException{
        Consulta consulta = null;
        Integer resultado;    
        //Sentencia SQL para guardar el registro
        String sql = "";
        ResultSet rs;    
        
        try {
            consulta = new Consulta(getConexion());
                sql = "UPDATE empleado "
                        +" set nitsubempresa = '"+ empleado.getNitsubempresa()+"'"
                        +" where cedula = '" + empleado.getCedula() + "'";

            resultado = consulta.actualizar(sql);
            return resultado;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }     
    }
    
    
    //busca empleado en todas las empresas solo admin
    public Empleado buscarempleadoAdmin(String cedula) throws SQLException {
        Consulta consulta = null;
        ResultSet rs;
        String sql;
        Date fechaNac;
        Empleado em = null;
        Municipio mu = null;
        
        try {
            consulta = new Consulta(getConexion());
            
            sql = "select em.nombres nombres,em.apellidos apellidos,em.fecha_nacimiento fecha_nac, "
                    + "m.municipio municipio,c.cargo cargo,det1.nombre sexo,det2.nombre e_civil, "
                    + "subempresa.nombre subempresa "
                    + "from empleado em "
                    + "inner join municipio m using (cod_municipio) "
                    + "inner join cargo c on (c.cod_cargo=em.cargo) "
                    + "inner join det_lista det1 on (det1.cod_det_lista=em.cod_det_lista_sexo) "
                    + "inner join det_lista det2 on (det2.cod_det_lista=em.cod_det_lista_ecivil) "
                    + "inner join subempresa on (subempresa.nitsubempresa=em.nitsubempresa) "
                    + "where cedula='" + cedula.trim() + "'";

            rs = consulta.ejecutar(sql);
            if (rs.next()) {
                em = new Empleado();  
                em.setCedula(cedula);
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));                
                em.getEcivil().setNombre(rs.getString("e_civil"));               
                em.getSexo().setNombre(rs.getString("sexo"));
                em.getCargo().setNombre(rs.getString("cargo")); 
                em.setNitsubempresa(rs.getString("subempresa"));

                //calculo de la edad
                fechaNac = rs.getDate("fecha_nac");
                
                Calendar fechaNacimiento = Calendar.getInstance();
                //Se crea un objeto con la fecha actual
                Calendar fechaActual = Calendar.getInstance();
                //Se asigna la fecha recibida a la fecha de nacimiento.
                fechaNacimiento.setTime(fechaNac);
                //Se restan la fecha actual y la fecha de nacimiento
                int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
                //int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
                //int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);  
                
                //Asignamos la edad al atributo de la clase Empleados
                em.setEdad(año); 
            }
            return em;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }
    
    
    
    public ArrayList<Empleado> listarEmpleados(String nitsesion) throws SQLException {
        Empleado em;
        ArrayList<Empleado> listaEmpleado = new ArrayList<>();
        ResultSet rs;
        String sql;
        Consulta consulta = null;           
        
        try {
            consulta = new Consulta(getConexion());
                    sql = "select e.cedula, e.nombres, e.apellidos,c.cargo cargo,ep.eps eps , e.sueldo_mes " +
                      "from empleado e " +
                      "inner join eps ep on (ep.cod_eps=e.cod_eps)"+
                      "inner join cargo c on (c.cod_cargo=e.cargo)"+
                      "where e.nitsubempresa = '" + nitsesion + "'";

            rs = consulta.ejecutar(sql);

            while (rs.next()) {
                em = new Empleado();
                em.setCedula(rs.getString("cedula"));
                em.setNombres(rs.getString("nombres"));
                em.setApellidos(rs.getString("apellidos"));
                em.getCargo().setNombre(rs.getString("cargo"));
                em.getEps().setNombre(rs.getString("eps"));
                em.setSueldo_mes(rs.getInt("sueldo_mes"));     

                listaEmpleado.add(em);
            }
            return listaEmpleado;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            consulta.desconectar();
        }
    }

public Integer guardarEmpleado(Empleado empleado, String nitsesion) throws SQLException{
    Consulta consulta = null;
    Integer resultado;    
    
    //Dar formato a la fecha 
    DateFormat formatoFecha1 = new SimpleDateFormat("yyyy/MM/dd");   

    //Sentencia SQL para guardar el registro
    String sql = "";
    try {
        consulta = new Consulta(getConexion());
        ResultSet rs;
        
            sql = "SELECT cod_municipio from municipio where municipio = '" + empleado.getResidencia().getNombre() + "'";
                rs = consulta.ejecutar(sql); 
                if (rs.next()) {
                    empleado.getResidencia().setCodigo(rs.getString("cod_municipio"));                   
                }                                                            
        
            sql = "INSERT INTO empleado ("
                    + "cedula, nombres, apellidos, cod_municipio, "
                    + "cod_eps, cod_det_lista_sexo, cod_det_lista_ecivil, "
                    + "nitsubempresa, fecha_nacimiento, sueldo_mes, aux_transporte, cargo, estado)"
                    + "VALUES ("                                                                                                    
                    + "'" + empleado.getCedula()+ "',"
                    + "'" + empleado.getNombres()+ "',"
                    + "'" + empleado.getApellidos()+ "',"
                    + "'" + empleado.getResidencia().getCodigo()+ "',"
                    + "'" + empleado.getEps().getCodigo()+ "',"
                    + "'" + empleado.getSexo().getCodigo()+ "',"
                    + "'" + empleado.getEcivil().getCodigo()+ "',"
                    + "'" + nitsesion + "',"
                    + "'" + formatoFecha1.format(empleado.getFecha_nac()) +"',"
                    + "'" + empleado.getSueldo_mes() +"',"
                    + "'" + empleado.getAux_transporte() +"',"
                    + "'" + empleado.getCargo().getCodigo()+"', true)";
        resultado = consulta.actualizar(sql);
        return resultado;
    } catch (SQLException ex) {
        throw ex;
    } finally {
        consulta.desconectar();
    }     
}  /**
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
