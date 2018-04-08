package yilungao.gmail.com.eganwarmingcenter;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements SiteFragment.OnFragmentInteractionListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mDatabase;
    private String mUserId;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public HashSet<Integer> selectedIndices = new HashSet<>();
    private int userPermissons;
    Messaging messagingFragment;
    SiteFragment siteFragment;
    AdminViewFragment adminViewFragment;
    ArrayList<Person> mainPeopleList;
    public ArrayAdapter<Person> mainPersonAdapter;

    public final int ADMIN = 0;
    public final int CAHOOTS = 1;
    public final int DRIVER = 2;
    public final int DRIVER_COORDINATOR = 3;
    public final int KITCHEN_LEAD = 4;
    public final int LOGISTICS = 5;
    public final int S_LEAD = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPeopleList = new ArrayList<>();

        mainPeopleList.add(new Person("what@gmail.com", "Rob"));
        mainPeopleList.add(new Person("what@yahoo.com", "Bob"));
        mainPeopleList.add(new Person("what@hotmail.com", "Cob"));


        mainPersonAdapter = new ArrayAdapter<Person>(this, R.layout.messaging_layout, mainPeopleList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.messaging_layout, parent, false);
                }

                ((TextView)convertView.findViewById(R.id.personEmail)).setText(getItem(position).email);
                ((TextView)convertView.findViewById(R.id.personName)).setText(getItem(position).name);
                return convertView;
            }
        };
        userPermissons = getIntent().getIntExtra("permissions", 0);
        Log.i("MAIN", "permissions: " + userPermissons);


        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        messagingFragment = Messaging.newInstance(mainPeopleList, this);
        siteFragment = SiteFragment.newInstance();
        adminViewFragment = AdminViewFragment.newInstance();
        switch (userPermissons){
            case(ADMIN):
                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public CharSequence getPageTitle(int position) {
                        if(position == 0) return "Site";
                        if(position == 1) return "Messaging";
                        return "Admin";
                    }
                    @Override
                    public int getCount() {
                        return 3;
                    }
                    @Override
                    public Fragment getItem(int position) {
                        if(position == 0) return siteFragment;
                        else if(position == 1) return messagingFragment;
                        else if(position == 2) return adminViewFragment;
                        return null;
                    }
                });
                break;
            case(KITCHEN_LEAD):
                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return "Messaging";
                    }
                    @Override
                    public int getCount() {
                        return 1;
                    }
                    @Override
                    public Fragment getItem(int position) {
                        if(position == 0) return messagingFragment;
                        return null;
                    }
                });
                break;
            default:
                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

                    @Override
                    public CharSequence getPageTitle(int position) {
                        if(position == 0) return "Site";
                        return "Messaging";
                    }
                    @Override
                    public int getCount() {
                        return 2;
                    }
                    @Override
                    public Fragment getItem(int position) {
                        if(position == 0) return siteFragment;
                        if(position == 1) return messagingFragment;
                        return null;
                    }
                });
                break;
        }



        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        } else {
            mUserId = mFirebaseUser.getUid();

            final ArrayAdapter<String> adapterString = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);


            // Use Firebase to populate the list.
            mDatabase.child("users").child(mUserId).child("items").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    adapterString.add((String) dataSnapshot.child("title").getValue());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    adapterString.remove((String) dataSnapshot.child("title").getValue());
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
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
                mFirebaseAuth.signOut();
                loadLogInView();

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
