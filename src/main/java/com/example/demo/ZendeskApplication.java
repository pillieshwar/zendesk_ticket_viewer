package com.example.demo;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ZendeskApplication {
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
	public static void main(String[] args) {
		SpringApplication.run(ZendeskApplication.class, args);
	}
	
	@CrossOrigin
	@GetMapping("/hello")
	public String hello(@RequestParam(value="name", defaultValue = "World") String Name) {
		return String.format("Hello %s!", Name);
	}
	
	@GetMapping("/data")
	public List<Object> getData(){
		System.out.println("inside data");
		String url = "https://jsonplaceholder.typicode.com/posts";
		RestTemplate restTemplate = new RestTemplate();
		Object[] data = restTemplate.getForObject(url, Object[].class);
		return Arrays.asList(data);
	}
	
	@CrossOrigin
	@GetMapping("/dataauth")
	public String getDataAuth(){
		Scanner sc;
		System.out.println("inside data Auth");
		String email_address = "eshwarnag.pilli@wsu.edu";
		String password = "Eswar123$";
		String stringURL = "https://zcceshwar.zendesk.com/api/v2/tickets/104.json";

		String basicAuth = "";
		String ticketsJSON = "";
		JSONObject jsonObject;
		JSONObject ticket1;
		URL url;
		try {
		url = new URL(stringURL);
		URLConnection urlConnection = url.openConnection();
				
		String userAuthentication = email_address + ":" + password;
		basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userAuthentication.getBytes());
		
		urlConnection.setRequestProperty ("Authorization", basicAuth);
		
		urlConnection.connect();
		InputStream inputStream = urlConnection.getInputStream();
		
		sc = new Scanner(inputStream);
		sc = sc.useDelimiter("\\A");
		String result = sc.hasNext() ? sc.next() : "";
		sc.close();
		ticketsJSON = result;
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		Customer customer = objectMapper.readValue(result);
//		JSONObject ticketsJSON = new JSONObject();
		jsonObject= new JSONObject(result);
		ticket1 = new JSONObject();
		ticket1 = jsonObject.getJSONObject("ticket");
		Date date = new Date();
		String dateStr = "";
		date = dateFormat.parse(ticket1.getString("updated_at"));
		ticket1.put("updated_at", date);
		ticket1.put("requester_id", ticket1.getInt("requester_id"));
//		 String str = jsonObject.getString("ticket");
		System.out.println(ticket1);
		}
		catch (MalformedURLException e) {
			System.out.println("ERROR: Failed connection. Malformed URL.");
			return null;
			
		} catch (Exception e) {
			System.out.println("ERROR: Ooops! Something went wrong fetching your ticket(s).");
			return null;
		}
		return ticket1.toString();
	}
	
	@CrossOrigin
	@GetMapping("/dataauthmultiple")
	public String getDataAuthMultiple(){
		Scanner sc;
		System.out.println("inside data Auth Multiple");
		String email_address = "eshwarnag.pilli@wsu.edu";
		String password = "Eswar123$";
		String stringURL = "https://zcceshwar.zendesk.com/api/v2/tickets.json";

		String basicAuth = "";
		String ticketsJSON = "";
		JSONObject jsonObject;
		JSONObject ticket1;
		JSONArray ticketsArr;
		URL url;
		try {
		url = new URL(stringURL);
		URLConnection urlConnection = url.openConnection();
				
		String userAuthentication = email_address + ":" + password;
		basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userAuthentication.getBytes());
		
		urlConnection.setRequestProperty ("Authorization", basicAuth);
		
		urlConnection.connect();
		InputStream inputStream = urlConnection.getInputStream();
		
		sc = new Scanner(inputStream);
		sc = sc.useDelimiter("\\A");
		String result = sc.hasNext() ? sc.next() : "";
		sc.close();
		ticketsJSON = result;
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		Customer customer = objectMapper.readValue(result);
//		JSONObject ticketsJSON = new JSONObject();
		jsonObject= new JSONObject(result);
		ticketsArr = new JSONArray();
		ticketsArr = jsonObject.getJSONArray("tickets");
		
		Date date = new Date();
		String dateStr = "";
		
		for(int i = 0; i < ticketsArr.length(); i++) {
			try {
				date = dateFormat.parse(ticketsArr.getJSONObject(i).getString("updated_at"));
				dateStr = date.toString();
			} catch (ParseException e) {
				System.out.println("ERROR: There was an issue regarding the last updated date on one of the tickets. Skipping Ticket...");
				continue;
			}
			
			ticketsArr.getJSONObject(i).put("updated_at", dateStr);
		}
//		date = dateFormat.parse(ticket1.getString("updated_at"));
//		ticket1.put("updated_at", date);
//		ticket1.put("requester_id", ticket1.getInt("requester_id"));
//		String str = jsonObject.getString("ticket");
		}
		catch (MalformedURLException e) {
			System.out.println("ERROR: Failed connection. Malformed URL.");
			return "ERROR: Failed connection. Malformed URL.";
			
		} catch (Exception e) {
			System.out.println("ERROR: Ooops! Something went wrong fetching your ticket(s).");
			return "ERROR: Ooops! Something went wrong fetching your ticket(s).";
		}
		return ticketsArr.toString();
	}

	@CrossOrigin
	@GetMapping("/ticketCount")
	public String getTicketCount(){
		Scanner sc;
		System.out.println("inside Ticket Count");
		String email_address = "eshwarnag.pilli@wsu.edu";
		String password = "Eswar123$";
		String stringURL = "https://zcceshwar.zendesk.com/api/v2/tickets/count.json";

		String basicAuth = "";
		String ticketsJSON = "";
		JSONObject jsonObject;
		JSONObject ticket1;
		URL url;
		try {
		url = new URL(stringURL);
		URLConnection urlConnection = url.openConnection();
				
		String userAuthentication = email_address + ":" + password;
		basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userAuthentication.getBytes());
		
		urlConnection.setRequestProperty ("Authorization", basicAuth);
		
		urlConnection.connect();
		InputStream inputStream = urlConnection.getInputStream();
		
		sc = new Scanner(inputStream);
		sc = sc.useDelimiter("\\A");
		String result = sc.hasNext() ? sc.next() : "";
		sc.close();
		ticketsJSON = result;
		
//		ObjectMapper objectMapper = new ObjectMapper();
//		Customer customer = objectMapper.readValue(result);
//		JSONObject ticketsJSON = new JSONObject();
		jsonObject= new JSONObject(result);
		ticket1 = new JSONObject();
		ticket1 = jsonObject.getJSONObject("count");
//		Date date = new Date();
//		String dateStr = "";
//		date = dateFormat.parse(ticket1.getString("updated_at"));
//		ticket1.put("updated_at", date);
//		ticket1.put("requester_id", ticket1.getInt("requester_id"));
//		 String str = jsonObject.getString("ticket");
		System.out.println(ticket1);
		}
		catch (MalformedURLException e) {
			System.out.println("ERROR: Failed connection. Malformed URL.");
			return null;
			
		} catch (Exception e) {
			System.out.println("ERROR: Ooops! Something went wrong fetching your ticket(s).");
			return null;
		}
		return ticket1.toString();
	}
}
