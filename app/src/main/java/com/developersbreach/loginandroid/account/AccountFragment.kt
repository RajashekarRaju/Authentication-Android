package com.developersbreach.loginandroid.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.snackbar.Snackbar


class AccountFragment : Fragment() {

    private lateinit var userTextView: TextView
    private lateinit var logoutButton: Button

    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        userTextView = view.findViewById(R.id.user_id)
        logoutButton = view.findViewById(R.id.user_logout_button)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.username.observe(viewLifecycleOwner, Observer { username ->
            userTextView.text = username
        })

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            if (authenticationState == AuthenticationState.AUTHENTICATED) {
                setLogoutState()
            } else if (authenticationState == AuthenticationState.UNAUTHENTICATED) {
                setLoginState()
            }
            handleBackPress()
        })
    }

    private fun setLoginState() {
        userTextView.text = getString(R.string.user_not_logged_in)
        logoutButton.text = getString(R.string.user_login_button_text)
        logoutButton.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        }
    }

    private fun setLogoutState() {
        logoutButton.text = getString(R.string.user_logout_button_text)
        logoutButton.setOnClickListener {
            viewModel.logoutUser()
            Snackbar.make(
                requireView(),
                getString(R.string.user_logged_out_message),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.listFragment)
            }
        })
    }
}