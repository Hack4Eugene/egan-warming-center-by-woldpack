package yilungao.gmail.com.eganwarmingcenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private TextView capacityHead;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    String siteID;
    MenuItem edit;
    MenuItem delete;
    Button setButton;

    int userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_page);
        setButton = findViewById(R.id.setButton);



        siteID = getIntent().getStringExtra("SITEID");
        site = new Site(siteID);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        capacityHead = findViewById(R.id.capacityHeader);
        if(site.numPeople > site.capacity) capacityHead.setText("Over Capacity: " + site.numPeople + "/" + site.capacity);
        else capacityHead.setText("Capacity: " + site.numPeople + "/" + site.capacity);




        mDatabase.child("sites").child(siteID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    site.activated = (boolean) dataSnapshot.child("active").getValue();
                    if (site.activated) site.isEnabled = 1;
                    else site.isEnabled = 0;
                    site.adult = (boolean) dataSnapshot.child("adult").getValue();
                    site.capacity = dataSnapshot.child("capacity").getValue(Integer.class);
                    site.children = (boolean) dataSnapshot.child("children").getValue();
                    site.disability = (boolean) dataSnapshot.child("disability").getValue();
                    site.siteName = (String) dataSnapshot.child("name").getValue();
                    site.numPeople = dataSnapshot.child("num_people").getValue(Integer.class);
                    site.pets = (boolean) dataSnapshot.child("pets").getValue();
                    GraphView graph = (GraphView) findViewById(R.id.graph);
                    series = new BarGraphSeries<DataPoint>(new DataPoint[]{
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

                    setTitle(site.siteName);
                    capacityHead.setText("Capacity: " + site.numPeople + "/" + site.capacity);

                    // set tag colors
                    if (site.children) {
                        ChildrenTag.setBackgroundColor(getResources().getColor(R.color.green));
                    } else {
                        ChildrenTag.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    // set tag colors
                    if (site.adult) {
                        AdultTag.setBackgroundColor(getResources().getColor(R.color.green));
                    } else {
                        AdultTag.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    // set tag colors
                    if (site.disability) {
                        DisabilityTag.setBackgroundColor(getResources().getColor(R.color.green));
                    } else {
                        DisabilityTag.setBackgroundColor(getResources().getColor(R.color.red));
                    }

                    // set tag colors
                    if (site.pets) {
                        PetsTag.setBackgroundColor(getResources().getColor(R.color.green));
                    } else {
                        PetsTag.setBackgroundColor(getResources().getColor(R.color.red));
                    }
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


        setButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = incrementField.getText().toString();
                mDatabase.child("sites").child(siteID).child("num_people").setValue(Integer.parseInt(temp));
                incrementField.setText("");
            }
        });


        mDatabase.child("users").child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userRole = dataSnapshot.child("role").getValue(Integer.class);
                if(userRole == 0 || userRole == 5 || userRole == 6){
                    plus.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);
                    incrementField.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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
                    Toast.makeText(getApplicationContext(), "Over Capacity.",
                            Toast.LENGTH_SHORT).show();
                }
                mDatabase.child("sites").child(siteID).child("num_people").setValue(site.numPeople + increment);
                incrementField.setText("");
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
        inflater.inflate(R.menu.site_page_menu, menu);
        edit = menu.findItem(R.id.action_edit);
        delete = menu.findItem(R.id.action_delete);
        mDatabase.child("users").child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userRole = dataSnapshot.child("role").getValue(Integer.class);
                if(userRole == 0){
                    edit.setVisible(true);
                    delete.setVisible(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("ROLE", userRole+"");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                loadLogInView();
                break;

            case R.id.action_edit:
                Intent intent = new Intent(this, EditClass.class);
                intent.putExtra("siteName", site.siteName);
                intent.putExtra("siteCapacity", site.capacity+"");
                intent.putExtra("siteChildren", site.children);
                intent.putExtra("siteAdult", site.adult);
                intent.putExtra("siteDisability", site.disability);
                intent.putExtra("sitePets", site.pets);
                intent.putExtra("enabled", site.activated);
                startActivityForResult(intent, 9001);
                break;

            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you really really want to delete this site?");
                builder.setPositiveButton("Yeah, I'm sure.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabase.child("sites").child(siteID).removeValue();
                                finish();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int baseRequestCode = requestCode & 0xffff; //apparently when you startactivityforresult from a fragment it puts random stuff in higher bits.
        if(baseRequestCode == 9001 && data != null) {
            int capacity = data.getIntExtra("capacity", 0);
            boolean children = data.getBooleanExtra("children", false);
            boolean adult = data.getBooleanExtra("adult", true);
            boolean disability = data.getBooleanExtra("disability", true);
            boolean pets = data.getBooleanExtra("pets", true);
            boolean enabled = data.getBooleanExtra("enabled", true);
            mDatabase.child("sites").child(siteID).child("capacity").setValue(capacity);
            mDatabase.child("sites").child(siteID).child("children").setValue(children);
            mDatabase.child("sites").child(siteID).child("adult").setValue(adult);
            mDatabase.child("sites").child(siteID).child("disability").setValue(disability);
            mDatabase.child("sites").child(siteID).child("pets").setValue(pets);
            mDatabase.child("sites").child(siteID).child("active").setValue(enabled);
        }
    }

    private void loadLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}

