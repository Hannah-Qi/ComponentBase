package com.example.order

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.annotation.ARouter
import com.example.annotation.Parameter
import com.example.common.NetManagerUtils
import com.example.common.config.BaseConfig
import com.example.common.config.orderMainActivityPath
import com.example.order.databinding.OrderMainActivityBinding
import com.example.order.debug.OrderDebugActivity
import com.example.router_api_k.ParameterManager

@ARouter(path = orderMainActivityPath)
class OrderMainActivity : AppCompatActivity() {
    private lateinit var binding: OrderMainActivityBinding

    @Parameter
    var isFemale: Boolean = false
    @Parameter
    var age: Int = 0
    @Parameter
    var name: String = ""

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

        ParameterManager.loadParameter(this)

        Log.i(BaseConfig.TAG, "test OrderMainActivity: name = $name")
        Log.i(BaseConfig.TAG, "test OrderMainActivity: age = $age")
    }
}
