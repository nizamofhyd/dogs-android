package com.dogs.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dogs.R
import com.dogs.di.Injectable
import com.dogs.fragments.BreedsListFragment

class MainActivity : AppCompatActivity(), Injectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeEvents()
        initialiseView()
    }

    private fun initialiseView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, BreedsListFragment())
            .commit()
    }

    private fun observeEvents() {
    }
}