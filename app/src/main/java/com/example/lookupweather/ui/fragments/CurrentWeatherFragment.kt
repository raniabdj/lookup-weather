package com.example.lookupweather.ui.fragments

import WeatherRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lookupweather.R
import com.example.lookupweather.models.Temperature
import com.example.lookupweather.databinding.FragmentCurrentWeatherBinding
import com.example.lookupweather.models.WeatherResponse
import com.example.lookupweather.utils.Status
import com.example.lookupweather.viewmodel.WeatherViewModel
import com.example.lookupweather.viewmodel.WeatherViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CurrentWeatherFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private val selectedCity: String? = null
    private var temperature: WeatherResponse? = null
    private val service = WeatherService.getRetrofit()
    private val repository = WeatherRepository(service)
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(
            this,
            WeatherViewModelFactory(repository)
        )[WeatherViewModel::class.java]
    }
    private lateinit var binding: FragmentCurrentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val city = arguments?.getString("city")
      //  if (city != null) {
      //      viewModel.getWeather(city)
      //  }

        val temp = arguments?.getString("temp")
        val feels = arguments?.getString("feels")
        val speed = arguments?.getString("speed")
        val deg = arguments?.getString("deg")
        val condition = arguments?.getString("condition")
        val visibility = arguments?.getString("visibility")
        if (temp != null &&
            feels != null &&
            speed != null &&
            deg != null &&
            visibility != null &&
            condition != null
        ) {
            binding.progressBarLoading.visibility = View.GONE
            binding.textViewLoading.visibility = View.GONE

            println("ihihihihihi")

            updateTemperatures(temp.toDouble(), feels.toDouble())
            updateCondition(condition)
            //updatePrecipitation(it.list[0].rain)
            updateWind(deg, speed.toDouble())
            updateVisibility(visibility.toDouble())
        }

        configureObservers()

        (activity as? AppCompatActivity)?.supportActionBar?.title = city



    }

    private fun configureObservers() {
        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                Status.SUCCESS -> {
                    binding.progressBarLoading.visibility = View.GONE
                    binding.textViewLoading.visibility = View.GONE
                    //binding.rvPersonList.visibility = View.VISIBLE
                    //binding.tvErrorText.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.textViewLoading.visibility = View.VISIBLE
                    // binding.rvPersonList.visibility = View.GONE
                    //  binding.tvErrorText.visibility = View.GONE
                }

                Status.ERROR, null -> {
                    binding.progressBarLoading.visibility = View.GONE
                    binding.textViewLoading.visibility = View.GONE
                    //  binding.rvPersonList.visibility = View.GONE
                    //  binding.tvErrorText.visibility = View.VISIBLE
                }

                Status.SELECTED -> {
                    // binding.flBottom.visibility = View.GONE
                    //binding.rvPersonList.visibility = View.GONE
                }
            }
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner, {




                updateTemperatures(it.list[0].main.temp, it.list[0].main.temp_kf)
                updateCondition(it.list[0].weather[0].main)
                //updatePrecipitation(it.list[0].rain)
                updateWind(it.list[0].wind.deg.toString(), it.list[0].wind.speed)
                updateVisibility(it.list[0].visibility.toDouble())

        })
        //  viewModel.weatherLiveData.observe(this, {
        //       weatherAdapter.setPersonList(it)
        //   })
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        // val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        binding.textViewTemperature.text = "$temperature"
        binding.textViewFeelsLikeTemperature.text = "Feels like $feelsLike"
    }

    private fun updateCondition(condition: String) {
        binding.textViewCondition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        //  val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")

        binding.textViewPrecipitation.text = "Preciptiation: $precipitationVolume "


    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        // val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        binding.textViewWind.text = "Wind: $windDirection, $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        // val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi.")
        binding.textViewVisibility.text = "Visibility: $visibilityDistance "
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CurrentWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CurrentWeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}