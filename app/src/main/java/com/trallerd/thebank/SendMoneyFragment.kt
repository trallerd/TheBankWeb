package com.trallerd.thebank

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.trallerd.thebank.adapters.AllAdapter
import com.trallerd.thebank.models.Records
import kotlinx.android.synthetic.main.fragment_send_money.*
import kotlin.properties.Delegates

class SendMoneyFragment : Fragment(), View.OnClickListener {

    private var navController: NavController? = null
    private lateinit var allAdapter: AllAdapter
    private var flag by Delegates.notNull<Boolean>()
    private var income by Delegates.notNull<Float>()
    private var spent by Delegates.notNull<Float>()
    private var money by Delegates.notNull<Float>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        flag = arguments?.getBoolean("flag")!!
        allAdapter = AllAdapter(this.context)
        return inflater.inflate(R.layout.fragment_send_money, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        income = arguments?.getFloat("income")!!
        spent = arguments?.getFloat("spent")!!
        money = arguments?.getFloat("money")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if (flag) {
            txtSendorRec.text = getString(R.string.receive_text_question)
            recipientAnswer.hint = getString(R.string.receive_from)
            btnSend.text = getString(R.string.receive_money)
        }
        view.findViewById<Button>(R.id.btnSend).setOnClickListener(this)
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSend -> {
                if (!TextUtils.isEmpty(amountAnswer.text.toString()) || !TextUtils.isEmpty(
                        recipientAnswer.text.toString()
                    )
                ) {
                    if (flag) {

                        val amount = amountAnswer.text.toString().toFloat()
                        val remarks = ""
                        val fk = Controller.users.id
                        val person = recipientAnswer.text.toString()
                        val received = true
                        val record = Records(amount, person, remarks, received, fk)
                        allAdapter.insertRecord(record)
                        val bundle = bundleOf(
                            "date" to record.registredAt,
                            "amount" to amount,
                            "sendTo" to person,
                            "flag" to flag
                        )
                        navController!!.navigate(R.id.sendToConfirmatio, bundle)

                    } else {

                        val amount = amountAnswer.text.toString().toFloat()
                        val remarks = ""
                        val fk = Controller.users.id
                        val person = recipientAnswer.text.toString()
                        val received = false
                        Log.i("AMOUNT", money.toString())

                        if (money >= amount) {
                            val record = Records(amount, person, remarks, received, fk)
                            allAdapter.insertRecord(record)
                            val bundle = bundleOf(
                                "date" to record.registredAt,
                                "amount" to amount,
                                "sendTo" to person,
                                "flag" to flag
                            )
                            navController!!.navigate(R.id.sendToConfirmatio, bundle)
                        } else {
                            Toast.makeText(activity, R.string.not_enough_money, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(activity, R.string.field_message, Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnCancel -> activity?.onBackPressed()
        }
    }
}