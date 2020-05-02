package com.developersbreach.loginandroid.detail

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.model.Sports

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var sportsArgs: Sports
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        val application: Application = requireActivity().application
        sportsArgs = DetailFragmentArgs.fromBundle(args).detailFragmentArgs
        val factory = DetailViewModelFactory(application, sportsArgs)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        handleBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_detail, container, false)

        val icon: ImageView = view.findViewById(R.id.banner_image_view)
        val title: TextView = view.findViewById(R.id.title_detail_text_view)
        val subtitle: TextView = view.findViewById(R.id.subtitle_detail_text_view)
        val about: TextView = view.findViewById(R.id.about_detail_text_view)

        viewModel.getSport().observe(viewLifecycleOwner, Observer { sport ->
            icon.setImageResource(sport.icon)
            title.text = sport.title
            subtitle.text = sport.subtitle
            about.text = sport.about
        })

        return view
    }

    private fun handleBackPress() = requireActivity().onBackPressedDispatcher.addCallback(object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    })
}
