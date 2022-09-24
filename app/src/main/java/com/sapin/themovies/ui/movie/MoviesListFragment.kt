package com.sapin.themovies.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sapin.themovies.R
import com.sapin.themovies.databinding.FragmentMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel :MovieViewModel by viewModels()
    private lateinit var binding:FragmentMoviesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMoviesListBinding.inflate(layoutInflater,container,false)
        iniView()
        return binding.root
    }


    fun iniView(){
        viewModel.getMovies()
        viewModel.responseMovies.observe(viewLifecycleOwner){
            val adapter= AdapterMovie(it)
            binding.MovieReciclerView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            binding.MovieReciclerView.adapter=adapter
            binding.MovieReciclerView.visibility=View.VISIBLE
            binding.progressBar.visibility=View.GONE
            binding.btncargar.visibility=View.GONE
        }
        viewModel.error.observe(viewLifecycleOwner){
            binding.progressBar.visibility=View.GONE
            Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
            binding.btncargar.visibility=View.VISIBLE
        }
        binding.btncargar.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            viewModel.getMovies()}
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoviesListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoviesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}