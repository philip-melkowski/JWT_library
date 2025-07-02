import React, { useEffect, useState } from "react";

interface FormAddAutorProps {
  onLogOut: () => void;
}

const FormAddAutor = ({ onLogOut }: FormAddAutorProps) => {
  const token = sessionStorage.getItem("token");

  const [imie, setImie] = useState("");
  const [nazwisko, setNazwisko] = useState("");
  const [blad, setBlad] = useState("");

  const addAuthor = async () => {
    try {
      const response = await fetch("/api/autorzy", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + token,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          imie: imie,
          nazwisko: nazwisko,
        }),
      });
      if (!response.ok) {
        throw new Error(
          "nie udalo sie dodac autora. kod bledu: " + response.status
        );
      } else {
        setBlad("");
        setImie("");
        setNazwisko("");
      }
    } catch (e: any) {
      setBlad(e.message);
    }
  };

  return (
    <>
      <h1>Dodaj Autora</h1>
      <form onSubmit={addAuthor}>
        <input
          placeholder="Imie"
          value={imie}
          onChange={(e) => setImie(e.target.value)}
        ></input>
        <input
          value={nazwisko}
          placeholder="Nazwisko"
          onChange={(e) => setNazwisko(e.target.value)}
        ></input>
        <button type="submit">Dodaj autora</button>
      </form>
    </>
  );
};

export default FormAddAutor;
