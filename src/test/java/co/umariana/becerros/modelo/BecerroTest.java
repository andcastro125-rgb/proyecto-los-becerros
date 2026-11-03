package co.umariana.becerros.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BecerroTest {

    private static Becerro becerro(String codigo, String titulo, String categoria, int apetito,
                                   double peso, String descripcion, String archivo) {
        return new Becerro(codigo, titulo, categoria, apetito, peso, descripcion, archivo);
    }

    private static Becerro valido() {
        return becerro("L001", "Gloria", "Ganado", 2, 1.32, "Becerro de ejemplo", "gloria.jpeg");
    }

    @Test
    @DisplayName("un becerro correcto es válido")
    void valido_noLanza() {
        assertDoesNotThrow(() -> valido().validar());
    }

    @Test
    @DisplayName("la descripción puede estar vacía")
    void descripcionVaciaEsValida() {
        assertDoesNotThrow(() -> becerro("L1", "T", "C", 5, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("los límites del apetito (1 y 10) son válidos")
    void limitesValidos() {
        assertDoesNotThrow(() -> becerro("L1", "T", "C", 1, 1, "", "a.png").validar());
        assertDoesNotThrow(() -> becerro("L1", "T", "C", 10, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el apetito fuera de 1..10 es inválida")
    void apetitoFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 0, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", -3, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el peso debe ser mayor que 0")
    void pesoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 0, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, -1.5, "", "a.png").validar());
    }

    @Test
    @DisplayName("código, título, categoría y archivo no pueden ser nulos ni estar en blanco")
    void textosObligatorios() {
        assertThrows(IllegalArgumentException.class, () -> becerro(null, "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("  ", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", null, "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", " ", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, "", "").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, "", null).validar());
    }

    @Test
    @DisplayName("la descripción no puede ser nula")
    void descripcionNula() {
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, null, "a.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener ';' (separa los campos del .txt)")
    void sinPuntoYComa() {
        assertThrows(IllegalArgumentException.class, () -> becerro("L;1", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T;x", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C;x", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, "a;b", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, "", "a;.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener saltos de línea")
    void sinSaltosDeLinea() {
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T\nx", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> becerro("L1", "T", "C", 5, 1, "uno\r\ndos", "a.png").validar());
    }

    @Test
    @DisplayName("el mensaje de error dice qué está mal")
    void mensajeDelError() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> becerro("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());
    }
}
