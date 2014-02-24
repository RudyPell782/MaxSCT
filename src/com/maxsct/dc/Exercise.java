package com.maxsct.dc;


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

	public Exercise(String workoutID) {
		put("workoutID", workoutID);
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
	
	public String getWeight() {
		return getString("weight");
	}

	public void setWeight(String weight) {
		put("weight", weight);
	}
	
}
