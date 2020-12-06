package com.trallerd.thebank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.trallerd.thebank.adapters.AllAdapter
import kotlinx.android.synthetic.main.fragment_confirmation.*
import kotlin.properties.Delegates

class ConfirmationFragment : Fragment(), View.OnClickListener {
    private var navController: NavController? = null
    private lateinit var allAdapter: AllAdapter
    private lateinit var date: String
    private var flag by Delegates.notNull<Boolean>()
    private var amount by Delegates.notNull<Float>()
    lateinit var name: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        date = arguments?.getString("date").toString()
        amount = arguments?.getFloat("amount")!!
        name = arguments?.getString("sendTo")!!
        flag = arguments?.getBoolean("flag")!!
        allAdapter = AllAdapter(this.context)
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (flag) {
            confirmTransaction.text = getString(R.string.receive_from)
        }
        val date = getString(R.string.info_str, date)
        val amount = getString(R.string.info_doub, amount)
        val sendTo = getString(R.string.info_str, name)
        confirmaDate.text = date
        confirmAmount.text = amount
        confirmName.text = sendTo
        view.findViewById<Button>(R.id.btnVExtract).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnHome).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnVExtract -> {

                navController!!.navigate(R.id.confirmToExtract)
            }
            R.id.btnHome -> {
                val user = Controller.users.username
                val bundle = bundleOf(
                    "user" to user
                )
                navController!!.navigate(R.id.confirmToHome, bundle)
            }
        }
    }
}