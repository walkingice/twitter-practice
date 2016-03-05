package julian.test.twitter.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://api.twitter.com/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass,
                                      String ck, String cs, String at, String as) {
        // Apply Consumer-Key, Consumer-Secret, Access-Token and Access-Secret to OkHttp.
        // Then let its SingingInterceptor to generate signature into header
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(ck, cs);
        consumer.setTokenWithSecret(at, as);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
