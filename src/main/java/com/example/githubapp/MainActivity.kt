package com.example.githubapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.rvUser.setHasFixedSize(true)
        showRecyclerList()

        mainViewModel.getUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showProgress(false)
            }
        })

    }
    private fun showRecyclerList(){
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.adapter = adapter


        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })

    }
    private fun showSelectedUser(user : User){
        val intent = Intent(this@MainActivity, UserActivity::class.java)
        intent.putExtra(UserActivity.EXTRA_USER, user)
        startActivity(intent)

    }
    private fun showProgress(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.enter_word)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataUserFromApi(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    fun getDataUserFromApi(username: String) {
        if(username.isEmpty()) return
        showProgress(true)
        mainViewModel.setUser(username)
    }

}