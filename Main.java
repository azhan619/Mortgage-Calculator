public class MCalcPro_Activity  extends AppCompatActivity implements TextToSpeech.OnInitListener,SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.tts = new TextToSpeech(this,this);
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mcalcpro_layout);
    }
    private TextToSpeech tts;
    public void onInit(int initStatus) {

        this.tts.setLanguage(Locale.US);
        }
    public void onAccuracyChanged(Sensor arg0, int arg1 ){
        }
    public void onSensorChanged(SensorEvent event){
        double ax = event.values[0];
        double ay = event.values[1];
        double az = event.values[2];
        double a = Math.sqrt(ax*ax + ay*ay + az*az);
        if (a>20)
        {
            ((EditText) findViewById(R.id.pBox)).setText("");
            ((EditText) findViewById(R.id.aBox)).setText("");
            ((EditText) findViewById(R.id.iBox)).setText("");
            ((TextView) findViewById(R.id.output)).setText("");


        }
    }
        public void buttonClicked(View V) {
        try {
            MPro mp = new MPro();
            mp.setPrinciple(((EditText) findViewById(R.id.pBox)).getText().toString());
            mp.setAmortization(((EditText) findViewById(R.id.aBox)).getText().toString());
            mp.setInterest(((EditText) findViewById(R.id.iBox)).getText().toString());
            String a = ((EditText) findViewById(R.id.aBox)).getText().toString();
            int j = Integer.parseInt(((EditText) findViewById(R.id.aBox)).getText().toString());

            String s = "Monthly Payment = " + mp.computePayment("%,.2f");
            s += "\n\n";
            s += "By making this payments monthly for ";
            s += a + " years, the mortgage will be paid in full." + "But if you terminate the mortgage on its nth anniversary," + "the balance still owing depends on n shown below:";
            s += "\n\n";

            s += "      n           Balance";
            s += "\n\n";
            int n = 0;

            for (int i = 0; i < 9; i++) {

                if (n <= 5) {

                    s += String.format("%8d", n) + mp.outstandingAfter(n, "%,16.0f");
                    s += "\n\n";
                    n++;
                } else {
                    if (n == 6) {


                       n = 10;
                    }
                    s += String.format("%8d", n) + mp.outstandingAfter(n, "%,16.0f");
                    s += "\n\n";
                    n += 5;
                }

            }
            ((TextView) findViewById(R.id.output)).setText(s);
            tts.speak(s,TextToSpeech.QUEUE_FLUSH, null);


        } catch (Exception e) {
            Toast label = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            label.show();


        }

    }

}
