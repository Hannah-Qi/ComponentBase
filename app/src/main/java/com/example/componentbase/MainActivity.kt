package com.example.componentbase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.annotation.ARouter
import com.example.common.config.BaseConfig
import com.example.componentbase.databinding.ActivityMainBinding

@ARouter(path = "app/MainActivity")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(BuildConfig.isRelese) {
            Log.i(BaseConfig.TAG, "onCreate: 集成化开发")
        } else {
            Log.i(BaseConfig.TAG, "onCreate: 组件化开发")
        }

        //组件化中不能直接使用其他组件的类，高耦合，引入ARouter路由框架
//        binding.apply {
//            butToOrder.setOnClickListener {
//                Log.i(BaseConfig.TAG, "app/MainActivity test butToOrder OnClick")
//                startActivity(Intent(this@MainActivity, OrderMainActivity::class.java))
//            }
//
//            butToPersonal.setOnClickListener {
//                Log.i(BaseConfig.TAG, "app/MainActivity test butToPersonal OnClick")
//                startActivity(Intent(this@MainActivity, PersonalMainActivity::class.java))
//            }
//        }
    }
}