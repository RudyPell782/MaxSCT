package com.maxsct.dc;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.maxsct.dc.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewWorkoutActivity extends Activity {

	private Workout workout;
	private boolean[] exerciseComplete = {false, false, false, false, false};
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			  Intent intent = new Intent(this, LoginActivity.class);
			  startActivity(intent);
			  finish();
			}
		workout = new Workout();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		// Begin with main data entry view,
		// NewMealFragment
		setContentView(R.layout.activity_new_workout);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new NewWorkoutFragment();
			manager.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}

	public Workout getCurrentWorkout() {
		return workout;
	}
	
	public boolean getExerciseComplete(int index) {
		return exerciseComplete[index];
	}
	public void setExerciseComplete(int index, boolean complete) {
		exerciseComplete[index] = complete;
	}

}
