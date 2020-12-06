package com.trallerd.thebank

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.trallerd.thebank.adapters.AllAdapter
import com.trallerd.thebank.models.Users
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null
    lateinit var allAdapter: AllAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        allAdapter = AllAdapter(this.context)

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btCreate).setOnClickListener(this)
        view.findViewById<Button>(R.id.btCancel).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btCreate -> {
                if (!TextUtils.isEmpty(newUsername.text.toString()) ||
                    !TextUtils.isEmpty(newPassword.text.toString()) ||
                    !TextUtils.isEmpty(confirmPassword.text.toString())
                ) {
                    if (newPassword.text.toString() != confirmPassword.text.toString()) {
                        Toast.makeText(activity, R.string.password_match, Toast.LENGTH_SHORT).show()
                    } else {
                        val user = Users(newUsername.text.toString(), newPassword.text.toString())
                        allAdapter.add(user)
                        navController!!.navigate(R.id.signInToLogin)
                    }
                } else {
                    Toast.makeText(activity, R.string.field_message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btCancel -> activity?.onBackPressed()
        }
    }
}