package com.alexp.vkfast.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alexp.vkfast.R
import com.vk.id.AccessToken
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthUiParams
import com.vk.id.onetap.common.OneTapOAuth
import com.vk.id.onetap.compose.onetap.OneTap


@Composable

fun LoginScreen(
)
{
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment =Alignment.Center
    )
    {
        Column (
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.vk_logo),
                contentDescription = null)
            Spacer(modifier = Modifier.height(100.dp))
           OneTap(modifier = Modifier.fillMaxWidth(),

               onAuth = { oneTapOAuth: OneTapOAuth?, accessToken: AccessToken ->
               {

               }
           },
               signInAnotherAccountButtonEnabled = false,
               onFail = { oAuth, fail ->
                when (fail) {
                    is VKIDAuthFail.Canceled -> {}
                    is VKIDAuthFail.FailedApiCall -> {}
                    is VKIDAuthFail.FailedOAuthState -> {}
                    is VKIDAuthFail.FailedRedirectActivity ->{}
                    is VKIDAuthFail.NoBrowserAvailable -> {}
                    is VKIDAuthFail.FailedOAuth -> {}
                }
            })

        }
    }
}


