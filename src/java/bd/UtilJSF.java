/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;


import java.io.IOException;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author juliano
 */
public class UtilJSF {

    public static final String REQUEST_SCOPE = "REQUEST_SCOPE";
    public static final String SESSION_SCOPE = "SESSION_SCOPE";
    public static final String APPLICATION_SCOPE = "APPLICATION_SCOPE";

    public static Object getBean(final String nombreBean) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Object bean;
        bean = facesContext.getExternalContext().getRequestMap().get(nombreBean);
        if (bean != null) {
            return bean;
        }
        bean = facesContext.getExternalContext().getSessionMap().get(nombreBean);
        if (bean != null) {
            return bean;
        }
        bean = facesContext.getExternalContext().getApplicationMap().get(nombreBean);
        if (bean != null) {
            return bean;
        }
        return bean;
    }

    public static void setBean(final String nombreBean, final Object bean, final String scope) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (scope.equals(REQUEST_SCOPE)) {
            facesContext.getExternalContext().getRequestMap().put(nombreBean, bean);
        } else if (scope.equals(SESSION_SCOPE)) {
            facesContext.getExternalContext().getSessionMap().put(nombreBean, bean);
        } else if (scope.equals(APPLICATION_SCOPE)) {
            facesContext.getExternalContext().getApplicationMap().put(nombreBean, bean);
        }
    }

    public static void redirect(final String pagina) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().redirect(pagina);
    }
    
    
    public static void execute(String evento) {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute(evento);
//        PrimeFaces.current().executeScript(evento);
    }
    
    public static void update(String campo) {
     //  PrimeFaces.current().ajax().update(campo);
    }

//    public static void hideDialog() {
//        Sesion sesion = (Sesion) getBean("sesion");
//        sesion.setDialogo(new Dialogo());
//        setBean("sesion", sesion, SESSION_SCOPE);
//        execute("PF('dialog').hide();");
//    }
}
