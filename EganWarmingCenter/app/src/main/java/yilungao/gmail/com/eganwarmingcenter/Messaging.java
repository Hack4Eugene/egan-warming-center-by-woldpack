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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Messaging extends Fragment {

    int position;
    private ListView peopleListView;
    private MainActivity myParent;
    ArrayList<Person> peopleList;
    public ArrayAdapter<Person> peopleAdapter;


    public static Messaging newInstance(ArrayList<Person> people, MainActivity parent) {
        Messaging messagingFrag = new Messaging();
        messagingFrag.myParent = parent;
        Bundle args = new Bundle();
        args.putParcelableArrayList("peopleList", people);
        messagingFrag.setArguments(args);
        return messagingFrag;
    }

    public static Fragment getInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position);
        Messaging messagingFragment = new Messaging();
        messagingFragment.setArguments(bundle);
        return messagingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myParent.mainPeopleList = getArguments().getParcelableArrayList("peopleList");
            position = getArguments().getInt("pos");
        }
        peopleAdapter = myParent.mainPersonAdapter;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_layout, container, false);
        peopleListView = view.findViewById(R.id.list_of_messages);
        peopleListView.setAdapter(peopleAdapter);
        peopleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Person clicked = myParent.mainPeopleList.get(position);
                Intent intent = new Intent(myParent, Message.class);
                intent.putExtra("index", clicked);
                startActivity(intent);
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