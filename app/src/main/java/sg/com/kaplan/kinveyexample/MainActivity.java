package sg.com.kaplan.kinveyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;

public class MainActivity extends AppCompatActivity {

    private Client mKinveyClient;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.textView);

        String app_key = "kid_BJoViSaxX";
        String app_secret = "c8ab7f0a7b01477593968345e76a1a0a";
        mKinveyClient = new Client.Builder(app_key, app_secret, this).build();

        mKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Kinvey Ping Failed", Toast.LENGTH_SHORT).show();
                status.setText("Kinvey Ping Failed");
            }
            public void onSuccess(Boolean b) {
                Toast.makeText(MainActivity.this, "Kinvey Ping Success", Toast.LENGTH_SHORT).show();
                status.setText("Kinvey Ping Success");
            }
        });
    }

    public Client getKinveyClient(){
        return mKinveyClient;
    }
}
