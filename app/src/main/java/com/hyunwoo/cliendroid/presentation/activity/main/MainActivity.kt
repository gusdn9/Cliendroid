package com.hyunwoo.cliendroid.presentation.activity.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppActivity
import com.hyunwoo.cliendroid.databinding.ActivityMainBinding
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.drawer.DrawerFragment
import com.hyunwoo.cliendroid.presentation.fragment.search.SearchArgs
import kotlin.math.max
import kotlin.math.roundToInt

class MainActivity : AppActivity(), DrawerFragment.Callback {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerFragment: DrawerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)

        drawerFragment = supportFragmentManager.findFragmentById(R.id.drawerFragment) as DrawerFragment
        adjustDrawerRatio()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setupDrawer(navController)
    }

    private fun adjustDrawerRatio() {
        val ratioWidth = (resources.displayMetrics.widthPixels * DRAWER_MAX_FILL_RATIO).roundToInt()
        val fixedWidth = resources.getDimensionPixelSize(R.dimen.main_drawer_fragment_min_width)
        drawerFragment.view?.layoutParams = drawerFragment.view?.layoutParams?.apply {
            width = max(ratioWidth, fixedWidth)
        }
    }

    private fun setupDrawer(navController: NavController) {
        // Root가 아닌 경우 drawer를 gesture로 열지 못하게 막음.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.drawerLayout.setDrawerLockMode(
                if (destination.id == navController.graph.startDestination) {
                    DrawerLayout.LOCK_MODE_UNLOCKED
                } else {
                    DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.let { query ->
                val args = SearchArgs(query)
                navController.navigate(
                    R.id.action_everyoneParkListFragment_to_searchFragment,
                    args.toFragmentArgsBundle()
                )
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        private const val DRAWER_MAX_FILL_RATIO = 0.78f
    }

    override fun onMenuCLicked() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
}
