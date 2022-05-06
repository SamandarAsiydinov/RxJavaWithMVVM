package com.example.rxjavamvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rxjavamvvm.adapter.RvAdapter
import com.example.rxjavamvvm.databinding.ActivityMainBinding
import com.example.rxjavamvvm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        rvAdapter = RvAdapter()
        setupRv()
        editSearch()
    }

    private fun setupRv() = binding.recyclerView.apply {
        val itemDecoration =
            DividerItemDecoration(this@MainActivity, StaggeredGridLayoutManager.VERTICAL)
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = rvAdapter
        addItemDecoration(itemDecoration)
    }

    private fun editSearch() {
        binding.inputBookName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadApi(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadApi(query: String) {
        mainViewModel.getBookListObserver().observe(this) { list ->
            if (list != null) {
                rvAdapter.submitList(list.items)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        mainViewModel.makeApiCall(query)
    }
}