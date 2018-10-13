package com.ahmedvargos.centralannotations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ahmedvargos.central_runtime.Central;
import com.ahmedvargos.central_runtime.annotations.LogAtEntry;
import com.ahmedvargos.central_runtime.annotations.RestrictToType;

import java.text.Normalizer;


public class ExampleActivity extends AppCompatActivity {

    private static final int NORMAL_USER = 0;
    private static final int PREMIUM_USER = 1;
    private static final int ADMIN = 2;

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the library to the type you want to be used during the running
        Central.setType(NORMAL_USER);
        setContentView(R.layout.activity_example);

        textView = findViewById(R.id.text_view);

        setAsNormalUser();
        setAsPremiumUser();
        setAsAdmin();

    }

    @LogAtEntry
    @RestrictToType(type = ADMIN)
    private void setAsAdmin() {
        textView.setText(getString(R.string.admin_set));
    }


    @RestrictToType(type = PREMIUM_USER)
    private void setAsPremiumUser() {
        textView.setText(getString(R.string.premium_user_set));
    }

    @LogAtEntry
    @RestrictToType(type = NORMAL_USER)
    private void setAsNormalUser() {
        textView.setText(getString(R.string.normal_user_set));
    }
}
