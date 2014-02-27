package com.maxsct.dc;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.mealspotting.R;

public class TopExerciseActivity extends Activity {

	private ParseQueryAdapter<Workout> mainAdapter;
	//private BestExerciseAdapter favoritesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			  Intent intent = new Intent(this, LoginActivity.class);
			  startActivity(intent);
			  finish();
			}
		super.onCreate(savedInstanceState);
		

		mainAdapter = new ParseQueryAdapter<Workout>(this, Workout.class){
		      @Override
		      public View getItemView(Workout workout, View view, ViewGroup parent) {
		        if (view == null) {
		          view = View.inflate(getContext(), R.layout.workout_list_item, null);
		        }
		        TextView contentView = (TextView) view.findViewById(R.id.contentView);
		        TextView usernameView = (TextView) view.findViewById(R.id.usernameView);
		        contentView.setText("Workout " + workout.getTitle());
		        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(workout.getDate());
		        usernameView.setText("Performed " + dateString);
		        return view;
		      }
		    };;
		mainAdapter.setTextKey("title");
		

		// Default view is all meals
	//	setListAdapter(mainAdapter);
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
