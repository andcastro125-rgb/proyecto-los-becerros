package co.umariana.becerros.modelo;

/**
 * Un becerro del Becerrario: una imagen con su título, categoría y nivel de apetito.
 *
 * <p>La clase ya tiene sus atributos, constructor y getters. Lo que falta es {@link #validar()}
 * (tarea de MODELO).
 */
public class Becerro {

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private final int apetito;
    private final double pesoKg;
    private final String descripcion;
    private final String archivo;

    public Becerro(String codigo, String titulo, String categoria, int apetito,
                   double pesoKg, String descripcion, String archivo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.apetito = apetito;
        this.pesoKg = pesoKg;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Revisa que el becerro sea válido. Si no lo es, lanza {@link IllegalArgumentException}
     * con un mensaje que explique el problema. Reglas:
     * <ul>
     *   <li>{@code codigo}, {@code titulo}, {@code categoria} y {@code archivo} no pueden ser nulos ni estar en blanco.</li>
     *   <li>{@code apetito} debe estar entre 1 y 10 (incluidos).</li>
     *   <li>{@code pesoKg} debe ser mayor que 0.</li>
     *   <li>{@code descripcion} puede estar vacía, pero no ser nula.</li>
     *   <li>Ningún texto puede contener el carácter {@code ;} (separa los campos del archivo .txt)
     *       ni saltos de línea.</li>
     * </ul>
     */
    public void validar() {

    // Validar que los textos obligatorios no sean nulos ni estén vacíos
    if (codigo == null || codigo.isBlank()) {
        throw new IllegalArgumentException("El código no puede estar vacío");
    }

    if (titulo == null || titulo.isBlank()) {
        throw new IllegalArgumentException("El título no puede estar vacío");
    }

    if (categoria == null || categoria.isBlank()) {
        throw new IllegalArgumentException("La categoría no puede estar vacía");
    }

    if (archivo == null || archivo.isBlank()) {
        throw new IllegalArgumentException("El archivo no puede estar vacío");
    }

    // Validar el apetito
    if (apetito < 1 || apetito > 10) {
        throw new IllegalArgumentException("El apetito debe estar entre 1 y 10");
    }

    // Validar el peso
    if (pesoKg <= 0) {
        throw new IllegalArgumentException("El peso debe ser mayor que cero");
    }

    // La descripción puede estar vacía, pero no ser nula
    if (descripcion == null) {
        throw new IllegalArgumentException("La descripción no puede ser nula");
    }

    // Validar que ningún texto contenga punto y coma ni saltos de línea
    String[] textos = {codigo, titulo, categoria, descripcion, archivo};

    for (String texto : textos) {
        if (texto != null && (texto.contains(";") || texto.contains("\n") || texto.contains("\r"))) {
            throw new IllegalArgumentException("Los textos no pueden contener ; ni saltos de línea");
        }
    }
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public int getApetito() { return apetito; }
    public double getPesoKg() { return pesoKg; }
    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + ", apetito " + apetito + ")";
    }
}
