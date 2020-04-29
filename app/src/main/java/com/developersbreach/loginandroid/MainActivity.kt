package com.developersbreach.loginandroid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    private lateinit var mNavigationController: NavController
    private lateinit var mToolbar: Toolbar
    private lateinit var mAppBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavigationController = findNavController(this, R.id.myNavHostFragment)
        mAppBarLayout = findViewById(R.id.appbar)
        mToolbar = findViewById(R.id.toolbar)
        NavigationUI.setupWithNavController(mToolbar, mNavigationController)

        setSupportActionBar(mToolbar)

        // Change behaviour of destination view or content based on type of destination is user at.
        mNavigationController.addOnDestinationChangedListener { _: NavController, destination: NavDestination, _: Bundle? ->
            this.onDestinationChanged(
                destination
            )
        }
    }

    private fun onDestinationChanged(
        destination: NavDestination
    ) {
        // Check for destination ArticleDetailFragment, if true hide the navigation view.
        when (destination.id) {
            R.id.loginFragment -> {
                mAppBarLayout.visibility = View.GONE
            }
            R.id.listFragment -> {
                mToolbar.navigationIcon = null
                mAppBarLayout.visibility = View.VISIBLE
            }
            else -> {
                mAppBarLayout.visibility = View.VISIBLE
                mToolbar.setNavigationOnClickListener {
                    mNavigationController.navigateUp()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavigationController.navigateUp()
    }
}
