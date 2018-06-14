package sg.com.kaplan.kinveyexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.model.User;
import com.kinvey.android.store.UserStore;
import com.kinvey.java.core.KinveyClientCallback;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Client myKinveyClient;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.textView);

        String app_key = "kid_BJoViSaxX";
        String app_secret = "c8ab7f0a7b01477593968345e76a1a0a";
        myKinveyClient = new Client.Builder(app_key, app_secret, this).build();

        myKinveyClient.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Kinvey Ping Failed", Toast.LENGTH_SHORT).show();
                status.setText("Kinvey Ping Failed");
            }
            public void onSuccess(Boolean b) {
                Toast.makeText(MainActivity.this, "Kinvey Ping Success", Toast.LENGTH_SHORT).show();
                status.setText("Kinvey Ping Success");
            }
        });
        final EditText mEditUserName = (EditText) findViewById(R.id.editTextUser);
        final EditText mEditPassword = (EditText) findViewById(R.id.editTextPassword);
        Button button = (Button) findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    UserStore.login(mEditUserName.getText().toString(), mEditPassword.getText().toString(), getKinveyClient(), new KinveyClientCallback<User>() {
                        @Override
                        public void onFailure(Throwable t) {
                            CharSequence text = "Wrong username or password.";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onSuccess(User u) {
                            CharSequence text = "Welcome back," + u.getUsername() + ".";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Client getKinveyClient(){
        return myKinveyClient;
    }


}
