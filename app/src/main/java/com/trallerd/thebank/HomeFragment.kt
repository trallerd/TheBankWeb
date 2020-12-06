package com.trallerd.thebank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.trallerd.thebank.adapters.AllAdapter


class HomeFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null
    lateinit var user: String
    lateinit var allAdapter: AllAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getString("user").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        allAdapter = AllAdapter(this.context)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val name = user
        val welcome = getString(R.string.welcome_text, name)
        view.findViewById<TextView>(R.id.welcomeText).text = welcome
        view.findViewById<ImageView>(R.id.btnWallet).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.btnExtract).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.btnSenMoney).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.btnReceiveMoney).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.btnLogout).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnWallet -> {
                allAdapter.getIncomes(Controller.users.id!!) { incomeAPI ->
                    val income = incomeAPI
                    allAdapter.getSpent(Controller.users.id!!) { spentAPI ->
                        val spent = spentAPI
                        val money = income - spent
                        Log.i("AMOUNT", money.toString())
                        val bundle = bundleOf(
                            "money" to money,
                            "income" to income,
                            "spent" to spent
                        )
                        navController!!.navigate(R.id.homeToWallet, bundle)
                    }
                }
            }
            R.id.btnExtract -> navController!!.navigate(R.id.homeToExtract)
            R.id.btnSenMoney -> {
                allAdapter.getIncomes(Controller.users.id!!) { incomeAPI ->
                    val income = incomeAPI
                    allAdapter.getSpent(Controller.users.id!!) { spentAPI ->
                        val spent = spentAPI
                        val money = income - spent
                        Log.i("AMOUNT", money.toString())
                        val flag = false
                        val bun = bundleOf(
                            "flag" to flag,
                            "income" to income,
                            "spent" to spent,
                            "money" to money
                        )
                        navController!!.navigate(R.id.homeToSendMOney, bun)
                    }
                }
            }
            R.id.btnReceiveMoney -> {
                allAdapter.getIncomes(Controller.users.id!!) { incomeAPI ->
                    val income = incomeAPI
                    allAdapter.getSpent(Controller.users.id!!) { spentAPI ->
                        val spent = spentAPI
                        val money = income - spent
                        Log.i("AMOUNT", money.toString())
                        val flag = true
                        val bun = bundleOf(
                            "flag" to flag,
                            "income" to income,
                            "spent" to spent,
                            "money" to money
                        )
                        navController!!.navigate(R.id.homeToSendMOney, bun)
                    }
                }
            }
            R.id.btnLogout -> {
                val pref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
                val edt = pref?.edit()
                edt?.putString("username", null)
                edt?.putString("password", null)
                edt?.commit()

                Controller.users.password = ""
                Controller.users.username = ""
                Controller.users.id = null

                navController!!.navigate(R.id.homeToLogin)
                val intent = Intent(activity, LoginFragment::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            }
        }
    }
}