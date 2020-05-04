package com.developersbreach.loginandroid.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {

    private lateinit var loginButton: Button
    private lateinit var skipButton: Button
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var userTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var registerUserTextView: TextView

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        loginButton = view.findViewById(R.id.login_button)
        skipButton = view.findViewById(R.id.skip_button)
        usernameEditText = view.findViewById(R.id.user_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        userTextInputLayout = view.findViewById(R.id.user_input_layout)
        passwordTextInputLayout = view.findViewById(R.id.password_input_layout)
        registerUserTextView = view.findViewById(R.id.register_user)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                navigateToListFragment(requireView())
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                Snackbar.make(requireView(), getString(R.string.user_not_logged_in), Snackbar.LENGTH_SHORT).show()
            }
        })

        loginButton.setOnClickListener {
            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            viewModel.loginUser(username, password)
        }

        viewModel.navigateToListFragment.observe(viewLifecycleOwner, Observer { isTrue ->
            if (isTrue) {
                navigateToListFragment(requireView())
            } else {
                setUserTextError()
            }
        })

        skipButton.setOnClickListener { view ->
            navigateToListFragment(view)
        }

        registerUserTextView.setOnClickListener { view ->
            val action: NavDirections =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun setUserTextError() {
        val usernameError: Boolean = viewModel.setUsernameError(usernameEditText)
        if (usernameError) {
            usernameEditText.error = getString(R.string.edit_text_char_enter_error)
        }
        val passwordError: Boolean = viewModel.setPasswordError(passwordEditText)
        if (passwordError) {
            passwordTextInputLayout.error = getString(R.string.edit_text_char_enter_error)
        }
    }

    private fun navigateToListFragment(view: View) {
        val action: NavDirections =
            LoginFragmentDirections.actionLoginFragmentToListFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
