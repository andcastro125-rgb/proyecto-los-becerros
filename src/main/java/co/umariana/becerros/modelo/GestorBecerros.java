package co.umariana.becerros.modelo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import co.umariana.becerros.estructura.Lista;
import co.umariana.becerros.persistencia.Repositorio;

public class GestorBecerros implements ServicioBecerros {

    private final Lista<Becerro> lista;
    private final Repositorio repositorio;

    // Constructor
    public GestorBecerros(Lista<Becerro> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;

        try {
            List<Becerro> guardados = repositorio.cargar();

            for (Becerro becerro : guardados) {
                lista.agregar(becerro);
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Método auxiliar para guardar los cambios
    private void guardar() {
        try {
            repositorio.guardar(listar());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Agregar un becerro
    @Override
    public void agregar(Becerro becerro) {

        if (becerro == null) {
            throw new IllegalArgumentException("El becerro no puede ser nulo");
        }

        becerro.validar();

        if (buscar(becerro.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un becerro con ese código");
        }

        lista.agregar(becerro);

        guardar();
    }

    // Buscar un becerro por su código
    @Override
    public Becerro buscar(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Becerro becerro : lista) {
            if (becerro.getCodigo().equals(codigo)) {
                return becerro;
            }
        }

        return null;
    }

    // Eliminar un becerro
    @Override
    public boolean eliminar(String codigo) {

        for (int i = 0; i < lista.tamano(); i++) {

            if (lista.obtener(i).getCodigo().equals(codigo)) {

                lista.eliminar(i);

                guardar();

                return true;
            }
        }

        return false;
    }

    // Listar todos los becerros
    @Override
    public List<Becerro> listar() {

        List<Becerro> resultado = new ArrayList<>();

        for (Becerro becerro : lista) {
            resultado.add(becerro);
        }

        return resultado;
    }

    // Ranking de mayor a menor apetito
    @Override
    public List<Becerro> ranking() {

        List<Becerro> resultado = listar();

        resultado.sort(
            Comparator.comparingInt(Becerro::getApetito)
                .reversed()
                .thenComparing(Becerro::getCodigo)
        );

        return resultado;
    }

    // Promedio de apetito por categoría
    @Override
    public Map<String, Double> promedioPorCategoria() {

        Map<String, Double> sumas = new TreeMap<>();
        Map<String, Integer> cantidades = new TreeMap<>();

        for (Becerro becerro : lista) {

            String categoria = becerro.getCategoria();

            sumas.put(
                categoria,
                sumas.getOrDefault(categoria, 0.0) + becerro.getApetito()
            );

            cantidades.put(
                categoria,
                cantidades.getOrDefault(categoria, 0) + 1
            );
        }

        Map<String, Double> promedios = new TreeMap<>();

        for (String categoria : sumas.keySet()) {

            double promedio = sumas.get(categoria) / cantidades.get(categoria);

            promedios.put(categoria, promedio);
        }

        return promedios;
    }

    // Cantidad total de becerros
    @Override
    public int total() {
        return lista.tamano();
    }

    // Obtener el becerro actual del cursor
    @Override
    public Becerro actual() {
        return lista.actual();
    }

    // Avanzar al siguiente becerro
    @Override
    public Becerro siguiente() {

        if (lista.estaVacia()) {
            return null;
        }

        lista.avanzar();

        return lista.actual();
    }

    // Retroceder al becerro anterior
    @Override
    public Becerro anterior() {

        if (lista.estaVacia()) {
            return null;
        }

        lista.retroceder();

        return lista.actual();
    }
}
