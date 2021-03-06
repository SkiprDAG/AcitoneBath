package com.example.acitonicbath;

/**
 *  ██████╗░███████╗██╗░░░██╗███████╗██╗░░░░░░█████╗░██████╗░███╗░░░███╗███████╗███╗░░██╗████████╗░░░██████╗░██╗░░░██╗
 *  ██╔══██╗██╔════╝██║░░░██║██╔════╝██║░░░░░██╔══██╗██╔══██╗████╗░████║██╔════╝████╗░██║╚══██╔══╝░░░██╔══██╗╚██╗░██╔╝
 *  ██║░░██║█████╗░░╚██╗░██╔╝█████╗░░██║░░░░░██║░░██║██████╔╝██╔████╔██║█████╗░░██╔██╗██║░░░██║░░░░░░██████╦╝░╚████╔╝░
 *  ██║░░██║██╔══╝░░░╚████╔╝░██╔══╝░░██║░░░░░██║░░██║██╔═══╝░██║╚██╔╝██║██╔══╝░░██║╚████║░░░██║░░░░░░██╔══██╗░░╚██╔╝░░
 *  ██████╔╝███████╗░░╚██╔╝░░███████╗███████╗╚█████╔╝██║░░░░░██║░╚═╝░██║███████╗██║░╚███║░░░██║░░░██╗██████╦╝░░░██║░░░
 *  ╚═════╝░╚══════╝░░░╚═╝░░░╚══════╝╚══════╝░╚════╝░╚═╝░░░░░╚═╝░░░░░╚═╝╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚═╝╚═════╝░░░░╚═╝░░░
 *
 *  ██╗░░██╗░█████╗░░██████╗████████╗░█████╗░███╗░░██╗███████╗████████╗
 *  ██║░██╔╝██╔══██╗██╔════╝╚══██╔══╝██╔══██╗████╗░██║██╔════╝╚══██╔══╝
 *  █████═╝░██║░░██║╚█████╗░░░░██║░░░██║░░██║██╔██╗██║█████╗░░░░░██║░░░
 *  ██╔═██╗░██║░░██║░╚═══██╗░░░██║░░░██║░░██║██║╚████║██╔══╝░░░░░██║░░░
 *  ██║░╚██╗╚█████╔╝██████╔╝░░░██║░░░╚█████╔╝██║░╚███║███████╗░░░██║░░░
 *  ╚═╝░░╚═╝░╚════╝░╚═════╝░░░░╚═╝░░░░╚════╝░╚═╝░░╚══╝╚══════╝░░░╚═╝░░░
 *
 *  ░██████╗██╗░░██╗██╗██████╗░██████╗░░█████╗░░██████╗░██╗░░░██╗░░░░░░░░█████╗░░█████╗░░█████╗░░░██╗██╗██████╗░
 *  ██╔════╝██║░██╔╝██║██╔══██╗██╔══██╗██╔══██╗██╔════╝░██║░░░██║░░██╗░░██╔══██╗██╔══██╗██╔══██╗░██╔╝██║██╔══██╗
 *  ╚█████╗░█████═╝░██║██████╔╝██║░░██║███████║██║░░██╗░██║░░░██║██████╗██║░░██║██║░░██║██║░░╚═╝██╔╝░██║██║░░██║
 *  ░╚═══██╗██╔═██╗░██║██╔═══╝░██║░░██║██╔══██║██║░░╚██╗██║░░░██║╚═██╔═╝██║░░██║██║░░██║██║░░██╗███████║██║░░██║
 *  ██████╔╝██║░╚██╗██║██║░░░░░██████╔╝██║░░██║╚██████╔╝╚██████╔╝░░╚═╝░░╚█████╔╝╚█████╔╝╚█████╔╝╚════██║██████╔╝
 *  ╚═════╝░╚═╝░░╚═╝╚═╝╚═╝░░░░░╚═════╝░╚═╝░░╚═╝░╚═════╝░░╚═════╝░░░░░░░░░╚════╝░░╚════╝░░╚════╝░░░░░░╚═╝╚═════╝░
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlayOrPause;
    private Bath arduino = new Bath();
    private TimeFragment timeFragment = new TimeFragment(arduino);
    private TemperatureFragment tempFragment = new TemperatureFragment(arduino);
    private SharedPreferences savePreference;
    /**
     * Point's enter in app
     * @param savedInstanceState idk
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        savePreference = getSharedPreferences("MyPref", MODE_PRIVATE);
        initUI();
        new ConnectionService(arduino);
    }
    /**
     * Init UI for app
     */
    public void initUI(){
        setFragment(timeFragment, false);
        setFragment(tempFragment, false);
        setFragment(timeFragment, false);

        final String KEY_IP = "IP";
        EditText editTextIP = findViewById(R.id.editIPid);
        editTextIP.setText(savePreference.getString(KEY_IP, "192.168.4.1:80"));
        Button saveBTN = findViewById(R.id.saveButtonID);
        saveBTN.setOnClickListener((view)->{
            SharedPreferences.Editor ed = savePreference.edit();
            ed.putString(KEY_IP, editTextIP.getText().toString());
            ed.apply();
            if(!arduino.getConnection()){
                initConnect();
            }
        });

        buttonPlayOrPause = findViewById(R.id.buttonStart);
        buttonPlayOrPause.setOnClickListener((View view)->{
            if(arduino.getState().equals(Bath.STATE_READY)){
                arduino.start();
                MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_pause_32));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    startForegroundService(new Intent(this, ConnectionService.class));
                }
            } else {
                arduino.pause();
                MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_play_32));
            }
        });

        Button buttonStop = findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener((View view)->{
            arduino.stop();
            Toast.makeText(this, "Остановленно", Toast.LENGTH_LONG).show();
            MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_play_32));
        });

        GestureDetectorCompat lSwipeDetector = new GestureDetectorCompat(this,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
                        if (Math.abs(e2.getX() - e1.getX()) > 50 && Math.abs(velocityX) > 0) {
                            boolean left = e2.getX() - e1.getX()>0;
                            Fragment fragment = timeFragment.isVisible()?tempFragment:timeFragment;
                            setFragment(fragment, left);
                        }
                        return false;
                    }
                });
        RelativeLayout main_layout = findViewById(R.id.main_layout);
        main_layout.setOnTouchListener((View v, MotionEvent event)-> lSwipeDetector.onTouchEvent(event));
    }

    /**
     * Setting fragment in frameLayout
     * @param fragment of view
     * @return null
     */
    public void setFragment(Fragment fragment, boolean left){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_in_left1, R.animator.slide_in_right1);//right animation on default
        if(left){
            ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);//left animation
        }
        ft.replace(R.id.frameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    /**
     * initializing connect with Bath by wifi
     */
    public void initConnect() {
        EditText editTextIP = findViewById(R.id.editIPid);
        String ipAddress = editTextIP.getText().toString();
        if(ipAddress.indexOf(":")<0)return;
        String portNumber = ipAddress.split(":")[1];
        ipAddress = ipAddress.split(":")[0];
        arduino.setServer(new WebServer(ipAddress, portNumber));
        CheckBox checkBox = findViewById(R.id.checkBox);
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //{command, args}
                boolean connection = false;
                String str = "";
                try {
                    String random = (int) (Math.random() * 10000) + "";
                    str = arduino.getServer().sendRequest("connection," + random);
                    if (str.split(",")[0].equals("connection")) {
                        if (str.split(",")[1].equals(random)) {
                            connection = true;
                        }
                    }
                }catch(Exception ex) {
                    Log.wtf("ERROR", ex);
                }
                arduino.setConnection(connection);
                try{
                    if(arduino.getConnection()){
                        str = arduino.getServer().sendRequest("state,0");
                        if(str.split(",")[0].equals("state")){
                            if(!arduino.getState().equals(str.split(",")[1]))
                                arduino.setState(str.split(",")[1]);

                            tempFragment.setTemperature((int)Float.parseFloat(str.split(",")[2]));
                        }
                        if(arduino.getState().equals(Bath.STATE_TIME0)){
                            str = arduino.getServer().sendRequest("getTime,0");
                            if(str.split(",")[0].equals("getTime")){
                                arduino.getCooler(0).setSecondTime(Integer.parseInt(str.split(",")[1]));
                            }
                        }
                        if(arduino.getState().equals(Bath.STATE_TIME1)){
                            str = arduino.getServer().sendRequest("getTime,1");
                            if(str.split(",")[0].equals("getTime")){
                                arduino.getCooler(1).setSecondTime(Integer.parseInt(str.split(",")[1]));
                            }
                        }
                    } else {

                    }
                } catch (Exception ex) {
                    Log.i("initConnect", "" + Arrays.toString(ex.getStackTrace()));
                }
                MainActivity.this.runOnUiThread(()->{
                    checkBox.setChecked(arduino.getConnection());
                    arduino.setTotalTime();
                });
                if(!arduino.getConnection()){
                    myTimer.cancel();
                    myTimer.purge();
                }
            }
        }, 0, 1000);
    }
    /**
     * class Bath is real Model Bath
     * can start, stop, pause, real Bath
     * uses InnerClasses Cooler for keep time
     * also update yourself text
     */
    public class Bath{
        /**
         * All possible state
         */
        public static final String STATE_READY = "ready";
        public static final String STATE_STOP = "stop";
        public static final String STATE_TEMP = "temp";
        public static final String STATE_TIME0 = "time0";
        public static final String STATE_TIME1 = "time1";

        private String state ="";
        private int temp = 50;
        private Cooler cooler0 = new Cooler();
        private Cooler cooler1 = new Cooler();
        private TextView totalTime;
        private TextView stateView;
        private WebServer server = new WebServer();
        /**
         * Setting state of bath
         * @param state only state from STATE
         */
        public void setState(String state){
            if(!this.state.equals(state)){
                this.state = state;
                switch (state){
                    case STATE_STOP:
                        stop();
                        break;
                    case STATE_TEMP:
                        setTemp();
                        break;
                    case STATE_READY:
                        setReady();
                        break;
                    case STATE_TIME0:
                    case STATE_TIME1:
                        MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_pause_32));
                        break;
                    default:
                        break;
                }
            }
        }

        /**
         * transition to temp state
         */
        public void setTemp(){

        }
        /**
         * transition to ready state
         */
        public void setReady(){
            server.sendAsyncRequest("setReady,0");
            MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_play_32));
        }
        public String getState(){
            return state;
        }
        public String getStateRUS(){
            String str = "Состояние: ";
            switch (getState()){
                case STATE_READY:
                    return str +"охотно сделаю";
                case STATE_STOP:
                    return str + "остановленно";
                case STATE_TIME0:
                    return str + "обработка";
                case STATE_TIME1:
                    return str + "обдув";
                case STATE_TEMP:
                    return str + "разогрев";
            }
            return null;
        }
        /**
         * set IndicatorConnection to server
         * @param connection true or false
         */
        public void setConnection(boolean connection){
            server.setConnection(connection);
        }
        public boolean getConnection(){
            return server.connection;
        }
        /**
         * @param id Cooler for id 0 or ever else
         * @return Cooler for using
         */
        public Cooler getCooler(int id){
            return id==0?cooler0:cooler1;
        }
        /**
         * set View at app
         * need for update time, and set it clickable
         * @param id 0 = first cooler text, 1 = second cooler text, else set common time
         * @param textView need for view text on the screen
         */
        public void setView(int id, TextView textView){
            switch (id){
                case 0:
                    cooler0.setView(textView);
                    break;
                case 1:
                    cooler1.setView(textView);
                    break;
                case 2:
                    stateView = textView;
                    break;
                default:
                    totalTime = textView;
                    setTotalTime();
            }
        }
        /**
         *
         * @return server for data exchange
         */
        public WebServer getServer(){
            return  server;
        }
        public void setServer(WebServer server){
            this.server = server;
        }
        /**
         * starting bath by wifi
         */
        public void start(){
            sendTime();
            sendTemp();
            String requests = "start,0";
            getServer().sendAsyncRequest(requests);
        }
        /**
         * pause bath by wifi
         */
        public void pause(){
            String requests = "stop,0";
            getServer().sendAsyncRequest(requests);
        }
        /**
         * stop bath by wifi
         */
        public void stop(){
            String requests = "stop,0";
            getServer().sendAsyncRequest(requests);
            clearTime();
            MainActivity.this.runOnUiThread(()->buttonPlayOrPause.setBackgroundResource(R.drawable.icons8_play_32));
            MainActivity.this.stopService(new Intent(MainActivity.this, ConnectionService.class));
            setReady();
        }
        /**
         * update text all time
         */
        public void setTotalTime() {
            int hours = (cooler0.workingTime.get(Calendar.HOUR_OF_DAY) + cooler1.workingTime.get(Calendar.HOUR_OF_DAY));
            int minutes = (cooler0.workingTime.get(Calendar.MINUTE) + cooler1.workingTime.get(Calendar.MINUTE));
            int seconds = (cooler0.workingTime.get(Calendar.SECOND) + cooler1.workingTime.get(Calendar.SECOND));
            hours += minutes/60;
            minutes = minutes%60;
            cooler1.update();
            cooler0.update();
            stateView.setText(getStateRUS());
            totalTime.setText(String.format("%02d:%02d:%02d",hours, minutes, seconds));
        }
        /**
         * send time to bath by wifi
         */
        public void sendTime() {
            if(!(cooler0.toSecond()+ cooler1.toSecond()>0)){
                return;
            }

            try{
                //OpenConnectionSendRequests
                if(getState().equals(Bath.STATE_READY)){
                    String requests = "setTime0," + cooler0.toSecond();
                    this.server.sendAsyncRequest(requests);
                    requests = "setTime1," + cooler1.toSecond();
                    this.server.sendAsyncRequest(requests);
                }
            }catch(Exception ex){
                Log.i("sendTime", "" + ex);
            }
        }
        /**
         * send temp to bath by wifi
         */
        public void sendTemp() {
            try{
                //OpenConnectionSendRequests
                String requests = "setTemp,"+ temp;
                this.server.sendAsyncRequest(requests);
            }catch(Exception ex){
                Log.i("sendTemp", "" + ex);
            }
        }
        public void setTemp(int temp){
            this.temp = temp;
        }
        public int getTemp(){
            return temp;
        }


        /**
         * reset cooler timer
         */
        public void clearTime(){
            cooler0.clearTime();
            cooler1.clearTime();
        }


        /**
         * class for information user about time
         */
        class Cooler {
            Calendar workingTime = Calendar.getInstance();
            TextView textView;
            {
                clearTime();
            }

            /**
             * @param textView where need to show time
             */
            public void setView(TextView textView){
                this.textView = textView;
                this.textView.setOnClickListener(view -> {
                    new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            workingTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            workingTime.set(Calendar.MINUTE, minute);
                            textView.setText(String.format("%02d:%02d",
                                    workingTime.get(Calendar.HOUR_OF_DAY),
                                    workingTime.get(Calendar.MINUTE)));
                            setTotalTime();
                        }
                    },
                            workingTime.get(Calendar.HOUR_OF_DAY),
                            workingTime.get(Calendar.MINUTE), true)
                            .show();
                });
                update();
            }
            /** set working time in second
             * @param second just second how much you need
             */
            public void setSecondTime(int second){
                int hour = second/3600;
                second = second%3600;
                int minute = second/60;
                second = second%60;
                workingTime.set(Calendar.SECOND, second);
                workingTime.set(Calendar.MINUTE, minute);
                workingTime.set(Calendar.HOUR_OF_DAY, hour);
            }
            /**
             * reset time
             */
            public void clearTime(){
                workingTime.set(Calendar.MINUTE, 0);
                workingTime.set(Calendar.HOUR_OF_DAY, 0);
                workingTime.set(Calendar.SECOND, 0);
            }
            /**
             * update textView for show time
             */
            public void update() {
                if (workingTime.get(Calendar.HOUR_OF_DAY) == 0) {
                    textView.setText(String.format("%02d:%02d", workingTime.get(Calendar.MINUTE), workingTime.get(Calendar.SECOND)));
                } else {
                    textView.setText(String.format("%02d:%02d", workingTime.get(Calendar.HOUR_OF_DAY), workingTime.get(Calendar.MINUTE)));
                }
            }
            /**
             * get working second
             * @return seconds
             */
            public int toSecond(){
                return ((workingTime.get(Calendar.HOUR_OF_DAY)*60+workingTime.get(Calendar.MINUTE))*60)+workingTime.get(Calendar.SECOND);
            }
            /**
             * need if need string working seconds
             * @return second working time
             */
            @Override
            public String toString() {
                return  "Cooler: "+textView.getText();
            }

        }
    }
    /**
     * class WebServer with possibly send requests on ipAddres:Port
     *
     */
    class WebServer{
        private boolean connection;
        private String ipAddress, portNumber;
        public WebServer(){}

        public WebServer(String ipAddress, String portNumber){
            this.ipAddress = ipAddress;
            this.portNumber = portNumber;
        }
        public boolean getConnection(){
            return connection;
        }
        public void setConnection(boolean connection) {
            this.connection = connection;
        }
        /**
         * send http get requst on ip
         * @return Answer text with or error message
         */
        public String sendRequest(String command) {
            String serverResponse = "";
            try {
                URL website = new URL("http://"+ipAddress+":"+portNumber+"/send?command="+command);
                HttpURLConnection httpURLConnection = (HttpURLConnection)website.openConnection();
                httpURLConnection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                serverResponse = response.toString();
            } catch (Exception ex){
                System.out.println(Arrays.toString(ex.getStackTrace()));
                Log.i("sendRequest", "" + ex);
                return null;
            }
            return serverResponse;
        }
        /**
         * also sendRequest with AsyncTask
         * @param command
         */
        public void sendAsyncRequest(String command){
            new HttpRequestAsyncTask(command).execute();
        }
        private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void>{
            String requests;
            public HttpRequestAsyncTask(String command){
                this.requests = command;
            }
            @Override
            protected Void doInBackground(Void... voids) {
                WebServer.this.sendRequest(requests);
                return null;
            }
        }
    }
}