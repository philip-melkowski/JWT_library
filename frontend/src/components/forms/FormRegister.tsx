import { useState } from "react";
import "../../styles/form.scss";
import { useNavigate } from "react-router-dom";

const FormRegister = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [blad, setBlad] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch("/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      if (response.ok) {
        setBlad("");
        navigate("/");
      }
      if (response.status === 409) {
        setBlad("nazwa uzytkownika jest juz zajeta");
      } else {
        setBlad("niepoprawny login lub haslo");
      }
    } catch {
      setBlad("blad polaczenia z serwerem");
    }
  };

  return (
    <>
      <form className="form" autoComplete="off" onSubmit={handleSubmit}>
        <div className="control">
          <h1>Rejestracja</h1>
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
            <div className="text">Zarejestruj konto</div>
          </button>
        </div>
        {blad && <p style={{ color: "red" }}>{blad}</p>}
      </form>
    </>
  );
};

export default FormRegister;
