package com.maxsct.dc;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import java.util.Date;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Workout 
 */

@ParseClassName("Workout")
public class Workout extends ParseObject {

	public Workout() {
		// A default constructor is required.
	}

	public String getTitle() {
		return getString("title");
	}

	public void setTitle(String title) {
		put("title", title);
	}

	public ParseUser getAuthor() {
		return getParseUser("author");
	}

	public void setAuthor(ParseUser user) {
		put("author", user);
	}

	public Date getDate() {
		return getDate("workoutDate");
	}

	public void setDate(Date date) {
		put("workoutDate", date);
	}

}
