package com.developersbreach.loginandroid.login

import android.os.Bundle
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
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.textfield.TextInputEditText


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginButton: Button
    private lateinit var skipButton: Button
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authenticationState.observe(viewLifecycleOwner, observer())

        loginButton.setOnClickListener { v ->
            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()
            viewModel.loginUser(username, password)
        }

        viewModel.navigateToListFragment.observe(viewLifecycleOwner, Observer { isTrue ->
            if (isTrue) {
                navigateToListFragment(requireView())
            }
        })

        skipButton.setOnClickListener { v -> navigateToListFragment(v) }
    }

    private fun observer(): Observer<AuthenticationState> {
        return Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                navigateToListFragment(requireView())
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                Toast.makeText(context, "NO_AUTHENTICATION", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.authenticationState.removeObserver(observer())
    }

    private fun navigateToListFragment(view: View) {
        val action: NavDirections =
            LoginFragmentDirections.actionLoginFragmentToListFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
