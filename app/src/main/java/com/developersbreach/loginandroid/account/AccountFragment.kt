package com.developersbreach.loginandroid.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.authentication.PrefUtils
import com.google.android.material.snackbar.Snackbar


class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    private var username: String? = String()
    private var password: String? = String()
    private lateinit var userTextView: TextView
    private lateinit var logoutButton: Button


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            if (authenticationState == AuthenticationState.AUTHENTICATED) {
                userTextView.text = username
                logoutButton.text = getString(R.string.user_logout_button_text)

                logoutButton.setOnClickListener {
                    viewModel.logoutUser()
                    Snackbar.make(
                        view,
                        getString(R.string.user_logged_out_message),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            } else if (authenticationState == AuthenticationState.UNAUTHENTICATED) {
                userTextView.text = getString(R.string.user_not_logged_in)
                logoutButton.text = getString(R.string.user_login_button_text)

                logoutButton.setOnClickListener {
                    Navigation.findNavController(view).navigate(R.id.loginFragment)
                }
            }
        })
    }
}
