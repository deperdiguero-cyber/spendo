# MesaApp — Sin Firebase, APK via GitHub Actions

## ¿Qué necesitás?
- Una cuenta en **GitHub** (gratis): https://github.com
- Nada más instalado en tu PC

---

## PASO 1 — Subir el proyecto a GitHub

### Desde el navegador (sin instalar Git)
1. Ir a https://github.com/new
2. Ponerle nombre (ej: `mesaapp`) → Create repository
3. En la página del repo → click en **"uploading an existing file"**
4. Arrastrar TODOS los archivos de esta carpeta (incluyendo la carpeta `.github`)
5. Click en **"Commit changes"**

---

## PASO 2 — Esperar que compile (automático, ~5 min)

1. Ir a la pestaña **"Actions"** en tu repo
2. Ver el workflow **"Build APK"** corriendo
3. Cuando termine → click en el run → bajar a **"Artifacts"**
4. Descargar **"MesaApp-debug"** → adentro está el APK

> También podés correrlo manualmente: Actions → Build APK → "Run workflow"

---

## PASO 3 — Instalar en el celular

1. Mandarte el APK (WhatsApp, Drive, lo que prefieras)
2. Abrirlo en el celu
3. Si pide permiso → Ajustes → **"Instalar apps de fuentes desconocidas"**
4. Instalar ✅

---

## Cómo usar la app

- Ingresás nombre y ID de mesa (ej: `MESA1`)
- Agregás consumos → se dividen automáticamente
- Tocás "Pagar" cuando cada uno paga
- "Nueva mesa" para empezar de nuevo

> ⚠️ 100% local: los datos se pierden si cerrás la app (es para test)
