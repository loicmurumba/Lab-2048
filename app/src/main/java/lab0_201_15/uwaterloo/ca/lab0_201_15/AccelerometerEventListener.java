package lab0_201_15.uwaterloo.ca.lab0_201_15;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import java.util.ArrayList;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by Loic Murumba on 2017-05-03.
 */

public class AccelerometerEventListener implements SensorEventListener {

    TextView output;
    TextView max;
    static float[] maxval;
    LineGraphView graph;
    static ArrayList<float[]> historical;

    public AccelerometerEventListener(TextView outputView, TextView maxText, LineGraphView graph) {
        output = outputView;
        max =maxText;
        this.graph = graph;
        maxval = new float[] {0,0,0};
        historical = new ArrayList<float[]>();

    }
    public static ArrayList<float[]> getHistorical(){
        return historical;
    }

    public void onAccuracyChanged(Sensor s, int i) {
    }
    public static void clearMax(){
        maxval = new float[]{0,0,0};
    }

    public void onSensorChanged(SensorEvent se) {

        if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            String s = String.format("(%.2f, %.2f, %.2f)", se.values[0], se.values[1], se.values[2]);
            output.setText(s);
            graph.addPoint(new float[]{se.values[0], se.values[1], se.values[2]});
            if (Math.sqrt(Math.pow(se.values[0], 2) + Math.pow(se.values[1], 2) + Math.pow(se.values[2], 2)) > Math.sqrt(Math.pow(maxval[1], 2) + Math.pow(maxval[0], 2) + Math.pow(maxval[2], 2))){
                maxval = new float[] {se.values[0], se.values[1], se.values[2]};
            }
            String m = String.format("(%.2f, %.2f, %.2f)", maxval[0], maxval[1], maxval[2]);
            max.setText(m);

            historical.add(0, new float[] {se.values[0], se.values[1], se.values[2]});
            if (historical.size() == 101){
                historical.remove(100);
            }

        }
    }
}