package com.theapplication.expensetracker.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.theapplication.expensetracker.Data.Model.ExpenseEntity
import com.theapplication.expensetracker.R
import com.theapplication.expensetracker.Utils.utils
import com.theapplication.expensetracker.ViewModel.AddExpenseViewModel
import com.theapplication.expensetracker.ViewModel.AddExpenseViewModelFactory
import com.theapplication.expensetracker.ui.theme.Zinc
import kotlinx.coroutines.launch

@Composable
fun AddExpense(navController: NavController) {

    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

    val coroutineScope= rememberCoroutineScope()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {

                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(35.dp)
                    )
                }


                Text(
                    text = "Add Expense",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.inter_bold)),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.dots_menu), contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            DataForm(modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, onAddExpenseclick = {
                    coroutineScope.launch {
                        viewModel.addExpense(it)
                    }
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataForm(modifier: Modifier, onAddExpenseclick: (model: ExpenseEntity) -> Unit) {
    var name by remember {
        mutableStateOf("")
    }

    var amount by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf(0L)
    }

    val dialogvisibility = remember {
        mutableStateOf(false)
    }

    val category = remember {
        mutableStateOf("")
    }

    val type = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {


        // Name

        Text(
            text = "Name", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.inter_bold))

        )
        Spacer(modifier = Modifier.size(4.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))

        // Amount

        Text(
            text = "Amount", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.inter_bold))

        )
        Spacer(modifier = Modifier.size(4.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))


        // Date

        Text(
            text = "Date", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.inter_bold))

        )

        OutlinedTextField(
            value = if (date == 0L) "" else utils.formatdate(date),
            onValueChange = { },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dialogvisibility.value = true },
            enabled = false
        )
        Spacer(modifier = Modifier.size(16.dp))

        // Category dropdown
        Text(
            text = "Select Category", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.inter_bold))
        )
        Spacer(modifier = Modifier.size(4.dp))

        ExpenseDropDown(
            listofItem = listOf("Freelancing", "Food", "Travling", "Side Income"),
            onItemSelected = { category.value = it })
        Spacer(modifier = Modifier.size(16.dp))

        // Type dropdown


        Text(
            text = "Select Type", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontFamily = FontFamily(Font(R.font.inter_bold))
        )
        Spacer(modifier = Modifier.size(4.dp))

        ExpenseDropDown(
            listofItem = listOf("Income", "Expense"),
            onItemSelected = { type.value = it })

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            onClick = {
                val model =
                    ExpenseEntity(null, name, amount.toDoubleOrNull() ?: 0.0, date.toShort().toLong(),category.value, type.value)
                onAddExpenseclick(model)
                Toast.makeText(context,"Expense Added",Toast.LENGTH_SHORT).show()

                name=""
                amount=""


            }, modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Zinc)
        ) {
            Text(
                text = "Add Expense", fontSize = 14.sp, color = Color.White
                ,modifier=Modifier.padding(8.dp),
                fontFamily = FontFamily(Font(R.font.inter_extra_bold))
            )
        }
    }
    if (dialogvisibility.value) {
        ExpenseDatePickerDialog(onDateSelected = {
            date = it
            dialogvisibility.value = false
        }, onDismiss = {
            dialogvisibility.value = false
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(onDateSelected: (date: Long) -> Unit, onDismiss: () -> Unit) {

    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L

    DatePickerDialog(
        onDismissRequest = { onDismiss },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Confirm")
            }
        }, dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                Text(text = "Cancel")
            }
        }) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listofItem: List<String>, onItemSelected: (item: String) -> Unit) {
    val expended = remember {
        mutableStateOf(false)
    }

    val selecteditem = remember {
        mutableStateOf<String>(listofItem[0])
    }
    ExposedDropdownMenuBox(expanded = expended.value, onExpandedChange = { expended.value = it }) {
        OutlinedTextField(
            value = (selecteditem.value),
            onValueChange = { },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            enabled = false
        )
        ExposedDropdownMenu(expanded = expended.value, onDismissRequest = { }) {
            listofItem.forEach {
                DropdownMenuItem(text = { Text(text = it) },
                    onClick = {
                        selecteditem.value = it
                        onItemSelected(selecteditem.value)
                        expended.value = false
                    })
            }
        }
    }
}

@Preview
@Composable
fun previewAddExpense() {
   // AddExpense()
}