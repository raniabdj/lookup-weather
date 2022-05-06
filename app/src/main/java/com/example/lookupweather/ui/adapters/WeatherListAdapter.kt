
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lookupweather.databinding.ItemWeekWeatherBinding
import com.example.lookupweather.models.Temperature
import com.example.lookupweather.ui.fragments.WeekFragment
import com.example.lookupweather.utils.SelectedWeatherListener

class WeatherListAdapter(private val listener: SelectedWeatherListener): RecyclerView.Adapter<WeatherListViewHolder>(){

    private val weatherList = mutableListOf<Temperature>()

    fun setWeatherList(list: List<Temperature>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeekWeatherBinding.inflate(inflater, parent, false)
        return WeatherListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        val currentWeather = weatherList[position]

        holder.binding.textViewDate.text = currentWeather.dt_txt
        holder.binding.textViewTemperature.text = currentWeather.main.temp.toString()
        holder.binding.textViewCondition.text = currentWeather.weather[0].main

      //  holder.binding.textViewDate.text = currentWeather.dt_txt
        holder.itemView.setOnClickListener {
            listener.onSelectedWeather(currentWeather)
        }


    }



    override fun getItemCount() = weatherList.size
}


class WeatherListViewHolder(val binding: ItemWeekWeatherBinding): RecyclerView.ViewHolder(binding.root)