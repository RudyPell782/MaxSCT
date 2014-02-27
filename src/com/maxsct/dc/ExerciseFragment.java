package com.maxsct.dc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

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

import com.maxsct.dc.R;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    private int exerciseID;	
    private String bestWeight;
    private String[] exerciseList = {"DB Flat Press", "HS Shoulder Press", "CG Bench Press","Wide Pulldowns", "T-Bar Row", "BB Curl", "Reverse-grip Curl", "Seated Calf",
    		"Lying Ham", "Front Squat","HS Incline", "DB OHP", "JM press","CG Pulldown", "BB Row","DB Curl", "Hammer Curl", "Standing Calf","Seated Ham", "Leg Press"};
	private String[] hints1 = {"Rest Pause", "11-15"};
	private String[] hints2 = {"2", "8-10, 20"};
	private String[] hints3 = {"2", "6-9"};
	
	// Map Exercises to their respective hint values
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
	    exerciseID = getArguments().getInt("exercise");  
	    workoutID = getArguments().getInt("workoutID");  
		View v = inflater.inflate(R.layout.fragment_exercise, parent, false);
		
		

		exerciseName = (TextView) v.findViewById(R.id.exercise_name);
		exerciseName.setText(exerciseNameLabel);
		setsHint = (TextView) v.findViewById(R.id.sets_hint);
		setsHint.setText(getValue(exerciseNameLabel, 0));
		repsHint = (TextView) v.findViewById(R.id.reps_hint);
		repsHint.setText(getValue(exerciseNameLabel, 1));
		
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
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Exercise");
		query.whereEqualTo("name", exerciseNameLabel);
		query.orderByDescending("weight");
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject object, ParseException e) {
		    if (object == null) {
		      Log.i("score", "The getFirst request failed.");
		    } else {
		      Log.d("score", "Retrieved the object.");
		      Log.i("Parse", Integer.toString(object.getInt("weight")));
		      bestWeight = Integer.toString(object.getInt("weight"));
		      weight.setHint("Best: " + bestWeight);
		    }
		  }
		});
		

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Exercise exercise = new Exercise();

				// When the user clicks "Save," upload the exercise to Parse
				// Add data to the exercise object:
				exercise.setName(exerciseNameLabel);
				exercise.setDate(new Date());

				// Associate the exercise with the current user
				exercise.setAuthor(ParseUser.getCurrentUser());

				// Add the user input data
				exercise.setReps(repsCount.getSelectedItem().toString());
				exercise.setSets(setsCount.getSelectedItem().toString());
				exercise.setWeight(Integer.parseInt(weight.getText().toString()));

				

				// Save the exercise and return
				exercise.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
						((NewWorkoutActivity) getActivity()).setExerciseComplete(exerciseID, true);
							getFragmentManager().popBackStackImmediate();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
							Log.i(TAG, e.getMessage());
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

		return v;
	}

	

	
	@Override
	public void onResume() {
		super.onResume();
	}

}