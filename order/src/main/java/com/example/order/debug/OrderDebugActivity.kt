package com.example.order.debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.order.databinding.OrderDebugActivityBinding

class OrderDebugActivity: AppCompatActivity() {
    private lateinit var binding: OrderDebugActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OrderDebugActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}