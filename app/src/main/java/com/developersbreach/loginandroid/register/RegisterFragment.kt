package com.developersbreach.loginandroid.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var firstNameInputLayout: TextInputLayout
    private lateinit var lastNameInputLayout: TextInputLayout
    private lateinit var mailInputLayout: TextInputLayout
    private lateinit var countryInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var mailEditText: TextInputEditText
    private lateinit var countryEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerUserButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        firstNameInputLayout = view.findViewById(R.id.first_name_input_layout)
        lastNameInputLayout = view.findViewById(R.id.last_name_input_layout)
        mailInputLayout = view.findViewById(R.id.mail_input_layout)
        countryInputLayout = view.findViewById(R.id.country_input_layout)
        passwordInputLayout = view.findViewById(R.id.password_input_layout)
        firstNameEditText = view.findViewById(R.id.first_name_edit_text)
        lastNameEditText = view.findViewById(R.id.last_name_edit_text)
        mailEditText = view.findViewById(R.id.mail_edit_text)
        countryEditText = view.findViewById(R.id.country_edit_text)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        registerUserButton = view.findViewById(R.id.sign_up_user)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        registerUserButton.setOnClickListener {
            viewModel.validateFields(
                firstNameEditText.text.toString(), firstNameInputLayout,
                lastNameEditText.text.toString(), lastNameInputLayout,
                mailEditText.text.toString(), mailInputLayout,
                countryEditText.text.toString(), countryInputLayout,
                passwordEditText.text.toString(), passwordInputLayout
            )
        }

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            if (authenticationState == AuthenticationState.AUTHENTICATED) {
                navigateToListFragment(requireView())
            }
        })
    }

    private fun navigateToListFragment(view: View) {
        val action: NavDirections = RegisterFragmentDirections.actionRegisterToListFragment()
        Navigation.findNavController(view).navigate(action)
    }
}
