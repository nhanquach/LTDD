package nhanquach.com.homework01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Khai bao
    Button btnRed, btnYellow, btnBlue, btnBlack;
    TextView tx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gan btn
        btnRed = (Button) findViewById(R.id.buttonRed);
        btnBlue = (Button) findViewById(R.id.buttonBlue);
        btnBlack = (Button) findViewById(R.id.buttonBlack);
        btnYellow = (Button) findViewById(R.id.buttonYellow);

        //Gan TextView
        tx = (TextView) findViewById(R.id.textView);

        //Set Listener
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setBackgroundResource(R.color.black);
            }
        });

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setBackgroundResource(R.color.blue);
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setBackgroundResource(R.color.red);
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setBackgroundResource(R.color.yellow);
            }
        });

    }
}
