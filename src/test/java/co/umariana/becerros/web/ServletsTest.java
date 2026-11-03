package co.umariana.becerros.web;

import co.umariana.becerros.modelo.Becerro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/** Prueba los servlets con una petición/respuesta simuladas y un servicio de mentira. */
class ServletsTest {

    private ServicioFalso servicio;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private RequestDispatcher despachador;

    @BeforeEach
    void preparar() {
        servicio = new ServicioFalso();
        Contexto.usar(servicio);
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        despachador = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher(anyString())).thenReturn(despachador);
    }

    @AfterEach
    void limpiar() {
        Contexto.usar(null);
    }

    private static Becerro l(String codigo, int apetito) {
        return new Becerro(codigo, "T" + codigo, "Ganado", apetito, 1.5, "d", codigo + ".png");
    }

    private void aplicarFormulario(String codigo, String apetito, String peso) {
        when(req.getParameter("codigo")).thenReturn(codigo);
        when(req.getParameter("titulo")).thenReturn("Mi titulo");
        when(req.getParameter("categoria")).thenReturn("Tráfico");
        when(req.getParameter("apetito")).thenReturn(apetito);
        when(req.getParameter("peso")).thenReturn(peso);
        when(req.getParameter("descripcion")).thenReturn("Una descripción");
        when(req.getParameter("archivo")).thenReturn("x.png");
    }

    // ---------- /listar ----------

    @Test
    @DisplayName("/listar pone la lista y los promedios y reenvía a listar.jsp")
    void listar() throws Exception {
        servicio.datos.add(l("A", 3));
        servicio.promedios.put("Ganado", 3.0);

        new ListarBecerros().doGet(req, resp);

        verify(req).setAttribute("becerros", servicio.datos);
        verify(req).setAttribute("promedios", servicio.promedios);
        verify(req).getRequestDispatcher("/WEB-INF/vistas/listar.jsp");
        verify(despachador).forward(req, resp);
    }

    @Test
    @DisplayName("/listar?orden=ranking muestra el ranking en lugar de la lista")
    void listarRanking() throws Exception {
        servicio.ranking.add(l("B", 9));
        when(req.getParameter("orden")).thenReturn("ranking");

        new ListarBecerros().doGet(req, resp);

        verify(req).setAttribute("becerros", servicio.ranking);
        assertTrue(servicio.llamadas.contains("ranking"));
        verify(despachador).forward(req, resp);
    }

    @Test
    @DisplayName("/listar con otro valor de 'orden' usa el orden normal")
    void listarOrdenDesconocido() throws Exception {
        when(req.getParameter("orden")).thenReturn("loquesea");

        new ListarBecerros().doGet(req, resp);

        verify(req).setAttribute("becerros", servicio.datos);
        assertFalse(servicio.llamadas.contains("ranking"));
    }

    // ---------- /registrar ----------

    @Test
    @DisplayName("GET /registrar muestra el formulario")
    void registrarGet() throws Exception {
        new RegistrarBecerro().doGet(req, resp);

        verify(req).getRequestDispatcher("/WEB-INF/vistas/formulario.jsp");
        verify(despachador).forward(req, resp);
    }

    @Test
    @DisplayName("POST /registrar con datos correctos agrega el becerro y redirige a listar")
    void registrarOk() throws Exception {
        aplicarFormulario("N1", "7", "2.5");

        new RegistrarBecerro().doPost(req, resp);

        
        assertEquals(1, servicio.datos.size());
        Becerro l = servicio.datos.get(0);
        assertEquals("N1", l.getCodigo());
        assertEquals("Mi titulo", l.getTitulo());
        assertEquals("Tráfico", l.getCategoria());
        assertEquals(7, l.getApetito());
        assertEquals(2.5, l.getPesoKg(), 0.0001);
        assertEquals("Una descripción", l.getDescripcion());
        assertEquals("x.png", l.getArchivo());
        verify(resp).sendRedirect("listar");
        verify(despachador, never()).forward(any(), any());
    }

    @Test
    @DisplayName("POST /registrar con apetito no numérica muestra el error en el formulario")
    void registrarNumeroInvalido() throws Exception {
        aplicarFormulario("N1", "mucha", "2.5");

        new RegistrarBecerro().doPost(req, resp);

        assertTrue(servicio.datos.isEmpty());
        verify(req).setAttribute(eq("error"), anyString());
        verify(req).getRequestDispatcher("/WEB-INF/vistas/formulario.jsp");
        verify(despachador).forward(req, resp);
        verify(resp, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("POST /registrar con peso no numérica muestra el error en el formulario")
    void registrarPesoInvalida() throws Exception {
        aplicarFormulario("N1", "5", "");

        new RegistrarBecerro().doPost(req, resp);

        verify(req).setAttribute(eq("error"), anyString());
        verify(despachador).forward(req, resp);
        verify(resp, never()).sendRedirect(anyString());
    }

    @Test
    @DisplayName("POST /registrar muestra el mensaje cuando el modelo rechaza el becerro")
    void registrarRechazadoPorElModelo() throws Exception {
        aplicarFormulario("N1", "5", "2.5");
        servicio.fallarAlAgregar = new IllegalArgumentException("Ya existe un becerro con el código N1");

        new RegistrarBecerro().doPost(req, resp);

        verify(req).setAttribute("error", "Ya existe un becerro con el código N1");
        verify(despachador).forward(req, resp);
        verify(resp, never()).sendRedirect(anyString());
    }

    // ---------- /eliminar ----------

    @Test
    @DisplayName("POST /eliminar elimina por código y redirige a listar")
    void eliminar() throws Exception {
        when(req.getParameter("codigo")).thenReturn("L003");

        new EliminarBecerro().doPost(req, resp);

        assertTrue(servicio.llamadas.contains("eliminar:L003"));
        verify(resp).sendRedirect("listar");
    }

    // ---------- /visor ----------

    @Test
    @DisplayName("/visor sin acción muestra el becerro actual")
    void visorActual() throws Exception {
        servicio.cursorActual = l("A", 3);
        servicio.datos.add(servicio.cursorActual);

        new Visor().doGet(req, resp);

        verify(req).setAttribute("becerro", servicio.cursorActual);
        verify(req).setAttribute("total", 1);
        verify(req).getRequestDispatcher("/WEB-INF/vistas/visor.jsp");
        verify(despachador).forward(req, resp);
        assertFalse(servicio.llamadas.contains("siguiente"));
        assertFalse(servicio.llamadas.contains("anterior"));
    }

    @Test
    @DisplayName("/visor?accion=siguiente mueve el cursor al siguiente")
    void visorSiguiente() throws Exception {
        servicio.cursorSiguiente = l("B", 4);
        when(req.getParameter("accion")).thenReturn("siguiente");

        new Visor().doGet(req, resp);

        assertTrue(servicio.llamadas.contains("siguiente"));
        verify(req).setAttribute("becerro", servicio.cursorSiguiente);
        verify(despachador).forward(req, resp);
    }

    @Test
    @DisplayName("/visor?accion=anterior mueve el cursor al anterior")
    void visorAnterior() throws Exception {
        servicio.cursorAnterior = l("C", 5);
        when(req.getParameter("accion")).thenReturn("anterior");

        new Visor().doGet(req, resp);

        assertTrue(servicio.llamadas.contains("anterior"));
        verify(req).setAttribute("becerro", servicio.cursorAnterior);
    }

    @Test
    @DisplayName("/visor sin becerros reenvía igual (becerro = null)")
    void visorVacio() throws Exception {
        new Visor().doGet(req, resp);

        verify(req).setAttribute("becerro", null);
        verify(req).setAttribute("total", 0);
        verify(despachador).forward(req, resp);
    }
}
