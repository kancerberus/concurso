/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.util.Date;


public class InformeAusentismo {
    Empleado empleado;
    Ausentismo ausentismo;
    double eps;
    double empleador;
    double arl;        
     
    public InformeAusentismo() {   
                     
        this.empleado = new Empleado();        
        this.ausentismo = new Ausentismo();
    }    

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Ausentismo getAusentismo() {
        return ausentismo;
    }

    public void setAusentismo(Ausentismo ausentismo) {
        this.ausentismo = ausentismo;
    }   
}