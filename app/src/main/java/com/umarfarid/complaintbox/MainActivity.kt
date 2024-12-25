package com.umarfarid.complaintbox

import android.content.Intent
import android.os.Bundle


import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.umarfarid.complaintbox.R
import com.umarfarid.complaintbox.ui.auth.AuthViewModel
import com.umarfarid.complaintbox.ui.auth.LoginActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar =findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        findViewById<FloatingActionButton>(R.id.addNew).setOnClickListener(){
            startActivity(Intent(this, Add_Complaint::class.java))
        }
        val imageView = findViewById<ImageView>(R.id.drawer_icon)
        imageView.setOnClickListener { view: View? ->
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val bottomNavigationView = findViewById<BottomNavigationView>(
            R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navHostFragment.navController)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_Logout) {
            val viewModel = AuthViewModel()
            viewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))

        } else if (item.itemId==R.id.item_About_Us){

        }
        return true
    }
}