package co.umariana.becerros.modelo;

import co.umariana.becerros.persistencia.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Repositorio de mentira (en memoria) para probar el modelo sin depender del archivo .txt. */
class RepositorioFalso implements Repositorio {

    List<Becerro> guardados = new ArrayList<>();
    int vecesGuardado = 0;
    boolean fallar = false;

    @Override
    public List<Becerro> cargar() throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        return new ArrayList<>(guardados);
    }

    @Override
    public void guardar(List<Becerro> becerros) throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        guardados = new ArrayList<>(becerros);
        vecesGuardado++;
    }
}
