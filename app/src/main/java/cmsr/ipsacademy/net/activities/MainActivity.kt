package cmsr.ipsacademy.net.activities


import android.annotation.SuppressLint
import android.graphics.Color.RED
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cmsr.ipsacademy.net.R
import cmsr.ipsacademy.net.api.ApiSet
import cmsr.ipsacademy.net.helpers.SharedPreferencesHelper
import cmsr.ipsacademy.net.api.controller
import cmsr.ipsacademy.net.databinding.ActivityMainBinding
import cmsr.ipsacademy.net.helpers.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var sharedPreferencesHelper: SharedPreferencesHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferencesHelper(this)
        val toolbar = findViewById<Toolbar>(R.id.toolBar)

        // navigation graph
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.frame_layout_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.navigationMenu.itemIconTintList = null

        binding.navigationMenu.menu.clear()

        when (sharedPreferencesHelper?.getValueString(AppConstants.user_role)) {
            "HOD" -> binding.navigationMenu.inflateMenu(R.menu.hod_menu)
            "Principal" -> binding.navigationMenu.inflateMenu(R.menu.principal_menu)
            "Teacher" -> binding.navigationMenu.inflateMenu(R.menu.teacher_menu)
            "Student" -> binding.navigationMenu.inflateMenu(R.menu.student_menu)
        }

        val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawer, toolbar, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding.navigationMenu.setupWithNavController(navController)

        // Profile menu
        val profileDropdownArray = resources.getStringArray(R.array.profile_dropdown)
        binding.profileDropdown.onItemSelectedListener
        binding.bottomCredit.movementMethod = LinkMovementMethod.getInstance()
        binding.bottomCredit.setLinkTextColor(RED)


        sharedPreferencesHelper?.getValueString(AppConstants.computer_code)
            ?.let { getUserDetails(it) }
        binding.role.text = sharedPreferencesHelper?.getValueString(AppConstants.user_role)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun getUserDetails(computer_code: String) {

        // If role is Student then get Student Details
        if (sharedPreferencesHelper?.getValueString(AppConstants.user_role)
                .equals(getString(R.string.role_student))
        ) {
            lifecycleScope.launch(Dispatchers.IO) {
                val res = controller.getInstance().create(ApiSet::class.java)
                    .getStudentDetails(computer_code).execute()

                if (res.body() != null)
                    withContext(Dispatchers.Main){
                        binding.name.text = res.body()!!.student_info[0].student_name
                    }
            }

        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val res = controller.getInstance().create(ApiSet::class.java)
                    .getFacultyDetails(computer_code).execute()

                if (res.body() != null) {
                    withContext(Dispatchers.Main){
                        binding.name.text = res.body()!!.faculty_info[0].first_name + " " + res.body()!!.faculty_info[0].last_name
                    }
                }
            }
        }
    }

}

