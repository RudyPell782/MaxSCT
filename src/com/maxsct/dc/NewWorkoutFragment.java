package com.maxsct.dc;

import java.util.Date;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/*
 * This fragment manages the data entry for a
 * new Meal object. It lets the user input a 
 * meal name, give it a rating, and take a 
 * photo. If there is already a photo associated
 * with this meal, it will be displayed in the 
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewWorkoutFragment extends Fragment {

	private Button exercise1Button;
	private Button exercise2Button;
	private Button exercise3Button;
	private Button exercise4Button;
	private Button exercise5Button;
	private Spinner workoutType;
	private Button saveButton;
	private Button cancelButton;
	private String workoutName;
	private String[] exerciseList;
	private String[] exerciseList1A = {"DB Flat Press", "HS Shoulder Press", "CG Bench Press","Wide Pulldowns", "T-Bar Row"};
	private String[] exerciseList1B = {"BB Curl", "Reverse grip curl", "seated calf","lying Ham", "Front Squat"};
	private String[] exerciseList2A = {"HS incline", "DB OHP", "JM press","CG Pulldown", "BB Row"};
	private String[] exerciseList2B = {"DB Curl", "hammer curl", "standing calf","Seated Ham", "Leg Press"};
	private final static String TAG = "Workout";
	
	//private Spinner mealRating;
	//private ParseImageView mealPreview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_new_workout, parent, false);
		
		
		//workoutName = ((EditText) v.findViewById(R.id.workout_label));
		workoutType = ((Spinner) v.findViewById(R.id.workout_label));
		ArrayAdapter<CharSequence> spinnerAdapterWorkout = ArrayAdapter
				.createFromResource(getActivity(), R.array.workout_array,
						android.R.layout.simple_spinner_dropdown_item);
		workoutType.setAdapter(spinnerAdapterWorkout);
		
		workoutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        //workoutName = parent.getItemAtPosition(pos).toString();
		        workoutName = workoutType.getSelectedItem().toString();
		        Log.i(TAG, "name" + workoutName);
		        if (workoutName.equals("1A")){
		        	exerciseList = exerciseList1A;
		        	updateButtonText();
		        	Log.i(TAG, "1A IF" );
		        }
		        else if (workoutName.equals("1B")){
		        	exerciseList = exerciseList1B;
		        	updateButtonText();
		        	Log.i(TAG, "1b IF" );
		        }
		        else if (workoutName.equals("2A")){
		        	exerciseList = exerciseList2A;
		        	updateButtonText();
		        	Log.i(TAG, "2A IF" );
		        }
		        else if (workoutName.equals("2B")){
		        	exerciseList = exerciseList2B;
		        	updateButtonText();
		        	Log.i(TAG, "2b IF" );
		        }
		        else
		        	exerciseList = exerciseList1A;
		            updateButtonText();
		            Log.i(TAG, "default IF" );
		    }
			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
				exerciseList = exerciseList1A;
				updateButtonText();
				Log.i(TAG, "nothing IF" );
		    }
		});
		

		exercise1Button = ((Button) v.findViewById(R.id.exercise1_button));
		exercise1Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(workoutName.getWindowToken(), 0);
				startExercise(workoutName, 1, 1, exerciseList[0]);
			}
		});
		
		exercise2Button = ((Button) v.findViewById(R.id.exercise2_button));
		exercise2Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(workoutName.getWindowToken(), 0);
				startExercise(workoutName, 2, 1, exerciseList[1]);
			}
		});
		
		exercise3Button = ((Button) v.findViewById(R.id.exercise3_button));
		exercise3Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(workoutName.getWindowToken(), 0);
				startExercise(workoutName, 3, 1, exerciseList[2]);
			}
		});
		exercise4Button = ((Button) v.findViewById(R.id.exercise4_button));
		exercise4Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(workoutName.getWindowToken(), 0);
				startExercise(workoutName, 4, 1, exerciseList[3]);
			}
		});
		exercise5Button = ((Button) v.findViewById(R.id.exercise5_button));
		exercise5Button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(workoutName.getWindowToken(), 0);
				startExercise(workoutName, 5, 1, exerciseList[4]);
			}
		});

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Workout workout = ((NewWorkoutActivity) getActivity()).getCurrentWorkout();

				// When the user clicks "Save," upload the meal to Parse
				// Add data to the workout object:
				workout.setTitle(workoutName.toString());
				workout.setDate(new Date());

				// Associate the workout with the current user
				workout.setAuthor(ParseUser.getCurrentUser());

				

				// Save the workout and return
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

		return v;
	}

	/*
	 * All data entry about a WOrkout object is managed from the NewWorkoutActivity.
	 * When the user wants to add a exercise, we'll start up a custom
	 * ExerciseFragment that will let them create an Exercise
	 */
	public void startExercise(String workout, int exercise, int workoutID, String exerciseName) {
		Fragment exerciseFragment = new ExerciseFragment();
		Bundle args = new Bundle();
	    args.putInt("exercise", exercise);
	    args.putInt("workoutID", workoutID);
	    args.putString("workout", workout);
	    args.putString("exerciseName", exerciseName);
	    exerciseFragment.setArguments(args);
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, exerciseFragment);
		transaction.addToBackStack("NewWorkoutFragment");
		transaction.commit();
	}
	
	
	public void updateButtonText() {
		exercise1Button.setText(exerciseList[0]);
		Log.i(TAG, "ex1 text" + exerciseList[0] );
		exercise2Button.setText(exerciseList[1]);
		Log.i(TAG, "ex2 text" + exerciseList[1] );
		exercise3Button.setText(exerciseList[2]);
		exercise4Button.setText(exerciseList[3]);
		exercise5Button.setText(exerciseList[4]);
		
	}

	/*
	 * On resume, check and see if a meal photo has been set from the
	 * CameraFragment. If it has, load the image in this fragment and make the
	 * preview image visible.
	 */
	@Override
	public void onResume() {
		super.onResume();
		
	}

}
