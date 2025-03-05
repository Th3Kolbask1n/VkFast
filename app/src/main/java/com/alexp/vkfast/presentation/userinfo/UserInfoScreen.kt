package com.alexp.vkfast.presentation.userinfo

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.alexp.vkfast.R
import com.alexp.vkfast.domain.entity.NewsItem
import com.alexp.vkfast.domain.entity.UserInfo
import com.alexp.vkfast.presentation.getApplicationComponent
import com.alexp.vkfast.ui.theme.DarkBlue
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onSwitchAccount: () -> Unit
) {

    val component = getApplicationComponent()

    val viewModel: AccountViewModel = viewModel(factory = component.getViewModelFactory())

    when (val state = viewModel.uiState.value) {
        is AccountViewModel.ProfileState.Loading -> {
            CircularProgressIndicator()
        }

        is AccountViewModel.ProfileState.Success -> {
            ProfileContent(
                profile = state.profile,
                onLogout = {

                    viewModel.logout()
                    onLogout
                },
                onSwitchAccount = {
                    viewModel.switchProfile()
                    onSwitchAccount
                }
            )
        }

        is AccountViewModel.ProfileState.Error -> {
            Text("Error: ${state.message}")
        }
    }
}

@Composable
private fun ProfileContent(
    profile: UserInfo,
    onLogout: () -> Unit,
    onSwitchAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = profile.photo200,
            contentDescription = "Profile photo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileSection(title = "Основная информация") {
            InfoRow(title = "Имя", value = profile.firstName)
            InfoRow(title = "Фамилия", value = profile.lastName)
            InfoRow(title = "Пол", value = formatGender(profile.sex))
            InfoRow(title = "Дата рождения", value = formatBirthDate(profile.birthDate))
            InfoRow(title = "Статус", value = profile.status ?: "Не указан")
        }

        ProfileSection(title = "Контактная информация") {
            InfoRow(title = "Телефон", value = profile.phone)
            InfoRow(title = "Родной город", value = profile.homeTown ?: "Не указан")
            InfoRow(title = "Город проживания", value = parseCity(profile.city))
        }

        ProfileSection(title = "Социальный статус") {
            InfoRow(title = "Семейное положение", value = formatRelationship(profile.relation))
        }

        Spacer(modifier = Modifier.height(32.dp))

        ActionButtons(
            onSwitchAccount = onSwitchAccount,
            onLogout = onLogout
        )
    }
}

@Composable
private fun ProfileSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Composable
private fun InfoRow(title: String, value: String?) {
    if (!value.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                modifier = Modifier.widthIn(min = 100.dp)
            )
        }
    }
}

@Composable
private fun ActionButtons(
    onSwitchAccount: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Button(
//            onClick = onSwitchAccount,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Сменить аккаунт")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Выйти из аккаунта")
        }
    }
}

private fun formatGender(sexCode: Int): String {
    return when (sexCode) {
        1 -> "Женский"
        2 -> "Мужской"
        else -> "Не указан"
    }
}

private fun formatBirthDate(bdate: String?): String {
    return bdate?.takeIf { it.isNotEmpty() }?.let {
        try {
            val parts = it.split(".")
            if (parts.size >= 2) {
                val day = parts[0]
                val month = when (parts[1].toInt()) {
                    1 -> "января"
                    2 -> "февраля"
                    3 -> "марта"
                    4 -> "апреля"
                    5 -> "мая"
                    6 -> "июня"
                    7 -> "июля"
                    8 -> "августа"
                    9 -> "сентября"
                    10 -> "октября"
                    11 -> "ноября"
                    12 -> "декабря"
                    else -> ""
                }
                val year = if (parts.size >= 3) " ${parts[2]} года" else ""
                "$day $month$year"
            } else {
                it
            }
        } catch (e: Exception) {
            it
        }
    } ?: "Не указана"
}

private fun formatRelationship(relationCode: Int): String {
    return when (relationCode) {
        1 -> "Не женат/Не замужем"
        2 -> "Есть партнер"
        3 -> "Помолвлен(а)"
        4 -> "Женат/Замужем"
        5 -> "Всё сложно"
        6 -> "В активном поиске"
        7 -> "Влюблен(а)"
        8 -> "В гражданском браке"
        else -> "Не указано"
    }
}

private fun parseCity(cityMap: Map<String, Any>?): String {
    return cityMap?.get("title") as? String ?: "Не указан"
}





