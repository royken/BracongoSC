package com.royken.bracongo.bracongosc.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royken.bracongo.bracongosc.entities.AchatProduit;
import com.royken.bracongo.bracongosc.entities.AchatProduitMois;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.LoginData;
import com.royken.bracongo.bracongosc.entities.LoginResponse;
import com.royken.bracongo.bracongosc.entities.Materiel;
import com.royken.bracongo.bracongosc.entities.MessageReponse;
import com.royken.bracongo.bracongosc.entities.Plainte;
import com.royken.bracongo.bracongosc.entities.PlainteReponse;
import com.royken.bracongo.bracongosc.entities.ProduitMois;
import com.royken.bracongo.bracongosc.entities.RemiseInfo;
import com.royken.bracongo.bracongosc.entities.VenteReponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

public interface WebService {

  /*  @GET("bracongo-api/clients/circuit/{circuit}")
    Call<List<Client>> getClientsCircuit(@Path("circuit") String circuit);
*/
    @GET("bracongo-api/achats/remise/histo/{numero}/{password}")
    Observable<List<RemiseInfo>> getHistoRemise(@Path("numero") String numero, @Path("password") String password);

    @GET("bracongo-api/achats/{numero}/{password}")
    Call<List<AchatProduit>> getHistoAchatsMois(@Path("numero") String numero, @Path("password") String password);

    @GET("bracongo-api/achats/produits/{numero}/{password}")
    Call<List<ProduitMois>> getProduitsAchatsMois(@Path("numero") String numero, @Path("password") String password);

    @GET("bracongo-api/achats/produits/circuit/{circuit}")
    Call<List<ProduitMois>> getProduitsAchatsMoisCircuit(@Path("circuit") String circuit);

    @GET("bracongo-api/achats/annee/{numero}/{password}")
    Call<List<AchatProduitMois>> getHistoAchatsAnnee(@Path("numero") String numero, @Path("password") String password);

    @GET("bracongo-api/achats/circuit/{circuit}")
    Call<List<AchatProduitMois>> getHistoAchatsMoisCircuit(@Path("circuit") String circuit);

    @GET("bracongo-api/materiels/maryse/client/{numero}")
    Call<List<Materiel>> getMaterielsClientMaryse(@Path("numero") String numero);

    @GET("bracongo-api/plaintes/maryse/client/{numero}")
    Observable<List<Plainte>> getPlaintesClientMaryse(@Path("numero") String numero);

    @GET("backendapi/rest/client/ventes/{numero}/{password}")
    Call<VenteReponse> getVentes(@Path("numero") String numero, @Path("password") String password);


    @GET("clientapi/rest/messages/{numero}/{password}")
    Call<MessageReponse> getMessages(@Path("numero") String numero, @Path("password") String password);

    @POST("suiviclient/v1/users/register")
    Observable<LoginResponse> register(@Body LoginData loginData);

    @POST("suiviclient/v1/compte/register")
    Observable<Compte> saveCompte(@Body Compte data);

    @GET("bracongo-api/clients/circuit/{circuit}")
    Observable<List<Client>> getClientsCircuit(@Path("circuit") String circuit);
}
