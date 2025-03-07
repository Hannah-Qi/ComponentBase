package com.example.order

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.common.NetManagerUtils
import com.example.order.databinding.OrderMainActivityBinding

class OrderMainActivity : AppCompatActivity() {
    private lateinit var binding: OrderMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = OrderMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NetManagerUtils().netManager()
    }
}