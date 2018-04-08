package yilungao.gmail.com.eganwarmingcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewFragment extends Fragment {

    int position;
    private ListView adminListView;
    private MainActivity myParent;
    ArrayList<Person> peopleList;
    public ArrayAdapter<Person> peopleAdapter;
    private DatabaseReference mDatabase;


    public static AdminViewFragment newInstance(ArrayList<Person> people, MainActivity parent) {
        AdminViewFragment adminFrag = new AdminViewFragment();
        adminFrag.myParent = parent;
        Bundle args = new Bundle();
        args.putParcelableArrayList("adminList", people);
        adminFrag.setArguments(args);
        return adminFrag;
    }

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        AdminViewFragment adminFragment = new AdminViewFragment();
        adminFragment.setArguments(bundle);
        return adminFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myParent.adminPeopleList = getArguments().getParcelableArrayList("adminList");
            position = getArguments().getInt("pos");
        }
        peopleAdapter = myParent.adminPersonAdapter;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_layout, container, false);
        adminListView = view.findViewById(R.id.adminList);
        adminListView.setAdapter(peopleAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Person> userArray = new ArrayList<Person>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = userDataSnapshot.getKey();
                    String name = (String) userDataSnapshot.child("name").getValue();
                    String email = (String) userDataSnapshot.child("email").getValue();
                    String role = userDataSnapshot.child("role").getValue(Integer.class) + "";

                    Person updatedPerson = new Person(email, name, role);
                    userArray.add(updatedPerson);
                }
                peopleAdapter.clear();
                peopleAdapter.addAll(userArray);
                peopleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}