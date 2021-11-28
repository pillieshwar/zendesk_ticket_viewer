package com.example.demo.tickets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "tickets")
public class TicketsController {

	private final TicketService ticketService;
	Credentials cred = new Credentials();

	@Autowired
	public TicketsController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@CrossOrigin
	@PostMapping(value = "/pp", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public String pp(@RequestBody Credentials credentials) {
		cred.setUsername(credentials.getUsername());
		cred.setPassword(credentials.getPassword());
		cred.setSubdomain(credentials.getSubdomain());
		return "success";
	}

	@CrossOrigin
	@GetMapping("/postpage")
	@ResponseBody
	public String pmapping(@RequestParam(required = false) String page, @RequestParam(required = false) String page2) {
		System.out.println(page + page2);
		return page + page2;
	}

	@CrossOrigin
	@GetMapping("/all")
	@ResponseBody
	public List<Tickets> getAllTickets(@RequestParam(required = false) String page,
			@RequestParam(required = false) String quarter) {
		return ticketService.getAllTickets(page, quarter, cred);
	}

	@CrossOrigin
	@GetMapping("/ticketCount")
	public String getTicketCount() {
		return ticketService.getTicketCount(cred);
	}

	@CrossOrigin
	@GetMapping("/singleTicket")
	@ResponseBody
	public Tickets getSingleTickets(@RequestParam(required = false) int ticketId) {
		return ticketService.getSingleTicket(ticketId, cred);
	}
}
