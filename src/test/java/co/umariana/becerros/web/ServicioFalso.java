package co.umariana.becerros.web;

import co.umariana.becerros.modelo.Becerro;
import co.umariana.becerros.modelo.ServicioBecerros;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Servicio de mentira que anota lo que le piden: permite probar los servlets sin el resto del equipo. */
class ServicioFalso implements ServicioBecerros {

    final List<Becerro> datos = new ArrayList<>();
    final List<Becerro> ranking = new ArrayList<>();
    final Map<String, Double> promedios = new TreeMap<>();
    final List<String> llamadas = new ArrayList<>();
    RuntimeException fallarAlAgregar = null;
    Becerro cursorActual = null;
    Becerro cursorSiguiente = null;
    Becerro cursorAnterior = null;

    @Override public void agregar(Becerro l) {
        llamadas.add("agregar");
        if (fallarAlAgregar != null) throw fallarAlAgregar;
        datos.add(l);
    }
    @Override public Becerro buscar(String codigo) { llamadas.add("buscar"); return null; }
    @Override public boolean eliminar(String codigo) { llamadas.add("eliminar:" + codigo); return true; }
    @Override public List<Becerro> listar() { llamadas.add("listar"); return datos; }
    @Override public List<Becerro> ranking() { llamadas.add("ranking"); return ranking; }
    @Override public Map<String, Double> promedioPorCategoria() { llamadas.add("promedios"); return promedios; }
    @Override public int total() { return datos.size(); }
    @Override public Becerro actual() { llamadas.add("actual"); return cursorActual; }
    @Override public Becerro siguiente() { llamadas.add("siguiente"); return cursorSiguiente; }
    @Override public Becerro anterior() { llamadas.add("anterior"); return cursorAnterior; }
}
