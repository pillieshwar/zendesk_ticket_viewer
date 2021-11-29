package com.example.demo.tickets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.tickets.controller.TicketsController;
import com.example.demo.tickets.model.Credentials;
import com.example.demo.tickets.model.Tickets;

@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
class TicketServiceTest {

	@Mock
	private TicketServiceImpl ticketService;

	@InjectMocks
	private TicketsController ticketsController;

	Credentials credentials = new Credentials();

	@BeforeEach
	void setMockOutput() {
		credentials.setUsername(Constants.USERNAME);
		credentials.setPassword(Constants.PASSWORD);
		credentials.setSubdomain(Constants.SUBDOMAIN);

		when(ticketService.apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				credentials.getSubdomain())).thenReturn(Constants.COUNT);
		when(ticketService.setCredentials(credentials)).thenReturn(Constants.SUCCESS);

	}

	@Test
	void testApiConnection() {
		assertEquals(Constants.COUNT, ticketService.apiAuthentication(credentials.getUsername(),
				credentials.getPassword(), credentials.getSubdomain()));
	}

	@Test
	void testSetCredentials() {
		assertEquals(Constants.SUCCESS, ticketService.setCredentials(credentials));
	}

	@Test
	void NegativeTestApiConnection() {
		assertNotEquals(Constants.NOTCOUNT, ticketService.apiAuthentication(credentials.getUsername(),
				credentials.getPassword(), credentials.getSubdomain()));
	}

	@Test
	void testGetTicketCount() {
		when(ticketService.getTicketCount()).thenReturn(Constants.COUNT);
		assertEquals(Constants.COUNT, ticketsController.getTicketCount());
	}

	@Test
	void NegativeTestGetTicketCount() {
		when(ticketService.getTicketCount()).thenReturn(Constants.COUNT);
		assertNotEquals(Constants.COUNT + 1, ticketsController.getTicketCount());
	}

	@Test
	void testGetQuarterTickets() {
		List<Tickets> tickets = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Tickets ticket = new Tickets();
			ticket.setId(Integer.toString(1));
			ticket.setDescription("desc");
			ticket.setError("error");
			ticket.setPriority("priority");
			ticket.setRequester_id("12345");
			ticket.setStatus("status");
			ticket.setSubject("subject");
			ticket.setUpdated_at("date");
			tickets.add(ticket);
		}
		when(ticketService.getQuarterTickets("1", "1")).thenReturn(tickets);
		assertEquals(tickets, ticketsController.getQuarterTickets("1", "1"));
	}

	@Test
	void negativeTestGetQuarterTickets() {
		List<Tickets> tickets = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Tickets ticket = new Tickets();
			ticket.setId(Integer.toString(1));
			ticket.setDescription("desc");
			ticket.setError("error");
			ticket.setPriority("priority");
			ticket.setRequester_id("12345");
			ticket.setStatus("status");
			ticket.setSubject("subject");
			ticket.setUpdated_at("date");
			tickets.add(ticket);
		}
		when(ticketService.getQuarterTickets("1", "1")).thenReturn(tickets);
		assertNotEquals("negative test for quarter multiple tickets", ticketsController.getQuarterTickets("1", "1"));
	}

	@Test
	void testGetSingleTicket() {
		Tickets ticket = new Tickets();
		ticket.setId(Integer.toString(1));
		ticket.setDescription("desc");
		ticket.setError("error");
		ticket.setPriority("priority");
		ticket.setRequester_id("12345");
		ticket.setStatus("status");
		ticket.setSubject("subject");
		ticket.setUpdated_at("date");

		when(ticketService.getSingleTicket(0)).thenReturn(ticket);
		assertEquals(ticket, ticketsController.getSingleTickets(0));
	}

	@Test
	void negativeTestGetSingleTicket() {
		Tickets ticket = new Tickets();
		ticket.setId(Integer.toString(1));
		ticket.setDescription("desc");
		ticket.setError("error");
		ticket.setPriority("priority");
		ticket.setRequester_id("12345");
		ticket.setStatus("status");
		ticket.setSubject("subject");
		ticket.setUpdated_at("date");

		when(ticketService.getSingleTicket(0)).thenReturn(ticket);
		assertNotEquals("negative test case for single ticket", ticketsController.getSingleTickets(0));
	}

	@Test
	void testGetAllTickets() throws JSONException {
		List<Tickets> tickets = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Tickets ticket = new Tickets();
			ticket.setId(Integer.toString(1));
			ticket.setDescription("desc");
			ticket.setError("error");
			ticket.setPriority("priority");
			ticket.setRequester_id("12345");
			ticket.setStatus("status");
			ticket.setSubject("subject");
			ticket.setUpdated_at("date");
			tickets.add(ticket);
		}
		when(ticketService.getAllTickets()).thenReturn(tickets);
		assertEquals(tickets, ticketsController.getAllTickets());
	}

	@Test
	void negativeTestGetAllTickets() throws JSONException {
		List<Tickets> tickets = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			Tickets ticket = new Tickets();
			ticket.setId(Integer.toString(1));
			ticket.setDescription("desc");
			ticket.setError("error");
			ticket.setPriority("priority");
			ticket.setRequester_id("12345");
			ticket.setStatus("status");
			ticket.setSubject("subject");
			ticket.setUpdated_at("date");
			tickets.add(ticket);
		}
		when(ticketService.getAllTickets()).thenReturn(tickets);
		assertNotEquals("negative test case for all tickets", ticketsController.getAllTickets());
	}

}
