package ca.uqam.veplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.common.collect.Range;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
	EditText startDirection, stopDirection, autonomy, batteryLevel;
	RadioButton arrivingTime, departureTime;
	Spinner cars;
	Switch customAutonomy;
	Button date, time, submit;

	AwesomeValidation awesomeValidation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.startDirection = findViewById(R.id.startDirection);
		this.stopDirection = findViewById(R.id.stopDirection);
		this.arrivingTime = findViewById(R.id.arrivingTime);
		this.departureTime = findViewById(R.id.departureTime);
		this.date = findViewById(R.id.date);
		this.time = findViewById(R.id.time);
		this.cars = findViewById(R.id.cars);
		this.customAutonomy = findViewById(R.id.customAutonomy);
		this.autonomy = findViewById(R.id.autonomy);
		this.batteryLevel = findViewById(R.id.batteryLevel);
		this.submit = findViewById(R.id.submit);

		this.date.setOnClickListener((View.OnClickListener) this);
		this.time.setOnClickListener((View.OnClickListener) this);

		this.awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

		this.awesomeValidation.addValidation(this, R.id.startDirection, RegexTemplate.NOT_EMPTY, R.string.err_startDirection);
		this.awesomeValidation.addValidation(this, R.id.stopDirection, RegexTemplate.NOT_EMPTY, R.string.stoptDirection);
		this.awesomeValidation.addValidation(this, R.id.autonomy, "[0-9]+", R.string.err_autonomy);
		this.awesomeValidation.addValidation(this, R.id.batteryLevel, Range.closed(0, 100), R.string.err_batteryLevel);

		this.submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (awesomeValidation.validate()) {
					Toast.makeText(getApplicationContext(), "Form validate succeflly", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getApplicationContext(), "Validation failed", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

//	public void onClick(View v) {
//		TimePickerDialog tp1 = new TimePickerDialog(this, this, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
//		tp1.show();
//	}
//
//	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
//		cal.set(Calendar.MINUTE, minute);
//		rest of the code
//	}
}
