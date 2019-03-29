package com.e.tauth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.tauth.R;
import com.e.tauth.model.EventName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventActivity extends AppCompatActivity {
    FloatingActionButton regEvent;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    TextView eventDate, eventDesc;
    ImageView eventImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        regEvent = findViewById(R.id.reg_event);
        eventDate = findViewById(R.id.event_date);
        eventDesc = findViewById(R.id.event_desc);
        eventImage = findViewById(R.id.event_img);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Events");


        Intent userName = getIntent();
        String currentUser = userName.getStringExtra("currentUser");
        Toast.makeText(EventActivity.this, currentUser, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null){
            String name = user.getDisplayName();
            Toast.makeText(EventActivity.this, name, Toast.LENGTH_SHORT).show();
        }

        createNewEvent();//This method is used to setText From FireBase of event;

        regEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EventActivity.this, BookEvent.class));
            }
        });

    }
	
	//This method checks whether the user is logged. If logged in, then stay on same activity else, go back to Login/create new activity register.
	@Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(EventActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }


    //creating a new Event
    public  void createNewEvent(){

        /*EventName eventName = new EventName("12 March 2019", "This is event Description", "This is image Url");
        mRef.push().setValue(eventName);*/

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot: dataSnapshot.getChildren()) {
                    EventName eventName = snapShot.getValue(EventName.class);
                    eventDate.setText(eventName.getEventDate());
                    eventDesc.setText(eventName.getEventDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EventActivity.this, databaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                break;

            case R.id.settings:
                Toast.makeText(EventActivity.this, "Settings Activity", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}