package com.trallerd.thebank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.trallerd.thebank.adapters.AllAdapter
import kotlinx.android.synthetic.main.fragment_extract.*
import kotlinx.android.synthetic.main.fragment_extract.view.*


class ExtractFragment : Fragment() {
    var navController: NavController? = null
    private lateinit var allAdapter: AllAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_extract, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allAdapter = AllAdapter(context)
        view.listRecords.adapter = AllAdapter(context)
        view.listRecords.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchBtn.setOnClickListener {
            val name = searchTxt.text.toString()
            if (name != null) {
                allAdapter.search(name)
            }
        }
    }


}
