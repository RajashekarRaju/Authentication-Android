package com.developersbreach.loginandroid.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.loginandroid.AuthenticationState

import com.developersbreach.loginandroid.R



class ListFragment : Fragment() {

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            when (authenticateState) {
                AuthenticationState.AUTHENTICATED -> {
                    Toast.makeText(context, "AUTHENTICATED", Toast.LENGTH_SHORT).show()
                }

                AuthenticationState.UNAUTHENTICATED -> {
                    Toast.makeText(context, "UNAUTHENTICATED", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
