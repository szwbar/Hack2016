package UserConnectedScreens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.szwba_000.killers.R;
import com.orcam.orcam_sdk.API.Interfaces.PresentDevicesUiCallback;
import com.orcam.orcam_sdk.API.Model.MyMeError;
import com.orcam.orcam_sdk.API.Model.MyMeFace;
import com.orcam.orcam_sdk.API.Model.MyMePerson;
import com.orcam.orcam_sdk.API.MyMe;
import com.orcam.orcam_sdk.API.MyMeCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 19/05/2016.
 */
public class MainScreenActivity extends AppCompatActivity {
    private TextView connectionStatusTextView;
    private TextView personTextView;
    private TextView textLineTextView;

    private MyMe myMe;


    // A list of stolen cars plate numbers, represented as text.
    private ArrayList<String> stolenNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionStatusTextView = (TextView) findViewById(R.id.connection_status_text_view);
        personTextView = (TextView) findViewById(R.id.person_text_view);
        textLineTextView= (TextView) findViewById(R.id.text_line_text_view);

        MyMeCallback myMeCallback = initMyMeCallback(); //(1)
        myMe = MyMe.getInstance(this, myMeCallback);     //(2)
        //activate SDK's autoCreatePerson
        myMe.setAutoCreatePerson(true);//(3)

        // TODO : initialize stolen cars numbers list.


        connect();  //(4)

    }



    private void connect() {
        myMe.presentDetectedDevices(this, false, new PresentDevicesUiCallback() { //(1)
            @Override
            public void OnSuccessConnecting(String deviceID, int state) {
                Log.d("MyMe", "connected successfully");
                connectionStatusTextView.setText("connected successfully");
            }

            @Override
            public void OnErrorConnecting(MyMeError myMeError) {
                Log.d("MyMe", "could not connect: " + myMeError);
                connectionStatusTextView.setText("could not connect: " + myMeError);
            }

            @Override
            public void OnCancel() {
                Log.d("MyMe", "dialog canceled.");
                connectionStatusTextView.setText("dialog canceled.");
            }
        });
    }

    private MyMeCallback initMyMeCallback() {
        return new MyMeCallback() {
            boolean isTextFinished = false;
            @Override
            public void ocrTextLineReceived(String textLine, boolean isEnd, long atTime) {
               // Don't need to print anything
//                Log.d("MyMe", "Text received: " + textLine); //(2)
//
//                if (isTextFinished){
//                    isTextFinished = false;
//                    textLineTextView.setText("");
//                }
//                if (isEnd) { //(3)
//                    isTextFinished = true;
//                    Log.d("MyMe", "---- END OF TEXT ----");
//                }

                if (textLine!=null){

                    // Compare the text line, the recieved plate number with all the stolen cars
                    // numbers. If there is a match, raise plate number pop up.
                    for (int i = 0; i < stolenNumbers.size(); i ++) {
                        if (stolenNumbers.get(i).equals(textLine))
                        {

                            // TODO : go to the StolenCarFound, which gets stolenNumbers.get(i)
                            // Ohad writes it.
                            Intent intent = new Intent(this, StolenCarFound.class(textLine));
                            startActivity(intent);
                        }

                    }
                    // Writes the text to scren
                    //                textLineTextView.append(textLine);
                }

            }

            @Override
            public void faceDetected(List<MyMeFace> faces, List<MyMePerson> persons, List<Float> probability, long time) { //(1)

//                Log.d("MyMe", "Face Detected");

                for (int i = 0; i < faces.size(); i++) {
                    if (persons.get(i) == null)//(2)
                    {
                        Log.d("MyMe", "Unknown Person Detected");
                        personTextView.setText("Unknown Person Detected");
                    } else {
                        Log.d("MyMe", persons.get(i).getPersonID() + " Detected at " + probability.get(i) + " certainty"); //(3)
                        personTextView.setText(persons.get(i).getPersonID() + " Detected at " + probability.get(i) + " certainty");


                        // TODO : when detecting a face from data, pop up a Person screen


                    }
                }
            }

            @Override
            public void newPersonCreated(MyMePerson person, long time) { //(1)
                Log.d("MyMe", "New Person Created. ID: " + person.getPersonID()); //(2)
                personTextView.setText("New Person Created. ID: " + person.getPersonID());
            }
        };
    }



}
