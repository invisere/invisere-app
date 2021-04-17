package dam.invisere.gtidic.udl.cat.invisereapp.network;

import dam.invisere.gtidic.udl.cat.invisereapp.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    public static final String API_URL2 = "http://192.168.43.206:8000";
    public static final String API_URL3 = "http://192.168.1.101:8000";

    private static Retrofit retrofit;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callFactory(client)
                    .build();
        }
        return retrofit;
    }
}
