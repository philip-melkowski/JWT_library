import React from "react";
import FormLogin from "./components/FormLogin";
import { useState } from "react";

function App() {
  const [token, setToken] = useState<string | null>(null);

  return (
    <>
      <div className="App">
        {token ? (
          <p>Zalogowano! Token: {token}</p>
        ) : (
          <FormLogin onLogin={(t) => setToken(t)}></FormLogin>
        )}
        <p>hello</p>
      </div>
    </>
  );
}

export default App;
