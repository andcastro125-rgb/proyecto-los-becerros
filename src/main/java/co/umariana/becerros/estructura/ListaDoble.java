package co.umariana.becerros.estructura;

import java.util.Iterator;

/**
 * Lista DOBLEMENTE enlazada del equipo (tarea de ESTRUCTURA).
 *
 * <p>Cada nodo conoce al nodo {@code siguiente} y al {@code anterior}. Implemente la lista con sus
 * propios nodos: no se permite usar {@code ArrayList}, {@code LinkedList} ni ninguna otra colección
 * de {@code java.util} para guardar los elementos (las pruebas lo revisan).
 *
 * <p>Se espera que guarde la {@code cabeza} y la {@code cola}, de modo que agregar al final y
 * retroceder el cursor sean operaciones inmediatas (sin recorrer toda la lista).
 *
 * <p>Comportamiento del cursor: se DETIENE en los extremos. {@link #avanzar()} desde el último
 * elemento devuelve {@code false} y el cursor no se mueve; {@link #retroceder()} desde el primero
 * también devuelve {@code false}. En una lista vacía ambos devuelven {@code false}.
 *
 * <p>Al eliminar el elemento donde está el cursor, el cursor pasa al siguiente; si no había
 * siguiente, pasa al anterior.
 */
public class ListaDoble<T> implements Lista<T> {

    // TODO (ESTRUCTURA): declarar la clase Nodo (con siguiente y anterior) y los atributos necesarios
    // (cabeza, cola, tamaño, cursor...).

    @Override
    public void agregar(T elemento) {
        throw new UnsupportedOperationException("TODO: agregar()");
    }

    @Override
    public void insertar(int indice, T elemento) {
        throw new UnsupportedOperationException("TODO: insertar()");
    }

    @Override
    public T obtener(int indice) {
        throw new UnsupportedOperationException("TODO: obtener()");
    }

    @Override
    public T eliminar(int indice) {
        throw new UnsupportedOperationException("TODO: eliminar()");
    }

    @Override
    public int tamano() {
        throw new UnsupportedOperationException("TODO: tamano()");
    }

    @Override
    public boolean estaVacia() {
        throw new UnsupportedOperationException("TODO: estaVacia()");
    }

    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("TODO: vaciar()");
    }

    @Override
    public T actual() {
        throw new UnsupportedOperationException("TODO: actual()");
    }

    @Override
    public boolean avanzar() {
        throw new UnsupportedOperationException("TODO: avanzar()");
    }

    @Override
    public boolean retroceder() {
        throw new UnsupportedOperationException("TODO: retroceder()");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("TODO: iterator()");
    }
}
