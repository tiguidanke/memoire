package com.example.memo.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.memo.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    static String TAG="APIService";
   public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
       assert cm != null;
       NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            int cacheSize = 100 * 1024 * 1024; // 10 MiB
            File httpCacheDirectory = new File(App.getAppContext().getCacheDir(), "responses");
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.sslSocketFactory(sslContext.getSocketFactory());
                    //.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
            builder.addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            if (response.cacheResponse() != null) {
                                Log.v(TAG, "Response from cacheControl(): " + response.cacheResponse());
                            }else if (response.networkResponse() != null) {
                                Log.v(TAG, "Response from networkResponse(): " + response.networkResponse());
                            }
                            return response;
                        }
                    });
            builder.connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(cache);

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
/*    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");

        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 2419200)
                    .build();
        } else {
            return originalResponse;
        }
    };
    private static final Interceptor OFFLINE_INTERCEPTOR = chain -> {
        Request request = chain.request();

        if (!isOnline()) {

            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }

        return chain.proceed(request);
    };*/
    private static <KO> KO createService(Class<KO> cls) {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient().build())
                .baseUrl(Constants.BASE_URL).build();
        return retrofit.create(cls);
    }

    public static final RetrofitApi retrofitApi = createService(RetrofitApi.class);
}
