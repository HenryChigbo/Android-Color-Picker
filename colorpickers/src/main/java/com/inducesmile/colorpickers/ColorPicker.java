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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorPicker implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = ColorPicker.class.getSimpleName();

    private Context context;

    private AlertDialog alertDialog;

    @BindView(R.id.color_preview)
    LinearLayoutCompat colorPreview;

    @BindView(R.id.red_bar_count)
    AppCompatTextView redBarCount;

    @BindView(R.id.green_bar_count)
    AppCompatTextView greenBarCount;

    @BindView(R.id.blue_bar_count)
    AppCompatTextView blueBarCount;

    @BindView(R.id.ok_button)
    AppCompatButton okButton;

    @BindView(R.id.cancel_button)
    AppCompatButton cancelButton;


    private int redValue, greenValue, blueValue = 0;


    AppCompatSeekBar redSeekBar, greenSeekBar, blueSeekBar;

    public OnColorSelectChangeListener onColorSelectChangeListener;

    private ColorConverter colorConverter;

    private int currentColor;


    public ColorPicker(Context context) {
        this.context = context;
        colorConverter = new ColorConverter(context);
        currentColor = R.color.colorPrimary;
        initColorPicker();
    }


    public void initColorPicker(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.color_picker_layout, null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        redSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_red);
        greenSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_green);
        blueSeekBar = (AppCompatSeekBar)view.findViewById(R.id.color_bar_blue);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        builder.setCancelable(false);
        alertDialog = builder.create();
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
        switch (id){
            case R.id.color_bar_red:
                redValue = progress;
                break;
            case R.id.color_bar_green:
                greenValue = progress;
                break;
            case R.id.color_bar_blue:
                blueValue = progress;
                break;
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


    @OnClick(R.id.ok_button)
    public void onOkButtonClick(View view){
        onColorSelectChangeListener.onColorChange(currentColor);
        alertDialog.dismiss();
    }


    @OnClick(R.id.cancel_button)
    public void onCancelButtonClick(View view){
        alertDialog.dismiss();
    }


    public void setOnColorSelectChangeListener(OnColorSelectChangeListener onColorSelectChangeListener){
        this.onColorSelectChangeListener = onColorSelectChangeListener;
    }

}
