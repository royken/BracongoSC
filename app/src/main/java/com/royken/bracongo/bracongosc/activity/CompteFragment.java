package com.royken.bracongo.bracongosc.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.CompteClientRecycleAdapter;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.viewmodel.CompteViewModel;

import java.util.List;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Compte> comptes;

    private CompteClientRecycleAdapter compteClientRecycleAdapter;

    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    private CompteViewModel compteViewModel;

    private Button ajouterCompteBtn;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ajouterCompteBtn = (Button) rootView.findViewById(R.id.ajouterCompteBtn);
        ajouterCompteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ChoixCdCircuitFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
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
}