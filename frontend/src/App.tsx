import React from "react";
import FormLogin from "./components/FormLogin";
import { useState } from "react";

function App() {
  const [token, setToken] = useState<string | null>(
    sessionStorage.getItem("token")
  );

  return (
    <>
      <div className="App">
        {token ? (
          <p>Zalogowano! Token: {token}</p>
        ) : (
          <FormLogin
            onLogin={() => setToken(sessionStorage.getItem("token"))}
          />
        )}
        <p>hello</p>
      </div>
    </>
  );
}

export default App;
