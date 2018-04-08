package yilungao.gmail.com.eganwarmingcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddSiteActivity extends AppCompatActivity {
    TextView name;
    EditText capacity;
    Switch children;
    Switch adult;
    Switch disability;
    Switch pets;
    Switch enabled;
    private Button editSave;
    private String siteID;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        enabled = findViewById(R.id.enabledSwitch);
        enabled.setChecked(false);
        name = findViewById(R.id.siteNameEdit);
        name.setText("");
        capacity = findViewById(R.id.capacitiesEntry);
        capacity.setText("0", TextView.BufferType.EDITABLE);
        children = findViewById(R.id.childrenSwitch);
        children.setChecked(false);
        adult = findViewById(R.id.adultsSwitch);
        adult.setChecked(false);
        disability = findViewById(R.id.disabilitiesSwitch);
        disability.setChecked(false);
        pets = findViewById(R.id.petsSwitch);
        pets.setChecked(false);
        editSave = findViewById(R.id.siteSaveButton);



        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siteID = name.getText().toString().replaceAll("(?:--|[\\[\\]{}()+/\\\\] )", "");

                // add site to database
                Map<String, Object> userUpdate = new HashMap<>();

                userUpdate.put("active", enabled.isChecked());
                userUpdate.put("adult", adult.isChecked());
                userUpdate.put("capacity", Integer.parseInt(capacity.getText().toString()));
                userUpdate.put("children", children.isChecked());
                userUpdate.put("disability", disability.isChecked());
                userUpdate.put("name", name.getText().toString());
                userUpdate.put("num_people", 0);
                userUpdate.put("pets", adult.isChecked());

                mDatabase.child("sites").child(siteID).setValue(userUpdate);


                finish();
            }
        });
    }
}