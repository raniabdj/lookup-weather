
import com.example.lookupweather.models.WeatherResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast")
    suspend fun getResults(
        @Query("q") city: String?,
        @Query("appid") key: String?,
    ): Response<WeatherResponse>

    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofit() : WeatherService {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(WeatherService::class.java)
        }
    }
}