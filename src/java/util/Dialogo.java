/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author juliano
 */
@ManagedBean
@SessionScoped

public class Dialogo implements Serializable {

    public static String EFECTO = "clip";
    public static String WIDTH_AUTO = "auto";
    public static String WIDTH_80 = "80%";
    public static String WIDTH_50 = "50%";
    public static String WIDTH_100 = "100%";

    public Dialogo() {
    }

    public Dialogo(String src) {
        this.src = src;
    }

    public Dialogo(String src, String header, String effect, String width) {
        this.src = src;
        this.header = header;
        this.effect = effect;
        this.width = width;
    }

    public Dialogo(String src, String header, String effect, String width, String style) {
        this.src = src;
        this.header = header;
        this.effect = effect;
        this.width = width;
        this.style = style;
    }

    private String src;
    private String header;
    private String effect;
    private String width;
    private String style;

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * @return the effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * @param effect the effect to set
     */
    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }
}
