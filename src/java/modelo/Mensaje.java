package modelo;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author carlosv
 */
public class Mensaje {

    private String descripcion;
    private String tipo;
    private String ruta;
    private String respuestaSi;
    private String respuestaNo;
    private ArrayList<String> parametros;

    public Mensaje(String descripcion, String tipo) { //Alert
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
    
    public Mensaje(String descripcion) { //Alert
        this.descripcion = descripcion;
    }

    public Mensaje(String descripcion, String tipo, String ruta, ArrayList<String> parametros) { //WindowOpen
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.ruta = ruta;
        this.parametros = parametros;
    }

    public Mensaje(String descripcion, String tipo, String respuestaSi, String respuestaNo, ArrayList<String> parametros) { //Confirm
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.parametros = parametros;
        this.respuestaSi = respuestaSi;
        this.respuestaNo = respuestaNo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the respuestaSi
     */
    public String getRespuestaSi() {
        return respuestaSi;
    }

    /**
     * @param respuestaSi the respuestaSi to set
     */
    public void setRespuestaSi(String respuestaSi) {
        this.respuestaSi = respuestaSi;
    }

    /**
     * @return the respuestaNo
     */
    public String getRespuestaNo() {
        return respuestaNo;
    }

    /**
     * @param respuestaNo the respuestaNo to set
     */
    public void setRespuestaNo(String respuestaNo) {
        this.respuestaNo = respuestaNo;
    }

    /**
     * @return the parametros
     */
    public ArrayList<String> getParametros() {
        return parametros;
    }

    /**
     * @param parametros the parametros to set
     */
    public void setParametros(ArrayList<String> parametros) {
        this.parametros = parametros;
    }

}