package com.royken.bracongo.bracongosc.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.android.material.appbar.AppBarLayout;
import com.royken.bracongo.bracongosc.ChoixCircuitFragment;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.ModuleChoice;

import java.io.IOException;
import java.security.GeneralSecurityException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String PAGE_NAME = "ACCUEIL";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout compte;
    private LinearLayout suiciClient;

    private SharedPreferences sharedPreferences;

    private String role;

    private String accessToken;

    private String userName;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        compte = (LinearLayout) rootView.findViewById(R.id.creationClient);
        suiciClient = (LinearLayout) rootView.findViewById(R.id.suiviClient);
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
            role = sharedPreferences.getString("user.role", "");
            accessToken = sharedPreferences.getString("user.accessToken", "");
            userName  = sharedPreferences.getString("user.username", "");
            logPage();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(role.equalsIgnoreCase("MERCH") || role.equalsIgnoreCase("SUP") || role.equalsIgnoreCase("DIR") || role.equalsIgnoreCase("BAC"))){
                    Toast.makeText(getContext(),"Vous n'êtes pas autorisé à créer un client", Toast.LENGTH_LONG).show();
                    return;
                }
                Fragment fragment = CompteFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        suiciClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ChoixCircuitFragment.newInstance(String.valueOf(ModuleChoice.SUIVI));
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
       // AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        //bar.setVisibility(View.INVISIBLE);
        //((ViewManager)bar.getParent()).removeView(bar);
        return rootView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
}
