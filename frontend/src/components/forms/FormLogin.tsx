import React, { useState } from "react";
import "../../styles/form.scss";
import { useNavigate } from "react-router-dom";

interface FormLoginProps {
  onLogin: () => void;
}

const FormLogin = ({ onLogin }: FormLoginProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [blad, setBlad] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault(); // nie przeładowuj strony po wysłaniu formularza
    try {
      const reponse = await fetch("api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      if (reponse.ok) {
        const token = await reponse.text();
        sessionStorage.setItem("token", token);
        onLogin();
        navigate("/uzytkownik");
      } else {
        setBlad("niepoprawny login lub haslo!");
      }
    } catch {
      setBlad("blad polaczenia z serwerem.");
    }
  };
  return (
    <>
      <form className="form" autoComplete="off" onSubmit={handleSubmit}>
        <div className="control">
          <h1>Logowanie</h1>
        </div>

        <div className="control block-cube block-input">
          <input
            type="text"
            name="username"
            placeholder="login"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <div className="bg-top">
            <div className="bg-inner" />
          </div>
          <div className="bg-right">
            <div className="bg-inner" />
          </div>
          <div className="bg">
            <div className="bg-inner" />
          </div>
        </div>

        <div className="control block-cube block-input">
          <input
            type="password"
            name="password"
            placeholder="hasło"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <div className="bg-top">
            <div className="bg-inner" />
          </div>
          <div className="bg-right">
            <div className="bg-inner" />
          </div>
          <div className="bg">
            <div className="bg-inner" />
          </div>
        </div>

        <div className="control">
          <button type="submit" className="btn block-cube block-cube-hover">
            <div className="bg-top">
              <div className="bg-inner" />
            </div>
            <div className="bg-right">
              <div className="bg-inner" />
            </div>
            <div className="bg">
              <div className="bg-inner" />
            </div>
            <div className="text">Zaloguj</div>
          </button>
        </div>
        <div className="control">
          <button
            type="button"
            onClick={() => navigate("/register")}
            className="btn block-cube block-cube-hover"
          >
            <div className="bg-top">
              <div className="bg-inner" />
            </div>
            <div className="bg-right">
              <div className="bg-inner" />
            </div>
            <div className="bg">
              <div className="bg-inner" />
            </div>
            <div className="text">Zarejestruj</div>
          </button>
        </div>

        <div className="credits">
          <a
            href="https://codepen.io/marko-zub/"
            target="_blank"
            rel="noreferrer"
          >
            My other codepens
          </a>
        </div>

        {blad && <p style={{ color: "red" }}>{blad}</p>}
      </form>
    </>
  );
};

export default FormLogin;
