package base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.BeforeClass;
import rest.API;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author by dvasilev on 15-Sep-17.
 */
public class BaseApiTest {
    protected static API api;
    private static Gson gson;

    @BeforeClass
    public static void initApiTest() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Gson gson = getGson();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        api = retrofit.create(API.class);
    }

    private static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat(Config.GSON_DATE_FORMAT)
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .setLenient()
                    .create();
        }
        return gson;
    }
}
