package cmsr.ipsacademy.net.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import cmsr.ipsacademy.net.R
import cmsr.ipsacademy.net.api.apiset
import cmsr.ipsacademy.net.api.controller
import cmsr.ipsacademy.net.helpers.SharedPreferencesHelper

import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import cmsr.ipsacademy.net.activities.models.LoginModel
import cmsr.ipsacademy.net.helpers.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val userApi = controller.getInstance().create(apiset::class.java)

        userApi.verifyuser(computer_code, password)
            .enqueue(object : Callback<LoginModel> {
                override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {

                    Log.e("Response", response.body().toString())

                    if (response.body()?.student_info?.isEmpty() == true) {
                        editTextComputerCode!!.setText("")
                        editTextPassword!!.setText("")
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.login_error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (response.body()?.student_info?.isEmpty() == false && response.body()!!.student_info[0].is_student == "1") {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.login_sucess),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        sharedPreference?.save(AppConstants.computer_code, computer_code)
                        sharedPreference?.save(
                            AppConstants.user_role,
                            getString(R.string.role_student)
                        )
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                    if (response.body()?.student_info?.isEmpty() == false && response.body()!!.student_info[0].is_student == "0") {

                        Toast.makeText(
                            applicationContext,
                            getString(R.string.login_sucess),
                            Toast.LENGTH_SHORT
                        )
                            .show()

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

                override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                    Log.e("Response", t.message.toString())
                }
            })

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