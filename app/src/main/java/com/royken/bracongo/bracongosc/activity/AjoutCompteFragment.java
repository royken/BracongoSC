package com.royken.bracongo.bracongosc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.CdRecycleAdapter;
import com.royken.bracongo.bracongosc.adapter.CircuitRecycleAdapter;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.LoginResponse;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.viewmodel.CdViewModel;
import com.royken.bracongo.bracongosc.viewmodel.CircuitViewModel;
import com.royken.bracongo.bracongosc.viewmodel.CompteViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AjoutCompteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjoutCompteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CODE_CD = "codeCd";
    private static final String ARG_CODE_CIRCUIT = "codeCircuit";

    // TODO: Rename and change types of parameters
    private String codeCd;
    private String codeCircuit;

    private View parent_view;
    private EditText nomProprioTxt;
    private EditText postnomProprio;
    private EditText prenomProprio;
    private EditText communeProprio;
    private EditText noAdresseProprio;
    private EditText avenueAdresseProprio;
    private EditText quartierAdresseProprio;
    private EditText noTel1;
    private EditText noTel2;
    private EditText mail;
    private RadioGroup radioGroup;
    private RadioButton ouiBtn;
    private RadioButton nonBtn;
    private LinearLayout premierCompteLayout;
    private LinearLayout secondCompteLayout;
    private LinearLayout troisiemeCompteLayout;
    private LinearLayout ancienneteLayout;

    private EditText nomPdvTxt;
    private EditText communePdvTxt;
    private EditText noAdressePdvTxt;
    private EditText avenuePdvTxt;
    private EditText quartierPdvTxt;


    private EditText noCompte1;
    private EditText noCompte2;
    private EditText noCompte3;
    private Spinner regimeSpinner;
    private Button enregistrerBtn;

    private boolean hasCompte;

    private SharedPreferences sharedPreferences;

    private String accessToken;

    private String userName;

    private String cd;

    private String circuit;

    private String user;

    private String role;

    private CdViewModel cdViewModel;
    private CircuitViewModel circuitViewModel;
    private CompteViewModel compteViewModel;

    private Compte compte_;

    private LinearLayout layout;

    KLoadingSpin spinner;

    public AjoutCompteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param codeCd Parameter 1.
     * @param codeCircuit Parameter 2.
     * @return A new instance of fragment AjoutCompteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AjoutCompteFragment newInstance(String codeCd, String codeCircuit) {
        AjoutCompteFragment fragment = new AjoutCompteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODE_CD, codeCd);
        args.putString(ARG_CODE_CIRCUIT, codeCircuit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codeCd = getArguments().getString(ARG_CODE_CD);
            codeCircuit = getArguments().getString(ARG_CODE_CIRCUIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_ajout_compte, container, false);
        parent_view = rootView.findViewById(android.R.id.content);
        nomProprioTxt = (EditText) rootView.findViewById(R.id.nomProprio);
        postnomProprio = (EditText) rootView.findViewById(R.id.postnomProprio);
        prenomProprio = (EditText) rootView.findViewById(R.id.prenomProprio);
        communeProprio = (EditText) rootView.findViewById(R.id.commune);
        noAdresseProprio = (EditText) rootView.findViewById(R.id.noAdresse);
        avenueAdresseProprio = (EditText) rootView.findViewById(R.id.avenue);
        quartierAdresseProprio = (EditText) rootView.findViewById(R.id.quartier);
        noTel1 = (EditText) rootView.findViewById(R.id.noTel1);
        noTel2 = (EditText) rootView.findViewById(R.id.noTel2);
        mail = (EditText) rootView.findViewById(R.id.mail);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
        ouiBtn = (RadioButton) rootView.findViewById(R.id.ouiBtn);
        nonBtn = (RadioButton) rootView.findViewById(R.id.nonBtn);
        nonBtn.setChecked(true);
        ancienneteLayout = (LinearLayout) rootView.findViewById(R.id.ancienneteLayout);
        premierCompteLayout = (LinearLayout) rootView.findViewById(R.id.premierCompteLayout);
        secondCompteLayout = (LinearLayout) rootView.findViewById(R.id.secondCompteLayout);
        troisiemeCompteLayout = (LinearLayout) rootView.findViewById(R.id.troisiemeCompteLayout);
        regimeSpinner = (Spinner) rootView.findViewById(R.id.regime);
        enregistrerBtn = (Button) rootView.findViewById(R.id.enregistrer);
        noCompte1 = (EditText) rootView.findViewById(R.id.noCompte1);
        noCompte2 = (EditText) rootView.findViewById(R.id.noCompte2);
        noCompte3 = (EditText) rootView.findViewById(R.id.noCompte3);
        nomPdvTxt = (EditText) rootView.findViewById(R.id.nomPdv);
        communePdvTxt = (EditText) rootView.findViewById(R.id.communePdv);
        noAdressePdvTxt = (EditText) rootView.findViewById(R.id.noAdressePdv);
        avenuePdvTxt = (EditText) rootView.findViewById(R.id.avenuePdv);
        quartierPdvTxt = (EditText) rootView.findViewById(R.id.quartierPdv);
        ancienneteLayout.removeView(premierCompteLayout);
        ancienneteLayout.removeView(secondCompteLayout);
        ancienneteLayout.removeView(troisiemeCompteLayout);
        spinner = rootView.findViewById(R.id.spinner);
        //layout = rootView.findViewById(R.id.layout);
        spinner.setIsVisible(false);
        Resources res = getResources();
        String[] regimes = res.getStringArray(R.array.regime_list);
        Log.i("LIST", regimes.toString());
        compte_ = new Compte();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doChangeListener(group, checkedId);
            }
        });
        regimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                compte_.setRegime(regimes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey.Builder(getContext(),MasterKey.DEFAULT_MASTER_KEY_ALIAS).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            sharedPreferences = EncryptedSharedPreferences.create(
                    getContext(),
                    "com.bracongo.bracongosc.sharedPrefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            accessToken = sharedPreferences.getString("user.accessToken", "");
            userName  = sharedPreferences.getString("user.username", "");
            user = sharedPreferences.getString("user.nom", "");
            role = sharedPreferences.getString("user.role", "");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        enregistrerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomProprio = nomProprioTxt.getText().toString().trim();
                if(nomProprio.isEmpty()){
                    Toast.makeText(getContext(),"Le nom dupropriétaire est obligatoire", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setNomProprietaire(nomProprio.toUpperCase());
                String prenom = prenomProprio.getText().toString().trim();
                if(!prenom.isEmpty()){
                    compte_.setPrenomProprietaire(prenom.toUpperCase());
                }
                String postNom = postnomProprio.getText().toString().trim();
                if(!postNom.isEmpty()){
                    compte_.setPostnomProprietaire(postNom.toUpperCase());
                }
                String commune = communeProprio.getText().toString().trim();
                if(!commune.isEmpty()){
                    compte_.setCommuneProprietaire(commune.toUpperCase());
                }
                String noAdresse = noAdresseProprio.getText().toString().trim();
                if(!noAdresse.isEmpty()){
                    compte_.setNoAdresseProprietaire(noAdresse.toUpperCase());
                }
                String avenue = avenueAdresseProprio.getText().toString().trim();
                if(!avenue.isEmpty()){
                    compte_.setAvenueProprietaire(avenue.toUpperCase());
                }
                String quartier = quartierAdresseProprio.getText().toString().trim();
                if(!quartier.isEmpty()){
                    compte_.setQuartierProprietaire(quartier.toUpperCase());
                }
                String tel1 = noTel1.getText().toString().trim();
                if(tel1.isEmpty() || tel1.length() < 9){
                    Toast.makeText(getContext(),"Le 1er numéro de téléphone est manquant ou incorrect", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setTelephoneProprio1(tel1);
                String tel2 = noTel2.getText().toString().trim();
                if(!tel2.isEmpty()){
                    compte_.setTelephoneProprio12(tel2);
                }
                String email = mail.getText().toString().trim();
                if(!email.isEmpty()){
                    compte_.setEmailProprio(email);
                }
                String nomPdv = nomPdvTxt.getText().toString().trim();
                if(nomPdv.isEmpty() || nomPdv.length() < 1){
                    Toast.makeText(getContext(),"Le nom du PDV est manquant", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setNomPdv(nomPdv.toUpperCase());
                String communePdv = communePdvTxt.getText().toString().trim();
                if(communePdv.isEmpty() || communePdv.length() < 1){
                    Toast.makeText(getContext(),"La commune du PDV est manquante", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setCommunePdv(communePdv.toUpperCase());
                String noAdressePdv = noAdressePdvTxt.getText().toString().trim();
                if(noAdressePdv.isEmpty() || noAdressePdv.length() < 1){
                    Toast.makeText(getContext(),"Le no Adresse du PDV est manquant", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setNoAdressePdv(noAdressePdv.toUpperCase());
                String avenuePdv = avenuePdvTxt.getText().toString().trim();
                if(avenuePdv.isEmpty() || avenuePdv.length() < 1){
                    Toast.makeText(getContext(),"Le nom avenue du PDV est manquant", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setAvenuePdv(avenuePdv.toUpperCase());
                String quartierPdv = quartierPdvTxt.getText().toString().trim();
                if(quartierPdv.isEmpty() || quartierPdv.length() < 1){
                    Toast.makeText(getContext(),"Le quartier du PDV est manquant", Toast.LENGTH_LONG).show();
                    return;
                }
                compte_.setQuartierPdv(quartierPdv.toUpperCase());
                if(hasCompte){
                    compte_.setAlreadyHasAccount(true);
                    String compte1 = noCompte1.getText().toString();
                    String compte2 = noCompte2.getText().toString();
                    String compte3 = noCompte3.getText().toString();
                    List<String> comptes = new ArrayList<>();
                    if(!compte1.isEmpty() && compte1.length() > 4)
                        comptes.add(compte1);
                    if(!compte2.isEmpty() && compte2.length() > 4)
                        comptes.add(compte2);
                    if(!compte3.isEmpty() && compte3.length() > 4)
                        comptes.add(compte3);
                    compte_.setNumeroComptes(comptes);
                }
                else{
                    compte_.setAlreadyHasAccount(false);
                }
                if(role.equalsIgnoreCase("VEND")){
                    compte_.setValidationVendeur(true);
                    compte_.setDateValidationVendeur(new Date());
                    compte_.setValideurVendeur(user);
                }
                if(role.equalsIgnoreCase("MERCH")){
                    compte_.setValidationMerch(true);
                    compte_.setDateValidationMerch(new Date());
                    compte_.setValideurMerch(user);
                }
                if(role.equalsIgnoreCase("SUP")){
                    compte_.setValidationSup(true);
                    compte_.setDateValidationSup(new Date());
                    compte_.setValideurSup(user);
                }
                if(role.equalsIgnoreCase("BAC")){
                    compte_.setValidationBac(true);
                    compte_.setDateValidationBac(new Date());
                    compte_.setValideurBac(user);
                }
                compte_.setDemandeur(userName);
                compte_.setDateDemande(new Date());
                Log.i("COMPTE", compte_.toString());
                registerCompte();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compteViewModel = new ViewModelProvider(this).get(CompteViewModel.class);
        cdViewModel = new ViewModelProvider(this).get(CdViewModel.class);
        //compteViewModel = new ViewModelProvider((ViewModelStoreOwner) getViewLifecycleOwner()).get(CompteViewModel.class);
        // title.setText("Accueil" );
        cdViewModel.getAllCds().observe(getViewLifecycleOwner(), cds_ -> {
        });

        cdViewModel.getCdByCode(codeCd).observe(getViewLifecycleOwner(), cd -> {
            compte_.setCodeCd(cd.getCdiCodecd());
            compte_.setNomCd(cd.getCdiNomcdi());
        });

        circuitViewModel = new ViewModelProvider(this).get(CircuitViewModel.class);
        circuitViewModel.getCircuitByCode(codeCircuit).observe(getViewLifecycleOwner(), circuit -> {
            compte_.setCodeCircuit(circuit.getCirCodcir());
            compte_.setCodeSigmaCircuit(circuit.getCirCodsigma());
        });

    }

    private void doChangeListener(RadioGroup group, int checkedId){
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId == R.id.ouiBtn){
            hasCompte = true;
            ancienneteLayout.addView(premierCompteLayout);
            ancienneteLayout.addView(secondCompteLayout);
            ancienneteLayout.addView(troisiemeCompteLayout);
        }
        if(checkedRadioId == R.id.nonBtn){
            hasCompte = false;
            ancienneteLayout.removeView(premierCompteLayout);
            ancienneteLayout.removeView(secondCompteLayout);
            ancienneteLayout.removeView(troisiemeCompteLayout);
        }
    }

    private void registerCompte() {
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        service.saveCompte(compte_)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Compte>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ERROR CALL", e.getMessage());
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Erreur connexion, essayer ultérieurement", Snackbar.LENGTH_LONG).show();
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                        layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                       // layout.setVisibility(View.VISIBLE);
                        if(compte_.getId() == null){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Erreur lors de l'enregistrement", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        compteViewModel.saveCompte(compte_);
                        Fragment fragment = CompteFragment.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment,fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Compte compte) {
                        compte_ = compte;
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}