package com.example.order

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.common.NetManagerUtils
import com.example.order.databinding.OrderMainActivityBinding
import com.example.order.debug.OrderDebugActivity

class OrderMainActivity : AppCompatActivity() {
    private lateinit var binding: OrderMainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = OrderMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NetManagerUtils().netManager()

        binding.apply {
            tvToDebugOrder.setOnClickListener {
                startActivity(Intent(this@OrderMainActivity, OrderDebugActivity::class.java))
            }
        }
    }
}
