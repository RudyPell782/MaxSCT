package com.maxsct.dc;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.mealspotting.R;

public class WorkoutListActivity extends ListActivity {

	private ParseQueryAdapter<Workout> mainAdapter;
	private BestExerciseAdapter favoritesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			  Intent intent = new Intent(this, LoginActivity.class);
			  startActivity(intent);
			  finish();
			}
		super.onCreate(savedInstanceState);
		getListView().setClickable(false);

		mainAdapter = new ParseQueryAdapter<Workout>(this, Workout.class);
		mainAdapter.setTextKey("title");
		mainAdapter.setImageKey("photo");

		// Subclass of ParseQueryAdapter
		favoritesAdapter = new BestExerciseAdapter(this);

		// Default view is all meals
		setListAdapter(mainAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_meal_list, menu);
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
			updateMealList();
			break;
		}

		case R.id.action_favorites: {
			showFavorites();
			break;
		}

		case R.id.action_new: {
			newMeal();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateMealList() {
		mainAdapter.loadObjects();
		setListAdapter(mainAdapter);
	}

	private void showFavorites() {
		favoritesAdapter.loadObjects();
		setListAdapter(favoritesAdapter);
	}

	private void newMeal() {
		Intent i = new Intent(this, NewWorkoutActivity.class);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			// If a new post has been added, update
			// the list of posts
			updateMealList();
		}
	}

}
