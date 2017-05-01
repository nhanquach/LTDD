package nhanquach.com.homework02;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    String resultString = "You choose: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                if (RGTortilla.getCheckedRadioButtonId() > -1
                        && RGsize.getCheckedRadioButtonId() > -1) {
                    RadioButton tempR = (RadioButton)
                            findViewById(RGsize.getCheckedRadioButtonId());
                    resultString = resultString + ""+ tempR.getText().toString() +" size, ";
                    tempR = (RadioButton)
                            findViewById(RGTortilla.getCheckedRadioButtonId());
                    resultString = resultString + " tortilla: "+ tempR.getText().toString();
                }else {
                    resultString = "";
                }

                int fla = 0;
                String tempString = "";
                List<CheckBox> fillings = new ArrayList<CheckBox>();
                fillings.add(beef);fillings.add(chicken); fillings.add(white_fish);
                fillings.add(beans);fillings.add(cheese); fillings.add(seafood);
                fillings.add(rice); fillings.add(pico); fillings.add(gua); fillings.add(lbt);
                for (CheckBox fill : fillings){
                    if (fill.isChecked()){
                        tempString += " " + fill.getText().toString() + ", ";
                        fla++;
                    }
                }
                if (fla > 0){
                    tempString = tempString.substring(0, tempString.length() - 2);
                    resultString += "\nFillings: " + tempString;
                }

                tempString = "";
                fla = 0;

                List<CheckBox> beverage = new ArrayList<CheckBox>();
                beverage.add(soda); beverage.add(margarita);
                beverage.add(cerveza); beverage.add(tequila);
                for (CheckBox b : beverage){
                    if (b.isChecked()){
                        tempString += " " + b.getText().toString() + ", ";
                        fla++;
                    }
                }

                if (fla > 0){
                    tempString = tempString.substring(0, tempString.length() - 2);
                    resultString += "\nBeverage: " + tempString;
                }

                resultString+= ".";

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Send message");
                //Add Text and Input to dialog

                final EditText input = new EditText(MainActivity.this);

                String[] NameArray = {"Enter a phone number","Myself","People 1", "People 2"};
                final String[] PhoneArray = {"","5554","5555", "5556"};

                //Buttons

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultString = "";
                        dialog.cancel();
                    }
                });

                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                //builder.setView(input);

                builder.setItems(NameArray, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which != 0) {
                            sendMessage(PhoneArray[which], resultString);
                            resultString = "";
                        } else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.create();
                            builder.setTitle("Send message");
                            builder.setMessage("Number to send order message to:");
                            builder.setView(input);
                            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    String PhoneNumber = input.getText().toString();
                                    sendMessage(PhoneNumber, resultString);
                                    resultString = "";
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    resultString = "";
                                }
                            });
                            builder.show();
                        }
                    }
                });
                builder.show();
            }
        });

        resultString = "";
    }

    private void sendMessage(String Phone, String message) {

        if (Phone.isEmpty()){
            Toast toast = Toast.makeText(getBaseContext(), "Phone number invalid", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
            toast.show();

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Phone, null, message,null, null);
        }
    }

}
