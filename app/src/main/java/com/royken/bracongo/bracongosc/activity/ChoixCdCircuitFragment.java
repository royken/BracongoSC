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
import android.widget.Toast;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.CdRecycleAdapter;
import com.royken.bracongo.bracongosc.adapter.CircuitRecycleAdapter;
import com.royken.bracongo.bracongosc.adapter.CompteClientRecycleAdapter;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.viewmodel.CdViewModel;
import com.royken.bracongo.bracongosc.viewmodel.CircuitViewModel;
import com.royken.bracongo.bracongosc.viewmodel.CompteViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoixCdCircuitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoixCdCircuitFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Circuit> circuits;
    private List<CentreDistribution> cds;
    private CdViewModel cdViewModel;
    private CircuitViewModel circuitViewModel;

    private RecyclerView cdRecyclerView;
    private RecyclerView circuitRecyclerView;

    private Button suivantBtn;

    private CdRecycleAdapter cdRecycleAdapter;
    private CircuitRecycleAdapter circuitRecycleAdapter;

    public ChoixCdCircuitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChoixCdCircuitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoixCdCircuitFragment newInstance() {
        ChoixCdCircuitFragment fragment = new ChoixCdCircuitFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_choix_cd_circuit, container, false);
        cdRecyclerView = (RecyclerView) rootView.findViewById(R.id.listCd);
        circuitRecyclerView = (RecyclerView) rootView.findViewById(R.id.listCircuits);
        suivantBtn = (Button) rootView.findViewById(R.id.suivant);
        suivantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CentreDistribution cd = cdRecycleAdapter.getSelectedCd();
                if(cd == null) {
                    Toast.makeText(getContext(), "La selection du CD est obligatoire", Toast.LENGTH_LONG).show();
                    return;
                }
                Circuit circuit = circuitRecycleAdapter.getSelectedCircuit();
                if(circuit == null){
                    Toast.makeText(getContext(), "La selection du circuit est obligatoire", Toast.LENGTH_LONG).show();
                }
                Fragment fragment = AjoutCompteFragment.newInstance(cd.getCdiCodecd(), circuit.getCirCodcir());
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cdViewModel = new ViewModelProvider(this).get(CdViewModel.class);
        //compteViewModel = new ViewModelProvider((ViewModelStoreOwner) getViewLifecycleOwner()).get(CompteViewModel.class);
        // title.setText("Accueil" );
        cdRecycleAdapter = new CdRecycleAdapter(getActivity());
        cdViewModel.getAllCds().observe(getViewLifecycleOwner(), cds_ -> {
            cdRecycleAdapter.setData(cds_ );
            cdRecyclerView.setAdapter(cdRecycleAdapter);
            cdRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });

        circuitViewModel = new ViewModelProvider(this).get(CircuitViewModel.class);
        circuitRecycleAdapter = new CircuitRecycleAdapter(getActivity());
        circuitViewModel.getAllCircuits().observe(getViewLifecycleOwner(), circuitList -> {
            circuitRecycleAdapter.setData(circuitList );
            circuitRecyclerView.setAdapter(circuitRecycleAdapter);
            circuitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}