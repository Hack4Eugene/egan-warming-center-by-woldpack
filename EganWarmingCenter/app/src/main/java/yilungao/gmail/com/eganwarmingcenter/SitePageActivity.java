package yilungao.gmail.com.eganwarmingcenter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

public class SitePageActivity extends AppCompatActivity {

    // Variables for Graph
    private Runnable mTimer;
    private BarGraphSeries<DataPoint> series;
    private final Handler mHandler = new Handler();

    public Site site;

    // Variables for Firebase
    private DatabaseReference mDatabase;

    // Variables for UI
    private ImageButton plus;
    private ImageButton minus;
    private EditText incrementField;
    private TextView ChildrenTag;
    private TextView AdultTag;
    private TextView DisabilityTag;
    private TextView PetsTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_page);

        final String siteID = getIntent().getStringExtra("SITEID");

        site = new Site(siteID);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("sites").child(siteID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("DEBUG", "Success");

                site.activated = (boolean) dataSnapshot.child("active").getValue();
                site.adult = (boolean) dataSnapshot.child("adult").getValue();
                site.capacity = dataSnapshot.child("capacity").getValue(Integer.class);
                site.children = (boolean) dataSnapshot.child("children").getValue();
                site.disability = (boolean) dataSnapshot.child("disability").getValue();
                site.siteName = (String) dataSnapshot.child("name").getValue();
                site.numPeople = dataSnapshot.child("num_people").getValue(Integer.class);
                site.pets = (boolean) dataSnapshot.child("pets").getValue();

                Log.i("Site",site.capacity + "");
                GraphView graph = (GraphView) findViewById(R.id.graph);
                series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(.5, site.numPeople)
                });
                Log.i("Site", site.siteName + "");
                series.setValueDependentColor(new mValueDependentColor(site.capacity));
                graph.removeAllSeries();
                graph.addSeries(series);

                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(1.0);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(site.capacity);

                graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
                graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

                setTitle(site.siteName + ": " + site.numPeople + "/" + site.capacity);

                // set tag colors
                if (site.children) {
                    ChildrenTag.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    ChildrenTag.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }

                // set tag colors
                if (site.adult) {
                    AdultTag.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    AdultTag.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }

                // set tag colors
                if (site.disability) {
                    DisabilityTag.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    DisabilityTag.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }

                // set tag colors
                if (site.pets) {
                    PetsTag.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                } else {
                    PetsTag.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DEBUG", "Failure");
            }
        });

        // Initialize UI elements
        plus = findViewById(R.id.plusBtn);
        minus =  findViewById(R.id.minusBtn);
        incrementField = findViewById(R.id.incrementNumField);
        ChildrenTag = findViewById(R.id.Children);
        AdultTag = findViewById(R.id.Adult);
        DisabilityTag = findViewById(R.id.Disability);
        PetsTag = findViewById(R.id.Pets);



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String entry = incrementField.getText().toString();
                int increment = 0;

                if (entry.isEmpty()) {
                    increment = 1;
                } else {
                    increment = Integer.parseInt(entry);
                }

                if (site.numPeople + increment > site.capacity) {
                    Toast.makeText(getApplicationContext(), "Invalid Increment.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase.child("sites").child(siteID).child("num_people").setValue(site.numPeople + increment);
                    incrementField.setText("");
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String entry = incrementField.getText().toString();
                int increment = 0;

                if (entry.isEmpty()) {
                    increment = 1;
                } else {
                    increment = Integer.parseInt(entry);
                }

                if (site.numPeople - increment < 0) {
                    Toast.makeText(getApplicationContext(), "Invalid Decrement.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    mDatabase.child("sites").child(siteID).child("num_people").setValue(site.numPeople - increment);
                    incrementField.setText("");
                }
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                loadLogInView();

            case R.id.action_edit:
                

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}