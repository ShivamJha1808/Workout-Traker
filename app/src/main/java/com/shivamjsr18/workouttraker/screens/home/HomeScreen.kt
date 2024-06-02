package com.shivamjsr18.workouttraker.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shivamjsr18.workouttraker.model.MuscleGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onMuscleGrpCardClick: (String)->Unit, openSetting:()->Unit)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "What are we doing today?") },
                actions = {
                    IconButton(onClick = { openSetting() }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                })
        }
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            MuscleGroup.getMuscleGroupList().forEach{name->
                MuscleGroupCard(name = name, onMuscleGrpCardClick= onMuscleGrpCardClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuscleGroupCard(name:String, onMuscleGrpCardClick:(String)->Unit)
{
    Card(onClick = { onMuscleGrpCardClick(name) },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()) {
        Row {
            Text(text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp))

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun CardPreview()
{
    HomeScreen({},{})
}