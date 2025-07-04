import { useEffect, useState } from "react";
import WylogujButton from "./buttons/WylogujButton";
import AdminLinks from "./AdminSegment";
import { useNavigate } from "react-router-dom";

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
  const [refreshTrigger, setRefreshTrigger] = useState(0);
  const [filtrAutor, setFiltrAutor] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      const payload = JSON.parse(atob(token.split(".")[1]));
      console.log(payload.roles);
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
  }, [token, refreshTrigger]); // była tez lista - ale wtedy zapletlony fetch, bo za kazdym setLista sie zmienia wiec ponownie fetch

  return (
    <>
      <div style={{ display: "flex", gap: "10px" }}>
        <WylogujButton onLogOut={onLogOut}></WylogujButton>
        <button
          className="btn btn-info"
          onClick={() => {
            navigate("/dodaj-ocene");
          }}
        >
          Oceń przeczytaną ksiązkę
        </button>
      </div>
      <AdminLinks></AdminLinks>
      <div style={{ padding: "20px", color: "white" }}>
        <h1>Witaj, {username}</h1>
      </div>
      {blad && <p style={{ color: "red" }}>{blad}</p>}
      <input
        value={filtrAutor}
        onChange={(e) => {
          setFiltrAutor(e.target.value);
        }}
        type="text"
        placeholder="Filtruj po autorze"
      ></input>
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
              <th style={{ textAlign: "center" }}>Zmień</th>
            </tr>
          </thead>
          <tbody>
            {lista
              .filter((k) => {
                return (k.autorImie + " " + k.autorNazwisko)
                  .toLowerCase()
                  .includes(filtrAutor.trim().toLowerCase());
              }) // nie ma tu potrzeby renderowania za kazdym razem, bo zmiana stanu filtrAutor zapewnia renderowanie
              .map((ksiazka) => (
                <tr key={ksiazka.ksiazkaId}>
                  <td>{ksiazka.ksiazkaId}</td>
                  <td>{ksiazka.ksiazkaTytul}</td>
                  <td>
                    {ksiazka.autorImie} {ksiazka.autorNazwisko}
                  </td>
                  <td>{ksiazka.ocena}</td>
                  <td>
                    <button
                      className="btn btn-light"
                      onClick={async () => {
                        try {
                          const nowa = prompt("Podaj nowa ocenę 1-5: ");
                          if (nowa != null) {
                            const nowaNum = parseInt(nowa);
                            if (
                              nowaNum != null &&
                              !isNaN(nowaNum) &&
                              nowaNum > 0 &&
                              nowaNum < 6
                            ) {
                              const response = await fetch(
                                "/api/uzytkownicy/getUserId",
                                {
                                  method: "GET",
                                  headers: {
                                    Authorization: "Bearer " + token,
                                  },
                                }
                              );
                              if (!response.ok) {
                                throw new Error(
                                  "blad przy getUserId" + response.status
                                );
                              }
                              const userId = await response.text();

                              const response2 = await fetch(
                                "/api/przeczytane",
                                {
                                  method: "PUT",
                                  headers: {
                                    Authorization: "Bearer " + token,
                                    "Content-Type": "application/json",
                                  },
                                  body: JSON.stringify({
                                    uzytkownikId: userId,
                                    ksiazkaId: ksiazka.ksiazkaId,
                                    ocena: nowaNum,
                                  }),
                                }
                              );
                              if (!response2.ok) {
                                throw new Error(
                                  "nie udalo sie zmienic oceny " +
                                    response2.status
                                );
                              }
                              setRefreshTrigger((prev) => prev + 1);
                            }
                          }
                        } catch (e: any) {
                          setBlad(e.message);
                        }
                      }}
                    >
                      Zmień
                    </button>
                  </td>
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
