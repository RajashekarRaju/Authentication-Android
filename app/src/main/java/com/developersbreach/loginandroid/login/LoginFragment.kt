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
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.developersbreach.loginandroid.AuthenticationState
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.account.AccountViewModel
import com.developersbreach.loginandroid.model.User
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    private lateinit var loginButton: Button
    private lateinit var skipButton: Button
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var user: User

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
            user = User(username, password)
            Log.e("VALUES", "$user.username $user.password")
            //viewModel.verifyUser(user.username, user.password)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                Log.e("LoginFragment", "AUTHENTICATED AUTHENTICATED")
                val action: NavDirections =
                    LoginFragmentDirections.actionLoginFragmentToListFragment(
                        user.username, user.password
                    )
                Navigation.findNavController(view).navigate(action)
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                Log.e("LoginFragment", "UNAUTHENTICATED UNAUTHENTICATED")
            }
        })

        skipButton.setOnClickListener {
            viewModel.unAuthenticateUser()
            val action: NavDirections =
                LoginFragmentDirections.actionLoginFragmentToListFragment(
                    null, null
                )
            Navigation.findNavController(view).navigate(action)
        }
    }
}

