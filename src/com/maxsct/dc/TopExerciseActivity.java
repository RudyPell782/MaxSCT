package com.maxsct.dc;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.maxsct.dc.R;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.SaveCallback;


public class TopExerciseActivity extends Activity {

	//private ParseQueryAdapter<Workout> mainAdapter;
	//private BestExerciseAdapter favoritesAdapter;
	
	private String[] bestWeights = new String[5];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			  Intent intent = new Intent(this, LoginActivity.class);
			  startActivity(intent);
			  finish();
			}
		super.onCreate(savedInstanceState);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercise");
		query.whereEqualTo("name", "DB Flat Press");
		query.orderByDescending("weight");
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.i("score", "The getFirst request failed.");
		    } else {
		      Log.d("Parse", "Retrieved the object.");
		      bestWeights[0] = Integer.toString(object.getInt("weight"));
		    }
		  }
		});
		
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Exercise");
		query2.whereEqualTo("name", "Front Squat");
		query2.orderByDescending("weight");
		query2.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.i("score", "The getFirst request failed.");
		    } else {
		      Log.d("Parse", "Retrieved the object.");
		      bestWeights[1] = Integer.toString(object.getInt("weight"));
		    }
		  }
		});
		
		ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Exercise");
		query3.whereEqualTo("name", "BB Row");
		query3.orderByDescending("weight");
		query3.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.i("score", "The getFirst request failed.");
		    } else {
		      Log.d("Parse", "Retrieved the object.");
		      bestWeights[2] = Integer.toString(object.getInt("weight"));
		      
		    }   
		  }
		});
		
		  View childView = getLayoutInflater().inflate(R.layout.activity_top_exercise, null);
		  TextView pressView = (TextView) childView.findViewById(R.id.press_view);
	      TextView rowView = (TextView) childView.findViewById(R.id.row_view);
	      TextView squatView = (TextView) childView.findViewById(R.id.squat_view);
	        pressView.setText("Best DB Press " + bestWeights[0]);
	        rowView.setText("Best BB Row " + bestWeights[1]);
	        squatView.setText("Best Front Squat " + bestWeights[2]);

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_workout_list, menu);
		return true;
	}

	/*
	 * Posting meals and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_refresh: {
			updateWorkoutList();
			break;
		}

		case R.id.action_favorites: {
			//showFavorites();
			break;
		}

		case R.id.action_new: {
			newWorkout();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateWorkoutList() {
		Intent i = new Intent(this, WorkoutListActivity.class);
		startActivity(i);
		finish();
	}

	private void showFavorites() {
		Intent i = new Intent(this, NewWorkoutActivity.class);
		startActivityForResult(i, 0);
	}

	private void newWorkout() {
		Intent i = new Intent(this, NewWorkoutActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			// If a new post has been added, update
			// the list of posts
			updateWorkoutList();
		}
	}

}
