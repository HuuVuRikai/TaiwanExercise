package com.rikai.taiwanexercise.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rikai.taiwanexercise.BaseApp.Companion.baseApp
import com.rikai.taiwanexercise.R
import com.rikai.taiwanexercise.databinding.ActivityDetailBinding
import com.rikai.taiwanexercise.models.User
import com.rikai.taiwanexercise.view_model.DetailViewModel
import com.rikai.taiwanexercise.view_model.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel
    lateinit var viewModelFactory: DetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = DetailViewModelFactory(baseApp!!.appRepositoryImpl)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        initializeView()
        initializeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeView(){
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        val bundle: Bundle? = intent.getBundleExtra("bundleDetailUser")
        bundle?.let {
            bundle.apply {
                val user = getSerializable("user") as User?
                if(user != null) {
                    viewModel.getUser(user.login)
                }
            }
        }
    }

    private fun initializeViewModel(){
        viewModel.showResult.observe(this, Observer {
            if(it != null) {
                Snackbar.make(binding.root, "Success", Snackbar.LENGTH_LONG).show()
                Glide.with(this).load(it.avatarUrl).into(binding.avatarImg);
                binding.nameTv.setText(it.name)
                binding.bioTv.setText(it.bio)
                binding.loginTv.setText(it.login)
                if(it.siteAdmin) {
                    binding.siteAdminTv.setText(R.string.admin)
                }else {
                    binding.siteAdminTv.setText(R.string.staff)
                }
                binding.locationTv.setText(it.location)
                binding.blogTv.setText(it.blog)
            }
        })
    }
}