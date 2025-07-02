import React from "react";
import FormLogin from "./components/FormLogin";
import { useState } from "react";
import UzytkownikPage from "./components/UzytkownikPage";
import FormRegister from "./components/FormRegister";
import FormAddBook from "./components/FormAddBook";

import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

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
              token ? (
                <FormAddBook onLogOut={() => setToken(null)}></FormAddBook>
              ) : (
                <Navigate to="/" replace></Navigate>
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
