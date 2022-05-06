import com.example.lookupweather.models.City

// Exists to get the data from the data source
class WeatherRepository(private val service: WeatherService) {

    suspend fun getResults(city: String) =
        service.getResults(city,"08a34a61ec89a4057b53b57b7f59df58")
}