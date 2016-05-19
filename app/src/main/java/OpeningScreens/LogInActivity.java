package OpeningScreens;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.szwba_000.killers.R;

/**
 * Created by szwba_000 on 19/05/2016.
 */
public class LogInActivity extends AppCompatActivity {

    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = (Button) findViewById(R.id.activity_login_enter_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = (EditText) findViewById(R.id.activity_login_enter_username);
                EditText password = (EditText) findViewById(R.id.activity_login_enter_password);
                if (userName == null || password == null) {
                    // TODO CHECK
                } else {
                    String userNameTxt = userName.getText().toString();
                    String passwordTxt = password.getText().toString();
                    if (!userNameTxt.matches("[0-9]{9}")) { // Password is not in ID form
                        userName.setText("");
                        return;
                    }

                    if (approveUser(userNameTxt, passwordTxt)) {


                    } else {
                        // TODO USER NOT RECOGNIZED, POP UP
                        userName.setText("");
                        password.setText("");
                    }
                }

            }
        });
    }

}
