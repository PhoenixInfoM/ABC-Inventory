package com.backofficecloudapps.prop.inventory.other;
/**
 * Created by Phoenix
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backofficecloudapps.prop.inventory.R;


/**
 * The type Custom number picker.
 */
public class CustomNumberPicker extends LinearLayout {

  private int number;
  private TextView numberTV;
  private int MAX_VALUE=1000, MIN_VALUE=-1000;

	/**
	 * Instantiates a new Custom number picker.
	 *
	 * @param context the context
	 */
	public CustomNumberPicker(@NonNull Context context){
	this(context, null);
  }

	/**
	 * Instantiates a new Custom number picker.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public CustomNumberPicker(@NonNull Context context, AttributeSet attrs){
	super(context, attrs);
	setOrientation(HORIZONTAL);
	setGravity(Gravity.CENTER_VERTICAL);

	LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	inflater.inflate(R.layout.number_picker, this, true);

	numberTV=(TextView) findViewById(R.id.number_pointer);
	Button up=(Button) findViewById(R.id.number_magnifier);
	up.setOnClickListener(new OnClickListener(){
	  @Override
	  public void onClick(View v){
		if(number>=MAX_VALUE)
		  number = MAX_VALUE;
		else
		  number++;
		numberTV.setText(String.valueOf(number));
		if(listener!=null){
		  listener.change(number);
		}
	  }
	});

	Button down=(Button) findViewById(R.id.number_decreaser);
	down.setOnClickListener(new OnClickListener(){
	  @Override
	  public void onClick(View v){
		if(number <= MIN_VALUE)
		  number = MIN_VALUE;
		else
		  number--;
		numberTV.setText(String.valueOf(number));
		if(listener!=null){
		  listener.change(number);
		}
	  }
	});
  }

	/**
	 * Set value.
	 *
	 * @param number the number
	 */
	public void setValue(int number){
	this.number=number;
	numberTV.setText(String.valueOf(number));
  }

	/**
	 * Get value int.
	 *
	 * @return the int
	 */
	public int getValue(){
	return number;
  }

	/**
	 * Set max value.
	 *
	 * @param maxValue the max value
	 */
	public void setMaxValue(int maxValue){
	this.MAX_VALUE=maxValue;
  }

	/**
	 * Set min value.
	 *
	 * @param minValue the min value
	 */
	public void setMinValue(int minValue){
	this.MIN_VALUE=minValue;
  }

  private ChangeListener listener;

	/**
	 * Set change listener.
	 *
	 * @param changeListener the change listener
	 */
	public void setChangeListener(ChangeListener changeListener){
	listener=changeListener;
  }

	/**
	 * The interface Change listener.
	 */
	public interface ChangeListener{
		/**
		 * Change.
		 *
		 * @param newValue the new value
		 */
		void change(int newValue);
  }
}
