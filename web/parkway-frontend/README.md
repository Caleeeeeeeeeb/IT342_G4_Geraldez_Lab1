# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.

---

## Connecting to the backend 🔧

This project is configured for local development with the backend running on `http://localhost:8080`.

- The Vite dev server proxies requests starting with `/api` to the backend (see `vite.config.js`).
- The backend should allow CORS from `http://localhost:5173` (configured in `AuthController`).

Quick start:

1. Start the backend (Spring Boot) from the `backend/parkway-backend` folder (e.g. `mvn spring-boot:run` or run from your IDE).
2. Start the frontend from `web/parkway-frontend` with `npm install` (first time) and `npm run dev`.
3. Open `http://localhost:5173` and use the Register / Login form to call `/api/auth/register` and `/api/auth/login`.

If you see "Error connecting to backend", make sure the backend is running on port 8080 and not blocked by firewall.
