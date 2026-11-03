package co.umariana.becerros.estructura;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ListaDobleTest {

    private static ListaDoble<String> con(String... elementos) {
        ListaDoble<String> lista = new ListaDoble<>();
        for (String e : elementos) {
            lista.agregar(e);
        }
        return lista;
    }

    private static List<String> contenido(Lista<String> lista) {
        List<String> r = new ArrayList<>();
        for (String s : lista) {
            r.add(s);
        }
        return r;
    }

    // ---------- básico ----------

    @Test
    @DisplayName("Una lista nueva está vacía")
    void nuevaEstaVacia() {
        ListaDoble<String> lista = new ListaDoble<>();
        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
        assertNull(lista.actual());
    }

    @Test
    @DisplayName("agregar pone los elementos al final, en orden")
    void agregarAlFinal() {
        ListaDoble<String> lista = con("A", "B", "C");
        assertEquals(3, lista.tamano());
        assertFalse(lista.estaVacia());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    @DisplayName("insertar al inicio, en medio y al final")
    void insertar() {
        ListaDoble<String> lista = con("B", "D");
        lista.insertar(0, "A");
        lista.insertar(2, "C");
        lista.insertar(4, "E");
        assertEquals(List.of("A", "B", "C", "D", "E"), contenido(lista));
    }

    @Test
    @DisplayName("insertar en una lista vacía")
    void insertarEnVacia() {
        ListaDoble<String> lista = new ListaDoble<>();
        lista.insertar(0, "A");
        assertEquals(List.of("A"), contenido(lista));
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("insertar y obtener fuera de rango lanzan IndexOutOfBoundsException")
    void indicesInvalidos() {
        ListaDoble<String> lista = con("A", "B");
        assertThrows(IndexOutOfBoundsException.class, () -> lista.insertar(-1, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.insertar(3, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.obtener(2));
        assertThrows(IndexOutOfBoundsException.class, () -> lista.eliminar(2));
        assertThrows(IndexOutOfBoundsException.class, () -> new ListaDoble<String>().eliminar(0));
        assertEquals(List.of("A", "B"), contenido(lista), "los intentos fallidos no deben cambiar la lista");
    }

    @Test
    @DisplayName("eliminar devuelve el elemento: primero, medio y último")
    void eliminar() {
        ListaDoble<String> lista = con("A", "B", "C", "D", "E");
        assertEquals("A", lista.eliminar(0));
        assertEquals("C", lista.eliminar(1));
        assertEquals("E", lista.eliminar(2));
        assertEquals(List.of("B", "D"), contenido(lista));
        assertEquals(2, lista.tamano());
    }

    @Test
    @DisplayName("eliminar el único elemento deja la lista vacía y reutilizable")
    void eliminarUnico() {
        ListaDoble<String> lista = con("A");
        assertEquals("A", lista.eliminar(0));
        assertTrue(lista.estaVacia());
        assertNull(lista.actual());
        lista.agregar("B");
        assertEquals(List.of("B"), contenido(lista));
        assertEquals("B", lista.actual());
    }

    @Test
    @DisplayName("vaciar elimina todo")
    void vaciar() {
        ListaDoble<String> lista = con("A", "B", "C");
        lista.vaciar();
        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamano());
        assertNull(lista.actual());
        assertFalse(lista.iterator().hasNext());
    }

    @Test
    @DisplayName("acepta cientos de elementos")
    void muchosElementos() {
        ListaDoble<Integer> lista = new ListaDoble<>();
        for (int i = 0; i < 1000; i++) {
            lista.agregar(i);
        }
        assertEquals(1000, lista.tamano());
        assertEquals(999, lista.obtener(999));
        assertEquals(500, lista.obtener(500));
        for (int i = 0; i < 500; i++) {
            lista.eliminar(0);
        }
        assertEquals(500, lista.tamano());
        assertEquals(500, lista.obtener(0));
    }

    // ---------- iterador ----------

    @Test
    @DisplayName("el iterador recorre cada elemento UNA vez y termina")
    void iteradorTermina() {
        ListaDoble<String> lista = con("A", "B", "C");
        Iterator<String> it = lista.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertEquals("B", it.next());
        assertEquals("C", it.next());
        assertFalse(it.hasNext(), "el iterador debe terminar después del último elemento");
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    @DisplayName("el iterador de una lista vacía no tiene elementos")
    void iteradorVacia() {
        assertFalse(new ListaDoble<String>().iterator().hasNext());
    }

    // ---------- cursor (se detiene en los extremos) ----------

    @Test
    @DisplayName("el cursor empieza en el primer elemento")
    void cursorInicial() {
        assertEquals("A", con("A", "B", "C").actual());
    }

    @Test
    @DisplayName("avanzar recorre los elementos y se detiene en el último")
    void avanzarSeDetiene() {
        ListaDoble<String> lista = con("A", "B", "C");
        assertTrue(lista.avanzar());
        assertEquals("B", lista.actual());
        assertTrue(lista.avanzar());
        assertEquals("C", lista.actual());
        assertFalse(lista.avanzar(), "desde el último ya no se puede avanzar");
        assertEquals("C", lista.actual(), "el cursor se queda en el último");
    }

    @Test
    @DisplayName("retroceder recorre hacia atrás y se detiene en el primero")
    void retrocederSeDetiene() {
        ListaDoble<String> lista = con("A", "B", "C");
        assertFalse(lista.retroceder(), "desde el primero ya no se puede retroceder");
        assertEquals("A", lista.actual());
        lista.avanzar();
        lista.avanzar();
        assertTrue(lista.retroceder());
        assertEquals("B", lista.actual());
        assertTrue(lista.retroceder());
        assertEquals("A", lista.actual());
        assertFalse(lista.retroceder());
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("con un solo elemento el cursor no se puede mover")
    void cursorUnElemento() {
        ListaDoble<String> lista = con("A");
        assertFalse(lista.avanzar());
        assertFalse(lista.retroceder());
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("en una lista vacía el cursor no se mueve")
    void cursorVacia() {
        ListaDoble<String> lista = new ListaDoble<>();
        assertFalse(lista.avanzar());
        assertFalse(lista.retroceder());
        assertNull(lista.actual());
    }

    @Test
    @DisplayName("se puede ir hasta el final y volver hasta el principio")
    void idaYVuelta() {
        ListaDoble<String> lista = con("A", "B", "C", "D");
        while (lista.avanzar()) {
            // avanzar hasta el último
        }
        assertEquals("D", lista.actual());
        StringBuilder recorrido = new StringBuilder(lista.actual());
        while (lista.retroceder()) {
            recorrido.append(lista.actual());
        }
        assertEquals("DCBA", recorrido.toString());
    }

    @Test
    @DisplayName("insertar antes del cursor no lo mueve de elemento")
    void insertarNoMueveElCursor() {
        ListaDoble<String> lista = con("A", "B");
        lista.agregar("C");
        lista.insertar(0, "Z");
        // Z A B C -> el cursor sigue en A
        assertEquals("A", lista.actual());
        assertTrue(lista.retroceder());
        assertEquals("Z", lista.actual());
        assertFalse(lista.retroceder());
    }

    @Test
    @DisplayName("eliminar el elemento actual deja el cursor en el siguiente")
    void eliminarElActual() {
        ListaDoble<String> lista = con("A", "B", "C");
        lista.avanzar(); // B
        lista.eliminar(1);
        assertEquals("C", lista.actual());
        assertEquals(List.of("A", "C"), contenido(lista));
        assertTrue(lista.retroceder());
        assertEquals("A", lista.actual());
    }

    @Test
    @DisplayName("eliminar el actual cuando es el último deja el cursor en el anterior")
    void eliminarElActualUltimo() {
        ListaDoble<String> lista = con("A", "B", "C");
        lista.avanzar();
        lista.avanzar(); // C
        lista.eliminar(2);
        assertEquals("B", lista.actual());
        assertFalse(lista.avanzar(), "B es ahora el último");
    }

    @Test
    @DisplayName("eliminar otro elemento no mueve el cursor")
    void eliminarOtroNoMueveElCursor() {
        ListaDoble<String> lista = con("A", "B", "C");
        lista.avanzar(); // B
        lista.eliminar(0);
        assertEquals("B", lista.actual());
        lista.eliminar(1); // C
        assertEquals("B", lista.actual());
    }

    @Test
    @DisplayName("tras vaciar, el cursor vuelve a empezar en el primer elemento nuevo")
    void cursorTrasVaciar() {
        ListaDoble<String> lista = con("A", "B");
        lista.avanzar();
        lista.vaciar();
        lista.agregar("X");
        lista.agregar("Y");
        assertEquals("X", lista.actual());
    }

    @Test
    @DisplayName("retroceder es inmediato: no recorre la lista desde el principio (usa los enlaces 'anterior')")
    void retrocederEsRapido() {
        ListaDoble<Integer> lista = new ListaDoble<>();
        for (int i = 0; i < 100_000; i++) {
            lista.agregar(i);
        }
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            while (lista.avanzar()) {
                // hasta el último
            }
            int pasos = 0;
            while (lista.retroceder()) {
                pasos++;
            }
            assertEquals(99_999, pasos);
        });
    }

    // ---------- reglas del proyecto ----------

    @Test
    @DisplayName("la lista se implementa con nodos propios (sin ArrayList, LinkedList, etc.)")
    void sinColeccionesDeJava() throws IOException {
        Path fuente = Path.of("src/main/java/co/umariana/becerros/estructura/ListaDoble.java");
        // Se ignoran los comentarios: solo importa el código.
        String codigo = Files.readString(fuente).replaceAll("(?s)/\\*.*?\\*/", "").replaceAll("//[^\\n]*", "");
        for (String prohibida : List.of("ArrayList", "LinkedList", "Vector", "Deque", "Stack", "HashMap",
                "TreeMap", "HashSet", "TreeSet", "java.util.List;", "java.util.*", "Arrays", "Collections")) {
            assertFalse(codigo.contains(prohibida),
                    "ListaDoble no debe usar '" + prohibida + "': implemente los nodos usted mismo");
        }
        assertTrue(codigo.contains("class Nodo"), "debe declarar su propia clase Nodo");
        assertTrue(codigo.matches("(?s).*Nodo\\s+anterior.*"), "el nodo debe tener el enlace 'anterior' (lista doblemente enlazada)");
        assertTrue(codigo.matches("(?s).*Nodo\\s+siguiente.*"), "el nodo debe tener el enlace 'siguiente'");
    }
}
