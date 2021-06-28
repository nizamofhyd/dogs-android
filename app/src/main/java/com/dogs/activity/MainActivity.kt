package com.dogs.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dogs.R
import com.dogs.di.Injectable

class MainActivity : AppCompatActivity(), Injectable {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}