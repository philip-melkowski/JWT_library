import { useEffect, useState } from "react";
import WylogujButton from "./WylogujButton";

interface PrzeczytanaKsiazka {
  ksiazkaId: number;
  ksiazkaTytul: string;
  ocena: number;
  autorImie: string;
  autorNazwisko: string;
}

interface UzytkownikPageProps {
  onLogOut: () => void; // do wylogowywania
}

const UzytkownikPage = ({ onLogOut }: UzytkownikPageProps) => {
  const token = sessionStorage.getItem("token");
  const [username, setUsername] = useState("");
  const [blad, setBlad] = useState("");
  const [lista, setLista] = useState<PrzeczytanaKsiazka[]>([]);

  useEffect(() => {
    if (token) {
      const payload = JSON.parse(atob(token.split(".")[1]));
      setUsername(payload.sub || payload.username);
    }
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
      .then(async (ksiazki) => {
        const ksiazkiZAutorami = [];

        for (const k of ksiazki) {
          const responseAutor = await fetch(
            `/api/autorzy/autorKsiazki?id=${k.ksiazkaId}`,
            {
              method: "GET",
              headers: {
                Accept: "application/json",
                Authorization: "Bearer " + token,
              },
            }
          );
          if (!responseAutor.ok) {
            throw new Error(
              "Nie znaleziono autora dla książki o ID " + k.ksiazkaId
            );
          }
          const autor = await responseAutor.json();

          ksiazkiZAutorami.push({
            ksiazkaId: k.ksiazkaId,
            ksiazkaTytul: k.ksiazkaTytul,
            ocena: k.ocena,
            autorImie: autor.imie,
            autorNazwisko: autor.nazwisko,
          });
        }
        setLista(ksiazkiZAutorami);
      })

      .catch((err) => setBlad(err.message));
  }, [token]); // była tez lista - ale wtedy zapletlony fetch, bo za kazdym setLista sie zmienia wiec ponownie fetch

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
                <td>
                  {ksiazka.autorImie} {ksiazka.autorNazwisko}
                </td>
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
