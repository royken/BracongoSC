package com.royken.bracongo.bracongosc.activity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.ChoixCircuitFragment;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.CompteClientRecycleAdapter;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.ModuleChoice;
import com.royken.bracongo.bracongosc.viewmodel.CompteViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String PAGE_NAME = "COMPTE_HOME_PAGE";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Compte> comptes;

    private CompteClientRecycleAdapter compteClientRecycleAdapter;

    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    private CompteViewModel compteViewModel;

    private Button ajouterCompteBtn;

    private SharedPreferences sharedPreferences;

    private String role;

    private String accessToken;

    private String userName;

    public CompteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CompteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompteFragment newInstance() {
        CompteFragment fragment = new CompteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compteViewModel = new ViewModelProvider(this).get(CompteViewModel.class);
        //compteViewModel = new ViewModelProvider((ViewModelStoreOwner) getViewLifecycleOwner()).get(CompteViewModel.class);
        // title.setText("Accueil" );
        compteClientRecycleAdapter = new CompteClientRecycleAdapter(getActivity());
        compteViewModel.getAllCompte().observe(getViewLifecycleOwner(), compteList -> {
            compteClientRecycleAdapter.setData(compteList);
            recyclerView.setAdapter(compteClientRecycleAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });
        //compteClientRecycleAdapter = new CompteClientRecycleAdapter(getActivity(), comptes);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_compte, container, false);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Liste des comptes");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ajouterCompteBtn = (Button) rootView.findViewById(R.id.ajouterCompteBtn);
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
            if(! "MERCH".equalsIgnoreCase(role)){
                Toast.makeText(getContext(),"Vous n'êtes pas autorisé à créer un compte", Toast.LENGTH_LONG).show();
            }
            else{
                ajouterCompteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = ChoixCircuitFragment.newInstance(String.valueOf(ModuleChoice.CREATION));
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment,fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                /*Fragment fragment = ChoixCdCircuitFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();*/
                    }
                });
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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