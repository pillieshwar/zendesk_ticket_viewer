package com.example.demo.tickets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tickets.TicketServiceImpl;
import com.example.demo.tickets.model.Credentials;
import com.example.demo.tickets.model.Tickets;

@RestController
@RequestMapping(path = "tickets")
public class TicketsController {

	private TicketServiceImpl ticketService;
	Credentials credentials = new Credentials();

	@Autowired
	public TicketsController(TicketServiceImpl ticketService) {
		this.ticketService = ticketService;
	}

	@CrossOrigin
	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public String login(@RequestBody Credentials credential) {

		credentials.setUsername(credential.getUsername());
		credentials.setPassword(credential.getPassword());
		credentials.setSubdomain(credential.getSubdomain());
		String connectionStatus = ticketService.setCredentials(credentials);
		return connectionStatus;
	}

	@CrossOrigin
	@GetMapping("/quarterall")
	@ResponseBody
	public List<Tickets> getQuarterTickets(@RequestParam(required = false) String page,
			@RequestParam(required = false) String quarter) {
		return ticketService.getQuarterTickets(page, quarter);
	}

	@CrossOrigin
	@GetMapping("/ticketCount")
	public String getTicketCount() {
		return ticketService.getTicketCount();
	}

	@CrossOrigin
	@GetMapping("/singleTicket")
	@ResponseBody
	public Tickets getSingleTickets(@RequestParam(required = false) int ticketId) {
		return ticketService.getSingleTicket(ticketId);
	}

	@CrossOrigin
	@GetMapping("/all")
	@ResponseBody
	public List<Tickets> getAllTickets() throws JSONException {
		return ticketService.getAllTickets();
	}

}
