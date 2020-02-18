package com.geekhub_android_2019.cherkasyguide

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.ui.placedetail.PlaceDetailFragment
import com.geekhub_android_2019.cherkasyguide.ui.placeslist.PlacesAdapter
import com.geekhub_android_2019.cherkasyguide.ui.placeslist.PlacesListFragment


class MainActivity : AppCompatActivity(), PlacesAdapter.OnItemClickListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navigation_view)
            .setupWithNavController(navController)
        findViewById<Toolbar>(R.id.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.nav_host_fragment,
                    PlacesListFragment.newInstance()
                )
                .commit()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onClick(place: Place) {
        val fragment = PlaceDetailFragment.newInstance(place)
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}



