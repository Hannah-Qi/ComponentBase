package com.example.componentbase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.annotation.ARouter
import com.example.annotation.Parameter
import com.example.common.config.BaseConfig
import com.example.common.config.mainActivityPath
import com.example.common.config.orderMainActivityPath
import com.example.common.config.personalMainActivityPath
import com.example.componentbase.databinding.ActivityMainBinding
import com.example.mylibrary.MainActivity
import com.example.router_api_k.RouterManager

@ARouter(path = mainActivityPath)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Parameter
    var name: String = ""
    @Parameter
    var age: Int = 0

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
        binding.apply {
            butToOrder.setOnClickListener {
                Log.i(BaseConfig.TAG, "app/MainActivity test butToOrder OnClick")
                RouterManager.build(orderMainActivityPath)
                    .withInt("age", 18)
                    .withString("name", "Tim")
                    .navigation(this@MainActivity)
            }

            butToPersonal.setOnClickListener {
                Log.i(BaseConfig.TAG, "app/MainActivity test butToPersonal OnClick")
                RouterManager.build(personalMainActivityPath).navigation(this@MainActivity)
            }

            butToMyLibrary.setOnClickListener {
                Log.i(BaseConfig.TAG, "app/MainActivity test butToMyLibrary OnClick")
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}