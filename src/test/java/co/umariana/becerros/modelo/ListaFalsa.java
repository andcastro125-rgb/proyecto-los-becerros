package co.umariana.becerros.modelo;

import co.umariana.becerros.estructura.Lista;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lista de mentira (con ArrayList) para probar el modelo sin depender de la ListaDoble del equipo.
 * El cursor se detiene en los extremos.
 */
class ListaFalsa<T> implements Lista<T> {

    private final List<T> datos = new ArrayList<>();
    private int cursor = 0;

    @Override public void agregar(T e) { datos.add(e); }
    @Override public void insertar(int i, T e) { datos.add(i, e); }
    @Override public T obtener(int i) { return datos.get(i); }
    @Override public T eliminar(int i) {
        T r = datos.remove(i);
        if (cursor >= datos.size()) {
            cursor = Math.max(datos.size() - 1, 0);
        }
        return r;
    }
    @Override public int tamano() { return datos.size(); }
    @Override public boolean estaVacia() { return datos.isEmpty(); }
    @Override public void vaciar() { datos.clear(); cursor = 0; }
    @Override public T actual() { return datos.isEmpty() ? null : datos.get(cursor); }
    @Override public boolean avanzar() {
        if (cursor + 1 >= datos.size()) return false;
        cursor++;
        return true;
    }
    @Override public boolean retroceder() {
        if (cursor <= 0) return false;
        cursor--;
        return true;
    }
    @Override public Iterator<T> iterator() { return new ArrayList<>(datos).iterator(); }
}
