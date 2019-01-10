package com.inducesmile.colorpickers;

import android.content.Context;
import android.graphics.Color;

public class ColorConverter {

    private static final String TAG = ColorConverter.class.getSimpleName();

    private Context context;


    public ColorConverter(Context context) {
        this.context = context;
    }


    public int convertIntValuesToColor(int redColor, int greenColor, int blueColor){
        return Color.rgb(redColor, greenColor, blueColor);
    }


    public String ConvertColorToHexCode(int redColor, int greenColor, int blueColor ){
        int colorMix = Color.rgb(redColor, greenColor, blueColor);
        return String.format("#%06X", (0xFFFFFF & colorMix));
    }

}
