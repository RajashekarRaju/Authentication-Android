package com.developersbreach.loginandroid.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationViewModel
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private val viewModel: AuthenticationViewModel by lazy {
        ViewModelProvider(this).get(AuthenticationViewModel::class.java)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.verifyUser(username, password)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                val action: NavDirections =
                    LoginFragmentDirections.actionLoginFragmentToListFragment(
                        usernameEditText.text.toString() ,passwordEditText.text.toString()
                    )
                Navigation.findNavController(view).navigate(action)
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                Log.e("LoginFragment", "UNAUTHENTICATED")
            }
        })

        skipButton.setOnClickListener {
            val action: NavDirections =
                LoginFragmentDirections.actionLoginFragmentToListFragment(
                    null, null
                )
            Navigation.findNavController(view).navigate(action)
        }
    }
}

