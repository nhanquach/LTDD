package nhanquach.com.homework02;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Khai bao
    Button buttonSend;
    RadioGroup RGsize, RGTortilla;
    CheckBox beef, chicken, white_fish, cheese, seafood, rice, beans, pico, gua, lbt;
    CheckBox soda, cerveza, margarita, tequila;
    RadioButton large, medium, corn, flour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);RelativeLayout activitymain = (RelativeLayout) findViewById(R.id.activity_main);

        buttonSend = (Button) findViewById(R.id.button);

        tequila = (CheckBox) findViewById(R.id.tequila);
        margarita = (CheckBox) findViewById(R.id.margarita);
        cerveza = (CheckBox) findViewById(R.id.cerveza);
        soda = (CheckBox) findViewById(R.id.soda);
        lbt = (CheckBox) findViewById(R.id.lbt);
        gua = (CheckBox) findViewById(R.id.guacamole);
        pico = (CheckBox) findViewById(R.id.picodegallo);
        beans = (CheckBox) findViewById(R.id.beans);
        rice = (CheckBox) findViewById(R.id.rice);
        seafood = (CheckBox) findViewById(R.id.seafood);
        cheese = (CheckBox) findViewById(R.id.cheese);
        white_fish = (CheckBox) findViewById(R.id.whitefish);
        chicken = (CheckBox) findViewById(R.id.chicken);
        beef = (CheckBox) findViewById(R.id.beef);

        RGsize = (RadioGroup) findViewById(R.id.size);
        RGTortilla = (RadioGroup) findViewById(R.id.tortilla);

        large = (RadioButton) findViewById(R.id.large);
        medium = (RadioButton) findViewById(R.id.med);
        corn = (RadioButton) findViewById(R.id.corn);
        flour = (RadioButton) findViewById(R.id.flour);

        //Click buttonSend
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultString = "You choose: ";
                if (RGTortilla.getCheckedRadioButtonId() > -1
                        && RGsize.getCheckedRadioButtonId() > -1) {
                    RadioButton tempR = (RadioButton)
                            findViewById(RGsize.getCheckedRadioButtonId());
                    resultString = resultString + ""+ tempR.getText().toString() +" size, ";
                    tempR = (RadioButton)
                            findViewById(RGTortilla.getCheckedRadioButtonId());
                    resultString = resultString + " tortilla: "+ tempR.getText().toString()
                            +". \n Fillings: ";
                }else {
                    resultString = "You need to choose Size and Tortilla.";
                }

                List<CheckBox> fillings = new ArrayList<CheckBox>();
                fillings.add(beef);fillings.add(chicken); fillings.add(white_fish);
                fillings.add(beans);fillings.add(cheese); fillings.add(seafood);
                fillings.add(rice); fillings.add(pico); fillings.add(gua); fillings.add(lbt);
                for (CheckBox fill : fillings){
                    if (fill.isChecked()){
                        resultString += " " + fill.getText().toString() +" ";
                    }
                }

                resultString += "\n Beverage:";

                List<CheckBox> beverage = new ArrayList<CheckBox>();
                beverage.add(soda); beverage.add(margarita);
                beverage.add(cerveza); beverage.add(tequila);
                for (CheckBox b : beverage){
                    if (b.isChecked()){
                        resultString += " " + b.getText().toString() + " ";
                    }
                }

                resultString+= ".";

                Toast toast = Toast.makeText(getBaseContext(), resultString, Toast.LENGTH_SHORT);
                toast.show();

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5554", null, resultString,null, null);
            }
        });

    }



}
