package com.royken.bracongo.bracongosc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.DemandeModificationMotDePasse;
import com.royken.bracongo.bracongosc.entities.DemandeModificationMotDePasseResult;
import com.royken.bracongo.bracongosc.entities.LoginResponse;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.network.NetworkUtil;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForgotPasswordActivity extends AppCompatActivity {

    private final String PAGE_NAME = "MDP_OUBLIE";
    private View parent_view;
    private AppCompatEditText usernameTvw;
    private Button generateBtn;
    private String email;
    private DemandeModificationMotDePasse demande;
    private DemandeModificationMotDePasseResult result;
    private LinearLayout layout;
    KLoadingSpin spinner;
    private String accessToken;
    private String userName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        parent_view = findViewById(android.R.id.content);
        usernameTvw = (AppCompatEditText) findViewById(R.id.username);
        generateBtn = (Button) findViewById(R.id.login);
        layout = findViewById(R.id.layout);
        spinner = findViewById(R.id.spinner);
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
            accessToken = sharedPreferences.getString("user.accessToken", "");
            userName  = sharedPreferences.getString("user.username", "");
            logPage();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = usernameTvw.getText().toString().trim();
                if(!isValid(email)){
                    Snackbar.make(parent_view, "Email invalide", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(!NetworkUtil.isConnected(getApplicationContext())){
                    Snackbar.make(parent_view, "Vérifiez votre connexion internet", Snackbar.LENGTH_LONG).show();
                }
                else {
                   demande = new DemandeModificationMotDePasse();
                   demande.setUsername(email.trim());
                    login();
                }

            }
        });
    }

    private void login() {
        layout.setVisibility(View.INVISIBLE);
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", "");
        WebService service = retrofit.create(WebService.class);
        service.passwordRecover(demande)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DemandeModificationMotDePasseResult>() {

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
                        if(!result.isStatus()){
                            Snackbar.make(parent_view, "Erreur lors de l'envoi, veuillez reessayer plus tard", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            Snackbar.make(parent_view, "Votre demande a été envoyée", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPasswordActivity.this,
                                    LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            ForgotPasswordActivity.this.finish();
                        }
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DemandeModificationMotDePasseResult response) {
                        result = response;
                    }
                });
    }

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        PageLog page = new PageLog();
        page.setPage(PAGE_NAME);
        page.setUtilisateur(userName);
        service.pageLog(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageLog>() {

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageLog compte) {

                    }
                });
    }

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
}