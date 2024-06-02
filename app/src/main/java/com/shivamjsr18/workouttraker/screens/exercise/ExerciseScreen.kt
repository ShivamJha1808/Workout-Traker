package com.shivamjsr18.workouttraker.screens.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shivamjsr18.workouttraker.model.Exercise
import com.shivamjsr18.workouttraker.model.MuscleGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen (
    openSetting:()->Unit,
    muscleGroupName: String,
    viewModel: ExerciseViewModel = hiltViewModel()
) {

    val muscleGroup = MuscleGroup.getMuscleGroupByName(muscleGroupName)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.showAddDialogue()}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add exercise")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = muscleGroupName) },
                actions = {
                    IconButton(onClick = { openSetting() }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
                )
        }
    ) {paddingValues->

        if(viewModel.isShowingAddDialogue){
            AddDialogue(viewModel,muscleGroup)
        }

        if(viewModel.isShowingAddSetDialogue){
            AddSetDialogue(viewModel = viewModel, exercise = viewModel.exerciseBeingAddedTo)
        }

        LazyColumn(modifier = Modifier.padding(paddingValues)){
            viewModel.listExercises.forEach{exercise->
                if(exercise.muscleGroup==muscleGroup){
                    item {
                        ExerciseCard(name = exercise.name, onExerciseCardClick = { viewModel.showAddSetDialogue(exercise) })
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCard(name: String, onExerciseCardClick: ()->Unit) {
    Card(onClick = { onExerciseCardClick() },
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

@Composable
fun AddDialogue(viewModel: ExerciseViewModel,muscleGroup: MuscleGroup) {
    AlertDialog(
        onDismissRequest = {viewModel.dismissAddDialogue()},
        title = {Text(text="Enter name of the exercise")},
        text = {
            Column {
                OutlinedTextField(
                    value = viewModel.newExerciseName,
                    onValueChange = {viewModel.onNewExerciseNameChange(it)},
                    label = { Text("Name") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Words
                    )
                )
            }
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { viewModel.onAddExercise(muscleGroup) }) {
                    Text(text="Add")
                }
                Button(onClick = {viewModel.dismissAddDialogue()}) {
                    Text(text = "Cancel")
                }
            }
        }
    )
}


@Composable
fun AddSetDialogue(viewModel: ExerciseViewModel, exercise: Exercise){
    AlertDialog(
        onDismissRequest = {viewModel.dismissAddSetDialogue()},
        title = {Text(text="Enter set details")},
        text = {
            Column {
                OutlinedTextField(
                    value = viewModel.newSetState.set1,
                    onValueChange = { viewModel.onSet1Change(it) },
                    label = { Text("Set 1") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.newSetState.set2,
                    onValueChange = { viewModel.onSet2Change(it) },
                    label = { Text("Set 2") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.newSetState.set3,
                    onValueChange = { viewModel.onSet3Change(it) },
                    label = { Text("Set 3") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.newSetState.set4,
                    onValueChange = { viewModel.onSet4Change(it) },
                    label = { Text("Set 4") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.newSetState.set5,
                    onValueChange = { viewModel.onSet5Change(it) },
                    label = { Text("Set 5") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))



                OutlinedTextField(
                    value = viewModel.newSetState.remarks,
                    onValueChange = {viewModel.onRemarkChange(it)},
                    label = { Text("Remarks") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )


            }
        },
        confirmButton = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { viewModel.onAddNewSet(exercise) }) {
                    Text(text="Add")
                }
                Button(onClick = {viewModel.dismissAddSetDialogue()}) {
                    Text(text = "Cancel")
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ExercisePreview() {
//    ExerciseScreen(MuscleGroup.Biceps)
}


object Test {
    val lst = listOf("Bicep curl","Preacher Curl","Hammer Curl")
}