package co.umariana.becerros.web;

import co.umariana.becerros.estructura.ListaDoble;
import co.umariana.becerros.modelo.GestorBecerros;
import co.umariana.becerros.modelo.Becerro;
import co.umariana.becerros.persistencia.RepositorioTxt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;

/** Arma el modelo al arrancar el servidor (lista + archivo + gestor). NO se modifica. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        Path archivo = Path.of(System.getProperty("becerros.archivo", "becerros.txt"));
        try {
            Contexto.usar(new GestorBecerros(new ListaDoble<Becerro>(), new RepositorioTxt(archivo)));
            evento.getServletContext().log("Becerrario listo. Datos en: " + archivo.toAbsolutePath());
        } catch (Throwable e) {
            // Mientras el equipo no termine sus clases, la aplicación arranca igual pero sin modelo.
            evento.getServletContext().log("El modelo todavía no está disponible: " + e);
            Contexto.usar(null);
        }
    }
}
