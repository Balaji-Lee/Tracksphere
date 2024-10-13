package com.example.tracksphere.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tracksphere.model.Vehicle
import com.example.tracksphere.ui.theme.TracksphereTheme

class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TracksphereTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    DashboardContent(Modifier.padding(paddingValues))
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Tracksphere") },
        backgroundColor = Color(0xFF6200EA),
        contentColor = Color.White,
        elevation = 12.dp
    )
}

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = false,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Place, contentDescription = "Trace") },
            selected = true,
            onClick = {}
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.MoreVert, contentDescription = "More") },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun DashboardContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 56.dp, bottom = 64.dp),
        ) {
            StatusSummary()
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(onSearch = ::filterVehicleList)
            Spacer(modifier = Modifier.height(16.dp))
            VehicleList()
        }
    }
}

@Composable
fun StatusSummary() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatusCard("Running", 10, Color.Green)
        StatusCard("Idle", 5, Color.Yellow)
        StatusCard("Stopped", 3, Color.Red)
        StatusCard("Offline", 2, Color.Gray)
    }
}

@Composable
fun StatusCard(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$count", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = color)
        Text(text = label, fontSize = 14.sp)
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val searchTextState = remember { mutableStateOf("") }

    TextField(
        value = searchTextState.value,
        onValueChange = { searchTextState.value = it },
        label = { Text("Search") },
        modifier = modifier
    )

    // Filter vehicle list based on search input
    filterVehicleList(searchTextState.value)
}

// Function to filter vehicle list based on search input
fun filterVehicleList(query: String) {
    val filteredVehicles = getMockVehicleList().filter { vehicle ->
        vehicle.vehicleNumber.contains(query, ignoreCase = true) ||
                vehicle.status.contains(query, ignoreCase = true) ||
                vehicle.speedOrTime.contains(query, ignoreCase = true)
    }

    // Update the VehicleList composable with the filtered vehicles
    VehicleList(filteredVehicles)
}

@Composable
fun VehicleList(vehicles: List<Vehicle> = getMockVehicleList()) {
    Column {
        vehicles.forEach { vehicle ->
            VehicleCard(
                vehicleNumber = vehicle.vehicleNumber,
                status = vehicle.status,
                info = vehicle.speedOrTime,
                statusColor = vehicle.statusColor
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun VehicleCard(vehicleNumber: String, status: String, info: String, statusColor: Color) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = vehicleNumber, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "MAT788052P7C06674", fontSize = 12.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = status,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = statusColor
                )
                Text(text = info, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TracksphereTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomNavigationBar() },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            DashboardContent(Modifier.padding(paddingValues))
        }
    }
}
