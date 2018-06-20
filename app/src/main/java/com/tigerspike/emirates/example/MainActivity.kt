package com.tigerspike.emirates.example

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.tigerspike.emirates.R
import com.tigerspike.emirates.example.viewmodel.ListViewModel
import com.tigerspike.emirates.tools.extensions.bind

class MainActivity : AppCompatActivity() {

    val recyclerView: RecyclerView by bind(R.id.recyclerView)
    val message: TextView by bind(R.id.message)
    val progressBar: ProgressBar by bind(R.id.progressBar)

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ReposAdapter(this, viewModel.repos)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.repos.observe(this, Observer<List<Repo>> {

        })
        viewModel.isError.observe(this, Observer<Boolean> {
            if (it == true) {
                message.setText(R.string.general_error_message)
            }
        })
        viewModel.isLoading.observe(this, Observer<Boolean> {
            progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
    }

}



