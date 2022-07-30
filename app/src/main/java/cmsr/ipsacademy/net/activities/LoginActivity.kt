package cmsr.ipsacademy.net.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import cmsr.ipsacademy.net.R
import cmsr.ipsacademy.net.api.ApiSet
import cmsr.ipsacademy.net.api.controller
import cmsr.ipsacademy.net.helpers.SharedPreferencesHelper

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import cmsr.ipsacademy.net.helpers.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var editTextComputerCode: EditText? = null
    private var editTextPassword: EditText? = null
    private var loginButton: ImageView? = null
    private var sharedPreference: SharedPreferencesHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreference = SharedPreferencesHelper(this)

        editTextComputerCode = findViewById(R.id.edit_text_computer_code_login)
        editTextPassword = findViewById(R.id.edit_text_password_login)
        loginButton = findViewById(R.id.login_button_image_submit)

        checkUserExistence()

        editTextPassword?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                processLogin()
                return@OnKeyListener true
            }
            false
        })

        loginButton!!.setOnClickListener(View.OnClickListener {
            processLogin()
        })

    }

    private fun processLogin() {
        val computer_code = editTextComputerCode!!.text.toString().trim()
        val password = editTextPassword!!.text.toString().trim()

        lifecycleScope.launch(Dispatchers.IO) {
            val res = controller.getInstance().create(ApiSet::class.java)
                .verifyUsers(computer_code, password).execute()

            if (res.body()?.student_info?.isEmpty() == true) {
                editTextComputerCode!!.setText("")
                editTextPassword!!.setText("")
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show()
                }
            }
            if (res.body()?.student_info?.isEmpty() == false && res.body()!!.student_info[0].is_student == "1") {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, getString(R.string.login_sucess), Toast.LENGTH_SHORT).show()
                }
                sharedPreference?.save(AppConstants.computer_code, computer_code)
                sharedPreference?.save(
                    AppConstants.user_role,
                    getString(R.string.role_student)
                )
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            if (res.body()?.student_info?.isEmpty() == false && res.body()!!.student_info[0].is_student == "0") {

                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, getString(R.string.login_sucess), Toast.LENGTH_SHORT).show()
                }

                sharedPreference?.save(AppConstants.computer_code, computer_code)

                if (sharedPreference?.getValueString(AppConstants.computer_code)
                        .equals("2083")
                ) {
                    sharedPreference?.save(
                        AppConstants.user_role,
                        getString(R.string.role_hod)
                    )

                    sharedPreference?.getValueString(AppConstants.computer_code)
                        ?.let { Log.e("ROLESAvED", it) }
                    sharedPreference?.getValueString(AppConstants.user_role)
                        ?.let { Log.e("role", it) }
                } else if (sharedPreference?.getValueString(AppConstants.computer_code)
                        .equals("1") || sharedPreference?.getValueString(AppConstants.computer_code)
                        .equals("2") || sharedPreference?.getValueString(AppConstants.computer_code)
                        .equals("3")
                ) {
                    sharedPreference?.save(
                        AppConstants.user_role,
                        getString(R.string.role_principal)
                    )

                    sharedPreference?.getValueString(AppConstants.computer_code)
                        ?.let { Log.e("ROLESAvED", it) }
                    sharedPreference?.getValueString(AppConstants.user_role)
                        ?.let { Log.e("role", it) }
                } else {
                    sharedPreference?.save(
                        AppConstants.user_role,
                        getString(R.string.role_teacher)
                    )
                    sharedPreference?.getValueString(AppConstants.user_role)
                        ?.let { Log.i("role", it) }

                }

                startActivity(Intent(applicationContext, MainActivity::class.java))

            }
        }

    }

    private fun checkUserExistence() {
        if (sharedPreference?.getValueString(AppConstants.computer_code) != null) {
            if (sharedPreference?.getValueString(AppConstants.user_role)
                    .equals(getString(R.string.role_student))
            ) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else if (sharedPreference?.getValueString(AppConstants.user_role)
                    .equals(getString(R.string.role_hod))
            ) {
                startActivity(Intent(applicationContext, UserActivity::class.java))
            } else if (sharedPreference?.getValueString(AppConstants.user_role)
                    .equals(getString(R.string.role_teacher))
            ) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else if (sharedPreference?.getValueString(AppConstants.user_role)
                    .equals(getString(R.string.role_principal))
            ) {
                startActivity(Intent(applicationContext, UserActivity::class.java))
            }

        } else {
            Toast.makeText(applicationContext, getString(R.string.please_login), Toast.LENGTH_SHORT)
                .show()
        }
    }

}