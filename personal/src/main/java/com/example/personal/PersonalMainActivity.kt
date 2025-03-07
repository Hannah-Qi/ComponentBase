package com.example.personal

import com.example.personal.viewmodel.WeViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.personal.ui.WeTopBar
import com.example.wecompose.ui.theme.WeComposeTheme

class PersonalMainActivity : ComponentActivity() {
    private val viewModel: WeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeComposeTheme(viewModel.theme) {
                // A surface container using the 'background' color from the theme
                Column(
                    Modifier
                        .background(WeComposeTheme.colors.background)
                        .fillMaxSize()
                ) {
                    WeTopBar(stringResource(R.string.personal))
                    
                    Box(
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Text(
                            stringResource(R.string.personal_home_page),
                            color = WeComposeTheme.colors.textPrimary,
                            fontSize = 30.sp
                        )
                    }
                }
            }
        }
    }
}