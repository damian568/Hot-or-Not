package com.example.hotornot

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hotornot.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(applicationContext)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        loadingFragments()
    }

    private fun loadingFragments(){
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        setIconInMenu(menu)
        setColorOnIcon(menu)
        return true
    }

    @SuppressLint("RestrictedApi")
    private fun setIconInMenu(menu: Menu?){
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
    }

    private fun setColorOnIcon(menu: Menu?){
        menu?.apply {
            for(index in 0 until this.size()){
                val item = this.getItem(index)
                setColorItem(item)
            }
        }
    }

    private fun setColorItem(item: MenuItem){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            item.icon.colorFilter = BlendModeColorFilter(
                Color.BLUE, BlendMode.SRC_IN
            )
        }else{
            item.icon.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.logOut) {
            closingAppQuestion()
            true
        } else {
            item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun closingAppQuestion() {
        val builder = AlertDialog.Builder(this)
        isBuildingAlertDialog(builder)
    }

    private fun isBuildingAlertDialog(builder: AlertDialog.Builder){
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            preferencesUtil.deleteUser()
            finish()
        }
        builder.setNegativeButton("No") { _: DialogInterface, _: Int -> }
        builder.show()
    }
}