package com.example.tp5_batterie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button boutonOk;
    private Button boutonStop;
    private TextView niveau;
    private TextView resultat;

    final recepteurEtatBatterie toto = new recepteurEtatBatterie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boutonOk=(Button) findViewById(R.id.boutonOk);
        boutonStop=(Button) findViewById(R.id.boutonStop);
        resultat=(TextView) findViewById(R.id.resultat);

        boutonStop.setEnabled(false);


        boutonStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resultat.setText("Affichage arrêté");
                boutonOk.setEnabled(true);
                boutonStop.setEnabled(false);
                unregisterReceiver(toto);
            }
        });
        boutonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resultat.setText("Affichage en marche");
                boutonOk.setEnabled(false);
                boutonStop.setEnabled(true);
                registerReceiver(toto,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        });



    }

    public class recepteurEtatBatterie extends BroadcastReceiver{

        private int valeurCharge;
        private int valeurMax;
        private int pctCharge;
        private double temperature;
        private int tension;

        @Override
        public void onReceive(Context context, Intent intent){
            Log.i("Batterie","Intent reçu");

            valeurCharge= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            valeurMax=intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            pctCharge=(valeurCharge*100)/valeurMax;
            tension=intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
            niveau.setText("Tension batterie= "+ Integer.toString(tension)+ " mV");
        }
    }
}
