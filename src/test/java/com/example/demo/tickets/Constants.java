package com.example.demo.tickets;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.tickets.model.Tickets;

public class Constants {

	public static String USERNAME = "dummyUsername";
	public static String PASSWORD = "dummyPassword";
	public static String SUBDOMAIN = "dummySubdomain";
	
	public static String SUCCESS = "success";
	
	public static String PAGE = "1";
	public static String QUARTER = "1";
	
	public static List<Tickets> tickets = new ArrayList<Tickets>();
	
		
	public static final List<Tickets> MULTIPLE_TICKETS = tickets;
			
	public static final String COUNT = "{\n"
			+ "    \"count\": {\n"
			+ "        \"value\": 102,\n"
			+ "        \"refreshed_at\": \"2021-11-28T17:54:07+00:00\"\n"
			+ "    }\n"
			+ "}";
	
	public static final String NOTCOUNT = "{\n"
			+ "    \"count\": {\n"
			+ "        \"value\": 103,\n"
			+ "        \"refreshed_at\": \"2021-11-28T17:54:07+00:00\"\n"
			+ "    }\n"
			+ "}";
	}
