package com.developersbreach.loginandroid.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.developersbreach.loginandroid.AuthenticationState
import com.developersbreach.loginandroid.R
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private lateinit var navController: NavController
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
        navController = findNavController()

        loginButton.setOnClickListener {
            viewModel.authenticate(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.refuseAuthentication()
            navController.popBackStack(R.id.listFragment, false)
        }

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            when (authenticateState) {
                AuthenticationState.AUTHENTICATED -> {
                    Toast.makeText(context, "LOGIN_AUTHENTICATED", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }

                AuthenticationState.INVALID_AUTHENTICATION -> {
                    Toast.makeText(context, "LOGIN_INVALID_AUTHENTICATION", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        skipButton.setOnClickListener {

        }
    }




    //        loginButton.setOnClickListener {
//            viewModel.validateUsername(usernameEditText.text)
//                .observe(viewLifecycleOwner, Observer { username ->
//                    validateUserAccount(username, usernameEditText)
//                })
//
//            viewModel.validatePassword(passwordEditText.text)
//                .observe(viewLifecycleOwner, Observer { password ->
//                    validateUserAccount(password, passwordEditText)
//                })
//        }

//    private fun validateUserAccount(
//        user: String,
//        userEditText: TextInputEditText
//    ) {
//        if (user.isNotEmpty()) {
//            if (user.length >= 8) {
//                Toast.makeText(context, user, Toast.LENGTH_SHORT).show()
//            } else {
//                userEditText.error = "Atleast 8 chars required"
//            }
//        } else {
//            userEditText.error = "Field Required"
//        }
//    }

}
