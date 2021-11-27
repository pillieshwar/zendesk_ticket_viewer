package com.example.demo.tickets;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

	@SuppressWarnings("resource")
	private String apiAuthentication(String username, String password, String stringURL) {
		Scanner sc;

		String basicAuth = "";
		String ticketsJSON = "";
		String stringOutput = "";
		URL url;

		try {
			url = new URL(stringURL);
			URLConnection urlConnection = url.openConnection();

			String userAuthentication = username + ":" + password;
			basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userAuthentication.getBytes());
			urlConnection.setRequestProperty("Authorization", basicAuth);
			urlConnection.connect();

			InputStream inputStream = urlConnection.getInputStream();
			sc = new Scanner(inputStream);
			sc = sc.useDelimiter("\\A");
			stringOutput = sc.hasNext() ? sc.next() : "";
			sc.close();

		} catch (MalformedURLException e) {
			System.out.println(e + " ERROR: Failed connection. Malformed URL.");
//			return "ERROR: Failed connection. Malformed URL.";

		} catch (Exception e) {
			System.out.println(e + " ERROR: Ooops! Something went wrong fetching your ticket(s).");
			return e.toString();
		}
		return stringOutput;
	}

	public List<Tickets> getAllTickets(String page, String quarter, Credentials credentials) {
		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://"+credentials.getSubdomain()+".zendesk.com/api/v2/tickets.json?page=" + page);

		List<Tickets> multipleTicketsList = new ArrayList<>();
		List<Tickets> errorList = new ArrayList<>();

		try {
			JSONArray ticketsList = new JSONArray();
			JSONObject jsonObject = new JSONObject(result);
			ticketsList = jsonObject.getJSONArray("tickets");
//			System.out.println("ticketsList" + result);
			String dateStr = "";
			Date date = new Date();

			int start = ((Integer.parseInt(quarter) - 1) * 25);
			int end = Math.min((((Integer.parseInt(quarter)) * 25)), ticketsList.length());

			for (int i = start; i < end; i++) {
				Tickets multipleTicket = new Tickets();
				try {
					JSONObject ticketObject = ticketsList.getJSONObject(i);

					multipleTicket.setId(ticketObject.getString("id"));
					multipleTicket.setSubject(ticketObject.getString("subject"));
					multipleTicket.setDescription(ticketObject.getString("description"));
					multipleTicket.setStatus(ticketObject.getString("status"));
					multipleTicket.setPriority(ticketObject.getString("priority"));
					multipleTicket.setRequester_id(ticketObject.getString("requester_id"));
					date = dateFormat.parse(ticketsList.getJSONObject(i).getString("updated_at"));
					dateStr = date.toString();
					multipleTicket.setUpdated_at(dateStr);

					multipleTicketsList.add(multipleTicket);

				} catch (ParseException e) {
					System.out.println(
							"ERROR: There was an issue regarding the last updated date on one of the tickets. Skipping Ticket...");
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e + "ERROR: Ooops! Error fetching ticket(s).");
			Tickets errorTicket = new Tickets();
			errorTicket.setError(e.toString());
			errorList.add(errorTicket);
			return errorList;
		}
		return multipleTicketsList;
	}

	public String getTicketCount(Credentials credentials) {

		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://"+credentials.getSubdomain()+".zendesk.com/api/v2/tickets/count.json");
		JSONObject count = new JSONObject();

		try {
			JSONObject jsonObject = new JSONObject(result);
			count = jsonObject.getJSONObject("count");
		} catch (Exception e) {
			System.out.println(e + " ERROR: Ooops! Something went wrong fetching your ticket(s).");
		}
		return count.toString();
	}
}
