package co.umariana.becerros.persistencia;

import co.umariana.becerros.modelo.Becerro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioTxtTest {

    @TempDir
    Path carpeta;

    private Path archivo;
    private RepositorioTxt repo;

    @BeforeEach
    void preparar() {
        archivo = carpeta.resolve("becerros.txt");
        repo = new RepositorioTxt(archivo);
    }

    private static Becerro l(String codigo, int apetito, double peso) {
        return new Becerro(codigo, "Título " + codigo, "Ganado", apetito, peso, "Descripción " + codigo, codigo + ".png");
    }

    @Test
    @DisplayName("si el archivo no existe, cargar devuelve una lista vacía")
    void archivoInexistente() throws IOException {
        assertTrue(repo.cargar().isEmpty());
    }

    @Test
    @DisplayName("guardar escribe una línea por becerro con el formato pedido")
    void formatoDelArchivo() throws IOException {
        repo.guardar(List.of(
                new Becerro("L001", "Gloria", "Ganado", 2, 1.32, "Becerro de ejemplo", "gloria.jpeg"),
                new Becerro("L002", "Otro", "Tráfico", 7, 5.0, "", "lola.jpg")));
        List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
        assertEquals(List.of(
                "L001;Gloria;Ganado;2;1.32;Becerro de ejemplo;gloria.jpeg",
                "L002;Otro;Tráfico;7;5.0;;lola.jpg"), lineas);
    }

    @Test
    @DisplayName("lo que se guarda se puede cargar igual (ida y vuelta)")
    void idaYVuelta() throws IOException {
        repo.guardar(List.of(l("A", 3, 1.25), l("B", 10, 12.5), l("C", 1, 0.5)));
        List<Becerro> cargados = repo.cargar();
        assertEquals(3, cargados.size());
        Becerro b = cargados.get(1);
        assertEquals("B", b.getCodigo());
        assertEquals("Título B", b.getTitulo());
        assertEquals("Ganado", b.getCategoria());
        assertEquals(10, b.getApetito());
        assertEquals(12.5, b.getPesoKg(), 0.0001);
        assertEquals("Descripción B", b.getDescripcion());
        assertEquals("B.png", b.getArchivo());
        assertEquals("A", cargados.get(0).getCodigo(), "debe conservar el orden");
        assertEquals("C", cargados.get(2).getCodigo());
    }

    @Test
    @DisplayName("conserva tildes y ñ (UTF-8)")
    void utf8() throws IOException {
        repo.guardar(List.of(new Becerro("L1", "¡Qué rabia, niño!", "Fútbol", 5, 1.0, "Año nuevo, vida nueva", "a.png")));
        Becerro c = repo.cargar().get(0);
        assertEquals("¡Qué rabia, niño!", c.getTitulo());
        assertEquals("Fútbol", c.getCategoria());
        assertEquals("Año nuevo, vida nueva", c.getDescripcion());
    }

    @Test
    @DisplayName("guardar reemplaza el contenido anterior (no agrega al final)")
    void guardarReemplaza() throws IOException {
        repo.guardar(List.of(l("A", 1, 1), l("B", 2, 1), l("C", 3, 1)));
        repo.guardar(List.of(l("Z", 9, 1)));
        List<Becerro> cargados = repo.cargar();
        assertEquals(1, cargados.size());
        assertEquals("Z", cargados.get(0).getCodigo());
    }

    @Test
    @DisplayName("guardar una lista vacía deja el archivo vacío")
    void guardarVacia() throws IOException {
        repo.guardar(List.of(l("A", 1, 1)));
        repo.guardar(List.of());
        assertTrue(Files.exists(archivo));
        assertTrue(repo.cargar().isEmpty());
    }

    @Test
    @DisplayName("guardar crea las carpetas que falten")
    void creaCarpetas() throws IOException {
        Path profundo = carpeta.resolve("datos").resolve("2026").resolve("becerros.txt");
        new RepositorioTxt(profundo).guardar(List.of(l("A", 1, 1)));
        assertTrue(Files.exists(profundo));
        assertEquals(1, new RepositorioTxt(profundo).cargar().size());
    }

    @Test
    @DisplayName("cargar ignora las líneas en blanco")
    void ignoraLineasEnBlanco() throws IOException {
        Files.writeString(archivo, "\nL1;T;C;3;1.5;d;a.png\n\n   \nL2;T2;C;4;2.5;;b.png\n", StandardCharsets.UTF_8);
        List<Becerro> cargados = repo.cargar();
        assertEquals(2, cargados.size());
        assertEquals("L2", cargados.get(1).getCodigo());
        assertEquals("", cargados.get(1).getDescripcion());
    }

    @Test
    @DisplayName("cargar ignora las líneas dañadas y sigue con las demás")
    void ignoraLineasDanadas() throws IOException {
        Files.writeString(archivo, String.join("\n",
                "L1;T;C;3;1.5;d;a.png",
                "esta linea no tiene el formato",
                "L2;T;C;no-es-numero;1.5;d;b.png",
                "L3;T;C;3;tampoco;d;c.png",
                "L4;T;C;3;1.5;d",
                "L5;T;C;3;1.5;d;e.png;sobra",
                "L6;T;C;8;2.0;d;f.png") + "\n", StandardCharsets.UTF_8);
        List<Becerro> cargados = repo.cargar();
        assertEquals(List.of("L1", "L6"), cargados.stream().map(Becerro::getCodigo).toList());
    }
}
