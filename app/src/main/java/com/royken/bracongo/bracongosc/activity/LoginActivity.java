package com.royken.bracongo.bracongosc.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.dao.CentreDistributionDao;
import com.royken.bracongo.bracongosc.dao.CircuitDao;
import com.royken.bracongo.bracongosc.dao.CompteDao;
import com.royken.bracongo.bracongosc.database.ClientDatabase;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.LoginData;
import com.royken.bracongo.bracongosc.entities.LoginResponse;
import com.royken.bracongo.bracongosc.network.NetworkUtil;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.security.Util;
import com.royken.bracongo.bracongosc.util.Tools;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    private AppCompatEditText usernameTvw;
    private AppCompatEditText passwordTvw;
    private Button loginBtn;
    private String email;
    private String password;

    private LoginData loginData;
    private LoginResponse loginResponse;

    private SharedPreferences sharedPreferences;

    private LinearLayout layout;

    KLoadingSpin spinner;



    private CircuitDao circuitDao;

    private CompteDao compteDao;

    private  CentreDistributionDao cdDao ;
    private LinearLayout passforgotLayout;

    private TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent_view = findViewById(android.R.id.content);

        usernameTvw = (AppCompatEditText) findViewById(R.id.username);
        passwordTvw = (AppCompatEditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        spinner = findViewById(R.id.spinner);
        layout = findViewById(R.id.layout);
        spinner.setIsVisible(false);
        passforgotLayout = (LinearLayout) findViewById(R.id.passforgotLayout);
        forgotPass = (TextView) findViewById(R.id.forgotPass);
        passforgotLayout.setVisibility(View.INVISIBLE);

        ClientDatabase clientDatabase = ClientDatabase.getInstance(this);
        circuitDao = clientDatabase.getCircuitDao();
        compteDao = clientDatabase.getCompteDao();
        cdDao = clientDatabase.getCentreDao();
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,
                        ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey.Builder(getApplicationContext(),MasterKey.DEFAULT_MASTER_KEY_ALIAS).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            sharedPreferences = EncryptedSharedPreferences.create(
                    getApplicationContext(),
                    "com.bracongo.bracongosc.sharedPrefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            boolean firstLoad = sharedPreferences.getBoolean("config.firstLoad", false);
            boolean isLogged = sharedPreferences.getBoolean("config.isLogged", false);
            String token = sharedPreferences.getString("user.accessToken", "");
            //if(firstLoad || (!isLogged) || (token == null) || token.length() < 1){
                // first load stuff, log or register user
                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        password = passwordTvw.getText().toString().trim();
                        email = usernameTvw.getText().toString().trim();
                        if(password.isEmpty() || email.isEmpty()){
                            Snackbar.make(parent_view, "Email & Mdp obligatoires", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if(password.length() < 4){
                            Snackbar.make(parent_view, "Mdp au moins 5 caractères", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if(!isValid(email)){
                            Snackbar.make(parent_view, "Email invalide", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if(!NetworkUtil.isConnected(getApplicationContext())){
                            Snackbar.make(parent_view, "Vérifiez votre connexion internet", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            loginData = buildLoginData();
                            login();
                        }

                    }
                });
            //}
           /* else{
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                LoginActivity.this.finish();
            }*/
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void login() {
        layout.setVisibility(View.INVISIBLE);
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", "");
        WebService service = retrofit.create(WebService.class);
        service.register(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ERROR CALL", e.getMessage());
                        Snackbar.make(parent_view, "Erreur connexion, essayer ultérieurement", Snackbar.LENGTH_LONG).show();
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                        layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                        layout.setVisibility(View.VISIBLE);
                        if(!loginResponse.isStatus()){
                            Snackbar.make(parent_view, "Login/Mdp incorrect", Snackbar.LENGTH_SHORT).show();
                            passforgotLayout.setVisibility(View.VISIBLE);
                            return;
                        }
                        if(!loginResponse.isHasWritgh()){
                            Snackbar.make(parent_view, "Vous n'êtes pas autorisé à utiliser cette application", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        if(!(loginResponse.getRole().equalsIgnoreCase("MERCH") || loginResponse.getRole().equalsIgnoreCase("SUP") || loginResponse.getRole().equalsIgnoreCase("DIR") || loginResponse.getRole().equalsIgnoreCase("BAC"))){

                        }
                        List<CentreDistribution> cds = loginResponse.getCds();
                        List<Circuit> circuits = loginResponse.getCircuits();
                        List<Compte> comptes = loginResponse.getCompteClients();
                        AsyncTask.execute(() ->
                        {
                            if(cds.size() > 0){
                                cdDao.deleteAllCentreDistribution();
                                cdDao.insertAllCentreDistribution(cds);
                            }
                            if(circuits.size() > 0){
                                circuitDao.deleteAllCircuit();
                                circuitDao.insertAllCircuit(circuits);
                            }
                            if(comptes.size() > 0){
                                compteDao.deleteAllCompte();
                                compteDao.insertAllCompte(comptes);
                            }
                        }
                        );

                        registerUser(loginResponse);
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse response) {
                        loginResponse = response;
                    }
                });
    }

    /* Test for castel-afrique.com mail*/
    private boolean isValid(String email) {
        String regex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
        if(!email.matches(regex)){
            return false;
        }
        if(email.length() < 20)
            return false;
        String root = email.substring(email.length()-19);
        if(!root.equalsIgnoreCase("@castel-afrique.com"))
            return false;
        return true;

    }

    private LoginData buildLoginData(){
        LoginData result = new LoginData();
        String deviceID = Tools.getDeviceID(getApplicationContext());
        String appVersion = Tools.getVersionNamePlain(getApplicationContext());
        Calendar cal = Calendar.getInstance();
        long ms = cal.getTimeInMillis();
        String dateYear = cal.get(Calendar.YEAR) + ""+ cal.get(Calendar.MONTH);
        String token = Util.getToken(ms,deviceID,dateYear);
        result.setToken(deviceID + "|" + dateYear + "|" + ms + "|" + token);
        result.setAppVersion(appVersion);
        result.setEmail(email);
        result.setPassword(password);
        return result;
    }

    private void registerUser(LoginResponse response){
        sharedPreferences.edit().putString("user.username", response.getUsername().trim()).apply();
        sharedPreferences.edit().putString("user.accessToken", response.getAccessToken().trim()).apply();
        sharedPreferences.edit().putString("user.role", response.getRole().trim()).apply();
        sharedPreferences.edit().putString("user.nom", response.getNom().trim()).apply();
        sharedPreferences.edit().putBoolean("config.firstLoad", false).apply();
        sharedPreferences.edit().putBoolean("config.isLogged", true).apply();
        sharedPreferences.edit().putBoolean("config.clientLoaded", false).apply();
    }
}
