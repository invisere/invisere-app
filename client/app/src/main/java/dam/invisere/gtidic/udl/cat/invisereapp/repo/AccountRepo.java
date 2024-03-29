package dam.invisere.gtidic.udl.cat.invisereapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.EntryActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceI;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceImpl;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeI;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeImpl;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo extends EntryActivity {

    private String TAG = "AccountRepo";

    private AccountServiceI accountService;

    public MutableLiveData<ReturnCodeI> mResponseRegister;
    public MutableLiveData<ReturnCodeI> mResponseLogin;
    public MutableLiveData<ReturnCodeI> mResponseGetPublicAccount;
    public MutableLiveData<ReturnCodeI> mResponseGetAccount;
    private MutableLiveData<String> mResponseDeleteToken;
    public MutableLiveData<ReturnCodeI> mResponseUpdate;
    private MutableLiveData<String> mResponseRecovery;
    private final MutableLiveData<List<Route>> routesList;
    private final MutableLiveData<List<Place>> placesList;
    private final MutableLiveData<List<Route>> ownRoutesList;
    private final MutableLiveData<List<Route>> favoriteRoutesList;

    String token = "";
    public static AccountProfile profile;
    public static PublicProfile Publicprofile;
    public static List routes;
    public static List<Place> places;
    public static List ownRoutes;
    public static List favoriteRoutes;

    public AccountRepo() {
        this.token = Preferences.providePreferences().getString("token", "");
        this.accountService = new AccountServiceImpl();
        this.mResponseRegister = new MutableLiveData<>();
        this.mResponseLogin = new MutableLiveData<>();
        this.mResponseGetAccount = new MutableLiveData<>();
        this.mResponseDeleteToken = new MutableLiveData<>();
        this.mResponseUpdate = new MutableLiveData<>();
        this.mResponseRecovery = new MutableLiveData<>();
        this.mResponseGetPublicAccount = new MutableLiveData<>();
        this.routesList = new MutableLiveData<>();
        this.placesList = new MutableLiveData<>();
        this.ownRoutesList = new MutableLiveData<>();
        this.favoriteRoutesList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Route>> getRoutesList() {
        return routesList;
    }
    public MutableLiveData<List<Place>> getPlacesList() {
        return placesList;
    }
    public MutableLiveData<List<Route>> getOwnRoutesList() {
        return ownRoutesList;
    }
    public MutableLiveData<List<Route>> getFavoriteRoutesList() {
        return favoriteRoutesList;
    }

    public void registerAccount(Account account) {
        Log.d(TAG, "registerAccount() -> he rebut l'account: " + account);
        accountService.register(account).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "registerAccount() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        mResponseRegister.setValue(new ReturnCodeImpl(true, return_code, 0));
//                        mResponseRegister.setValue("El registre s'ha fet correctament.");
                        break;
                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRegister.setValue(new ReturnCodeImpl(true, return_code, 0));
//                        mResponseRegister.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseRegister.setValue(new ReturnCodeImpl(false));
                String error_msg = "Error: " + t.getMessage();
//                mResponseRegister.setValue(error_msg);
                mResponseRegister.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }

    public void createTokenUser(String auth){
        Log.d(TAG, "createTokenUser() -> he rebut el header: " + auth);
        accountService.login(auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "createTokenUser() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        try {
                            token = response.body().string().split(":")[1];
                            token = token.substring(2,token.length()-2);
//                            mResponseLogin.setValue("El login s'ha fet correctament.");
                            Log.d(TAG, "onResponse () -> envio el token: " + token);
                            Preferences.providePreferences().edit().putString("token", token).commit();
                            mResponseLogin.setValue(new ReturnCodeImpl(true, return_code, 200));
                            break;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    default:
                        String error_msg = "Error: " + response.errorBody();
//                        mResponseLogin.setValue(error_msg);
                        mResponseLogin.setValue(new ReturnCodeImpl(true, return_code, 0));
                        Preferences.providePreferences().edit().remove("token").apply();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseLogin.setValue(new ReturnCodeImpl(false));
                String error_msg = "Error: " + t.getMessage();
//                mResponseLogin.setValue(error_msg);
                Preferences.providePreferences().edit().remove("token").apply();
                Log.d(TAG, error_msg);
            }
        });
    }

    public void deleteTokenUser(String token){
        Log.d(TAG, "deleteTokenUser() -> he rebut el token: " + token);
        accountService.delete_token(token, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "deleteTokenUser() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> deleteTokenUser: " + token);
                        mResponseDeleteToken.setValue("Token deleted successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseDeleteToken.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseDeleteToken.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }


    public void get_account(String token){
        Log.d(TAG, "get_account() -> he rebut el header: " + token);

        accountService.get_account(token).enqueue(new Callback<AccountProfile>() {

            @Override
            public void onResponse(Call<AccountProfile> call, Response<AccountProfile> response) {

                int return_code = response.code();
                Log.d(TAG, "get_account() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        profile = response.body();
                        Log.d(TAG, "Code 200 () -> get_account: " + profile);
                        Preferences.providePreferences().edit().putString("account" ,new Gson().toJson(profile)).apply();

                        mResponseGetAccount.setValue(new ReturnCodeImpl(true, 200, 0));
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseGetAccount.setValue(new ReturnCodeImpl(false));
                        break;
                }
            }

            @Override
            public void onFailure(Call<AccountProfile> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseGetAccount.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }


    public void updateAccount(Account account){
        Log.d(TAG, "UpdateAccount() -> he rebut el header: " + token);

        accountService.updateAccount(account,token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int return_code = response.code();
                Log.d(TAG, "UpdateAccount() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Updated: ");
                        mResponseUpdate.setValue(new ReturnCodeImpl(true, return_code, 0));
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseUpdate.setValue(new ReturnCodeImpl(true, return_code, 0));
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseUpdate.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }



    public void updatePhoto(MultipartBody.Part photo, String token){
        Log.d(TAG, "AccountRepo -> updatePhoto() -> he rebut el header: " + token);

        accountService.updatePhoto(photo,token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int return_code = response.code();
                Log.d(TAG, "updatePhoto() -> ha rebut el codi: " + return_code);

                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Updated: ");
                        mResponseUpdate.setValue(new ReturnCodeImpl(true, return_code, 0));
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseUpdate.setValue(new ReturnCodeImpl(true, return_code, 0));
                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseUpdate.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }

    public void recovery(String email) {
        accountService.recovery(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "Recovery() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Recovery: ");
                        mResponseRecovery.setValue("Recovery successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRecovery.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRecovery.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void password_update(String email, String password, String code) {
        accountService.password_update(email, password, code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "password_update() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> password_update: ");
                        mResponseRecovery.setValue("password_update successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRecovery.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRecovery.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void get_public_account(String token, String username){
        Log.d(TAG, "get_public_account() -> he rebut el token: " + token);

        accountService.get_public_account(token,username).enqueue(new Callback<PublicProfile>() {

            @Override
            public void onResponse(Call<PublicProfile> call, Response<PublicProfile> response) {

                int return_code = response.code();
                Log.d(TAG, "get_public_account() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Publicprofile = response.body();
                        Log.d(TAG, "Code 200 () -> get_public_account: " + Publicprofile);

                        Preferences.providePreferences().edit().putString("publicAccount" ,new Gson().toJson(Publicprofile)).apply();
                        mResponseGetPublicAccount.setValue(new ReturnCodeImpl(true, return_code, 0));
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseGetPublicAccount.setValue(new ReturnCodeImpl(true, return_code, 0));
                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<PublicProfile> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
                mResponseGetPublicAccount.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }


    public void get_routes(String token){
        Log.d(TAG, "get_routes() -> he rebut el token: " + token);
        accountService.get_routes(token).enqueue(new Callback<List<Route>>() {

            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {

                int return_code = response.code();
                Log.d(TAG, "get_routes() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        routes = response.body();
                        Log.d(TAG, "Code 200 () -> get_routes: " + routes);

                        Log.d(TAG, "Code 200 () -> get_routes lenght: " + routes.size());

                        Route route = (Route) routes.get(0);

                        Log.d(TAG, "Code 200 () -> get_routes nom: " + route.getName());
                        Log.d(TAG, "Code 200 () -> get_routes distance: " + route.getDistance());
                        Log.d(TAG, "Code 200 () -> get_routes points: " + route.getPoints());
                        Place[] place = route.getPoints();

                        routesList.setValue(routes);
                        Log.d(TAG, "Code 200 () -> get_routes name point: " + routesList);
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();

                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
               ;
                Log.d(TAG, error_msg);
            }
        });
    }


    public void get_own_routes(String token){
        Log.d(TAG, "get_routes() -> he rebut el token: " + token);

        accountService.get_own_routes(token).enqueue(new Callback<List<Route>>() {

            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {

                int return_code = response.code();
                Log.d(TAG, "get_own_routes -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        ownRoutes = response.body();
                        Log.d(TAG, "Code 200 () -> get_own_routes: " + ownRoutes);

                        Log.d(TAG, "Code 200 () -> get_own_routes lenght: " + ownRoutes.size());

                        Route route = (Route) ownRoutes.get(0);

                        Log.d(TAG, "Code 200 () -> get_own_routes nom: " + route.getName());
                        Log.d(TAG, "Code 200 () -> get_own_routes distance: " + route.getDistance());
                        Log.d(TAG, "Code 200 () -> get_own_routes points: " + route.getPoints());
                        Place[] place = route.getPoints();

                        Log.d(TAG, "Code 200 () -> get_own_routes name point: " +  place[1].getName());

                        ownRoutesList.setValue(ownRoutes);
                        Log.d(TAG, "Code 200 () -> get_own_routes name point: " + ownRoutesList);
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void get_favorite_routes(String token){
        Log.d(TAG, "get_favorite_routes() -> he rebut el token: " + token);

        accountService.get_favorite_routes(token).enqueue(new Callback<List<Route>>() {

            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {

                int return_code = response.code();
                Log.d(TAG, "get_favorite_routes -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        favoriteRoutes = response.body();
                        Log.d(TAG, "Code 200 () -> get_favorite_routes: " + favoriteRoutes);

                        Log.d(TAG, "Code 200 () -> get_favorite_routes lenght: " + favoriteRoutes.size());

                        favoriteRoutesList.setValue(favoriteRoutes);
                        Log.d(TAG, "Code 200 () -> get_favorite_routes name point: " + favoriteRoutes);
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void get_places(String token){
        Log.d(TAG, "get_places() -> he rebut el token: " + token);
        accountService.get_places(token).enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                int return_code = response.code();
                Log.d(TAG, "get_places() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        places = response.body();
                        Log.d(TAG, "Code 200 () -> get_places: " + places);

                        Log.d(TAG, "Code 200 () -> get_places lenght: " + places.size());

                        placesList.setValue(places);
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();

                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
                ;
                Log.d(TAG, error_msg);
            }
        });
    }

    public void add_route_favorite(String token, int id){
        Log.d(TAG, "add_route_favorite() -> he rebut el token: " + token);

        accountService.add_route_favorite(token,id).enqueue(new Callback<PublicProfile>() {

            @Override
            public void onResponse(Call<PublicProfile> call, Response<PublicProfile> response) {

                int return_code = response.code();
                Log.d(TAG, "add_route_favorite() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "onResponse: Added id: " + id);
                        //mResponseGetPublicAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseGetPublicAccount.setValue(new ReturnCodeImpl(true, return_code, 0));
                        //mResponseGetPublicAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<PublicProfile> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                //mResponseGetPublicAccount.setValue(error_msg);
                mResponseGetPublicAccount.setValue(new ReturnCodeImpl(false));
                Log.d(TAG, error_msg);
            }
        });
    }
}
