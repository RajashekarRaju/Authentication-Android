package com.developersbreach.loginandroid.list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.account.AccountViewModel
import com.developersbreach.loginandroid.authentication.AuthenticationState


class ListFragment : Fragment() {

    private lateinit var authTextView: TextView
    private val viewModel: AccountViewModel by lazy {
        ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        authTextView = view.findViewById(R.id.auth_text)
        setHasOptionsMenu(true)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                authTextView.text = "AUTHENTICATED"
                handleBackPress()
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                authTextView.text = "UN-AUTHENTICATED"
                handleBackPress()
            }
        })
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_account, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.accountFragment) {
            val action =
                ListFragmentDirections.actionListFragmentToAccountFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }

        return true
    }

}
