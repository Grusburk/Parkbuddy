package mettinochmette.myapplication.data.api;

import mettinochmette.myapplication.model.Place;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Mattias on 03/12/15.
 */
public interface StockholmParkingService {

    @GET("/LTF-Tolken/v1/servicedagar/{operation}/{day}?outputFormat=json&apiKey=0d49d540-8c75-4a01-b8c7-ad221c4708ba")
    Call<Place> getServiceTimeByDay(@Path("operation") String operation, @Path("day") String day);

    @GET("LTF-Tolken/v1/ptillaten/all?outputFormat=json&apiKey=0d49d540-8c75-4a01-b8c7-ad221c4708ba")
    Call<Place> getAvailableParkings();
}
