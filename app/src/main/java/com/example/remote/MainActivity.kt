package com.example.remote

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import android.hardware.ConsumerIrManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remote.ui.theme.RemoteTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val irEmitter = InfraredEmitter(getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager)
        enableEdgeToEdge()
        setContent {
            RemoteTheme {
                RemoteApp(irEmitter)
            }
        }
    }
}

enum class RemoteType {
    AIR_CONDITIONER,
    TELEVISION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteApp(infraredEmitter : InfraredEmitter) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var currentRemote by remember { mutableStateOf(RemoteType.AIR_CONDITIONER) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = { Text("Aire Acondicionado") },
                    selected = currentRemote == RemoteType.AIR_CONDITIONER,
                    onClick = {
                        scope.launch { drawerState.close() }
                        currentRemote = RemoteType.AIR_CONDITIONER
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Televisión") },
                    selected = currentRemote == RemoteType.TELEVISION,
                    onClick = {
                        scope.launch { drawerState.close() }
                        currentRemote = RemoteType.TELEVISION
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(if (currentRemote == RemoteType.AIR_CONDITIONER) "Aire Acondicionado" else "Televisión") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú de navegación"
                            )
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            when (currentRemote) {
                RemoteType.AIR_CONDITIONER -> {
                    val viewModel = SurreyAcRemoteViewModel(infraredEmitter)
                    SurreyAcRemoteView(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
                }
                RemoteType.TELEVISION -> {
                    val viewModel: TelevisionViewModel = viewModel()
                    TelevisionRemote(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun TelevisionRemote(modifier: Modifier = Modifier, viewModel: TelevisionViewModel) {
    val buttonPressed = viewModel.buttonPressed

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.onPlusButtonPressed() }) {
            Text("+")
        }
        Button(onClick = { viewModel.onMinusButtonPressed() }) {
            Text("-")
        }
        if (buttonPressed.isNotEmpty()) {
            Text(text = "tocaste el botón $buttonPressed")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RemoteAppPreview() {
    RemoteTheme {
        RemoteApp(InfraredEmitter(null))
    }
}
