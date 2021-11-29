import React, { useState } from "react";
import { Grid, GridItem, Heading } from "@chakra-ui/react";
import axios from "axios";
import {
  Button,
  FormControl,
  InputRightElement,
  Input,
} from "@chakra-ui/react";
import Dashboard from "./dashboard";

export default function LoginForm() {
  const [connectionStatus, setConnectionStatus] = React.useState(false);

  const initialRef = React.useRef();
  const [show, setShow] = React.useState(false);
  const handleClick = () => setShow(!show);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [subdomain, setSubDomain] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    debugger;
    const params = JSON.stringify({
      username: username,
      password: password,
      subdomain: subdomain,
    });

    try {
      const res = await axios.post(
        "http://127.0.0.1:8080/tickets/login",
        params,
        {
          headers: {
            "content-type": "application/json",
            "Access-Control-Allow-Origin": "*",
          },
        }
      );
      console.log(res.data);
      if (res.data === "success") {
        setConnectionStatus(true);
      }
    } catch (err) {
      console.log(err);
    }
  }
  return (
    <div>
      {connectionStatus ? (
        <Dashboard />
      ) : (
        <Grid templateColumns="repeat(12, 1fr)" mt="10%" gap={4}>
          <GridItem colSpan={4} bg="white"></GridItem>
          <GridItem pt={10} pb={5} colSpan={4}>
            <Heading size="md">Connect to Zendesk Account</Heading>
            <form onSubmit={(e) => handleSubmit(e)}>
              <FormControl mt={5} id="username">
                <Input
                  bg="white"
                  type="username"
                  ref={initialRef}
                  placeholder="Username"
                  onChange={(event) => setUsername(event.currentTarget.value)}
                />
              </FormControl>

              <FormControl id="password" mt={4}>
                <Input
                  bg="white"
                  pr="4.5rem"
                  type={show ? "text" : "password"}
                  placeholder="Password"
                  onChange={(event) => setPassword(event.currentTarget.value)}
                />
                <InputRightElement>
                  <Button size="md" onClick={handleClick}>
                    {show ? "Hide" : "Show"}
                  </Button>
                </InputRightElement>
              </FormControl>

              <FormControl id="subdomain" mt={4}>
                <Input
                  bg="white"
                  placeholder="Sub Domain"
                  type="subdomain"
                  onChange={(event) => setSubDomain(event.currentTarget.value)}
                />
              </FormControl>

              <Button mt={5} colorScheme="blue" mr={3} type="submit">
                Connect
              </Button>
            </form>
          </GridItem>
          <GridItem colSpan={4} bg="white"></GridItem>
        </Grid>
      )}
    </div>
  );
}
