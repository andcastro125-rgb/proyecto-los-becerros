<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="co.umariana.becerros.web.Html" %>
<%
    String context = request.getContextPath();
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar becerro · Becerrario</title>
    <style>
        :root{--ink:#28352d;--muted:#778078;--green:#42654b;--green-dark:#2d4935;--cream:#f7f7f1;--line:#e5e8df;--white:#fff;--red:#a84f40}
        *{box-sizing:border-box}body{margin:0;background:var(--cream);color:var(--ink);font:15px/1.55 Inter,ui-sans-serif,system-ui,-apple-system,"Segoe UI",sans-serif}a{color:inherit;text-decoration:none}.shell{max-width:1120px;margin:auto;padding:0 28px}
        .topbar{height:76px;display:flex;align-items:center;justify-content:space-between;border-bottom:1px solid var(--line)}.brand{display:flex;align-items:center;gap:11px;font-weight:750;letter-spacing:-.04em;font-size:19px}.brand-mark{width:38px;height:38px;border-radius:13px;background:#e6eee4;display:grid;place-items:center;font-size:20px}nav{display:flex;gap:26px;color:var(--muted);font-size:14px}nav a:hover{color:var(--green-dark)}
        main{padding:45px 0 72px}.back{font-size:13px;color:var(--muted);display:inline-block;margin-bottom:24px}.back:hover{color:var(--green)}.layout{display:grid;grid-template-columns:minmax(0,1.2fr) minmax(280px,.8fr);gap:44px;align-items:start}.eyebrow{color:var(--green);font-size:12px;font-weight:800;text-transform:uppercase;letter-spacing:.13em;margin:0 0 7px}.intro h1{font-size:clamp(32px,4vw,43px);line-height:1.1;letter-spacing:-.055em;margin:0}.intro>p:last-child{color:var(--muted);margin:12px 0 26px}
        .form-card{background:white;border:1px solid var(--line);border-radius:20px;padding:25px;box-shadow:0 8px 28px #26372b08}.form-grid{display:grid;grid-template-columns:1fr 1fr;gap:18px}.field{display:flex;flex-direction:column;gap:7px}.field.full{grid-column:1/-1}label{font-size:13px;font-weight:700;color:#46534a}input,select,textarea{width:100%;border:1px solid #dfe4dc;border-radius:11px;padding:12px 13px;background:#fff;color:var(--ink);font:inherit;font-size:14px;outline:none;transition:.15s}input:focus,select:focus,textarea:focus{border-color:#78957e;box-shadow:0 0 0 3px #42654b19}textarea{min-height:112px;resize:vertical}input::placeholder,textarea::placeholder{color:#a1aaa2}.hint{color:var(--muted);font-size:11px}.error{margin:0 0 18px;padding:12px 14px;border-radius:11px;background:#fff1ee;border:1px solid #f2d3cd;color:var(--red);font-size:13px}.actions{display:flex;justify-content:space-between;align-items:center;gap:12px;margin-top:22px;padding-top:19px;border-top:1px solid #eff0eb}.button{display:inline-flex;align-items:center;justify-content:center;gap:8px;border:0;border-radius:12px;padding:12px 17px;background:var(--green);color:white;font-weight:750;font-size:14px;cursor:pointer;transition:.18s}.button:hover{background:var(--green-dark);transform:translateY(-1px)}.button.light{background:#edf1ea;color:var(--green-dark)}
        .aside{background:#eaf0e7;border-radius:21px;padding:25px;position:sticky;top:24px}.aside .illustration{height:170px;display:grid;place-items:center;font-size:84px;background:linear-gradient(145deg,#dfeadb,#f7f5e9);border-radius:16px;margin-bottom:22px}.aside h2{font-size:19px;letter-spacing:-.03em;margin:0 0 8px}.aside p{font-size:13px;color:#66756a;margin:0}.tips{list-style:none;margin:19px 0 0;padding:0;display:grid;gap:11px}.tips li{font-size:12px;color:#526057;display:flex;gap:9px}.tips b{color:var(--green)}footer{border-top:1px solid var(--line);padding:22px 0;color:var(--muted);font-size:12px}
        @media(max-width:760px){.layout{grid-template-columns:1fr;gap:22px}.aside{position:static}.aside .illustration{height:120px}.shell{padding:0 18px}}@media(max-width:520px){.topbar{height:auto;min-height:70px;flex-wrap:wrap;padding:12px 0;gap:10px}nav{gap:15px;font-size:12px}.form-card{padding:18px}.form-grid{grid-template-columns:1fr}.field.full{grid-column:auto}.actions{align-items:stretch;flex-direction:column-reverse}.actions .button{text-align:center}}
    </style>
</head>
<body><div class="shell">
    <header class="topbar"><a class="brand" href="<%= context %>/index.html"><span class="brand-mark">🐄</span> Becerrario</a><nav aria-label="Navegación principal"><a href="<%= context %>/listar">Manada</a><a href="<%= context %>/visor">Visor</a><a href="<%= context %>/catalogo">Catálogo</a></nav></header>
    <main><a class="back" href="<%= context %>/listar">← Volver a la manada</a>
        <div class="layout"><section><div class="intro"><p class="eyebrow">Nuevo integrante</p><h1>Registrar becerro</h1><p>Completa su ficha para sumarlo al registro de la finca.</p></div>
            <div class="form-card">
                <% if (error != null && !error.isBlank()) { %><div class="error" role="alert"><strong>No pudimos guardar el registro.</strong><br><%= Html.esc(error) %></div><% } %>
                <form method="post" action="<%= context %>/registrar" autocomplete="on">
                    <div class="form-grid">
                        <div class="field"><label for="codigo">Código</label><input id="codigo" name="codigo" type="text" maxlength="40" placeholder="Ej. BEC-004" required><span class="hint">Identificador único del registro.</span></div>
                        <div class="field"><label for="titulo">Nombre</label><input id="titulo" name="titulo" type="text" maxlength="80" placeholder="Ej. Lucero" required></div>
                        <div class="field"><label for="categoria">Categoría</label><select id="categoria" name="categoria" required><option value="" disabled selected>Selecciona una categoría</option><option value="Cría">Cría</option><option value="Levante">Levante</option><option value="Ceba">Ceba</option><option value="Otro">Otro</option></select></div>
                        <div class="field"><label for="archivo">Archivo de imagen</label><input id="archivo" name="archivo" type="text" maxlength="160" placeholder="Ej. lucero.jpg" required><span class="hint">Nombre del archivo ubicado en la carpeta media/.</span></div>
                        <div class="field"><label for="apetito">Nivel de apetito <span class="hint">(1 a 10)</span></label><input id="apetito" name="apetito" type="number" min="1" max="10" step="1" placeholder="7" required></div>
                        <div class="field"><label for="peso">Peso <span class="hint">(kg)</span></label><input id="peso" name="peso" type="number" min="0.1" step="0.1" placeholder="85.5" required></div>
                        <div class="field full"><label for="descripcion">Descripción</label><textarea id="descripcion" name="descripcion" maxlength="500" placeholder="Cuéntanos algo sobre este becerro…"></textarea><span class="hint">Puedes dejar este campo vacío.</span></div>
                    </div>
                    <div class="actions"><a class="button light" href="<%= context %>/listar">Cancelar</a><button class="button" type="submit">Guardar en la manada&nbsp; →</button></div>
                </form>
            </div>
        </section>
        <aside class="aside"><div class="illustration" aria-hidden="true">🐮</div><h2>Una ficha completa ayuda a cuidar mejor</h2><p>Registra los datos principales para llevar el seguimiento de cada integrante de la finca.</p><ul class="tips"><li><b>01</b> Usa un código diferente para cada becerro.</li><li><b>02</b> El apetito se registra en una escala de 1 a 10.</li><li><b>03</b> Escribe el nombre de una imagen que ya exista en <code>media/</code>.</li></ul></aside>
        </div>
    </main><footer>🐄 Becerrario · Registro de la finca</footer>
</div></body></html>
