package co.umariana.becerros.estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista DOBLEMENTE enlazada del equipo (tarea de ESTRUCTURA).
 * Cada nodo conoce al nodo siguiente y al anterior. No usa colecciones de java.util.
 * El cursor se DETIENE en los extremos. Al eliminar el elemento del cursor, este pasa al
 * siguiente; si no había siguiente, pasa al anterior.
 */
public class ListaDoble<T> implements Lista<T> {

    private class Nodo {
        T dato;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T datos) {
            this.dato = datos;
        }
    }

    private Nodo cabeza;
    private Nodo cola;
    private Nodo cursor;
    private int tamano;

    // Auxiliar privado: encuentra el nodo que está en una posición (desde la cabeza o desde la cola).
    private Nodo nodoEn(int indice) {
        Nodo n;
        if (indice < tamano / 2) {
            n = cabeza;
            for (int i = 0; i < indice; i++) {
                n = n.siguiente;
            }
        } else {
            n = cola;
            for (int i = tamano - 1; i > indice; i--) {
                n = n.anterior;
            }
        }
        return n;
    }

    // Valida posiciones de elementos existentes: 0 <= indice < tamano.
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamano);
        }
    }

    @Override
    public void agregar(T elemento) {
        Nodo nuevoNodo = new Nodo(elemento);
        if (cola == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
            cursor = nuevoNodo;
        } else {
            nuevoNodo.anterior = cola;
            cola.siguiente = nuevoNodo;
            cola = nuevoNodo;
        }
        tamano++;
    }

    @Override
    public void insertar(int indice, T elemento) {
        if (indice < 0 || indice > tamano) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamano);
        }
        if (indice == tamano) { // al final (también cubre lista vacía)
            agregar(elemento);
            return;
        }
        Nodo nuevoNodo = new Nodo(elemento);
        if (indice == 0) {
            nuevoNodo.siguiente = cabeza;
            cabeza.anterior = nuevoNodo;
            cabeza = nuevoNodo;
        } else {
            Nodo posterior = nodoEn(indice);
            Nodo previo = posterior.anterior;
            nuevoNodo.anterior = previo;
            nuevoNodo.siguiente = posterior;
            previo.siguiente = nuevoNodo;
            posterior.anterior = nuevoNodo;
        }
        tamano++;
    }

    @Override
    public T obtener(int indice) {
        validarIndice(indice);
        return nodoEn(indice).dato;
    }

    @Override
    public T eliminar(int indice) {
        validarIndice(indice);
        Nodo nodo = nodoEn(indice);

        if (nodo.anterior == null) {
            cabeza = nodo.siguiente;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
        }
        if (nodo.siguiente == null) {
            cola = nodo.anterior;
        } else {
            nodo.siguiente.anterior = nodo.anterior;
        }

        if (nodo == cursor) {
            cursor = (nodo.siguiente != null) ? nodo.siguiente : nodo.anterior;
        }

        tamano--;

        T dato = nodo.dato;
        nodo.siguiente = null;
        nodo.anterior = null;
        nodo.dato = null;
        return dato;
    }

    @Override
    public int tamano() {
        return tamano;
    }

    @Override
    public boolean estaVacia() {
        return tamano == 0;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        cursor = null;
        tamano = 0;
    }

    @Override
    public T actual() {
        if (cursor == null) {
            return null;
        }
        return cursor.dato;
    }

    @Override
    public boolean avanzar() {
        if (cursor == null || cursor.siguiente == null) {
            return false;
        }
        cursor = cursor.siguiente;
        return true;
    }

    @Override
    public boolean retroceder() {
        if (cursor == null || cursor.anterior == null) {
            return false;
        }
        cursor = cursor.anterior;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo siguienteNodo = cabeza;

            @Override
            public boolean hasNext() {
                return siguienteNodo != null;
            }

            @Override
            public T next() {
                if (siguienteNodo == null) {
                    throw new NoSuchElementException();
                }
                T dato = siguienteNodo.dato;
                siguienteNodo = siguienteNodo.siguiente;
                return dato;
            }
        };
    }
}