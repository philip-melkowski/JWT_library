import React from "react";
import { useNavigate } from "react-router-dom";
import "tailwindcss";

interface WylogujButtonProps {
  onLogOut: () => void;
}

const WylogujButton = ({ onLogOut }: WylogujButtonProps) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.removeItem("token");
    onLogOut();
    navigate("/");
  };

  return (
    <div>
      <button className="btn btn-danger" onClick={handleLogout}>
        Wyloguj
      </button>
    </div>
  );
};

export default WylogujButton;
