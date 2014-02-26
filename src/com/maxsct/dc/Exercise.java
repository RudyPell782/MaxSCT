package com.maxsct.dc;


import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Workout 
 */

@ParseClassName("Exercise")
public class Exercise extends ParseObject {

	public Exercise() {
		
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public ParseUser getAuthor() {
		return getParseUser("author");
	}

	public void setAuthor(ParseUser user) {
		put("author", user);
	}

	public String getSets() {
		return getString("sets");
	}

	public void setSets(String sets) {
		put("sets", sets);
	}
	
	public String getReps() {
		return getString("reps");
	}

	public void setReps(String reps) {
		put("reps", reps);
	}
	
	public int getWeight() {
		return getInt("weight");
	}

	public void setWeight(int weight) {
		put("weight", weight);
	}
	
	public Date getDate() {
		return getDate("exerciseDate");
	}

	public void setDate(Date date) {
		put("exerciseDate", date);
	}
	
}
