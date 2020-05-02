package com.developersbreach.loginandroid.list

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.snackbar.Snackbar


class ListFragment : Fragment() {

    private lateinit var authTextView: TextView

    private val viewModel : ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        authTextView = view.findViewById(R.id.auth_text)
        setHasOptionsMenu(true)
        handleBackPress()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                viewModel.username.observe(viewLifecycleOwner, Observer { username ->
                    Snackbar.make(requireView(), "Welcome $username!", Snackbar.LENGTH_SHORT).show()
                })
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                viewModel.username.observe(viewLifecycleOwner, Observer { username ->
                    Snackbar.make(requireView(), username, Snackbar.LENGTH_SHORT).show()
                })
            }
        })
    }

    private fun handleBackPress() = requireActivity().onBackPressedDispatcher.addCallback(object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    })

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
