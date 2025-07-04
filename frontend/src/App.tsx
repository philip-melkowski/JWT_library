import React from "react";
import FormLogin from "./components/forms/FormLogin";
import { useState } from "react";
import UzytkownikPage from "./components/UzytkownikPage";
import FormRegister from "./components/forms/FormRegister";
import FormAddBook from "./components/forms/FormAddBook";

import { jwtDecode } from "jwt-decode";

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import FormAddAutor from "./components/forms/FormAddAutor";
import RateBook from "./components/RateBook";

function isRole(token: string | null, rola: string): boolean {
  if (!token) {
    return false;
  }
  try {
    const decoded: any = jwtDecode(token);
    const roles = decoded?.roles || [];
    return roles.includes(rola);
  } catch {
    return false;
  }
}

function App() {
  const [token, setToken] = useState<string | null>(
    sessionStorage.getItem("token")
  );

  return (
    <>
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              token ? (
                <Navigate to="/uzytkownik" replace /> // jesli ma uzyt. token to przekieruj na /uzyt
              ) : (
                <FormLogin
                  onLogin={() => setToken(sessionStorage.getItem("token"))}
                ></FormLogin>
              ) // jesli nie ma tokenu to probuj sie logowac
            }
          />

          <Route
            path="/uzytkownik"
            element={
              token ? (
                <UzytkownikPage
                  onLogOut={() => setToken(null)}
                ></UzytkownikPage>
              ) : (
                <Navigate to="/" replace></Navigate>
              )
            }
          ></Route>
          <Route
            path="/register"
            element={
              !token ? (
                <FormRegister></FormRegister>
              ) : (
                <Navigate to="/" replace></Navigate>
              )
            }
          ></Route>
          <Route
            path="/dodaj-ksiazke"
            element={
              token && isRole(token, "ROLE_ADMIN") ? (
                <FormAddBook onLogOut={() => setToken(null)}></FormAddBook>
              ) : (
                <Navigate to="/" replace></Navigate>
              )
            }
          ></Route>
          <Route
            path="/dodaj-autora"
            element={
              token && isRole(token, "ROLE_ADMIN") ? (
                <FormAddAutor onLogOut={() => setToken(null)}></FormAddAutor>
              ) : (
                <Navigate to="/"></Navigate>
              )
            }
          ></Route>
          <Route
            path="/dodaj-ocene"
            element={
              token ? (
                <RateBook onLogOut={() => setToken(null)}></RateBook>
              ) : (
                <Navigate to="/"></Navigate>
              )
            }
          ></Route>
          <Route
            path="*"
            element={<Navigate to="/" replace></Navigate>}
          ></Route>
        </Routes>
      </Router>
    </>
  );
}

export default App;
