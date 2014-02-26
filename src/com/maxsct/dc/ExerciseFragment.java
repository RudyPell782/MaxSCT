package com.maxsct.dc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.inputmethod.InputMethodManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.mealspotting.R;

public class ExerciseFragment extends Fragment{

	public static final String TAG = "ExerciseFragment";

	
	private Button saveButton;
	private Button cancelButton;
	private TextView workoutName;
	private TextView setsHint;
	private TextView repsHint;
	private Spinner repsCount;
	private Spinner setsCount;
	private TextView weight;
	private TextView exerciseName;
	private String exerciseNameLabel;
	private String workout;
	private int workoutID;
    private int exercise;	
    private String[] exerciseList = {"DB Flat Press", "HS Shoulder Press", "CG Bench Press","Wide Pulldowns", "T-Bar Row", "BB Curl", "Reverse-grip Curl", "Seated Calf",
    		"Lying Ham", "Front Squat","HS Incline", "DB OHP", "JM press","CG Pulldown", "BB Row","DB Curl", "Hammer Curl", "Standing Calf","Seated Ham", "Leg Press"};
	private String[] hints1 = {"Rest Pause", "11-15"};
	private String[] hints2 = {"2", "8-10, 20"};
	private String[] hints3 = {"2", "6-9"};
	
	
	HashMap<String, String[]> hintsDictionary = new HashMap<String, String[]>(){{
	  for(int i=0; i<exerciseList.length; i++){
		  if(exerciseList[i].equals("Front Squat") || exerciseList[i].equals("Leg Press")){
			  put(exerciseList[i], hints2);
		  }
		  else if(exerciseList[i].equals("BB Row") || exerciseList[i].equals("T-Bar Row")){
			  put(exerciseList[i], hints3);
		  }
		  else {
			  put(exerciseList[i], hints1);
		  }
	  }
	    
	}};

    public String getValue (String key, int valueIdx) {
        String[] valueSet = hintsDictionary.get(key);
        return valueSet[valueIdx];
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
	    workout = getArguments().getString("workout");
	    exerciseNameLabel = getArguments().getString("exerciseName");  
	    exercise = getArguments().getInt("exercise");  
	    workoutID = getArguments().getInt("workoutID");  
		View v = inflater.inflate(R.layout.fragment_exercise, parent, false);
		
		

		exerciseName = (TextView) v.findViewById(R.id.exercise_name);
		exerciseName.setText(exerciseNameLabel);
		setsHint = (TextView) v.findViewById(R.id.sets_hint);
		setsHint.setText(getValue(exerciseNameLabel, 0));
		repsHint = (TextView) v.findViewById(R.id.reps_hint);
		repsHint.setText(getValue(exerciseNameLabel, 1));
		// The mealRating spinner lets people assign favorites of meals they've
		// eaten.
		// Meals with 4 or 5 ratings will appear in the Favorites view.
		setsCount = ((Spinner) v.findViewById(R.id.sets_spinner));
		ArrayAdapter<CharSequence> spinnerAdapterSets = ArrayAdapter
				.createFromResource(getActivity(), R.array.sets_array,
						android.R.layout.simple_spinner_dropdown_item);
		setsCount.setAdapter(spinnerAdapterSets);

		repsCount = ((Spinner) v.findViewById(R.id.reps_spinner));
		ArrayAdapter<CharSequence> spinnerAdapterReps = ArrayAdapter
				.createFromResource(getActivity(), R.array.reps_array,
						android.R.layout.simple_spinner_dropdown_item);
		repsCount.setAdapter(spinnerAdapterReps);
		
		weight = ((EditText) v.findViewById(R.id.weight));

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Workout workout = ((NewWorkoutActivity) getActivity()).getCurrentWorkout();

				// When the user clicks "Save," upload the meal to Parse
				// Add data to the meal object:
				workout.setTitle(workoutName.getText().toString());

				// Associate the meal with the current user
				workout.setAuthor(ParseUser.getCurrentUser());

				// Add the rating
				workout.setEx1Reps(repsCount.getSelectedItem().toString());
				workout.setEx1Sets(setsCount.getSelectedItem().toString());

				// If the user added a photo, that data will be
				// added in the CameraFragment

				// Save the meal and return
				workout.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});

			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		// Until the user has taken a photo, hide the preview
	//	mealPreview = (ParseImageView) v.findViewById(R.id.meal_preview_image);
	//	mealPreview.setVisibility(View.INVISIBLE);

		return v;
	}

	/*
	 * All data entry about a Meal object is managed from the NewMealActivity.
	 * When the user wants to add a photo, we'll start up a custom
	 * CameraFragment that will let them take the photo and save it to the Meal
	 * object owned by the NewMealActivity. Create a new CameraFragment, swap
	 * the contents of the fragmentContainer (see activity_new_meal.xml), then
	 * add the NewMealFragment to the back stack so we can return to it when the
	 * camera is finished.
	 */
	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("NewMealFragment");
		transaction.commit();
	}

	/*
	 * On resume, check and see if a meal photo has been set from the
	 * CameraFragment. If it has, load the image in this fragment and make the
	 * preview image visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((NewWorkoutActivity) getActivity())
				.getCurrentWorkout().getPhotoFile();
		if (photoFile != null) { 
	//		mealPreview.setParseFile(photoFile);
		//	mealPreview.loadInBackground(new GetDataCallback() {
	//			@Override
			//	public void done(byte[] data, ParseException e) {
			//		mealPreview.setVisibility(View.VISIBLE);
			//	}
		//	});
		}
	}

}