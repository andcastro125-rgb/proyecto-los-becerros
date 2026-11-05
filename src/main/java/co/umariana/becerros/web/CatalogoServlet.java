package co.umariana.becerros.web;

import co.umariana.becerros.modelo.Becerro;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Página del catálogo: muestra los imágenes que vienen en el proyecto y permite agregarlos al
 * Becerrario con un botón. Ya está hecha: es la forma de tener datos para probar la aplicación.
 * NO se modifica.
 */
@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String ok = req.getParameter("ok");
        String error = req.getParameter("error");
        String context = req.getContextPath();
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.println("<title>Catálogo · Becerrario</title><style>");
        out.println("""
            :root{--ink:#28352d;--muted:#778078;--green:#42654b;--green-dark:#2d4935;--cream:#f7f7f1;--line:#e5e8df;--white:#fff}
            *{box-sizing:border-box}body{margin:0;background:var(--cream);color:var(--ink);font:15px/1.55 Inter,ui-sans-serif,system-ui,-apple-system,"Segoe UI",sans-serif}
            a{color:inherit;text-decoration:none}.shell{max-width:1180px;margin:auto;padding:0 28px}
            .topbar{min-height:76px;display:flex;align-items:center;justify-content:space-between;gap:24px;border-bottom:1px solid var(--line)}
            .brand{display:flex;align-items:center;gap:11px;font-weight:750;letter-spacing:-.04em;font-size:19px}.brand-mark{width:38px;height:38px;border-radius:13px;background:#e6eee4;display:grid;place-items:center;font-size:20px}
            nav{display:flex;align-items:center;gap:25px;color:var(--muted);font-size:14px}nav a:hover,nav a.active{color:var(--green-dark)}nav a.active{font-weight:750}
            .button{display:inline-flex;align-items:center;justify-content:center;gap:8px;border:0;border-radius:12px;padding:11px 16px;background:var(--green);color:white;font:inherit;font-size:14px;font-weight:750;cursor:pointer;transition:.18s}
            .button:hover{background:var(--green-dark);transform:translateY(-1px)}.button:disabled{background:#edf1ea;color:#647267;cursor:default;transform:none}
            main{padding:42px 0 68px}.eyebrow{margin:0 0 7px;color:var(--green);font-size:12px;font-weight:800;text-transform:uppercase;letter-spacing:.13em}
            .heading{display:flex;justify-content:space-between;align-items:flex-end;gap:24px;margin-bottom:28px}.heading h1{margin:0;font-size:clamp(30px,4vw,42px);letter-spacing:-.055em;line-height:1.1}.heading p{margin:10px 0 0;color:var(--muted)}
            .heading-link{white-space:nowrap;color:var(--green-dark);font-weight:700}.notice{padding:13px 16px;border:1px solid #cfe1d0;background:#edf6ed;color:#315f3c;border-radius:13px;margin:0 0 20px}.notice.error{border-color:#f0d4ce;background:#fff1ee;color:#a84f40}
            .catalog-grid{display:grid;grid-template-columns:repeat(3,minmax(0,1fr));gap:18px}.card{overflow:hidden;border:1px solid var(--line);background:#fff;border-radius:18px;box-shadow:0 5px 20px #26372b08}
            .photo{height:220px;position:relative;background:#e5e9df}.photo img{width:100%;height:100%;object-fit:cover;display:block}.category{position:absolute;top:13px;left:13px;padding:6px 10px;background:#fffffff0;border-radius:99px;font-size:11px;font-weight:750;color:var(--green-dark)}
            .card-body{padding:17px 18px 18px}.card-head{display:flex;justify-content:space-between;align-items:start;gap:10px}.card h2{font-size:19px;margin:0;letter-spacing:-.035em}.code{font-size:11px;color:var(--muted);background:#f3f4ef;padding:5px 8px;border-radius:8px;white-space:nowrap}
            .description{color:var(--muted);font-size:13px;min-height:40px;margin:10px 0 14px}.metrics{display:flex;gap:8px;flex-wrap:wrap;margin-bottom:15px}.metric{font-size:11px;font-weight:650;background:#f3f5f0;padding:5px 8px;border-radius:8px;color:#526057}
            .card-footer{display:flex;justify-content:space-between;align-items:center;gap:12px;padding-top:13px;border-top:1px solid #eff0eb}.source{font-size:11px;color:var(--muted)}form{margin:0}.empty{background:white;border:1px dashed #cad2c8;border-radius:18px;padding:40px;text-align:center;color:var(--muted)}
            footer{border-top:1px solid var(--line);padding:22px 0;color:var(--muted);font-size:12px}.footer-inner{display:flex;justify-content:space-between;gap:12px}
            @media(max-width:850px){.catalog-grid{grid-template-columns:repeat(2,minmax(0,1fr))}.heading{align-items:flex-start}}
            @media(max-width:580px){.shell{padding:0 17px}.topbar{padding:13px 0;flex-wrap:wrap;gap:12px}nav{order:3;width:100%;justify-content:space-between;gap:10px;font-size:12px}.topbar>.button{font-size:12px;padding:9px 11px}.catalog-grid{grid-template-columns:1fr}.photo{height:230px}.heading{flex-direction:column}.heading-link{align-self:flex-start}.footer-inner{flex-wrap:wrap}}
            """);
        out.println("</style></head><body><div class=\"shell\">");
        out.println("<header class=\"topbar\"><a class=\"brand\" href=\"" + context + "/index.html\"><span class=\"brand-mark\">🐄</span> Becerrario</a>");
        out.println("<nav aria-label=\"Navegación principal\"><a href=\"" + context + "/listar\">Manada</a><a href=\"" + context + "/visor\">Visor</a><a class=\"active\" href=\"" + context + "/catalogo\">Catálogo</a></nav>");
        out.println("<a class=\"button\" href=\"" + context + "/registrar\">＋ Registrar becerro</a></header><main>");
        out.println("<section class=\"heading\"><div><p class=\"eyebrow\">Biblioteca de la finca</p><h1>Catálogo de becerros</h1><p>Elige una ficha y agrégala a tu manada para empezar el registro.</p></div><a class=\"heading-link\" href=\"" + context + "/listar\">Ver mi manada&nbsp; →</a></section>");
        if (ok != null) {
            out.println("<p class=\"notice\" role=\"status\">✓ Se agregó <strong>" + Html.esc(ok) + "</strong> a la manada. <a href=\"" + context + "/listar\"><strong>Ver registro →</strong></a></p>");
        }
        if (error != null) {
            out.println("<p class=\"notice error\" role=\"alert\">" + Html.esc(error) + "</p>");
        }
        out.println("<section class=\"catalog-grid\" aria-label=\"Becerros disponibles\">");
        if (Catalogo.entradas().isEmpty()) {
            out.println("<p class=\"empty\">No hay imágenes disponibles en el catálogo.</p>");
        }
        for (Catalogo.Entrada e : Catalogo.entradas()) {
            Becerro l = e.becerro();
            boolean agregado = yaAgregado(l.getCodigo());
            out.println("<article class=\"card\"><div class=\"photo\"><img loading=\"lazy\" alt=\"Fotografía de " + Html.esc(l.getTitulo()) + "\" src=\"" + context + "/media/" + Html.esc(l.getArchivo()) + "\"><span class=\"category\">" + Html.esc(l.getCategoria()) + "</span></div><div class=\"card-body\">");
            out.println("<div class=\"card-head\"><h2>" + Html.esc(l.getTitulo()) + "</h2><span class=\"code\">" + Html.esc(l.getCodigo()) + "</span></div>");
            out.println("<div class=\"metrics\"><span class=\"metric\">♥ Apetito " + l.getApetito() + "/10</span><span class=\"metric\">⚖ " + l.getPesoKg() + " kg</span></div>");
            out.println("<p class=\"description\">" + Html.esc(l.getDescripcion()) + "</p>");
            out.println("<div class=\"card-footer\"><span class=\"source\">Fuente: " + Html.esc(e.autor()) + "</span>");
            out.println("<form method=\"post\" action=\"" + context + "/catalogo\"><input type=\"hidden\" name=\"codigo\" value=\"" + Html.esc(l.getCodigo()) + "\">");
            out.println(agregado ? "<button class=\"button\" type=\"submit\" disabled>✓ Ya agregado</button>" : "<button class=\"button\" type=\"submit\">＋ Agregar</button>");
            out.println("</form></div></div></article>");
        }
        out.println("</section></main><footer><div class=\"footer-inner\"><span>🐄 Becerrario · Registro de la finca</span><span>Proyecto final · Estructuras de Datos I</span></div></footer></div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Catalogo.Entrada entrada = Catalogo.buscar(req.getParameter("codigo"));
        if (entrada == null) {
            resp.sendRedirect("catalogo?error=" + codificar("Esa imagen no está en el catálogo."));
            return;
        }
        try {
            Contexto.servicio().agregar(entrada.becerro());
            resp.sendRedirect("catalogo?ok=" + codificar(entrada.becerro().getTitulo()));
        } catch (UnsupportedOperationException | IllegalStateException e) {
            resp.sendRedirect("catalogo?error=" + codificar("Todavía falta implementar una parte: " + e.getMessage()));
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("catalogo?error=" + codificar(e.getMessage()));
        }
    }

    private static boolean yaAgregado(String codigo) {
        try {
            return Contexto.servicio().buscar(codigo) != null;
        } catch (RuntimeException e) {
            return false; // el modelo aún no está listo: se deja el botón activo
        }
    }

    private static String codificar(String texto) {
        return URLEncoder.encode(String.valueOf(texto), StandardCharsets.UTF_8);
    }
}
