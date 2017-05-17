package lab0_201_15.uwaterloo.ca.lab0_201_15;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

/**
 * Created by Loic Murumba on 2017-05-03.
 */

public class LightSensorEventListener implements SensorEventListener {

    TextView output;
    TextView max;
    static float maxval;
    public LightSensorEventListener(TextView outputView, TextView max) {
        output = outputView;
        this.max = max;
        maxval = 0;
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }
    static public void clearMax(){
        maxval = 0;
    }

    public void onSensorChanged(SensorEvent se) {
        if (se.sensor.getType() == Sensor.TYPE_LIGHT) {
            output.setText(String.valueOf(se.values[0]));
            if (se.values[0] > maxval){
                maxval = se.values[0];

            }
            max.setText(String.valueOf(String.valueOf(maxval)));
        }
    }
}