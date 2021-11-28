import React, { useEffect } from "react";
import LeftSideBar from "./leftsidebar";
import Header from "./header";
import axios from "axios";
import { Grid, GridItem, Button, Stack } from "@chakra-ui/react";
import { ChevronLeftIcon } from "@chakra-ui/icons";

export default function Dashboard() {
  const [ticketCount, setTicketCount] = React.useState("");
  const [page, setPage] = React.useState(1);
  const [quarter, setQuarter] = React.useState(1);
  const [pageNumber, setPageNum] = React.useState(1);
  const [viewAllPages, setViewAllPages] = React.useState(false);

  useEffect(() => {
    async function fetchTicketCount() {
      try {
        await axios
          .get("http://localhost:8080/tickets/ticketCount")
          .then((res) => setTicketCount(res.data));
      } catch (err) {
        console.log(err);
      }
    }
    fetchTicketCount();
  }, []);

  function pageDetails(pageNum) {
    if (pageNum >= 1) {
      setPageNum(pageNum);
    } else {
      pageNum = 1;
    }

    if (pageNum > 4) {
      setPage(Math.trunc(pageNum / 4) + 1);
    } else {
      setPage(0);
    }

    if (pageNum === 4) {
      setQuarter(4);
    } else {
      setQuarter(pageNum % 4);
    }
  }

  function allPages() {
    if (viewAllPages === false) {
      setViewAllPages(true);
    } else {
      setViewAllPages(false);
    }
  }

  const arr = [];
  const pagination = Math.ceil(ticketCount.value / 25);
  for (var i = 0; i < pagination; i++) {
    arr[i] = i;
  }

  return (
    <div>
      <Grid
        h="670px"
        templateRows="repeat(10, 1fr)"
        templateColumns="repeat(10, 1fr)"
        gap={2}
        p={2}
      >
        <GridItem rowSpan={1} colSpan={10} bg="white">
          <Header />
        </GridItem>

        <GridItem rowSpan={8} colSpan={10} bg="white">
          <LeftSideBar
            page={page}
            quarter={quarter}
            viewAllPages={viewAllPages}
          />
        </GridItem>

        <GridItem rowSpan={1} colSpan={5}>
          <Stack direction="row" spacing={2} align="center">
            <Button
              key="back "
              onClick={() => pageDetails(pageNumber - 1)}
              colorScheme="teal"
              size="xs"
              variant="outline"
            >
              <ChevronLeftIcon />
            </Button>
            {arr.map((item, key) => (
              <Button
                key={item}
                onClick={() => pageDetails(key + 1)}
                colorScheme="teal"
                size="xs"
                variant="outline"
              >
                {key + 1}
              </Button>
            ))}
            <Button
              key="front"
              onClick={() => allPages()}
              colorScheme="teal"
              size="xs"
              variant="outline"
            >
              View All
            </Button>
          </Stack>
        </GridItem>
      </Grid>
    </div>
  );
}
