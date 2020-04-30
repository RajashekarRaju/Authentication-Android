package com.developersbreach.loginandroid.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.account.AccountViewModel
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    private lateinit var loginButton: Button
    private lateinit var skipButton: Button
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = view.findViewById(R.id.login_button)
        skipButton = view.findViewById(R.id.skip_button)
        usernameEditText = view.findViewById(R.id.user_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeAuthenticationState(requireView())

        loginButton.setOnClickListener { view ->
            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            viewModel.verifyUser(username, password)
            // TODO navigate with conditional navigation
            //navigateToListFragment(view)
            observeAuthenticationState(requireView())
        }

        skipButton.setOnClickListener { view -> navigateToListFragment(view) }
    }

    private fun observeAuthenticationState(view: View) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                navigateToListFragment(view)
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                Toast.makeText(context, "NO_AUTHENTICATION", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToListFragment(view: View) {
        val action: NavDirections =
            LoginFragmentDirections.actionLoginFragmentToListFragment()
        Navigation.findNavController(view).navigate(action)
    }
}

