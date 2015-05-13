package com.example.jennifer.practicaandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.SoapEnvelope;

import java.sql.DriverPropertyInfo;


public class MainActivity extends ActionBarActivity {

    private static final String SOAP_ACTION = "http://tempuri.org/findContact";
    private static final String OPERATION_NAME = "Estudiante";
    public  static final  String  WSDL_TRAGET_NAMESPACE = "http://tempuri.org";
    private static final String SOAP_ADDRESS = "http://localhost:1065/WebService.asmx";

    TextView tvData1;
    EditText edata;
    Button button;
    String StudentNO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData1=(TextView)findViewById(R.id.textView1);
        button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoapObject request = new SoapObject(WSDL_TRAGET_NAMESPACE, OPERATION_NAME);
                PropertyInfo propertyinfo = new PropertyInfo();
                propertyinfo.type = PropertyInfo.STRING_CLASS;
                propertyinfo.name="eid";

                edata=(EditText)findViewById(R.id.editText1);
                StudentNO=edata.getText().toString();

                request.addProperty(propertyinfo, StudentNO);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                  SoapEnvelope.VER11);
                envelope.dotNet = true;

                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

                try{
                    httpTransport.call(SOAP_ACTION, envelope);
                    Object response = envelope.getResponse();
                    tvData1.setText(response.toString());
                    }catch(Exception exception){
                    tvData1.setText(exception.toString()+"Or enter number is not Available!");
                }
                tvData1=(TextView)findViewById(R.id.textView1);

            }

        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
