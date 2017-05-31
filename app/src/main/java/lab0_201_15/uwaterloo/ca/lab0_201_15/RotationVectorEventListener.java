package lab0_201_15.uwaterloo.ca.lab0_201_15;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by Loic Murumba on 2017-05-03.
 */

public class RotationVectorEventListener implements SensorEventListener {

    TextView output;
    TextView max;
    static float[] maxval;

    public RotationVectorEventListener(TextView outputView, TextView maxText) {
        output = outputView;
        max =maxText;
        maxval = new float[] {0,0,0};
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }
    public static void clearMax(){
        maxval = new float[]{0,0,0};
    }

    public void onSensorChanged(SensorEvent se) {

        if (se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            String s = String.format("(%.2f, %.2f, %.2f)", se.values[0], se.values[1], se.values[2]);
            output.setText(s);
            if (Math.sqrt(Math.pow(se.values[0], 2) + Math.pow(se.values[1], 2) + Math.pow(se.values[2], 2)) > Math.sqrt(Math.pow(maxval[1], 2) + Math.pow(maxval[0], 2) + Math.pow(maxval[2], 2))){
                maxval = new float[] {se.values[0], se.values[1], se.values[2]};
            }
            String m = String.format("(%.2f, %.2f, %.2f)", maxval[0], maxval[1], maxval[2]);
            max.setText(m);

        }
    }
}