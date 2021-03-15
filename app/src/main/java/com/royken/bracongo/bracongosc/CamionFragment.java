package com.royken.bracongo.bracongosc;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.royken.bracongo.bracongosc.activity.AjoutCompteFragment;
import com.royken.bracongo.bracongosc.adapter.CustomCamionInfoWindowAdapter;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.entities.geo.CircInfoJSResult;
import com.royken.bracongo.bracongosc.entities.geo.PositionsCamion;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Constants;
import com.royken.bracongo.bracongosc.util.GeoUrlUtil;
import com.royken.bracongo.bracongosc.util.Tools;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CamionFragment extends Fragment {
    private static final String ARG_CIRCUIT_ID = "circuitId";
    private final String PAGE_NAME = "POSITION_CAMION";

    private String accessToken;

    private String userName;

    private SharedPreferences sharedPreferences;

    private String circuitId;

    PositionsCamion positionsCamion;

    private static final int DEFAULT_ZOOM = 15;

    private int markerHeight = 100;
    private int markerWidth = 100;


    public static CamionFragment newInstance(String circuit) {
        CamionFragment fragment = new CamionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIRCUIT_ID, circuit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            circuitId = getArguments().getString(ARG_CIRCUIT_ID);
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            String deviceID = Tools.getDeviceID(getContext());
            String token = GeoUrlUtil.getCamionPositionUrl(deviceID, circuitId);
            Log.i("URLGEO", token);

            getCamionPosition(token, googleMap);

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camion, container, false);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Position Camion SRD");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getCamionPosition(String token, GoogleMap googleMap) {
        Retrofit retrofit = RetrofitBuilder.getRetrofit("https://4track.net/", accessToken);
        WebService service = retrofit.create(WebService.class);
        service.getPositionCamion(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PositionsCamion>() {

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "Une erreur s'est produite, merci de reessayer plus tard", Toast.LENGTH_LONG).show();
                        return;
                    }

                    @Override
                    public void onComplete() {
                        if (positionsCamion == null) {
                            Toast.makeText(getContext(), "Une inattendue erreur s'est produite, merci de reessayer plus tard", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<CircInfoJSResult> positions = positionsCamion.getCircInfoJSResultList();
                        if (positions.isEmpty()) {
                            Toast.makeText(getContext(), "Aucune position pour ce camion", Toast.LENGTH_LONG).show();
                            return;
                        }

                        CircInfoJSResult position = positions.get(0);
                        String adresse = getAddressFromLocation(position.getLat(), position.getLng());
                        LatLng camion = new LatLng(position.getLat(), position.getLng());
                        int camionIcon = position.getSpeed() == 0 ? R.drawable.camion_rouge : R.drawable.camion_vert;
                        Bitmap b = BitmapFactory.decodeResource(getResources(), camionIcon);
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, markerWidth, markerHeight, false);
                        MarkerOptions markerOpt = new MarkerOptions();
                        markerOpt.position(camion).title(position.getcRegNo().trim())
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                        CustomCamionInfoWindowAdapter adapter = new CustomCamionInfoWindowAdapter(getContext(), position.getcRegNo().trim(), position.getLat() + "", position.getLng() + "", buildDateFromTime(position.getLdt()), adresse, position.getSpeed() + "km/h", "20350");
                        googleMap.setInfoWindowAdapter(adapter);
                        googleMap.addMarker(markerOpt).showInfoWindow();
                         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camion, 16));

                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            Toast.makeText(getContext(), "Vous devez accepter l'utilisation du GPS", Toast.LENGTH_LONG).show();
                            return;
                        }
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.getUiSettings().setCompassEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                        googleMap.getUiSettings().setRotateGesturesEnabled(true);
                        googleMap.getUiSettings().setScrollGesturesEnabled(true);
                        googleMap.getUiSettings().setTiltGesturesEnabled(true);
                        googleMap.getUiSettings().setZoomGesturesEnabled(true);
                        googleMap.setTrafficEnabled(true);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PositionsCamion positions) {
                        positionsCamion = positions;
                    }
                });
    }

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit(Constants.API_BASE_URL, accessToken);
        WebService service = retrofit.create(WebService.class);
        PageLog page = new PageLog();
        page.setPage(PAGE_NAME);
        page.setUtilisateur(userName);
        page.setClient(circuitId + "");
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

    private String getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(getContext(), Locale.FRANCE);
        String result = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                }
               result = fetchedAddress.getAddressLine(0) + ", " + fetchedAddress.getLocality();
                return strAddress.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String buildDateFromTime(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"+ cal.get(Calendar.SECOND);
    }


}