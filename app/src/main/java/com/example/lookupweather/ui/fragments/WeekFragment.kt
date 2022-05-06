package com.example.lookupweather.ui.fragments

import WeatherListAdapter
import WeatherRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lookupweather.R
import com.example.lookupweather.databinding.FragmentWeekBinding
import com.example.lookupweather.models.Temperature
import com.example.lookupweather.models.WeatherResponse
import com.example.lookupweather.utils.SelectedWeatherListener
import com.example.lookupweather.viewmodel.WeatherViewModel
import com.example.lookupweather.viewmodel.WeatherViewModelFactory
import okhttp3.internal.wait

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeekFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeekFragment : Fragment() ,SelectedWeatherListener{
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    private val weatherAdapter = WeatherListAdapter(this)

    private lateinit var binding: FragmentWeekBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = view.findViewById<RecyclerView>(R.id.week_list)
        recyclerview.layoutManager = LinearLayoutManager(context)
        val city = arguments?.getString("city")
        if (city != null) {
            viewModel.getWeather(city)
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = city

        configureObservers()

      recyclerview.adapter=weatherAdapter


    }

 //   override fun onSelectedWeather(selected: Temperature) {
 //       viewModel.()
//
 //       val fragment = PersonFragment(selected, viewModel)
//
 //       supportFragmentManager.beginTransaction()
 //           .replace(R.id.frag_container, fragment)
 //           .addToBackStack(null)
 //           .commit()
 //   }

    private fun configureObservers() {
        viewModel.statusLiveData.observe(viewLifecycleOwner, {

        })

        viewModel.weatherLiveData.observe(viewLifecycleOwner, {
            weatherAdapter.setWeatherList(it.list)
        })
        //  viewModel.weatherLiveData.observe(this, {
        //       weatherAdapter.setPersonList(it)
        //   })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeekFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeekFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSelectedWeather(selected: Temperature) {

        println(selected)

      //  val bundle = bundleOf("weather" to selected)
        val bundle =Bundle()
        bundle.putString("temp",selected.main.temp.toString())
        bundle.putString("feels",selected.main.feels_like.toString())
        bundle.putString("speed",selected.wind.speed.toString())
        bundle.putString("deg",selected.wind.deg.toString())
        bundle.putString("visibility",selected.visibility.toString())
        bundle.putString("condition",selected.weather[0].description.toString())
        bundle.putString("city",(activity as? AppCompatActivity)?.supportActionBar?.title.toString())
        view?.findNavController()?.navigate(R.id.actionDetail,bundle)

    }
}