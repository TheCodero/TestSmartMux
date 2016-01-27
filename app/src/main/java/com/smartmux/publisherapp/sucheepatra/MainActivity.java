package com.smartmux.publisherapp.sucheepatra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thecodero.lib.easypayway.Easypayway;
import com.thecodero.lib.easypayway.ErrorKeys;

public class MainActivity extends AppCompatActivity {

    private static final int reqCode=100;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView) findViewById(R.id.textView);
        Long timeStamp = System.currentTimeMillis();

        Intent intent = new Intent(this,Easypayway.class);
        intent.putExtra("cus_name","Md. Zahidul Islam");
        intent.putExtra("cus_email","zahidul@thecodero.com.bd");
        intent.putExtra("cus_add1","Address1");
        intent.putExtra("cus_add2","Address2");
        intent.putExtra("cus_city","Dhaka");
        intent.putExtra("cus_state","Taltola,Dhaka");
        intent.putExtra("cus_postcode","1219");
        intent.putExtra("cus_country","Bandladesh");
        intent.putExtra("cus_phone","88029892982");
        intent.putExtra("amount","10");
        intent.putExtra("currency","BDT");
        intent.putExtra("tran_id","INV-"+timeStamp);
        intent.putExtra("desc","JM Redwan Items or Service Order Details");
        intent.putExtra("store_id","sucheepatra");
        intent.putExtra("signature_key","a2ab5410a25dc5c3fddf3000b3dc9dce");
        intent.putExtra("opt_a","Mr. ABC");
        intent.putExtra("opt_b","Mr. ABC");
        intent.putExtra("opt_c","Mr. ABC");
        intent.putExtra("opt_d", "Mr. ABC");
        startActivityForResult(intent, reqCode);


        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(reqCode==requestCode){
            if(resultCode==RESULT_OK && data.getExtras().containsKey("pay_status")){
                textView.setText("Pay Status : " +data.getStringExtra("pay_status")+"\n"+"Epw Txtid : "+data.getStringExtra("epw_txnid")+"\n");
            }
            if(resultCode==RESULT_CANCELED){
                textView.setText("Error Code : "+ data.getIntExtra("error", ErrorKeys.ERROR_UNKNOWN_ERROR));

                // This block will response if there are any problem from user user Input
                if(data.getIntExtra("error",0) == ErrorKeys.ERROR_USER_INPUTSET){
                    if(data.hasExtra("errorMessage")) {
                        String errorMessage = data.getStringExtra("errorMessage").toString();

                        // This array will give you all error message
                        String[] allMessage = errorMessage.split("\\|");
                        for (String error : allMessage) {
                            textView.setText(textView.getText() + "\n" + error);
                        }
                    }
                }
            }
        }
    }
}
