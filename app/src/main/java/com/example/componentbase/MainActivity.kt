package com.example.componentbase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.common.config.BaseConfig
import com.example.componentbase.databinding.ActivityMainBinding
import com.example.order.OrderMainActivity
import com.example.personal.PersonalMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            butToOrder.setOnClickListener {
                Log.i(BaseConfig.TAG, "app/MainActivity test butToOrder OnClick")
                startActivity(Intent(this@MainActivity, OrderMainActivity::class.java))
            }

            butToPersonal.setOnClickListener {
                Log.i(BaseConfig.TAG, "app/MainActivity test butToPersonal OnClick")
                startActivity(Intent(this@MainActivity, PersonalMainActivity::class.java))
            }
        }
    }
}