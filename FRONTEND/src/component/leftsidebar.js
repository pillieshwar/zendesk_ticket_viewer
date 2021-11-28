import React, { useEffect } from "react";
import axios from "axios";
import RightSideBar from "./rightsidebar";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Badge,
  Grid,
  GridItem,
  IconButton,
  Tag,
  TagLabel,
} from "@chakra-ui/react";
import { ViewIcon } from "@chakra-ui/icons";

export default function LeftSideBar(props) {
  const [apidata, setapidata] = React.useState([]);
  const [ticketId, setTicketId] = React.useState("1");

  useEffect(() => {
    async function fetchData() {
      try {
        const res = await axios.get(
          "http://127.0.0.1:8080/tickets/quarterall?page=" +
            props.page +
            "&quarter=" +
            props.quarter +
            ""
        );
        setapidata(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    fetchData();
  }, [props.page, props.quarter]);

  useEffect(() => {
    async function fetchAllPages() {
      try {
        const res = await axios.get("http://127.0.0.1:8080/tickets/all");
        setapidata(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    fetchAllPages();
  }, [props.viewAllPages]);

  function setDisplayTicket(id) {
    setTicketId(id);
  }

  return (
    <div>
      <Grid
        h="595px"
        templateRows="repeat(10, 1fr)"
        templateColumns="repeat(10, 1fr)"
        gap={2}
        p={2}
      >
        <GridItem overflow="scroll" rowSpan={9} colSpan={5} bg="white">
          <Table size="sm">
            <Thead>
              <Tr>
                <Th>Ticket Id</Th>
                <Th>Subject</Th>
                <Th>Status</Th>
                <Th>Date</Th>
                <Th>View</Th>
              </Tr>
            </Thead>
            <Tbody>
              {apidata.map((ticket, index) => (
                <Tr key={index}>
                  <Td>
                    <Badge variant="outline" colorScheme="magenta">
                      #{ticket.id}
                    </Badge>
                  </Td>
                  <Td>{ticket.subject}</Td>
                  <Td>
                    <Tag size="sm" colorScheme="green" borderRadius="full">
                      <TagLabel>{ticket.status}</TagLabel>
                    </Tag>
                  </Td>
                  {/* <Td fontSize="xs">{ticket.updated_at.substring(0, 16)}</Td> */}
                  <Td fontSize="xs">{ticket.updated_at}</Td>
                  <Td>
                    <IconButton
                      variant="outline"
                      colorScheme="red"
                      aria-label="view ticket"
                      icon={<ViewIcon />}
                      onClick={() => setDisplayTicket(ticket.id)}
                    />
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </GridItem>
        <GridItem overflow="scroll" rowSpan={9} colSpan={5} bg="white">
          <RightSideBar ticketId={ticketId} />
        </GridItem>
      </Grid>
    </div>
  );
}
