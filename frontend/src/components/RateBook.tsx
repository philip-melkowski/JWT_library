import React, { useState, useEffect, useCallback } from "react";
import LogOutAndUserPageSegment from "./LogOutAndUserPageSegment";

interface RateBookProps {
  onLogOut: () => void;
}

interface KsiazkaDTO {
  id: string;
  tytul: string;
  imieAutora: string;
  nazwiskoAutora: string;
  sredniaOcen: string;
}

const RateBook = ({ onLogOut }: RateBookProps) => {
  const [blad, setBlad] = useState("");
  const token = sessionStorage.getItem("token");
  const [nieprzeczytane, setNieprzeczytane] = useState<KsiazkaDTO[]>([]);
  const [zaznaczonaId, setZaznaczonaId] = useState<string | null>();
  const [ocena, setOcena] = useState<number>(1);
  const [filtrTytulu, setFiltrTytulu] = useState<string>("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState<number>(0);

  const handleSubmit = async () => {
    let userId = "";
    try {
      const res1 = await fetch("/api/uzytkownicy/getUserId", {
        method: "GET",
        headers: {
          Authorization: "Bearer " + token,
        },
      });
      if (!res1.ok) {
        throw new Error("nie udalo sie pobrac id uzytkownika " + res1.status);
      }
      userId = await res1.text();
    } catch (e: any) {
      setBlad(e.message);
    }
    try {
      const response = await fetch("/api/przeczytane", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + token,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          ksiazka: { id: zaznaczonaId },
          uzytkownik: { id: userId },
          ocena: ocena,
        }),
      });
      if (!response.ok) {
        throw new Error("nie udalo sie dodac rekordu: " + response.status);
      }
      fetchNieprzeczytane();
    } catch (e: any) {
      setBlad(e.message);
    }
  };
  const fetchNieprzeczytane = useCallback(async () => {
    try {
      const response = await fetch(`/api/ksiazki/nieprzeczytane?page=${page}`, {
        method: "GET",
        headers: {
          Authorization: "Bearer " + token,
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        throw new Error("nie udalo sie pobrac ksiazek " + response.status);
      } else {
        setBlad("");
        const data = await response.json();
        setNieprzeczytane(data.content);
        setTotalPages(data.totalPages);
      }
    } catch (e: any) {
      setBlad(e.message);
    }
  }, [token, page]);
  useEffect(() => {
    fetchNieprzeczytane();
  }, [fetchNieprzeczytane]);

  const nieprzeczytaneLi = nieprzeczytane
    .filter((np) => np.tytul.toLowerCase().includes(filtrTytulu.toLowerCase()))
    .map((n) => {
      return (
        <tr key={n.id}>
          <td>{n.id}</td>
          <td>
            {n.tytul.length > 30 ? n.tytul.slice(0, 30) + "..." : n.tytul}
          </td>
          <td>
            {n.imieAutora} {n.nazwiskoAutora}
          </td>
          <td>{n.sredniaOcen}</td>
          <td>
            <input
              type="radio"
              name="ocena"
              value={n.id}
              checked={zaznaczonaId === n.id}
              onChange={() => setZaznaczonaId(n.id)}
            ></input>
          </td>
        </tr>
      );
    });
  return (
    <>
      <LogOutAndUserPageSegment onLogOut={onLogOut}></LogOutAndUserPageSegment>
      {blad && <p style={{ color: "red" }}>{blad}</p>}
      <button
        className="btn btn-light"
        onClick={() => {
          const nowa = prompt("Podaj ocene 1-5");
          if (nowa != null) {
            const nowaNum = parseInt(nowa);
            if (nowaNum != null && nowaNum > 0 && nowaNum < 6) {
              setOcena(nowaNum);
              handleSubmit();
            }
          }
        }}
      >
        Oceń zaznaczoną
      </button>
      <input
        type="text"
        placeholder="Filtruj po tytule..."
        value={filtrTytulu}
        onChange={(e) => {
          setFiltrTytulu(e.target.value);
        }}
        style={{ margin: "10px" }}
      ></input>
      <table style={{ color: "white" }}>
        <thead>
          <tr>
            <th style={{ textAlign: "left" }}>ID</th>
            <th style={{ textAlign: "left" }}>tytul</th>
            <th style={{ textAlign: "left" }}>Autor</th>
            <th style={{ textAlign: "left", width: "120px" }}>Średnia ocen</th>
            <th style={{ textAlign: "left", width: "100px" }}>Zaznacz</th>
          </tr>
        </thead>
        <tbody>{nieprzeczytaneLi}</tbody>
      </table>
      <div style={{ marginTop: "10px" }}>
        <button
          disabled={page === 0}
          onClick={() => {
            setPage(page - 1);
          }}
        >
          Poprzednia
        </button>
        {(() => {
          const visiblePages = [];
          const maxPagesToShow = 5;

          const startPage = Math.max(1, page - maxPagesToShow);
          const endPage = Math.min(totalPages - 2, page + maxPagesToShow);

          visiblePages.push(
            <button key={0} onClick={() => setPage(0)} disabled={page === 0}>
              1
            </button>
          );

          if (startPage > 1) {
            visiblePages.push(<span key="start-ellipsis">...</span>);
          }

          for (let i = startPage; i <= endPage; i++) {
            visiblePages.push(
              <button key={i} onClick={() => setPage(i)} disabled={page === i}>
                {i + 1}
              </button>
            );
          }

          if (endPage < totalPages - 2) {
            visiblePages.push(<span key="end-ellipsis">...</span>);
          }

          if (totalPages > 1) {
            visiblePages.push(
              <button
                key={totalPages - 1}
                onClick={() => setPage(totalPages - 1)}
                disabled={page === totalPages - 1}
              >
                {totalPages}
              </button>
            );
          }

          return visiblePages;
        })()}
      </div>
    </>
  );
};

export default RateBook;
