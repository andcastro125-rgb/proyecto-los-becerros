package co.umariana.becerros.web;

import co.umariana.becerros.modelo.Becerro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /registrar  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li><b>GET:</b> reenvía a {@code /WEB-INF/vistas/formulario.jsp}.</li>
 *   <li><b>POST:</b> lee los parámetros {@code codigo, titulo, categoria, apetito, peso,
 *       descripcion, archivo}; crea un {@code Becerro} (el apetito es un entero y el peso un
 *       decimal) y llama a {@code Contexto.servicio().agregar(...)}. Si todo sale bien, redirige a
 *       {@code "listar"} ({@code resp.sendRedirect("listar")}).</li>
 *   <li><b>Si algo falla</b> ({@link NumberFormatException} o {@link IllegalArgumentException}), NO redirige:
 *       pone el mensaje de la excepción en el atributo {@code "error"} y reenvía de nuevo a
 *       {@code formulario.jsp}, que debe mostrar ese error.</li>
 * </ul>
 */
@WebServlet("/registrar")
public class RegistrarBecerro extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Becerro becerro = new Becerro(
                    req.getParameter("codigo"),
                    req.getParameter("titulo"),
                    req.getParameter("categoria"),
                    Integer.parseInt(req.getParameter("apetito")),
                    Double.parseDouble(req.getParameter("peso")),
                    req.getParameter("descripcion"),
                    req.getParameter("archivo"));
            Contexto.servicio().agregar(becerro);
            resp.sendRedirect("listar");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp").forward(req, resp);
        }
    }
}
