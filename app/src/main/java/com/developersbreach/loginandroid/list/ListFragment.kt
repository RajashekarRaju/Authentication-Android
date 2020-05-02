package com.developersbreach.loginandroid.list

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.developersbreach.loginandroid.R
import com.developersbreach.loginandroid.authentication.AuthenticationState
import com.google.android.material.snackbar.Snackbar


class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        setHasOptionsMenu(true)
        handleBackPress()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticateState ->
            if (authenticateState == AuthenticationState.AUTHENTICATED) {
                showData()
            } else if (authenticateState == AuthenticationState.UNAUTHENTICATED) {
                showLogin()
            }
        })
    }

    private fun showData() {
        viewModel.username.observe(viewLifecycleOwner, Observer { username ->
            Snackbar.make(requireView(), "Welcome $username!", Snackbar.LENGTH_LONG).show()
        })

        viewModel.getSports().observe(viewLifecycleOwner, Observer { sportsList ->
            val sportsAdapter =
                SportsAdapter(sportsList, SportsAdapter.OnClickListener { sports ->
                    val action: NavDirections = ListFragmentDirections.actionListToDetailFragment(sports)
                    findNavController().navigate(action)
                })
            recyclerView.adapter = sportsAdapter
        })
    }

    private fun showLogin() {
        viewModel.username.observe(viewLifecycleOwner, Observer { username ->
            val loginAction = Snackbar.make(requireView(), username, Snackbar.LENGTH_LONG)
            loginAction.setAction(getString(R.string.user_login_button_text)) {
                findNavController().navigate(R.id.loginFragment)
            }.show()
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
