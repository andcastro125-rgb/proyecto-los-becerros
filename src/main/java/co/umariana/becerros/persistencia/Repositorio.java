package co.umariana.becerros.persistencia;

import co.umariana.becerros.modelo.Becerro;

import java.io.IOException;
import java.util.List;

/** Contrato de la persistencia. NO se modifica. {@link RepositorioTxt} es la implementación. */
public interface Repositorio {

    /** Lee todos los becerros guardados. Si todavía no hay nada guardado, devuelve una lista vacía. */
    List<Becerro> cargar() throws IOException;

    /** Guarda TODOS los becerros, reemplazando lo que hubiera antes. */
    void guardar(List<Becerro> becerros) throws IOException;
}
