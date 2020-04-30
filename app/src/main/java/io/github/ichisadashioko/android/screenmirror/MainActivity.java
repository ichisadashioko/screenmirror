package io.github.ichisadashioko.android.screenmirror;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logTextView = (TextView) findViewById(R.id.log);
        Logger.LOG_TEXT_VIEW = logTextView;
    }

    public void captureScreenBtnPressed(View view){
        captureScreen(this);
    }

    public static Bitmap captureScreen(Activity activity) {
        String[] captureProgramPaths = {"/dev/graphics/fb0", "/system/bin/screencap", "/system/bin/screenshot"};
        String availableCaptureProgram = null;

        for (int i = 0; i < captureProgramPaths.length; i++) {
            File programPath = new File(captureProgramPaths[i]);

            if (programPath.exists()) {
                availableCaptureProgram = captureProgramPaths[i];
                Logger.info(availableCaptureProgram + " is available!");
                break;
            }
        }

        if (availableCaptureProgram == null) {
            Logger.info("no available program for capturing screen");
            return null;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        Logger.info("width: " + width + " - height: " + height);
        // not sure if we will get 3 channels or not
        Bitmap frame = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        return frame;
    }
}