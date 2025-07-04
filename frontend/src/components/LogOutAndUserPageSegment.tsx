import React from "react";
import WylogujButton from "./buttons/WylogujButton";
import MoveToUzytButton from "./buttons/MoveToUzytButton";

interface LogOutAndUserPageSegmentProps {
  onLogOut: () => void;
}

const LogOutAndUserPageSegment = ({
  onLogOut,
}: LogOutAndUserPageSegmentProps) => {
  return (
    <>
      <div style={{ display: "flex", gap: "10px" }}>
        <WylogujButton onLogOut={onLogOut}></WylogujButton>
        <MoveToUzytButton></MoveToUzytButton>
      </div>
    </>
  );
};

export default LogOutAndUserPageSegment;
