package com.example.demo.tickets;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONException;

public interface TicketService {

	public String apiAuthentication(String username, String password, String stringURL);
	
	public List<Tickets> getQuarterTickets(String page, String quarter);
	
	public Tickets getSingleTicket(int ticketId);
	
	public String setCredentials(Credentials credential);
	
	public String getTicketCount();
	
	public List<Tickets> getAllTickets() throws JSONException;
	
}
