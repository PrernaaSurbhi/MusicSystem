package com.example.musicsystem

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.musicsystem.databinding.ActivityMainBinding
import com.example.musicsystem.service.MyService


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.buttonStart.setOnClickListener(this)
        binding.buttonStop.setOnClickListener(this)
    }

    fun startBackGroundService(){
        val intent = Intent(this,MyService::class.java)
        startService(intent)
    }

    fun stopBackGroundService(){
        val intent = Intent(this,MyService::class.java)
        stopService(intent)
    }

    override fun onClick(view: View?) {
       when(view ){
           binding?.buttonStart ->{
               startBackGroundService()
           }

           binding?.buttonStop->{
               stopBackGroundService()
           }
        }


    }
}