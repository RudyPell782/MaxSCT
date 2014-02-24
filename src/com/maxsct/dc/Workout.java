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

	public String getEx1Sets() {
		return getString("ex1sets");
	}

	public void setEx1Sets(String sets) {
		put("ex1sets", sets);
	}
	public String getEx2Sets() {
		return getString("ex2sets");
	}

	public void setEx2Sets(String sets) {
		put("ex2sets", sets);
	}
	public String getEx3Sets() {
		return getString("ex3sets");
	}

	public void setEx3Sets(String sets) {
		put("ex3sets", sets);
	}
	public String getEx4Sets() {
		return getString("ex4sets");
	}

	public void setEx4Sets(String sets) {
		put("ex4sets", sets);
	}
	public String getEx5Sets() {
		return getString("ex5sets");
	}

	public void setEx5Sets(String sets) {
		put("ex5sets", sets);
	}
	
	public String getEx1Reps() {
		return getString("ex1reps");
	}

	public void setEx1Reps(String reps) {
		put("ex1reps", reps);
	}
	public String getEx2Reps() {
		return getString("ex2reps");
	}

	public void setEx2Reps(String reps) {
		put("ex2reps", reps);
	}
	public String getEx3Reps() {
		return getString("ex3reps");
	}

	public void setEx3Reps(String reps) {
		put("ex3reps", reps);
	}
	
	public String getEx4Reps() {
		return getString("ex4reps");
	}

	public void setEx4Reps(String reps) {
		put("ex4reps", reps);
	}
	public String getEx5Reps() {
		return getString("ex5reps");
	}

	public void setEx5Reps(String reps) {
		put("ex5reps", reps);
	}
	
	public String getEx1Weight() {
		return getString("ex1weight");
	}

	public void setEx1Weight(String weight) {
		put("ex1weight", weight);
	}
	public String getEx2Weight() {
		return getString("ex2weight");
	}

	public void setEx2Weight(String weight) {
		put("ex2weight", weight);
	}
	public String getEx3Weight() {
		return getString("ex3weight");
	}

	public void setEx3Weight(String weight) {
		put("ex3weight", weight);
	}
	public String getEx4Weight() {
		return getString("ex4weight");
	}

	public void setEx4Weight(String weight) {
		put("ex4weight", weight);
	}
	public String getEx5Weight() {
		return getString("ex5weight");
	}

	public void setEx5Weight(String weight) {
		put("ex5weight", weight);
	}

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}

}
