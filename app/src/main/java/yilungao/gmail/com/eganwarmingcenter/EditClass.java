package yilungao.gmail.com.eganwarmingcenter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;


public class EditClass extends AppCompatActivity{
    TextView name;
    EditText capacity;
    Switch children;
    Switch adult;
    Switch disability;
    Switch pets;
    Switch enabled;
    private Button editSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_site_page);

        String siteName = getIntent().getStringExtra("siteName");
        String siteCapacity = getIntent().getStringExtra("siteCapacity");
        Boolean siteChildren = getIntent().getBooleanExtra("siteChildren", false);
        Boolean siteAdult = getIntent().getBooleanExtra("siteAdult", true);
        Boolean siteDisability = getIntent().getBooleanExtra("siteDisability", true);
        Boolean sitePets = getIntent().getBooleanExtra("sitePets", true);
        Boolean active = getIntent().getBooleanExtra("enabled", true);


        enabled = findViewById(R.id.enabledSwitch);
        enabled.setChecked(active);
        name = findViewById(R.id.siteNameEdit);
        name.setText(siteName);
        capacity = findViewById(R.id.capacitiesEntry);
        capacity.setText(siteCapacity, TextView.BufferType.EDITABLE);
        children = findViewById(R.id.childrenSwitch);
        children.setChecked(siteChildren);
        adult = findViewById(R.id.adultsSwitch);
        adult.setChecked(siteAdult);
        disability = findViewById(R.id.disabilitiesSwitch);
        disability.setChecked(siteDisability);
        pets = findViewById(R.id.petsSwitch);
        pets.setChecked(sitePets);
        editSave = findViewById(R.id.siteSaveButton);

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("capacity", Integer.parseInt(capacity.getText().toString()));
                intent.putExtra("children", children.isChecked());
                intent.putExtra("adult", adult.isChecked());
                intent.putExtra("disability", disability.isChecked());
                intent.putExtra("pets", pets.isChecked());
                intent.putExtra("enabled", enabled.isChecked());
                setResult(CommonStatusCodes.SUCCESS, intent);
                finish();
            }
        });
    }
}
