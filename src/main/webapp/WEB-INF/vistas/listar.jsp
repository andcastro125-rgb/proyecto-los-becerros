<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,java.util.Map,co.umariana.becerros.modelo.Becerro,co.umariana.becerros.web.Html" %>
<%
    List<Becerro> becerros = (List<Becerro>) request.getAttribute("becerros");
    Map<String, Double> promedios = (Map<String, Double>) request.getAttribute("promedios");
    String context = request.getContextPath();
    boolean ranking = "ranking".equals(request.getParameter("orden"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Becerrario · La manada</title>
    <style>
        :root{--ink:#28352d;--muted:#778078;--green:#42654b;--green-dark:#2d4935;--cream:#f7f7f1;--line:#e5e8df;--gold:#d6a64c;--white:#fff}
        *{box-sizing:border-box}body{margin:0;background:var(--cream);color:var(--ink);font:15px/1.55 Inter,ui-sans-serif,system-ui,-apple-system,"Segoe UI",sans-serif}
        a{color:inherit;text-decoration:none}.shell{max-width:1180px;margin:auto;padding:0 28px}.topbar{height:76px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid var(--line)}
        .brand{display:flex;align-items:center;gap:11px;font-weight:750;letter-spacing:-.04em;font-size:19px}.brand-mark{width:38px;height:38px;border-radius:13px;background:#e6eee4;display:grid;place-items:center;font-size:20px}
        nav{display:flex;align-items:center;gap:28px;color:var(--muted);font-size:14px}nav a:hover,nav a.active{color:var(--green-dark)}nav a.active{font-weight:700}
        .button{display:inline-flex;align-items:center;justify-content:center;gap:8px;border:0;border-radius:12px;padding:11px 16px;background:var(--green);color:white;font:inherit;font-weight:700;font-size:14px;cursor:pointer;transition:.18s}.button:hover{background:var(--green-dark);transform:translateY(-1px)}.button.light{background:#edf1ea;color:var(--green-dark)}.button.danger{background:#fff0ed;color:#a84f40;padding:9px 12px}.button.danger:hover{background:#ffe1dc}
        main{padding:42px 0 68px}.eyebrow{margin:0 0 7px;color:var(--green);font-size:12px;font-weight:800;text-transform:uppercase;letter-spacing:.13em}.heading{display:flex;align-items:flex-end;justify-content:space-between;gap:24px;margin-bottom:28px}.heading h1{margin:0;font-size:clamp(30px,4vw,42px);letter-spacing:-.055em;line-height:1.1}.heading p{margin:10px 0 0;color:var(--muted)}.heading-art{width:158px;height:120px;flex:0 0 158px;display:grid;place-items:center;overflow:hidden;border:1px solid var(--line);border-radius:22px;background:linear-gradient(145deg,#e5eee3,#f7f0e5);box-shadow:0 8px 24px #26372b12}.heading-art svg{display:block;width:100%;height:100%}.actions{display:flex;gap:10px;flex-wrap:wrap}
        .stats{display:grid;grid-template-columns:repeat(3,1fr);gap:14px;margin:26px 0}.stat{background:var(--white);border:1px solid var(--line);border-radius:18px;padding:18px 20px}.stat small{display:block;color:var(--muted);font-size:12px;font-weight:650}.stat strong{display:block;font-size:25px;letter-spacing:-.04em;margin-top:3px}.stat .spark{color:var(--gold);font-size:13px}
        .section-title{display:flex;justify-content:space-between;align-items:center;margin:36px 0 15px}.section-title h2{font-size:18px;letter-spacing:-.025em;margin:0}.section-title span{color:var(--muted);font-size:13px}
        .cards{display:grid;grid-template-columns:repeat(3,minmax(0,1fr));gap:17px}.card{overflow:hidden;border:1px solid var(--line);background:#fff;border-radius:18px;box-shadow:0 5px 20px #26372b08}.photo{height:205px;position:relative;background:#e5e9df}.photo img{width:100%;height:100%;object-fit:cover;display:block}.tag{position:absolute;top:13px;left:13px;padding:6px 10px;background:#fffffff0;border-radius:99px;font-size:11px;font-weight:750;color:var(--green-dark)}
        .card-body{padding:17px 18px 18px}.card-head{display:flex;justify-content:space-between;gap:10px;align-items:start}.card h3{font-size:18px;margin:0;letter-spacing:-.035em}.code{font-size:11px;color:var(--muted);background:#f3f4ef;padding:5px 8px;border-radius:8px}.card p{margin:9px 0 14px;color:var(--muted);font-size:13px;min-height:40px}.metrics{display:flex;gap:8px;flex-wrap:wrap;margin-bottom:15px}.metric{font-size:11px;font-weight:650;background:#f3f5f0;padding:5px 8px;border-radius:8px;color:#526057}.card-actions{display:flex;justify-content:space-between;align-items:center;padding-top:13px;border-top:1px solid #eff0eb}.card-actions form{margin:0}
        .empty{padding:52px 24px;text-align:center;background:white;border:1px dashed #cad2c8;border-radius:20px}.empty .icon{font-size:34px}.empty h2{margin:9px 0 5px}.empty p{color:var(--muted);margin:0 0 18px}
        .averages{display:flex;gap:10px;flex-wrap:wrap}.average{background:white;border:1px solid var(--line);border-radius:12px;padding:10px 14px;color:#5c665f;font-size:13px}.average b{color:var(--green-dark);margin-left:5px}
        .inspiration{display:grid;grid-template-columns:repeat(3,1fr);gap:14px}.inspiration figure{margin:0;position:relative;overflow:hidden;height:140px;border-radius:16px;background:#e4e9e2}.inspiration img{width:100%;height:100%;object-fit:cover;display:block;transition:transform .3s}.inspiration figure:hover img{transform:scale(1.04)}.inspiration figcaption{position:absolute;bottom:0;left:0;right:0;padding:26px 14px 11px;color:white;font-weight:700;font-size:13px;background:linear-gradient(transparent,#17221bd9)}
        footer{border-top:1px solid var(--line);padding:22px 0;color:var(--muted);font-size:12px}.footer-inner{display:flex;justify-content:space-between}
        @media(max-width:800px){.cards{grid-template-columns:repeat(2,minmax(0,1fr))}.heading{align-items:flex-start;flex-wrap:wrap}.heading-art{margin-left:auto}.actions{width:100%}.stats{grid-template-columns:repeat(3,1fr)}}@media(max-width:560px){.shell{padding:0 17px}.topbar{height:auto;min-height:70px;flex-wrap:wrap;padding:12px 0;gap:12px}nav{gap:14px;flex-wrap:wrap;font-size:12px}.cards,.inspiration{grid-template-columns:1fr}.photo{height:220px}.heading-art{width:105px;height:88px;flex-basis:105px;border-radius:17px}.stats{gap:8px}.stat{padding:13px}.stat strong{font-size:20px}.stat small{font-size:10px}.actions{width:100%}.actions .button{flex:1}.footer-inner{gap:10px;flex-wrap:wrap}}
    </style>
</head>
<body>
<div class="shell">
    <header class="topbar">
        <a class="brand" href="<%= context %>/index.html"><span class="brand-mark">🐄</span> Becerrario</a>
        <nav aria-label="Navegación principal"><a class="active" href="<%= context %>/listar">Manada</a><a href="<%= context %>/visor">Visor</a><a href="<%= context %>/catalogo">Catálogo</a></nav>
        <a class="button" href="<%= context %>/registrar">＋ Registrar becerro</a>
    </header>
    <main>
        <section class="heading"><div><p class="eyebrow">Panel de la finca</p><h1>La manada</h1><p>Un vistazo claro a cada integrante del Becerrario.</p></div>
            <div class="heading-art" role="img" aria-label="Ilustración de la cara de un becerro">
                <svg viewBox="0 0 200 150" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                    <path d="M48 61C28 43 22 26 23 17c18 3 36 15 48 31M152 61c20-18 26-35 25-44-18 3-36 15-48 31" fill="#eee8d6" stroke="#b9ad93" stroke-width="5" stroke-linejoin="round"/>
                    <path d="M70 38C57 26 55 15 58 8c13 7 22 16 27 29M130 38c13-12 15-23 12-30-13 7-22 16-27 29" fill="#f8f4e8" stroke="#c5baa2" stroke-width="4" stroke-linejoin="round"/>
                    <path d="M44 52Q100 25 156 52L164 92Q159 132 100 139Q41 132 36 92Z" fill="#f5f1e5" stroke="#c9c1ae" stroke-width="4"/>
                    <ellipse cx="72" cy="79" rx="10" ry="14" fill="#fff"/><ellipse cx="128" cy="79" rx="10" ry="14" fill="#fff"/>
                    <ellipse cx="74" cy="81" rx="5" ry="9" fill="#30352f"/><ellipse cx="126" cy="81" rx="5" ry="9" fill="#30352f"/>
                    <circle cx="76" cy="77" r="2.4" fill="#fff"/><circle cx="128" cy="77" r="2.4" fill="#fff"/>
                    <path d="M48 101Q100 85 152 101Q150 129 100 132Q50 129 48 101Z" fill="#e9b7a2" stroke="#d2a08b" stroke-width="3"/>
                    <ellipse cx="78" cy="111" rx="5" ry="7" fill="#59483f"/><ellipse cx="122" cy="111" rx="5" ry="7" fill="#59483f"/>
                    <path d="M89 133q11 8 22 0" fill="none" stroke="#9b7665" stroke-width="3" stroke-linecap="round"/>
                </svg>
            </div>
            <div class="actions"><a class="button light" href="<%= context %>/listar">Todos</a><a class="button light" href="<%= context %>/listar?orden=ranking">✦ Ver ranking</a></div>
        </section>
        <section class="stats" aria-label="Resumen"><div class="stat"><small>Integrantes registrados</small><strong><%= becerros == null ? 0 : becerros.size() %></strong><span class="spark">● Manada actual</span></div>
            <div class="stat"><small>Categorías con promedio</small><strong><%= promedios == null ? 0 : promedios.size() %></strong><span class="spark">● Seguimiento activo</span></div>
            <div class="stat"><small>Vista</small><strong style="font-size:20px"><%= ranking ? "Ranking" : "General" %></strong><span class="spark">● <%= ranking ? "Por apetito" : "Todos los registros" %></span></div></section>
        <div class="section-title"><h2><%= ranking ? "Los de mayor apetito" : "Becerros registrados" %></h2><span><%= becerros == null ? 0 : becerros.size() %> registros</span></div>
        <% if (becerros == null || becerros.isEmpty()) { %>
            <section class="empty"><div class="icon">🌱</div><h2>Tu manada empieza aquí</h2><p>Aún no hay becerros registrados. Agrega uno para verlo en esta lista.</p><a class="button" href="<%= context %>/registrar">＋ Registrar el primero</a></section>
        <% } else { %>
            <section class="cards">
            <% for (Becerro b : becerros) { String archivo = b.getArchivo() == null ? "" : b.getArchivo().replace("\\", "").replace("..", "").replace("\"", "").replace("'", ""); %>
                <article class="card"><div class="photo"><img loading="lazy" src="<%= context %>/media/<%= Html.esc(archivo) %>" alt="Fotografía de <%= Html.esc(b.getTitulo()) %>"><span class="tag"><%= Html.esc(b.getCategoria()) %></span></div>
                    <div class="card-body"><div class="card-head"><h3><%= Html.esc(b.getTitulo()) %></h3><span class="code"><%= Html.esc(b.getCodigo()) %></span></div>
                        <p><%= Html.esc(b.getDescripcion()) %></p><div class="metrics"><span class="metric">♥ Apetito <%= b.getApetito() %>/10</span><span class="metric">⚖ <%= b.getPesoKg() %> kg</span></div>
                        <div class="card-actions"><a class="button light" href="<%= context %>/visor">Ver en visor&nbsp; →</a><form method="post" action="<%= context %>/eliminar" onsubmit="return confirm('¿Eliminar este becerro de la manada?');"><input type="hidden" name="codigo" value="<%= Html.esc(b.getCodigo()) %>"><button class="button danger" type="submit">Eliminar</button></form></div>
                    </div></article>
            <% } %>
            </section>
        <% } %>
        <section><div class="section-title"><h2>Promedio de apetito por categoría</h2><span>Escala de 1 a 10</span></div>
            <% if (promedios == null || promedios.isEmpty()) { %><p class="average">Los promedios aparecerán cuando haya registros.</p><% } else { %><div class="averages"><% for (Map.Entry<String, Double> entry : promedios.entrySet()) { %><span class="average"><%= Html.esc(entry.getKey()) %><b><%= String.format(java.util.Locale.forLanguageTag("es-CO"), "%.1f", entry.getValue()) %>/10</b></span><% } %></div><% } %>
        </section>
        <section><div class="section-title"><h2>Momentos de la finca</h2><span>Galería · 3 fotografías</span></div>
            <div class="inspiration">
                <figure><img loading="lazy" src="https://images.unsplash.com/photo-1488727625812-16758df25132?auto=format&amp;fit=crop&amp;fm=jpg&amp;q=80&amp;w=700" alt="Becerro descansando en la hierba"><figcaption>Un día tranquilo en el campo</figcaption></figure>
                <figure><img loading="lazy" src="https://images.unsplash.com/photo-1524024973431-2ad916746881?auto=format&amp;fit=crop&amp;w=700&amp;q=80" alt="Vacas en un paisaje rural"><figcaption>La finca, nuestro hogar</figcaption></figure>
                <figure><img loading="lazy" src="https://images.unsplash.com/photo-1500595046743-cd271d694d30?auto=format&amp;fit=crop&amp;w=700&amp;q=80" alt="Paisaje de una finca ganadera"><figcaption>Campo abierto para crecer</figcaption></figure>
            </div>
        </section>
    </main>
    <footer><div class="footer-inner"><span>🐄 Becerrario · Registro de la finca</span><span>Proyecto final · Estructuras de Datos I</span></div></footer>
</div>
</body>
</html>
