package rta.phatnguyen.assignment4.Activity;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

import java.util.Random;

import rta.phatnguyen.assignment4.Instance.Cookie;
import rta.phatnguyen.assignment4.R;

public class MainActivity extends AppCompatActivity {

    public TextView tv1;
    public TextView tv2;
    public TextView tv3;
    public TextView tv4;
    public ProgressBar progress1;
    public ProgressBar progress2;
    public ProgressBar progress3;
    public Button startBtn;
    public Button cancelBtn;
    public ProgressWheel pw;
    public Cookie cookie;

    public Monster monster1;
    public Monster monster2;
    public GrandmaMonster grandma;
    public int seconds;
    public boolean canEat;
    public CountDownTimer timer;
    public boolean show;
    public boolean start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.cookie1);
        tv2 = (TextView) findViewById(R.id.cookie2);
        tv3 = (TextView) findViewById(R.id.cookie3);
        tv4 = (TextView) findViewById(R.id.tv4);
        progress1 = (ProgressBar) findViewById(R.id.progress1);
        progress2 = (ProgressBar) findViewById(R.id.progress2);
        progress3 = (ProgressBar) findViewById(R.id.progress3);
        startBtn = (Button) findViewById(R.id.StartBtn);
        cancelBtn = (Button) findViewById(R.id.CancelBtn);
        pw = (ProgressWheel) findViewById(R.id.spinner);
        cookie = new Cookie(0);
        canEat = true;
        show = true;
        start = true;
        monster1 = new Monster("monster1", cookie);
        monster2 = new Monster("monster2", cookie);
        grandma = new GrandmaMonster("grandma", cookie);

        tv1.setText("0");
        tv2.setText("0");
        tv3.setText("0");
        tv4.setText("Simulation Clock: 0/120 sec");
        progress1.setProgress(0);
        progress2.setProgress(0);

        timer = new CountDownTimer(121000, 1000) {

            public void onTick(long millisUntilFinished) {
                seconds = (int) millisUntilFinished / 1000;
                seconds = 120 - seconds;
                tv4.setText("Simulation Clock: " + seconds + "/120" + " sec");
                progress3.setProgress(seconds);

            }

            public void onFinish() {
                seconds = 120;
                tv4.setText("Simulation Clock: " + seconds + "/120" + " sec");
                progress3.setProgress(seconds);
                pw.stopSpinning();
                canEat = false;
                monster1.interrupt();
                monster2.interrupt();
                grandma.interrupt();

                if (monster1.amount > monster2.amount) {
                    Toast.makeText(MainActivity.this, "Monter 1 Win!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Monter 2 Win!", Toast.LENGTH_SHORT).show();
                }


            }
        };

        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pw.startSpinning();

                tv1.setText("0");
                tv2.setText("0");
                tv3.setText("0");
                tv4.setText("Simulation Clock: 0/120 sec");
                progress1.setProgress(0);
                progress2.setProgress(0);


                if (start == true) {
                    start = false;
                    monster1.start();
                    monster2.start();
                    grandma.start();
                    timer.start();
                } else {

                    if (canEat == true) {

                        Toast.makeText(MainActivity.this, "Game already is running!", Toast.LENGTH_SHORT).show();

                    } else {

                        cookie = new Cookie(0);
                        monster1 = new Monster("monster1", cookie);
                        monster2 = new Monster("monster2", cookie);
                        grandma = new GrandmaMonster("grandma", cookie);

                        canEat = true;

                        monster1.start();
                        monster2.start();
                        grandma.start();
                        timer.start();
                    }


                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start == false) {
                    pw.stopSpinning();
                    timer.cancel();
                    canEat = false;
                    monster1.interrupt();
                    monster2.interrupt();
                    grandma.interrupt();

                }
            }
        });
    }


    class Monster extends Thread {

        public Thread t;
        public String threadName;
        Cookie vcookie;
        public int amount;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Monster(String name, Cookie cook) {
            threadName = name;
            vcookie = cook;
            amount = 0;
        }

        public void run() {

            while (canEat == true) {
                synchronized (vcookie) {
                    amount = amount + vcookie.eatCookie();
                    if (threadName == "monster 1") {
                        new Thread1().execute(amount);
                    } else {
                        new Thread2().execute(amount);
                    }
                }
                int max = 5;
                int min = 1;
                Random rand = new Random();
                final int time = rand.nextInt((max - min) + 1) + min;
                try {
                    Thread.sleep(time * 1000);
                } catch (java.lang.InterruptedException ie) {
                    System.out.println(ie);
                }
            }
        }

        public void start() {

            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }
    }




    class GrandmaMonster extends Thread {
        private Thread t;
        private String threadName;
        Cookie mCookie;


        public GrandmaMonster(String name, Cookie cookie) {
            threadName = name;
            mCookie = cookie;
        }

        public void run() {
            while (canEat == true) {

                synchronized (cookie) {
                    cookie.bakeCookie();
                    new Thread3().execute(cookie.eatCookie());
                }
                try {
                    Thread.sleep(5000);

                } catch (java.lang.InterruptedException ie) {
                    System.out.println(ie);
                }
            }
        }

        public void start() {

            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }
    }
        public class Thread1 extends AsyncTask<Integer, String, Integer> {

            @Override
            protected Integer doInBackground(Integer... params) {
                Integer t = params[0];
                if (t >= 100) {
                    timer.cancel();
                    canEat = false;
                    monster1.interrupt();
                    monster2.interrupt();
                    grandma.interrupt();

                }
                return t;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                tv1.setText(String.valueOf(result));
                progress1.setProgress(result);
                tv3.setText(String.valueOf(cookie.getCookie()));
                if (result >= 100) {
                    Toast.makeText(MainActivity.this, "Monster 1 Win!", Toast.LENGTH_SHORT).show();
                    pw.stopSpinning();
                }
            }
        }

        public class Thread2 extends AsyncTask<Integer, String, Integer> {

            @Override
            protected Integer doInBackground(Integer... params) {
                Integer t = params[0];
                if (t >= 100) {
                    timer.cancel();
                    canEat = false;
                    monster1.interrupt();
                    monster2.interrupt();
                    grandma.interrupt();

                }
                return t;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                tv2.setText(String.valueOf(result));
                progress2.setProgress(result);
                tv3.setText(String.valueOf(cookie.getCookie()));
                if (result >= 100) {
                    Toast.makeText(MainActivity.this, "Monster 2 Win!", Toast.LENGTH_SHORT).show();
                    pw.stopSpinning();
                }
            }
        }

        public class Thread3 extends AsyncTask<Integer, String, Integer> {

            @Override
            protected Integer doInBackground(Integer... params) {

                Integer t = params[0];
                return t;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                tv3.setText(String.valueOf(result));
            }
        }
    }


