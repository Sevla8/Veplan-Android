package ca.uqam.veplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
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

public class MainActivity extends AppCompatActivity implements
		View.OnClickListener,
		CompoundButton.OnCheckedChangeListener,
		AdapterView.OnItemSelectedListener {

	private EditText startDirection, stopDirection, autonomy, batteryLevel, date, time;
	private RadioButton arrivingTime, departureTime;
	private Spinner cars;
	private Switch customAutonomy;
	private Button datePicker, timePicker, submit;
	private AwesomeValidation awesomeValidation;
	private int mYear, mMonth, mDay, mHour, mMinute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.startDirection = findViewById(R.id.startDirection);
		this.stopDirection = findViewById(R.id.stopDirection);
		this.arrivingTime = findViewById(R.id.arrivingTime);
		this.departureTime = findViewById(R.id.departureTime);
		this.cars = findViewById(R.id.cars);
		this.customAutonomy = findViewById(R.id.customAutonomy);
		this.autonomy = findViewById(R.id.autonomy);
		this.batteryLevel = findViewById(R.id.batteryLevel);
		this.submit = findViewById(R.id.submit);
		this.datePicker = findViewById(R.id.btn_date);
		this.timePicker = findViewById(R.id.btn_time);
		this.date = findViewById(R.id.in_date);
		this.time = findViewById(R.id.in_time);

		this.datePicker.setOnClickListener(this);
		this.timePicker.setOnClickListener(this);
		this.submit.setOnClickListener(this);
		this.customAutonomy.setOnCheckedChangeListener(this);
		this.cars.setOnItemSelectedListener(this);

		this.awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

		this.autonomy.setEnabled(false);

		this.awesomeValidation.addValidation(this, R.id.startDirection,
				RegexTemplate.NOT_EMPTY, R.string.err_startDirection);
		this.awesomeValidation.addValidation(this, R.id.stopDirection,
				RegexTemplate.NOT_EMPTY, R.string.err_stopDirection);
		this.awesomeValidation.addValidation(this, R.id.autonomy,
				"[0-9]+", R.string.err_autonomy);
		this.awesomeValidation.addValidation(this, R.id.batteryLevel,
				Range.closed(0, 100), R.string.err_batteryLevel);
//		this.awesomeValidation.addValidation(this, R.id.date, Range.closed(Calendar.getInstance().getTime(), 100), R.string.err_batteryLevel);
//		this.awesomeValidation.addValidation(this, R.id.time, Range.closed(0, 100), R.string.err_batteryLevel);
	}

	@Override
	public void onClick(View v) {
		if (v == this.datePicker) {
			// Get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog datePickerDialog = new DatePickerDialog(
					this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view,
						                      int year,
						                      int monthOfYear,
						                      int dayOfMonth) {
							date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
						}
					},
					mYear,
					mMonth,
					mDay);
			datePickerDialog.show();
		}

		else if (v == this.timePicker) {
			// Get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog timePickerDialog = new TimePickerDialog(
					this,
					new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker view,
						                      int hourOfDay,
						                      int minute) {
							time.setText(hourOfDay + ":" + minute);
						}
					},
					mHour,
					mMinute,
					false);
			timePickerDialog.show();
		}

		else if (v == this.submit) {
			if (awesomeValidation.validate()) {
//				Toast.makeText(getApplicationContext(), "Form validate succeflly", Toast.LENGTH_SHORT).show();

				// Get values
				String startDirection = this.startDirection.getText().toString();
				String stopDirection = this.stopDirection.getText().toString();
			}
			else {
				Toast.makeText(getApplicationContext(), "Validation failed",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			this.autonomy.setEnabled(true);
			this.cars.setEnabled(false);
		} else {
			this.autonomy.setEnabled(false);
			this.cars.setEnabled(true);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
