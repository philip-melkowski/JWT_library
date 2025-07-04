import React from "react";
import { useNavigate } from "react-router-dom";

const MoveToUzytButton = () => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate("/");
  };
  return (
    <button className="btn btn-danger" onClick={handleClick}>
      Strona uzytkownika
    </button>
  );
};

export default MoveToUzytButton;
