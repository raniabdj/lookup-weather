package com.example.lookupweather.ui.fragments

import WeatherRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lookupweather.R
import com.example.lookupweather.databinding.FragmentLookUpCityBinding
import com.example.lookupweather.models.WeatherResponse
import com.example.lookupweather.viewmodel.WeatherViewModel
import com.example.lookupweather.viewmodel.WeatherViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LookUpCity.newInstance] factory method to
 * create an instance of this fragment.
 */
class LookUpCity : Fragment() {
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
    private lateinit var binding: FragmentLookUpCityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =FragmentLookUpCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRefresh.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //viewModel.getWeather(binding.search.text.toString())
                println("good job")
                print(binding.search.text.toString())
                val bundle = bundleOf("city" to binding.search.text.toString())
                view?.findNavController()?.navigate(R.id.action_lookUpCity_to_futureListWeatherFragment2,bundle)
            }

        })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LookUpCity.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LookUpCity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}