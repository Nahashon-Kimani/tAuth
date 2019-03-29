package com.e.tauth.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.tauth.R;
import com.e.tauth.model.EventRegiatration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class BookEvent extends AppCompatActivity {
    Spinner classSpinner;
    ArrayAdapter<CharSequence> classAdapter;
    EditText noOfTickets;
    Button resetRegistration, submitRegistration;
    TextView totalAmount;
    String noOfTicket;
    int tickets, payableAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_dialog);

		//Initializing the objects
        classSpinner = findViewById(R.id.class_spinner);
        noOfTickets = findViewById(R.id.number_of_tickets);
        resetRegistration = findViewById(R.id.reset_registration);
        submitRegistration = findViewById(R.id.submit_registration);
        totalAmount = findViewById(R.id.total_amount);

        setUpSpinner();//Calling the method to setUp the  Spinner.

        resetRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
			/*This code is used to reset the texview containing numbe of tickets text back to zero
                noOfTickets.setText("0");
                totalAmount.setText(getResources().getString(R.string.nothing));*/
                resetRegistration();//working correctly.
            }
        });

        submitRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRegistration();
                submitRegistration.setEnabled(false);
            }
        });
    }


    public void setUpSpinner() {
        //This method is used to setUp the design of the Spinner;
        classAdapter = ArrayAdapter.createFromResource(
                this, R.array.event_class, R.layout.spinner_layout);
        classAdapter.setDropDownViewResource(R.layout.spinner_layout);
        classSpinner.setAdapter(classAdapter);
    }


    public void resetRegistration() {
        noOfTickets.setText("0");
        //totalAmount.setText(getResources().getString(R.string.nothing));
    }

    public void submitRegistration() {
        //This method should trigger an alertDialog on which the user should confirm or cancel thr request;
        final Dialog dialog = new Dialog(BookEvent.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirm_dialog);
        //dialog.setTitle("");
        dialog.setCancelable(true);

        //getting the textView and Buttons(Views n the dialog)
        Button resetConfirm = dialog.findViewById(R.id.reset_confirm);
        Button submitConfirm = dialog.findViewById(R.id.submit_confirm);


        submitConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the details set then place them in te userDetails textView
                //Register the user details to FireBase DataBase;

                String userClass = classSpinner.getSelectedItem().toString().trim();
                Toast.makeText(BookEvent.this, userClass, Toast.LENGTH_SHORT).show();

//Text Validation: Confirm whether class is selected and numbet of Tickesa is not equal to zero.
                if (userClass.equals("Select Class")){
                    userClass = "Business Class";
                }

                 noOfTicket = noOfTickets.getText().toString().trim();
                if (noOfTicket.isEmpty()) {
                    noOfTicket = "1";
                    tickets = Integer.parseInt(noOfTicket);
                }else {
                    tickets = Integer.parseInt(noOfTicket);
                }

                if (userClass.equals("Business Class")||userClass.equals("Select Class")){
                    payableAmount = tickets*2000;
                }else{
                    payableAmount = tickets*4500;
                }

                //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String regDate = DateFormat.getDateTimeInstance().format(new Date());
                //Toast.makeText(BookEvent.this, regDate, Toast.LENGTH_SHORT).show();

                //Inserting the new applicant to the database.
				EventRegiatration eventRegiatration = new EventRegiatration(userClass, "John", regDate, tickets, payableAmount);
                FirebaseDatabase.getInstance().getReference("Confirmed Users").push()
                        .setValue(eventRegiatration).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BookEvent.this, "Than you. Your have successfully registered", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(BookEvent.this, "Sorry. An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        resetConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userClass = classSpinner.getSelectedItem().toString().trim();

                Toast.makeText(BookEvent.this, userClass, Toast.LENGTH_SHORT).show();

                if (userClass.equals("Select Class")){
                    userClass = "Business Class";
                }

                noOfTicket = noOfTickets.getText().toString().trim();
                if (noOfTicket.isEmpty()) {
                    noOfTicket = "1";
                    tickets = Integer.parseInt(noOfTicket);
                    Toast.makeText(BookEvent.this, Integer.toString(tickets), Toast.LENGTH_SHORT).show();
                }else {
                    tickets = Integer.parseInt(noOfTicket);
                    Toast.makeText(BookEvent.this, Integer.toString(tickets), Toast.LENGTH_SHORT).show();
                }

                if (userClass.equals("Business Class")||userClass.equals("Select Class")){
                    payableAmount = tickets*2000;
                    Toast.makeText(BookEvent.this, Integer.toString(payableAmount), Toast.LENGTH_SHORT).show();
                }else{
                    payableAmount = tickets*4500;
                    Toast.makeText(BookEvent.this, Integer.toString(payableAmount), Toast.LENGTH_SHORT).show();
                }
                TextView userDetails = dialog.findViewById(R.id.user_details);
                userDetails.setText(
                        "Please confirm whether the details are correct.\nClass: "+ userClass+" \nNumber of Tickets "
                        + noOfTicket + "\n and\n Payable Amount is: Ksh."+payableAmount
                );

            }
        }); //working.

        dialog.show();
    }
}
