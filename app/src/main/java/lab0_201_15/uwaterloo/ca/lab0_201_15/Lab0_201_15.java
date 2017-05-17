package lab0_201_15.uwaterloo.ca.lab0_201_15;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;

public class Lab0_201_15 extends AppCompatActivity {

    private LinearLayout ll;
    private LightSensorEventListener lsel;
    private MagneticFieldEventListener mfel;
    private AccelerometerEventListener ael;
    int filesCount;
    private RotationVectorEventListener rvel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab0_201_15);


        LineGraphView graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x","y","z"));

        graph.setVisibility(View.VISIBLE);


        SensorManager sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor lightSensor =sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        int filesCount = 0;
        Button clearRecords = new Button(getApplicationContext());
        Button generateCSV = new Button(getApplicationContext());


        TextView[] texts = {
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext()),
                new TextView(getApplicationContext()), new TextView(getApplicationContext())
        };

        texts[0].setText("Light Sensor Reading:");
        texts[2].setText("Record High Light Sensor Reading:");
        texts[4].setText("Accelerometer:");
        texts[6].setText("Record High Accelerometer:");
        texts[8].setText("Magnetic Field:");
        texts[10].setText("Record High Magnetic Field:");
        texts[12].setText("Rotation Vector:");
        texts[14].setText("Record High Rotation Vector:");

        LightSensorEventListener lsel = new LightSensorEventListener(texts[1],texts[3]);
        sensorManager.registerListener(lsel, lightSensor,SensorManager.SENSOR_DELAY_NORMAL);

        AccelerometerEventListener ael = new AccelerometerEventListener(texts[5],texts[7], graph);
        sensorManager.registerListener(ael, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        MagneticFieldEventListener mfel = new MagneticFieldEventListener(texts[9],texts[11]);
        sensorManager.registerListener(mfel, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_NORMAL);

        RotationVectorEventListener rvel = new RotationVectorEventListener(texts[13],texts[15]);
        sensorManager.registerListener(rvel, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),SensorManager.SENSOR_DELAY_NORMAL);



        LinearLayout ll = (LinearLayout) findViewById(R.id.layout);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(graph);

        clearRecords.setText("Clear Recorded Max Values");
        generateCSV.setText("Generate CSV");
        ll.addView(clearRecords);
        ll.addView(generateCSV);

        clearRecords.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearMaxes();
            }
        });
        generateCSV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                writeFile();
            }
        });
        for (TextView text : texts) {
            ll.addView(text);
        }

    }
    private void clearMaxes(){
        AccelerometerEventListener.clearMax();
        LightSensorEventListener.clearMax();
        RotationVectorEventListener.clearMax();
        MagneticFieldEventListener.clearMax();
    }
    private void writeFile(){
        ArrayList<float[]> dataAccel = AccelerometerEventListener.getHistorical();
        File myFile = null;
        PrintWriter myPTR = null;
        filesCount++;
        try{
            myFile = new File(getExternalFilesDir("CSV"), "output.txt");

            myPTR = new PrintWriter(myFile);

            for (float[] vals: dataAccel){
                int i = 0;
                myPTR.println(String.format("(%.2f, %.2f, %.2f)",vals[0],vals[1],vals[2]));
                myPTR.print(",");
            }
            myPTR.println("test");
            System.out.print("no e");

        }
        catch (IOException e){
            System.out.print("EXEPTON " + e);
        }
        finally {
            if (myPTR != null){

                myPTR.close();
            }
        }
    }
}
