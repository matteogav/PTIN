package com.example.ptin;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private Spinner _languageSpinner;
    
    private AutoCompleteTextView _nameText;
    private AutoCompleteTextView _surnameText;
    private AutoCompleteTextView _usernameText;
    private AutoCompleteTextView _emailText;
    private AutoCompleteTextView _phoneText;
    private AutoCompleteTextView _passwordText;
    private AutoCompleteTextView _confirmPasswordText;
    private AutoCompleteTextView _vehicle_brandText;
    private AutoCompleteTextView _vehicle_modelText;
    private AutoCompleteTextView _vehicle_license_plateText;

    private Button _registerButton;

    private static boolean RecreateAvoiderOnFirstLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RecreateAvoiderOnFirstLoad = true;

        _languageSpinner = (Spinner) findViewById(R.id.register_lang_select_spinner);
        
        _nameText = (AutoCompleteTextView) findViewById(R.id.name);
        _surnameText = (AutoCompleteTextView) findViewById(R.id.surname);
        _usernameText = (AutoCompleteTextView) findViewById(R.id.username);
        _emailText = (AutoCompleteTextView) findViewById(R.id.email);
        _phoneText = (AutoCompleteTextView) findViewById(R.id.phone);
        _passwordText = (AutoCompleteTextView) findViewById(R.id.password);
        _confirmPasswordText = (AutoCompleteTextView) findViewById(R.id.confirmPassword);
        _vehicle_brandText = (AutoCompleteTextView) findViewById(R.id.vehicle_brand);
        _vehicle_modelText = (AutoCompleteTextView) findViewById(R.id.vehicle_model);
        _vehicle_license_plateText = (AutoCompleteTextView) findViewById(R.id.vehicle_license_plate);

        _registerButton = (Button) findViewById(R.id.register_button);
        
        


        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


        _languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String new_lang=_languageSpinner.getSelectedItem().toString().toLowerCase();
                if (new_lang.length() > 2){new_lang = new_lang.substring(0, 2);}


                //_nameText.setText(getBaseContext().getResources().getString(R.string.locale));

                Locale locale = new Locale(new_lang);
                Locale.setDefault(locale);
                //Locale.setDefault(Locale.getDefault());
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                /*ViewGroup vg = findViewById (R.id.linearLayout);
                vg.invalidate();*/



                if (!RecreateAvoiderOnFirstLoad){recreate(); RecreateAvoiderOnFirstLoad = true;
                    //try {TimeUnit.MILLISECONDS.sleep(10000);} catch (InterruptedException e){e.printStackTrace();}
                    /*Es agresivo, pero funciona; hay que arreglar el parpadeo causado por el bucle infinito*/}
                else if (RecreateAvoiderOnFirstLoad) {RecreateAvoiderOnFirstLoad = false;}




                //findViewById(R.id.linearLayout).setVisibility(View.GONE);
                //findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);


                //_nameText.setText(getResources().getConfiguration().locale.toString());
                //_nameText.setText(Locale.getDefault().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        
        
    }



    public void register() {
        Log.d(TAG, "Register");

        if (!validate()) {
            onRegisterFailed();
            return;
        }

        _registerButton.setEnabled(false);








        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark); //Con AppTheme_Dark_Dialog daba error
        //progressDialog.getWindow().setGravity(Gravity.CENTER); //Esto aparentemente no hace nada? Mi idea era que apareciese en el centro de la pantalla... Pero supongo que como sale ahora tampoco está mal
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
/*
        String name = _nameText.getText().toString();
        String surname = _surnameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirmPasswordText.getText().toString();
        String vehicle_brand = _vehicle_brandText.getText().toString();
        String vehicle_model = _vehicle_modelText.getText().toString();
        String vehicle_license_plate = _vehicle_license_plateText.getText().toString();
*/
        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onRegisterSuccess or onRegisterFailed
                        // depending on success
                        onRegisterSuccess();
                        // onRegisterFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onRegisterSuccess() {

        String name = _nameText.getText().toString();
        String surname = _surnameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirmPasswordText.getText().toString();
        String vehicle_brand = _vehicle_brandText.getText().toString();
        String vehicle_model = _vehicle_modelText.getText().toString();
        String vehicle_license_plate = _vehicle_license_plateText.getText().toString();

        //sendDataToAPI();

        Toast toast = Toast.makeText(this, "REGISTER SUCCESSFUL!\nWelcome, " + name + "!", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();


        _registerButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onRegisterFailed() {

        //Toast.makeText(getBaseContext(), "    Register failed\nCheck the errors above!", Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(this, "REGISTER FAILED\nCheck the errors above!", Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();

        _registerButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String surname = _surnameText.getText().toString();
        String username = _usernameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmPassword = _confirmPasswordText.getText().toString();
        String vehicle_brand = _vehicle_brandText.getText().toString();
        String vehicle_model = _vehicle_modelText.getText().toString();
        String vehicle_license_plate = _vehicle_license_plateText.getText().toString();


        if (name.isEmpty() || name.length() < 2) {
            _nameText.setError("at least 2 characters");
            valid = false;
        } else {_nameText.setError(null);}


        if (surname.isEmpty() || surname.length() < 2) {
            _surnameText.setError("at least 2 characters");
            valid = false;
        } else {_surnameText.setError(null);}


        if (username.isEmpty() || username.length() < 3) {
            _usernameText.setError("at least 3 characters");
            valid = false;
        } else {
            /*if (username.alreadyExistsAPI()) {
            _usernameText.setError("username already in use");
            valid = false;
            } else {_usernameText.setError(null);}*/
            /**/_usernameText.setError(null);}





        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {/*if (email.alreadyExistsAPI()) {
            _emailText.setError("email already in use");
            valid = false;
            } else {_emailText.setError(null);}*/
            /**/_emailText.setError(null);}



        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phoneText.setError("enter a valid phone number");
            valid = false;
        } else {_phoneText.setError(null);}


        if (password.isEmpty() || password.length() < 4 || password.length() > 32) {
            _passwordText.setError("between 4 and 32 alphanumeric characters");
            valid = false;
        } else {_passwordText.setError(null);}


        if (!confirmPassword.equals(password)) {
            _confirmPasswordText.setError("password confirmation has to match password");
            valid = false;
        } else {_confirmPasswordText.setError(null);}



        //Marca y modelo del vehículo realmente solo incumben al usuario, para que sepa qué vehículo usa cada vez
        if (vehicle_brand.isEmpty()) {
            _vehicle_brandText.setError("specify a brand for your vehicle");
            valid = false;
        } else {_vehicle_brandText.setError(null);}


        if (vehicle_model.isEmpty()) {
            _vehicle_modelText.setError("specify a model for your vehicle");
            valid = false;
        } else {_vehicle_modelText.setError(null);}


        //matricula 4 números y 3 letras; es necesario siquiera reconocerlas? Si el cliente la falsifica no saca nada; luego podría editar su coche si se ha equivocado
        if (vehicle_license_plate.isEmpty()) {
            _vehicle_license_plateText.setError("specify the license plate of your vehicle");
            valid = false;
        } else {_vehicle_license_plateText.setError(null);}

        return valid;
    }













}