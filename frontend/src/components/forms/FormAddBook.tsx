import React, { useEffect, useState } from "react";
import LogOutAndUserPageSegment from "../LogOutAndUserPageSegment";

interface FormAddBookProps {
  onLogOut: () => void;
}

interface Autor {
  id: number;
  imie: string;
  nazwisko: string;
}

const FormAddBook = ({ onLogOut }: FormAddBookProps) => {
  const token = sessionStorage.getItem("token");
  const [autorzy, setAutorzy] = useState<Autor[]>([]);
  const [filtr, setFiltr] = useState("");

  const [autorId, setAutorId] = useState("");
  const [tytul, setTytul] = useState("");
  const [blad, setBlad] = useState("");

  useEffect(() => {
    const fetchAutorzy = async () => {
      try {
        const response = await fetch("/api/autorzy", {
          method: "GET",
          headers: {
            Authorization: "Bearer " + token,
            Accept: "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("nie udalo sie zaladowac autora " + response.status);
        } else {
          setBlad("");
        }
        const data = await response.json();
        setAutorzy(data);
      } catch (e: any) {
        setBlad(e.message);
      }
    };
    fetchAutorzy();
  }, [token]);

  const filteredAutorzy = autorzy
    .filter(
      (a) =>
        a.nazwisko.toLowerCase().includes(filtr.toLowerCase()) ||
        a.imie.toLowerCase().includes(filtr.toLowerCase())
    )
    .map((a) => (
      <tr key={a.id}>
        <td>{a.id}</td>
        <td>
          {a.imie} {a.nazwisko}
        </td>
      </tr>
    ));

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch("/api/ksiazki", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + token,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          tytul: tytul,
          autor: {
            id: Number(autorId),
          },
        }),
      });
      if (!response.ok) {
        throw new Error("nie udalo sie dodac ksiazki" + response.status);
      } else {
        setBlad("");
        setTytul("");
        setAutorId("");
      }
    } catch (e: any) {
      setBlad(e.message);
    }
  };

  return (
    <>
      <LogOutAndUserPageSegment onLogOut={onLogOut}></LogOutAndUserPageSegment>
      <form onSubmit={handleSubmit}>
        <div style={{ color: "white" }}>Dodaj ksiązkę</div>
        <p></p>
        <input
          type="text"
          placeholder="ID autora"
          value={autorId}
          onChange={(e) => setAutorId(e.target.value)}
        ></input>
        <input
          placeholder="Tytuł"
          value={tytul}
          type="text"
          onChange={(e) => setTytul(e.target.value)}
        ></input>
        <button type="submit">Dodaj ksiązkę do bazy</button>
      </form>
      {blad && <p style={{ color: "red" }}>{blad}</p>}
      <input
        value={filtr}
        onChange={(e) => setFiltr(e.target.value)}
        placeholder="Znajdź ID autora"
      ></input>
      <table style={{ color: "white" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Autor</th>
          </tr>
        </thead>
        <tbody>{filteredAutorzy}</tbody>
      </table>
    </>
  );
};

export default FormAddBook;
