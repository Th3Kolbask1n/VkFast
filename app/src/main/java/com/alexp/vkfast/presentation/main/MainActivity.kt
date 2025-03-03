package com.alexp.vkfast.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexp.vkfast.presentation.NewsFeedApplication
import com.alexp.vkfast.presentation.ViewModelFactory
import com.alexp.vkfast.presentation.getApplicationComponent
import com.alexp.vkfast.ui.theme.vkfastTheme
import com.vk.id.VKID
import javax.inject.Inject


class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        VKID.init(this)

        enableEdgeToEdge()
        setContent {

            val component = getApplicationComponent()
            val viewModel:MainViewModel = viewModel(factory = component.getViewModelFactory())

            val authState by viewModel.authState.collectAsState()

            vkfastTheme {



                LaunchedEffect(authState) {

                    when (authState) {
                        AuthState.Authorized -> {
                            Log.d("Auth", "User is authorized")
                        }

                        AuthState.NotAuthorized -> {
                            Log.d("Auth", "User is not authorized")
                        }

                        AuthState.Initial -> {
                            Log.d("Auth", "Initial state")
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.background)
                ) {

                    when (authState) {
                        AuthState.Authorized -> {
                            MainScreen()
                        }

                        AuthState.NotAuthorized -> LoginScreen()
                        AuthState.Initial -> { }
                    }


                }


            }
        }

    }
}


//@Composable
//private fun TextText(
//    count: Int,
//    text: String
//) {
//    repeat(count)
//    {
//        Text(text = text)
//    }
//}
//
//@Composable
//fun TestText() {
//    Image(
//        modifier = Modifier.clip(CircleShape),
//        painter = painterResource(R.drawable.ic_instagram),
//        contentDescription = "",
//        contentScale = ContentScale.Crop
//    )
//
//}
//
