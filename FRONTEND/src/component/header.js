import React from "react";
import { Grid, GridItem, Heading } from "@chakra-ui/react";

export default function Header() {
  return (
    <div>
      <Grid h="50px" templateColumns="repeat(10, 1fr)" gap={4}>
        <GridItem colSpan={2}>
          <Heading mt="4%" float="left" size="sm">
            ZENDESK CODING CHALLENGE
          </Heading>
        </GridItem>
        <GridItem colSpan={2} bg="white" />
        <GridItem colSpan={2} bg="white" />
        <GridItem colSpan={2} bg="white" />
        <GridItem colSpan={2} bg="white"></GridItem>
      </Grid>
    </div>
  );
}
