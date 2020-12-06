package com.trallerd.thebank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.trallerd.thebank.adapters.AllAdapter
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), View.OnClickListener {
    private var navController: NavController? = null
    private lateinit var allAdapter: AllAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allAdapter = AllAdapter(this.context)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val pref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        if (pref?.getString("username", null) != null) {
            allAdapter.login(
                pref.getString("username", "").toString(),
                pref.getString("password", "").toString()
            ) { usersAPI ->
                val bundle = bundleOf(
                    "user" to pref.getString("username", usersAPI.username)
                )

                navController!!.navigate(R.id.loginToHome, bundle)
                val intent = Intent(this.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        }
        view.findViewById<Button>(R.id.btLogin).setOnClickListener(this)
        view.findViewById<TextView>(R.id.signin).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btLogin -> {
                if (!TextUtils.isEmpty(userName.text.toString()) || !TextUtils.isEmpty(password.text.toString())) {
                    allAdapter.login(
                        userName.text.toString(),
                        password.text.toString()
                    ) { userAPI ->
                        val login = userAPI
                        if (login.id != null) {
                            saveData()
                            val bundle = bundleOf("user" to login.username)
                            navController!!.navigate(R.id.loginToHome, bundle)
                        } else {
                            Toast.makeText(activity, R.string.user_error, Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(activity, R.string.field_message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.signin -> navController!!.navigate(R.id.loginToSignIn)
        }
    }

    private fun saveData() {
        val pref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val edt = pref?.edit()
        edt?.putString("username", userName.text.toString())
        edt?.putString("password", password.text.toString())
        edt?.apply()
    }
}