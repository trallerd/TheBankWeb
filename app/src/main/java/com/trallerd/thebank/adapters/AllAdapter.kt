package com.trallerd.thebank.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trallerd.thebank.R
import com.trallerd.thebank.Controller
import com.trallerd.thebank.dao.RecordsDAO
import com.trallerd.thebank.dao.UserDAO
import com.trallerd.thebank.models.Records
import com.trallerd.thebank.models.Users
import kotlinx.android.synthetic.main.item_records.view.*
import kotlinx.android.synthetic.main.item_records_edit.view.*


class AllAdapter(context: Context?) : RecyclerView.Adapter<AllAdapter.RecorsHolder>() {
    private val daoRecords = RecordsDAO()
    private val daoUsers = UserDAO()
    private var recordEdit: Records? = null
    private var records = listOf<Records>()

    init {
        if (Controller.users.id != null) {
            daoRecords.getAllById(Controller.users.id!!) { recordsAPI ->
                records = recordsAPI
                notifyDataSetChanged()
            }
        }
    }

    fun add(user: Users) {
        daoUsers.insert(user) { userAPI ->
            user.id = userAPI.id
        }
    }

    fun login(username: String, password: String, done: (Users) -> Unit) {
        daoUsers.getUser(username, password) { userAPI ->
            val user = userAPI
            if (user != null) {
                Controller.users = user
            }
            done(user)
        }
    }

    fun search(name: String) {
        daoRecords.getAllByName(name) { recordsAPI ->
            records = recordsAPI
            notifyDataSetChanged();
        }
    }


    fun getIncomes(idUser: Long, done: (Float) -> Unit) =
        daoRecords.getAllById(idUser) { recordsAPI ->
            var sum = 0.0
            for (record in recordsAPI) {
                if (record.receive) {
                    sum += record.value
                }

            }
            done(sum.toFloat())
            Log.i("TOAT", recordsAPI.toString())
        }

    fun getSpent(idUser: Long, done: (Float) -> Unit) =
        daoRecords.getAllById(idUser) { recordsAPI ->
            var sum = 0.0
            for (record in recordsAPI) {
                if (!record.receive) {
                    sum += record.value
                }
            }
            done(sum.toFloat())

        }

    fun insertRecord(record: Records) = daoRecords.insert(record) { recordsAPI ->
        record.id = recordsAPI.id
    }


    fun edit(record: Records) {
        recordEdit = record
        val position = records.indexOf(record)
        notifyItemChanged(position, record)
    }

    fun saveRec(record: Records) {
        daoRecords.update(record.id!!, record) {}
        val position = records.indexOf(record)
        recordEdit = null
        notifyItemChanged(position)

    }

    override fun getItemCount(): Int {
        return records.size
    }


    override fun getItemViewType(position: Int): Int {
        val records = records[position]
        return if (records == recordEdit) {
            R.layout.item_records_edit

        } else {
            R.layout.item_records
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecorsHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun onBindViewHolder(holder: RecorsHolder, position: Int) {
        val record = records[position]
        holder.fillView(record)
    }

    inner class RecorsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(record: Records) {
            if (record.receive) {
                if (record == recordEdit) {
                    itemView.editRecipient.text = record.person
                    itemView.editRemarks.setText(record.remarks)
                    itemView.editAmount.setTextColor(Color.parseColor("#008000"))
                    itemView.editAmount.text = "+" + record.value.toString()
                    itemView.editDate.text = record.registredAt
                    itemView.okEdit.setOnClickListener {
                        record.remarks = itemView.editRemarks.text.toString()
                        saveRec(record)
                    }

                } else {
                    itemView.lbPerson.text = record.person
                    itemView.lbRemarks.text = record.remarks
                    itemView.lbInfoAmount.setTextColor(Color.parseColor("#008000"))
                    itemView.lbInfoAmount.text = "+" + record.value.toString()
                    itemView.lbDateInfo.text = record.registredAt
                }
            } else {
                if (record == recordEdit) {
                    itemView.editRecipient.text = record.person
                    itemView.editRemarks.setText(record.remarks)
                    itemView.editAmount.setTextColor(Color.parseColor("#ff0000"))
                    itemView.editAmount.text = "+" + record.value.toString()
                    itemView.editDate.text = record.registredAt
                    itemView.okEdit.setOnClickListener {
                        record.remarks = itemView.editRemarks.text.toString()
                        saveRec(record)
                    }
                } else {
                    itemView.lbPerson.text = record.person
                    itemView.lbRemarks.text = record.remarks
                    itemView.lbInfoAmount.setTextColor(Color.parseColor("#ff0000"))
                    itemView.lbInfoAmount.text = "-" + record.value.toString()
                    itemView.lbDateInfo.text = record.registredAt
                }

            }

            itemView.setOnClickListener {
                edit(record)
            }
        }
    }


}