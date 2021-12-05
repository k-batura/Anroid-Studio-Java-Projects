package com.example.mcalcpro2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ca.roumani.i2c.MPro;

public class MCalcPro_Activity extends AppCompatActivity
        implements TextToSpeech.OnInitListener, SensorEventListener {

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
        this.tts = new TextToSpeech(this, this);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void buttonClicked(View v)
    {
        EditText pBox = findViewById(R.id.pBox);
        EditText aBox = findViewById(R.id.aBox);
        EditText iBox = findViewById(R.id.iBox);

        String p = pBox.getText().toString();
        String a = aBox.getText().toString();
        String i = iBox.getText().toString();

        TextView outbox = findViewById(R.id.output);

        MPro mp = new MPro();

        try
        {
            mp.setPrinciple(p);
            mp.setAmortization(a);
            mp.setInterest(i);

            String output = "Monthly Payment = " + mp.computePayment("%.2f");
            tts.speak(output, TextToSpeech.QUEUE_FLUSH, null);
            output += "\n\n";
            output += "By making this payment monthly for 20 years, the mortgage will be payed in full."
                    + "But  if you terminate the mortgage on its nth anniversary, the balance still owing"
                    + "Depends on n as shown below.";
            output += "\n\n\n\n";

            for (int n = 0; n <= 20; n++)
            {
                output += String.format("%8d", n) + mp.outstandingAfter(n, "%,16.0f");
                output += "\n\n";

                if (n == 5 || n == 10 || n == 15)
                {
                    n += 4;
                }
            }

            outbox.setText(output);

        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInit(int status) { this.tts.setLanguage(Locale.US); }

    @Override
    public void onSensorChanged (SensorEvent event) {
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];

        double a = Math.sqrt(ax*ax + ay*ay + az*az);

        if(a>20)
        {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
