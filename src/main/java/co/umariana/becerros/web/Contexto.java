package co.umariana.becerros.web;

import co.umariana.becerros.modelo.ServicioBecerros;

/**
 * Punto único de acceso al modelo desde los servlets. NO se modifica.
 * Los servlets hacen {@code Contexto.servicio().listar()}, etc.
 */
public final class Contexto {

    private static volatile ServicioBecerros servicio;

    private Contexto() {
    }

    public static ServicioBecerros servicio() {
        ServicioBecerros s = servicio;
        if (s == null) {
            throw new IllegalStateException("El modelo no está disponible todavía. Revise la consola del servidor: "
                    + "probablemente falta implementar alguna clase (GestorBecerros, ListaDoble o RepositorioTxt).");
        }
        return s;
    }

    /** Lo usa {@link AppListener} al arrancar y las pruebas para poner un servicio de mentira. */
    public static void usar(ServicioBecerros nuevo) {
        servicio = nuevo;
    }
}
