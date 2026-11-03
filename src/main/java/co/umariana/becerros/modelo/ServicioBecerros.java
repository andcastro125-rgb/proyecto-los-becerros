package co.umariana.becerros.modelo;

import java.util.List;
import java.util.Map;

/**
 * Lo que la aplicación web necesita del modelo. NO se modifica.
 * {@link GestorBecerros} es la implementación (tarea de MODELO).
 */
public interface ServicioBecerros {

    /** Valida el becerro, rechaza códigos repetidos y guarda el cambio en el archivo. */
    void agregar(Becerro becerro);

    /** Devuelve el becerro con ese código, o {@code null} si no existe. */
    Becerro buscar(String codigo);

    /** Elimina el becerro con ese código y guarda el cambio. Devuelve {@code false} si no existía. */
    boolean eliminar(String codigo);

    /** Todos los becerros, en el orden de la lista. */
    List<Becerro> listar();

    /** Lista NUEVA ordenada de mayor a menor apetito (empate: por código). No altera el orden de la lista. */
    List<Becerro> ranking();

    /** Promedio de apetito por categoría, ordenado alfabéticamente por categoría. */
    Map<String, Double> promedioPorCategoria();

    /** Cantidad de becerros guardados. */
    int total();

    /** Becerro donde está el cursor (el visor), o {@code null} si no hay becerros. */
    Becerro actual();

    /** Mueve el cursor al siguiente becerro y lo devuelve ({@code null} si no hay becerros). */
    Becerro siguiente();

    /** Mueve el cursor al becerro anterior y lo devuelve ({@code null} si no hay becerros). */
    Becerro anterior();
}
