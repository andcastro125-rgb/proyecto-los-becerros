<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.umariana.becerros.modelo.Becerro,co.umariana.becerros.web.Html" %>
<%
    Becerro becerro = (Becerro) request.getAttribute("becerro");
    Object totalObj = request.getAttribute("total");
    int total = totalObj instanceof Number ? ((Number) totalObj).intValue() : 0;
    String context = request.getContextPath();
    String archivo = becerro == null || becerro.getArchivo() == null ? "" : becerro.getArchivo().replace("\\", "").replace("..", "").replace("\"", "").replace("'", "");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Visor de la manada · Becerrario</title>
    <style>
        :root{--ink:#28352d;--muted:#778078;--green:#42654b;--green-dark:#2d4935;--cream:#f7f7f1;--line:#e5e8df;--white:#fff;--gold:#d6a64c}
        *{box-sizing:border-box}body{margin:0;background:var(--cream);color:var(--ink);font:15px/1.55 Inter,ui-sans-serif,system-ui,-apple-system,"Segoe UI",sans-serif}a{color:inherit;text-decoration:none}.shell{max-width:1120px;margin:auto;padding:0 28px}.topbar{height:76px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid var(--line)}.brand{display:flex;align-items:center;gap:11px;font-weight:750;letter-spacing:-.04em;font-size:19px}.brand-mark{width:38px;height:38px;border-radius:13px;background:#e6eee4;display:grid;place-items:center;font-size:20px}nav{display:flex;gap:26px;color:var(--muted);font-size:14px}nav a:hover,nav a.active{color:var(--green-dark)}nav a.active{font-weight:700}
        main{padding:42px 0 70px}.eyebrow{color:var(--green);font-size:12px;font-weight:800;text-transform:uppercase;letter-spacing:.13em;margin:0 0 7px}.heading{display:flex;justify-content:space-between;align-items:flex-end;margin-bottom:24px;gap:20px}.heading h1{font-size:clamp(30px,4vw,42px);letter-spacing:-.055em;line-height:1.1;margin:0}.heading p:last-child{color:var(--muted);margin:9px 0 0}.counter{border:1px solid var(--line);border-radius:99px;padding:8px 13px;background:white;font-size:12px;color:var(--muted);white-space:nowrap}.counter strong{color:var(--green-dark)}
        .viewer{display:grid;grid-template-columns:minmax(0,1.3fr) minmax(280px,.7fr);overflow:hidden;border-radius:22px;background:white;border:1px solid var(--line);box-shadow:0 10px 34px #26372b0b;min-height:430px}.image-pane{position:relative;min-height:430px;background:#e4e9e2}.image-pane img{position:absolute;width:100%;height:100%;inset:0;object-fit:cover;display:block}.photo-label{position:absolute;top:18px;left:18px;padding:7px 12px;border-radius:99px;background:#ffffffed;color:var(--green-dark);font-weight:750;font-size:12px}.info{padding:clamp(25px,4vw,43px);display:flex;flex-direction:column;justify-content:center}.info .code{color:var(--muted);font-size:12px;font-weight:700;letter-spacing:.1em;text-transform:uppercase}.info h2{font-size:clamp(28px,3vw,36px);line-height:1.1;letter-spacing:-.05em;margin:10px 0 13px}.info .description{color:#68736b;margin:0 0 22px;font-size:14px}.facts{display:grid;grid-template-columns:1fr 1fr;gap:10px}.fact{padding:13px;background:#f5f6f2;border-radius:13px}.fact small{display:block;color:var(--muted);font-size:11px}.fact strong{display:block;font-size:16px;margin-top:3px}.navigation{display:flex;justify-content:space-between;align-items:center;margin-top:24px;gap:14px}.button{display:inline-flex;align-items:center;justify-content:center;gap:8px;border:1px solid var(--line);border-radius:12px;padding:12px 17px;background:white;color:var(--green-dark);font-weight:750;font-size:14px;transition:.18s}.button:hover{border-color:#b8c6b9;background:#f7faf6;transform:translateY(-1px)}.button.primary{background:var(--green);border-color:var(--green);color:white}.button.primary:hover{background:var(--green-dark)}.position{color:var(--muted);font-size:12px;text-align:center}.empty{min-height:340px;display:grid;place-items:center;text-align:center;padding:35px;background:white;border:1px dashed #cad2c8;border-radius:22px}.empty .icon{font-size:48px}.empty h2{margin:12px 0 4px;font-size:24px;letter-spacing:-.04em}.empty p{color:var(--muted);margin:0 0 18px}footer{border-top:1px solid var(--line);padding:22px 0;color:var(--muted);font-size:12px}
        @media(max-width:760px){.viewer{grid-template-columns:1fr}.image-pane{min-height:340px}.info{padding:25px}.heading{align-items:flex-start;flex-direction:column}}@media(max-width:540px){.shell{padding:0 17px}.topbar{height:auto;min-height:70px;flex-wrap:wrap;padding:12px 0;gap:10px}nav{gap:15px;font-size:12px}.image-pane{min-height:260px}.navigation{display:grid;grid-template-columns:1fr 1fr}.position{grid-column:1/-1;grid-row:1}.button{padding:11px 9px;font-size:13px}}
    </style>
</head>
<body><div class="shell">
    <header class="topbar"><a class="brand" href="<%= context %>/index.html"><span class="brand-mark">🐄</span> Becerrario</a><nav aria-label="Navegación principal"><a href="<%= context %>/listar">Manada</a><a class="active" href="<%= context %>/visor">Visor</a><a href="<%= context %>/catalogo">Catálogo</a></nav><a href="<%= context %>/registrar">＋ Registrar</a></header>
    <main><section class="heading"><div><p class="eyebrow">Recorrido de la manada</p><h1>Visor de becerros</h1><p>Conoce a cada integrante, uno a la vez.</p></div><span class="counter"><strong><%= total %></strong> <%= total == 1 ? "integrante" : "integrantes" %></span></section>
        <% if (becerro == null) { %>
            <section class="empty"><div><div class="icon">🌾</div><h2>La manada está esperando</h2><p>Cuando registres un becerro, podrás recorrerlo desde aquí.</p><a class="button primary" href="<%= context %>/registrar">＋ Registrar becerro</a></div></section>
        <% } else { %>
            <article class="viewer"><div class="image-pane"><img src="<%= context %>/media/<%= Html.esc(archivo) %>" alt="Fotografía de <%= Html.esc(becerro.getTitulo()) %>"><span class="photo-label"><%= Html.esc(becerro.getCategoria()) %></span></div>
                <div class="info"><span class="code">Registro · <%= Html.esc(becerro.getCodigo()) %></span><h2><%= Html.esc(becerro.getTitulo()) %></h2><p class="description"><%= Html.esc(becerro.getDescripcion()) %></p>
                    <div class="facts"><div class="fact"><small>Nivel de apetito</small><strong>♥ &nbsp;<%= becerro.getApetito() %> / 10</strong></div><div class="fact"><small>Peso registrado</small><strong>⚖ &nbsp;<%= becerro.getPesoKg() %> kg</strong></div></div>
                    <div class="navigation"><a class="button" href="<%= context %>/visor?accion=anterior">←&nbsp; Anterior</a><span class="position">Usa las flechas para recorrer</span><a class="button primary" href="<%= context %>/visor?accion=siguiente">Siguiente&nbsp; →</a></div>
                </div></article>
        <% } %>
    </main><footer>🐄 Becerrario · Registro de la finca</footer>
</div></body></html>
