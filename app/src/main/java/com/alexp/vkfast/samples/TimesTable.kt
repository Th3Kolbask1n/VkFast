package com.alexp.vkfast.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun TimesTable() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for (i in 1 until 10)

            Row(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)

            ) {
                for (j in 1 until 10) {
                    val color = if ((i + j) % 2 == 0) {
                        Color.Yellow
                    } else {
                        Color.White
                    }
                    Box(
                        modifier = Modifier.fillMaxHeight()
                            .weight(1f)
                            .border(width = 1.dp, color = Color.DarkGray)
                            .background(color = color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${i * j}")
                    }
                }
            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Test() {

ModalNavigationDrawer( drawerContent = {
    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text(text = " Text 1") },
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Filled.Call, contentDescription = null)}

                )
                Spacer(modifier = Modifier.padding(8.dp))
                NavigationDrawerItem(
                    label = { Text(text = " Text 2") },
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Email, contentDescription = null) }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                NavigationDrawerItem(
                    label = { Text(text = " Text 3") },
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Place, contentDescription = null) }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "Another simple text for show test!")
            }
    }
)
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Simple TopAppBar") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Menu, "Localized description")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = {
                            Text(text = "2")
                        })
                    NavigationBarItem(selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Outlined.Edit, contentDescription = null) },
                        label = {
                            Text(text = "2")
                        })
                    NavigationBarItem(selected = true,
                        onClick = {},
                        icon = { Icon(Icons.Outlined.Delete, contentDescription = null) },
                        label = {
                            Text(text = "2")
                        })

                }
            }
        )
        {
            Text(
                modifier = Modifier.padding(it),
                text = "This"
            )
        }
    }

}