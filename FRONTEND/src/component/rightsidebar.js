import React, { useEffect } from "react";
import axios from "axios";
import {
  Wrap,
  WrapItem,
  Avatar,
  Divider,
  Badge,
  Heading,
  Text,
  Grid,
  GridItem,
} from "@chakra-ui/react";

export default function RightSideBar(props) {
  const [apidata, setapidata] = React.useState([]);
  useEffect(() => {
    async function fetchData() {
      try {
        const res = await axios.get(
          "http://127.0.0.1:8080/tickets/singleTicket?ticketId=" +
            props.ticketId +
            ""
        );
        setapidata(res.data);
      } catch (err) {
        console.log(err);
      }
    }

    fetchData();
  }, [props.ticketId]);

  return (
    <div>
      <Grid
        h="500px"
        templateRows="repeat(10, 1fr)"
        templateColumns="repeat(14, 1fr)"
        gap={2}
      >
        <GridItem rowSpan={1} colSpan={14} bg="white">
          <Heading ml="3" mt="2" float="left" as="h3" size="md">
            <Badge fontSize="lg" variant="outline" colorScheme="black">
              #{apidata.id} -
            </Badge>
            &nbsp;
            {apidata.subject}
            <Badge ml="4" variant="outline" colorScheme="green">
              {apidata.status}
            </Badge>
            <Badge ml="4" variant="outline" colorScheme="red">
              {apidata.priority}
            </Badge>
          </Heading>

          <Divider mt="10" orientation="horizontal" />
        </GridItem>

        <GridItem rowSpan={9} colSpan={2}>
          <Wrap ml="4">
            <WrapItem>
              <Avatar
                name={apidata.subject}
                src="https://bit.ly/tioluwani-kolawole"
              />
            </WrapItem>
          </Wrap>
        </GridItem>

        <GridItem rowSpan={1} colSpan={11} bg="papayawhip">
          <Heading ml="4" mt="3" float="left" size="xs">
            Requester Id: {apidata.requester_id}
          </Heading>
          <Heading mr="3" mt="4" float="right" fontSize="xs" size="xs">
            {apidata.updated_at}
          </Heading>
        </GridItem>

        <GridItem rowSpan={1} colSpan={11}></GridItem>

        <GridItem rowSpan={7} colSpan={11}>
          <Text fontSize="sm" align="left">
            {apidata.description}
          </Text>
        </GridItem>
      </Grid>
    </div>
  );
}
