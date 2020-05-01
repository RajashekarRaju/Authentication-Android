package com.developersbreach.loginandroid.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.google.android.material.snackbar.Snackbar


class AccountFragment : Fragment() {

    private lateinit var viewModel: AccountViewModel

    private var username: String? = String()
    private var password: String? = String()
    private lateinit var userTextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        userTextView = view.findViewById(R.id.user_id)
        logoutButton = view.findViewById(R.id.user_logout_button)
        username = PrefUtils.getUsernamePrefs(requireContext())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authenticationState.observe(viewLifecycleOwner, observer())
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.listFragment)
                Toast.makeText(context, "Pressed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.authenticationState.removeObserver(observer())
        Log.e("ListFragment", "Destroyed")
    }

    private fun observer(): Observer<AuthenticationState> {
        return Observer { authenticationState ->
            if (authenticationState == AuthenticationState.AUTHENTICATED) {
                userTextView.text = username
                logoutButton.text = getString(R.string.user_logout_button_text)

                logoutButton.setOnClickListener {
                    viewModel.logoutUser()
                    Snackbar.make(
                        requireView(),
                        getString(R.string.user_logged_out_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                handleBackPress()

            } else if (authenticationState == AuthenticationState.UNAUTHENTICATED) {
                userTextView.text = getString(R.string.user_not_logged_in)
                logoutButton.text = getString(R.string.user_login_button_text)

                logoutButton.setOnClickListener { view ->
                    Navigation.findNavController(view).navigate(R.id.loginFragment)
                }
            }
        }
    }
}
