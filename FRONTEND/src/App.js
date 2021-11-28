import "./App.css";

import * as React from "react";
import { ChakraProvider } from "@chakra-ui/react";
import LoginForm from "./component/loginform";
function App() {
  return (
    <div className="App">
      <ChakraProvider>
        <LoginForm />
      </ChakraProvider>
    </div>
  );
}

export default App;
