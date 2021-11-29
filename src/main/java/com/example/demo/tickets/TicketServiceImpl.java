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
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.tickets.model.Credentials;
import com.example.demo.tickets.model.Tickets;
import com.example.demo.tickets.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	static Credentials credentials = new Credentials();

	@Override
	public String setCredentials(Credentials credential) {
		credentials.setUsername(credential.getUsername());
		credentials.setPassword(credential.getPassword());
		credentials.setSubdomain(credential.getSubdomain());

		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://" + credentials.getSubdomain() + ".zendesk.com/api/v2/users/me.json");

		JSONObject ticketObject = new JSONObject();
		try {

			JSONObject jsonObject = new JSONObject(result);

			if (jsonObject.has("user")) {
				ticketObject = jsonObject.getJSONObject("user");
				if (!ticketObject.get("name").equals("Anonymous user")) {
					return "success";
				} else {
					System.out.println("Enter Correct username/password");
					return "false";
				}
			} else {
				System.out.println("Enter correct subdomain");
				return "false";
			}
		} catch (Exception e) {
			System.out.println(e + "ERROR: Unable to fetch user details/ enter correct credentials");
			return "false";
		}
	}

	@SuppressWarnings("resource")
	public String apiAuthentication(String username, String password, String stringURL) {
		Scanner sc;

		String basicAuth = "";
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
			return e.toString();

		} catch (Exception e) {
			System.out.println(e + " ERROR: Something went wrong with the credentials or sub-domain.");
			return e.toString();
		}
		return stringOutput;
	}

	@Override
	public List<Tickets> getQuarterTickets(String page, String quarter) {
		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://" + credentials.getSubdomain() + ".zendesk.com/api/v2/tickets.json?page=" + page);

		List<Tickets> multipleTicketsList = new ArrayList<>();
		List<Tickets> errorList = new ArrayList<>();

		try {
			JSONArray ticketsList = new JSONArray();
			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("tickets")) {
				ticketsList = jsonObject.getJSONArray("tickets");

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
			}
		} catch (Exception e) {
			System.out.println(e + "ERROR: Unabe to fetch multiple ticket(s).");
			Tickets errorTicket = new Tickets();
			errorTicket.setError(e.toString());
			errorList.add(errorTicket);
			return errorList;
		}
		return multipleTicketsList;
	}

	@Override
	public Tickets getSingleTicket(int ticketId) {

		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://" + credentials.getSubdomain() + ".zendesk.com/api/v2/tickets/" + ticketId + ".json");

		JSONObject ticketObject = new JSONObject();
		Tickets singleTicket = new Tickets();
		try {

			JSONObject jsonObject = new JSONObject(result);
			if (jsonObject.has("ticket")) {
				ticketObject = jsonObject.getJSONObject("ticket");
				String dateStr = "";
				Date date = new Date();

				try {
					singleTicket.setId(ticketObject.getString("id"));
					singleTicket.setSubject(ticketObject.getString("subject"));
					singleTicket.setDescription(ticketObject.getString("description"));
					singleTicket.setStatus(ticketObject.getString("status"));
					singleTicket.setPriority(ticketObject.getString("priority"));
					singleTicket.setRequester_id(ticketObject.getString("requester_id"));
					date = dateFormat.parse(ticketObject.getString("updated_at"));
					dateStr = date.toString();
					singleTicket.setUpdated_at(dateStr);
				} catch (ParseException e) {
					System.out.println(e + "ERROR: There was an issue in ticket details");
				}
			}
		} catch (

		Exception e) {
			System.out.println(e + "ERROR: Error fetching ticket.");
			Tickets errorTicket = new Tickets();
			errorTicket.setError(e.toString());
			return errorTicket;
		}
		return singleTicket;
	}

	@Override
	public String getTicketCount() {
		String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
				"https://" + credentials.getSubdomain() + ".zendesk.com/api/v2/tickets/count.json");
		JSONObject count = new JSONObject();

		try {
			JSONObject jsonObject = new JSONObject(result);
			count = jsonObject.getJSONObject("count");
		} catch (Exception e) {
			System.out.println(e + " ERROR: Something went wrong fetching count of tickets.");
		}
		return count.toString();
	}

	@Override
	public List<Tickets> getAllTickets() throws JSONException {

		String pageCountStr = getTicketCount();
		JSONObject jsonPageObject = new JSONObject(pageCountStr);
		int pageCountVal = 0;
		if (jsonPageObject.has("value")) {
			if (jsonPageObject.getString("value") != null) {
				pageCountVal = (int) Math.ceil(Float.parseFloat(jsonPageObject.getString("value")) / 100);
			}
		}
		List<Tickets> multipleTicketsList = new ArrayList<>();
		List<Tickets> errorList = new ArrayList<>();

		String dateStr = "";
		Date date = new Date();
		for (int j = 1; j <= pageCountVal; j++) {

			String result = apiAuthentication(credentials.getUsername(), credentials.getPassword(),
					"https://" + credentials.getSubdomain() + ".zendesk.com/api/v2/tickets.json?page=" + j);

			try {
				JSONArray ticketsList = new JSONArray();
				JSONObject jsonObject = new JSONObject(result);
				ticketsList = jsonObject.getJSONArray("tickets");

				for (int i = 0; i < ticketsList.length(); i++) {

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
				System.out.println(e + "ERROR: Error fetching all ticket(s).");
				Tickets errorTicket = new Tickets();
				errorTicket.setError(e.toString());
				errorList.add(errorTicket);
				return errorList;
			}
		}

		return multipleTicketsList;
	}
}
