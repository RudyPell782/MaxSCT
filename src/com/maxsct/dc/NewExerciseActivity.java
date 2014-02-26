package com.maxsct.dc;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.parse.ParseFile;
import com.parse.mealspotting.R;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewExerciseActivity extends Activity {

	private Exercise exercise;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		exercise = new Exercise();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		// Begin with main data entry view,
		// NewExerciseFragment
		setContentView(R.layout.activity_new_workout);
		FragmentManager manager = getFragmentManager();
		Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = new ExerciseFragment();
			manager.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}

	public Exercise getCurrentExercise() {
		return exercise;
	}

}
