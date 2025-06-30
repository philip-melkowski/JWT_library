import { useEffect, useState } from "react";
import WylogujButton from "./WylogujButton";

interface PrzeczytanaKsiazka {
  uzytkownikId: number;
  uzytkownikUsername: string;
  ksiazkaId: number;
  ksiazkaTytul: string;
  ocena: number;
}

interface UzytkownikPageProps {
  onLogOut: () => void; // do wylogowywania
}

const UzytkownikPage = ({ onLogOut }: UzytkownikPageProps) => {
  const token = sessionStorage.getItem("token");
  let username = "";
  const [blad, setBlad] = useState("");
  const [lista, setLista] = useState<PrzeczytanaKsiazka[]>([]);
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    username = payload.sub || payload.username;
  }
  useEffect(() => {
    if (!token) {
      setBlad("brak tokenu");
      return;
    }
    fetch("/api/przeczytane/moje", {
      method: "GET",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if (response.ok) {
          setBlad("");
          return response.json();
        } else if (response.status === 401) {
          throw new Error("brak tokenu lub token niewazny");
        } else if (response.status === 403) {
          throw new Error("brak uprawnien");
        } else {
          setBlad("jakis blad");
        }
      })
      .then(setLista)
      .catch((err) => setBlad(err.message));
  }, [token]);

  return (
    <>
      <WylogujButton onLogOut={onLogOut}></WylogujButton>
      <div style={{ padding: "20px", color: "white" }}>
        <h1>Witaj, {username}</h1>
      </div>
      {blad && <p style={{ color: "red" }}>{blad}</p>}
      {lista.length > 0 && (
        <table
          style={{
            color: "white",
            backgroundColor: "#222",
            border: "1px solid white",
          }}
        >
          <thead>
            <tr>
              <th>ID</th>
              <th>Tytuł</th>
              <th>Autor</th>
              <th>Ocena</th>
            </tr>
          </thead>
          <tbody>
            {lista.map((ksiazka) => (
              <tr key={ksiazka.ksiazkaId}>
                <td>{ksiazka.ksiazkaId}</td>
                <td>{ksiazka.ksiazkaTytul}</td>
                <td>{ksiazka.uzytkownikUsername}</td>
                <td>{ksiazka.ocena}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      {lista.length === 0 && (
        <p style={{ color: "white" }}>Brak przeczytanych książek</p>
      )}
    </>
  );
};

export default UzytkownikPage;
