package org.code5.code5push.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

 import com.wang.avi.AVLoadingIndicatorView;

import org.code5.code5push.R;
import org.code5.code5push.interfaces.FragmentListener;
import org.code5.code5push.network.apiservice.UserRegistrationService;
import org.code5.code5push.network.model.RegistrationResponseData;
import org.code5.code5push.widget.PlaceSearchDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.subscriptions.CompositeSubscription;

import static org.code5.code5push.Validation.validateEmail;
import static org.code5.code5push.Validation.validateFields;


/**
 * Created by User on 8/18/2017.
 */

public class SignUpFragment extends Fragment
{

    private EditText eT_name;
    private EditText eT_email;
    private EditText eT_phoneNumber;
    private EditText eT_password;
    private EditText eT_confirmPassword;
    private EditText eT_stateName;
    private EditText eT_cityName;
    private EditText eT_subdivision;
    private EditText eT_area;
    private Button   signUpButton;
    private TextView  loginText;
    private AVLoadingIndicatorView loadingView;
    private static SignUpFragment mInstance = null;
    private static FragmentListener fragmentListener;
    private CompositeSubscription mSubscriptions;


    public static SignUpFragment newInstance(FragmentListener listener)
    {
        fragmentListener = listener;
        if(mInstance ==null){


            mInstance = new SignUpFragment();
        }
        return mInstance;


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        mSubscriptions = new CompositeSubscription();
        return inflater.inflate(R.layout.signup,container,false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eT_name = (EditText)view.findViewById(R.id.inputName);
        eT_email = (EditText)view.findViewById(R.id.inputEmail);
        eT_phoneNumber = (EditText)view.findViewById(R.id.inputPhoneNumber);
        eT_password = (EditText)view.findViewById(R.id.inputPassword);
        eT_confirmPassword = (EditText)view.findViewById(R.id.inputConfirmPassword);
        eT_stateName = (EditText)view.findViewById(R.id.inputState);
        eT_stateName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showStatePickerDialog(eT_stateName,"Enter state");;
                }
                return false;
            }
        });
        eT_cityName = (EditText)view.findViewById(R.id.inputCity);
        eT_cityName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showStatePickerDialog(eT_cityName,"Enter city");;
                }
                return false;
            }
        });

        eT_subdivision =(EditText)view.findViewById(R.id.inputSubdivision);
        eT_subdivision.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showStatePickerDialog(eT_subdivision,"Enter division");;
                }
                return false;
            }
        });

        eT_area = (EditText)view.findViewById(R.id.inputArea);
        eT_area.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    showStatePickerDialog(eT_area,"Enter area");;
                }
                return false;
            }
        });

        signUpButton =(Button)view.findViewById(R.id.btnSignup);
        signUpButton.setOnClickListener(signUpListener);
        loginText = (TextView)view.findViewById(R.id.loginText);
        loginText.setOnClickListener(loginListener);
        loadingView = (AVLoadingIndicatorView)view.findViewById(R.id.loadingView);


    }

public View.OnClickListener signUpListener = new View.OnClickListener() {
    @Override
    public void onClick(View v)
    {
        setError();

        String name = eT_name.getText().toString();
        String email = eT_email.getText().toString();
        String password = eT_password.getText().toString();
        String confirm = eT_confirmPassword.getText().toString();
        String phoneNumber = eT_phoneNumber.getText().toString();
        String stateName = eT_stateName.getText().toString();
        String cityName = eT_cityName.getText().toString();
        String subDivision = eT_cityName.getText().toString();
        String area = eT_cityName.getText().toString();



        int err = 0;

        if (!validateFields(name)) {

            err++;
            eT_name.setError("Name should not be empty !");
        }

        if (!validateEmail(email)) {

            err++;
            eT_email.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            eT_password.setError("Password should not be empty !");
        }

        if (!validateFields(password)) {

            err++;
            eT_password.setError("Password should not be empty !");
        }
        if (!validateFields(password)) {

            err++;
            eT_password.setError("Password should not be empty !");
        }
        if (!validateFields(password)) {

            err++;
            eT_password.setError("Password should not be empty !");
        }



        if (err == 0) {


          //  mProgressbar.setVisibility(View.VISIBLE);
            loadingView.show();
            registerProcess(name,email,password,confirm,phoneNumber,stateName,cityName);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }


    }
};



private void registerProcess(String name, String email, String password, String confirm, String phoneNumber, String stateName, String cityName)
{
    UserRegistrationService userService = UserRegistrationService.retrofit.create(UserRegistrationService.class);
    Call<RegistrationResponseData> call = userService.registerInDetail(name,email,password,confirm,phoneNumber,stateName,cityName);
    call.enqueue(new Callback<RegistrationResponseData>() {
        @Override
        public void onResponse(Call<RegistrationResponseData> call, Response<RegistrationResponseData> response)
        {
            Log.d("Testtag",response.message());
            loadingView.hide();

        }

        @Override
        public void onFailure(Call<RegistrationResponseData> call, Throwable t)
        {

            loadingView.hide();

        }
    });


}



    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        //ft.replace(R.id.fragmentFrame, fragment, LoginFragment.TAG);
        ft.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }

    private void setError() {

        eT_name.setError(null);
        eT_email.setError(null);
        eT_password.setError(null);
        eT_phoneNumber.setError(null);
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }


public View.OnClickListener loginListener = new View.OnClickListener()
{

    @Override
    public void onClick(View v)
    {

        fragmentListener.onLoginClick();

    }


};


    private void showStatePickerDialog(final EditText et,String message)
    {

        PlaceSearchDialog placeSearchDialog = new PlaceSearchDialog.Builder(getActivity())
                .setHintText(message)
                .setHintTextColor(android.R.color.black)
                .setNegativeText("CANCEL")
                .setNegativeTextColor(R.color.colorAccent)
                .setPositiveText("SUBMIT")
                .setPositiveTextColor(R.color.colorAccent)
                .setLocationNameListener(new PlaceSearchDialog.LocationNameListener() {
                    @Override
                    public void locationName(String locationName)
                    {
                        et.setText(locationName);

                    }
                })
                .build();
        placeSearchDialog.show();


    }


}
