import React, { useState } from "react";

interface FormLoginProps {
  onLogin: () => void;
}

const FormLogin = ({ onLogin }: FormLoginProps) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [blad, setBlad] = useState("");

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
      } else {
        setBlad("niepoprawny login lub haslo!");
      }
    } catch {
      setBlad("blad polaczenia z serwerem.");
    }
  };
  return (
    <form onSubmit={handleSubmit}>
      <input
        placeholder="login"
        value={username}
        onChange={(u) => setUsername(u.target.value)}
      ></input>
      <input
        placeholder="Hasło"
        value={password}
        onChange={(p) => setPassword(p.target.value)}
      ></input>
      <button type="submit">Zaloguj</button>
      {blad && <p style={{ color: "red" }}>{blad}</p>}
    </form>
  );
};

export default FormLogin;
