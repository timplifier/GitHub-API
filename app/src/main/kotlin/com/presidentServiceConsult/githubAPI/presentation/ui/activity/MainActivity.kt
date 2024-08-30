package com.presidentServiceConsult.githubAPI.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.data.local.UserPreferences
import com.presidentServiceConsult.githubAPI.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fcv_main) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        val userPreferences by inject<UserPreferences>()
        val navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
        navGraph.setStartDestination(
            when (userPreferences.githubAPIToken.isEmpty()) {
                true -> R.id.signInFragment

                false -> R.id.mainFragment
            }
        )
        navController.graph = navGraph
    }
}