package com.example.demo.tickets;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "tickets")
public class TicketsController {

	private final TicketService ticketService;

	@Autowired
	public TicketsController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@CrossOrigin
	@GetMapping("/postpage")
	@ResponseBody
	public String pmapping(@RequestParam(required = false) String page,@RequestParam(required = false) String page2) {
		System.out.println(page+page2);
		return page+page2;
	}

	@CrossOrigin
	@GetMapping("/all")
	@ResponseBody
	public List<Tickets> getAllTickets(@RequestParam(required = false) String page,@RequestParam(required = false) String quarter) {
		return ticketService.getAllTickets(page, quarter);
	}
	
	@CrossOrigin
	@GetMapping("/ticketCount")
	public String getTicketCount() {
		return ticketService.getTicketCount();
	}

//	@CrossOrigin
//	@GetMapping("/hello")
//	public String hello(@RequestParam(value="name", defaultValue = "World") String Name) {
//		return String.format("Hello %s!", Name);
//	}
//	
//	@GetMapping("/data")
//	public List<Object> getData(){
//		System.out.println("inside data");
//		String url = "https://jsonplaceholder.typicode.com/posts";
//		RestTemplate restTemplate = new RestTemplate();
//		Object[] data = restTemplate.getForObject(url, Object[].class);
//		return Arrays.asList(data);
//	}
//	
//	@CrossOrigin
//	@GetMapping("/dataauth")
//	public Tickets getDataAuth(){
//		Scanner sc;
//		System.out.println("inside data Auth");
//		String email_address = "eshwarnag.pilli@wsu.edu";
//		String password = "Eswar123$";
//		String stringURL = "https://zcceshwar.zendesk.com/api/v2/tickets/104.json";
//
//		String basicAuth = "";
//		String ticketsJSON = "";
//		JSONObject jsonObject;
//		JSONObject ticket1;
//		URL url;
//		Tickets singleTicket = new Tickets();
//		try {
//		url = new URL(stringURL);
//		URLConnection urlConnection = url.openConnection();
//				
//		String userAuthentication = email_address + ":" + password;
//		basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userAuthentication.getBytes());
//		
//		urlConnection.setRequestProperty ("Authorization", basicAuth);
//		
//		urlConnection.connect();
//		InputStream inputStream = urlConnection.getInputStream();
//		
//		sc = new Scanner(inputStream);
//		sc = sc.useDelimiter("\\A");
//		String result = sc.hasNext() ? sc.next() : "";
//		sc.close();
//		ticketsJSON = result;
//		
////		ObjectMapper objectMapper = new ObjectMapper();
////		Customer customer = objectMapper.readValue(result);
////		JSONObject ticketsJSON = new JSONObject();
//		jsonObject= new JSONObject(result);
//		ticket1 = new JSONObject();
//		ticket1 = jsonObject.getJSONObject("ticket");
//		Date date = new Date();
//		String dateStr = "";
//		date = dateFormat.parse(ticket1.getString("updated_at"));
//		ticket1.put("updated_at", date);
//		ticket1.put("requester_id", ticket1.getInt("requester_id"));
////		 String str = jsonObject.getString("ticket");
//		
//		singleTicket.setId(ticket1.getString("id"));
//		singleTicket.setSubject(ticket1.getString("subject"));
//		singleTicket.setDescription(ticket1.getString("description"));
//		
//		
//		System.out.println(ticket1);
//		}
//		catch (MalformedURLException e) {
//			System.out.println("ERROR: Failed connection. Malformed URL.");
//			return null;
//			
//		} catch (Exception e) {
//			System.out.println("ERROR: Ooops! Something went wrong fetching your ticket(s).");
//			return null;
//		}
//		return singleTicket;
//	}


}
