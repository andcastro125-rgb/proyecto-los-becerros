package co.umariana.becerros.persistencia;

import co.umariana.becerros.modelo.Becerro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda los becerros en un archivo de texto (tarea de PERSISTENCIA).
 *
 * <p>Formato: UNA línea por becerro, con los campos separados por {@code ;} y en este orden:
 * <pre>codigo;titulo;categoria;apetito;pesoKg;descripcion;archivo</pre>
 * Ejemplo:
 * <pre>L001;Gloria;Ganado;2;1.32;Becerro de ejemplo de todos los días;gloria.jpeg</pre>
 * <ul>
 *   <li>Codificación UTF-8 (para las tildes y la ñ).</li>
 *   <li>{@code pesoKg} se escribe con punto decimal.</li>
 *   <li>{@code cargar}: si el archivo no existe devuelve una lista vacía; ignora las líneas en
 *       blanco y las líneas dañadas (cantidad de campos distinta de 7 o números inválidos).</li>
 *   <li>{@code guardar}: reemplaza todo el contenido del archivo y crea las carpetas que falten.
 *       Una lista vacía deja el archivo vacío.</li>
 * </ul>
 */
public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Becerro> cargar() throws IOException {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        List<Becerro> becerros = new ArrayList<>();
        for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
            if (linea.isBlank()) {
                continue;
            }

            String[] campos = linea.split(";", -1);
            if (campos.length != 7) {
                continue;
            }

            try {
                int apetito = Integer.parseInt(campos[3]);
                double pesoKg = Double.parseDouble(campos[4]);
                if (!Double.isFinite(pesoKg)) {
                    continue;
                }
                becerros.add(new Becerro(
                        campos[0], campos[1], campos[2], apetito, pesoKg, campos[5], campos[6]));
            } catch (NumberFormatException e) {
                // Una línea con valores numéricos inválidos no impide cargar las demás.
            }
        }
        return becerros;
    }

    @Override
    public void guardar(List<Becerro> becerros) throws IOException {
        Path carpeta = archivo.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }

        List<String> lineas = new ArrayList<>(becerros.size());
        for (Becerro becerro : becerros) {
            lineas.add(String.join(";",
                    becerro.getCodigo(),
                    becerro.getTitulo(),
                    becerro.getCategoria(),
                    Integer.toString(becerro.getApetito()),
                    Double.toString(becerro.getPesoKg()),
                    becerro.getDescripcion(),
                    becerro.getArchivo()));
        }

        Files.write(archivo, lineas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }
}
