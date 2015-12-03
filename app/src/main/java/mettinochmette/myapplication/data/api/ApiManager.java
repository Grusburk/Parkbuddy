package mettinochmette.myapplication.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Mattias on 03/12/15.
 */
public class ApiManager {

    private static Retrofit manager;
    private static StockholmParkingService service;


    public static StockholmParkingService getApi() {
        if (manager == null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            initRetrofit(gson);
            service = manager.create(StockholmParkingService.class);
        }
        return service;
    }

    private static void initRetrofit(Gson gson) {
        manager = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://openparking.stockholm.se")
                .build();
    }
}
