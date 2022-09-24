package com.sapin.themovies.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapin.themovies.R
import com.sapin.themovies.data.db.model.LocationModel
import com.sapin.themovies.databinding.FragmentLocationBinding
import com.sapin.themovies.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

 const val LONGITUDE="longitude"
 const val LATITUDE="latitude"

@AndroidEntryPoint
class LocationFragment : Fragment(),OnClickListenerLocation {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentLocationBinding
    private val viewModel:LocationViewModel by viewModels()
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLocationBinding.inflate(layoutInflater,container,false)
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            navController = Navigation.findNavController(view)

    }


    fun initView(){
        viewModel.responseLocations.observe(viewLifecycleOwner){
            var  adapterLocation=AdapterLocation(it,this)
            binding.locationRecycler.adapter=adapterLocation
            binding.locationRecycler.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            binding.pgbloadingLocations.visibility=View.GONE
            binding.locationRecycler.visibility=View.VISIBLE
        }
    }

    override fun OnClickListenerLocation(locationModel: LocationModel) {
        val bundle= Bundle()
        bundle.putDouble(LATITUDE,locationModel.latitude)
        bundle.putDouble(LONGITUDE,locationModel.longitude)
     navController.navigate(R.id.nav_mapLocation,bundle)

    }


}