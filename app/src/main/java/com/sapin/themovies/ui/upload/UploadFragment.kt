package com.sapin.themovies.ui.upload


import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sapin.themovies.R
import com.sapin.themovies.databinding.FragmentUploadBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class UploadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val PHOTO_PICKER_MULTI_SELECT_REQUEST_CODE=6
    private val REQUEST_PHOTO_PICKER_SINGLE_SELECT=1
    private lateinit var binding:FragmentUploadBinding
    private lateinit var navController:NavController
    private val viewModel: UploadViewModel by viewModels()
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
        binding= FragmentUploadBinding.inflate(layoutInflater,container,false)
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController=Navigation.findNavController(view)
        var btnCamera=view.findViewById<Button>(R.id.btntakephotos)
        var btnSelector=view.findViewById<Button>(R.id.btnUpload)
        btnSelector.setOnClickListener {
            val intent = Intent();
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.setType("image/*")
            intent.action=Intent.ACTION_GET_CONTENT
            someActivityResultLauncher.launch(Intent.createChooser(intent,"SELECTOR"))
        }
        btnCamera.setOnClickListener { navController.navigate(R.id.nav_camera) }
        super.onViewCreated(view, savedInstanceState)

    }

    var someActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            for(i:Int in 0..data!!.clipData!!.itemCount-1)
                viewModel.uploadPhoto(data.clipData!!.getItemAt(i).uri)
        }
    }



    // onActivityResult() handles callbacks from the photo picker.


companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UploadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}