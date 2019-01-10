package com.inducesmile.colorpickers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

public class ColorPicker implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = ColorPicker.class.getSimpleName();

    private Context context;

    private AlertDialog alertDialog;


    private LinearLayoutCompat colorPreview;

    AppCompatTextView redBarCount, greenBarCount, blueBarCount;

    AppCompatButton okButton, cancelButton;

    private int redValue, greenValue, blueValue = 0;

    AppCompatSeekBar redSeekBar, greenSeekBar, blueSeekBar;

    public OnColorSelectChangeListener onColorSelectChangeListener;

    private ColorConverter colorConverter;

    private int currentColor;


    public ColorPicker(Context context) {
        this.context = context;
        colorConverter = new ColorConverter(context);
        currentColor = R.color.colorBackground;
        initColorPicker();
    }


    public void initColorPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.color_picker_layout, null);
        builder.setView(view);

        colorPreview = (LinearLayoutCompat) view.findViewById(R.id.color_preview);

        redBarCount = (AppCompatTextView)view.findViewById(R.id.red_bar_count);
        greenBarCount = (AppCompatTextView)view.findViewById(R.id.green_bar_count);
        blueBarCount = (AppCompatTextView)view.findViewById(R.id.blue_bar_count);

        redSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_red);
        greenSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_green);
        blueSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_blue);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        builder.setCancelable(false);
        alertDialog = builder.create();

        okButton = (AppCompatButton) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onColorSelectChangeListener.onColorChange(currentColor);
                alertDialog.dismiss();
            }
        });


        cancelButton = (AppCompatButton)view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }


    public void show(){
        alertDialog.show();
    }


    public void hide(){
        alertDialog.hide();
    }


    public void setDefaultColor(int backgroundColor){
        colorPreview.setBackgroundColor(backgroundColor);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "Log progress " + progress);
        int id = ((SeekBar)seekBar).getId();
        if(id == R.id.color_bar_red){
            redValue = progress;
        }
        if(id == R.id.color_bar_green){
            greenValue = progress;
        }
        if(id == R.id.color_bar_blue){
            blueValue = progress;
        }
        currentColor = colorConverter.convertIntValuesToColor(redValue, greenValue, blueValue);
        Log.d(TAG, "Log current color " + progress);
        setDefaultColor(currentColor);
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    public void setOnColorSelectChangeListener(OnColorSelectChangeListener onColorSelectChangeListener){
        this.onColorSelectChangeListener = onColorSelectChangeListener;
    }

}
