import React from "react";
import { useNavigate } from "react-router-dom";

const AdminLinks = () => {
  const navigate = useNavigate();
  const token = sessionStorage.getItem("token");
  if (!token) return null;
  const payload = JSON.parse(atob(token.split(".")[1]));
  const admin = payload.roles?.includes("ROLE_ADMIN");

  if (!admin) return null;

  return (
    <div>
      <p></p>
      <button
        className="btn btn-outline-light"
        onClick={(e) => {
          navigate("/dodaj-autora");
        }}
      >
        Dodaj autora
      </button>
      <button
        className="btn btn-outline-light"
        style={{ marginLeft: "5px" }}
        onClick={(e) => {
          navigate("/dodaj-ksiazke");
        }}
      >
        Dodaj książkę
      </button>
    </div>
  );
};

export default AdminLinks;
