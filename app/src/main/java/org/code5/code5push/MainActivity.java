package org.code5.code5push;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.code5.code5push.fragments.LoginFragment;
import org.code5.code5push.fragments.SignUpFragment;
import org.code5.code5push.interfaces.FragmentListener;

public class MainActivity extends AppCompatActivity implements FragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container,SignUpFragment.newInstance(this)).commit();
    }

    @Override
    public void onLoginClick()
    {
        getFragmentManager().beginTransaction().replace(R.id.container,new LoginFragment()).commit();
    }
}
