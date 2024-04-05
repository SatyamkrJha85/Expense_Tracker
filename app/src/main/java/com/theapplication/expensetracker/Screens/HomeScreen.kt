package com.theapplication.expensetracker.Screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.theapplication.expensetracker.Data.Model.ExpenseEntity
import com.theapplication.expensetracker.Navigation.Route
import com.theapplication.expensetracker.R
import com.theapplication.expensetracker.ViewModel.HomeViewModel
import com.theapplication.expensetracker.ViewModel.HomeViewModelFactory
import com.theapplication.expensetracker.ui.theme.InterFont
import com.theapplication.expensetracker.ui.theme.Zinc
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel =
        HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(
        modifier = Modifier.fillMaxSize()
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {

                Column {
                    Text(
                        text = "Good Afternoon", fontSize = 16.sp, color = Color.White,
                        fontFamily = InterFont
                    )
                    Text(
                        text = "Hello Satyam",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.inter_bold))

                    )
                }


                IconButton(
                    onClick = {
                        navController.navigate(Route.AddexpenceScreen.route)
                    }, modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }

            }

            val state = viewModel.expenses.collectAsState(initial = emptyList())
            val expenses = viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)
            CardItem(
                modifier = Modifier.constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, balance, income, expenses
            )

            TransactionList(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(list) {
                        top.linkTo(card.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    }, list = state.value, viewModel
            )
        }
    }
}

@Composable
fun CardItem(modifier: Modifier, balance: String, income: String, expenses: String) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Zinc)
            .padding(16.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Total Balance", fontSize = 16.sp, color = Color.White,
                    fontFamily = FontFamily(Font(R.font.inter_semi_bold))
                )
                Text(
                    text = balance,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.inter_bold))

                )
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            ) {
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                title = "Income",
                amount = income,
                image = R.drawable.ic_income
            )

            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                amount = expenses,
                image = R.drawable.ic_expense
            )
        }
    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: HomeViewModel) {
    val coroutineScope= rememberCoroutineScope()

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Recent Transations", fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semi_bold))
                )
                Text(
                    text = "See All",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semi_bold)),
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
        }
        items(list) { item ->
            TransactionItem(
                title = item.title,
                amount = item.amount.toString(),
                category = item.category.toString(),
                icon = viewModel.getItemIcon(item),
                date = item.date.toString(),
                color = if (item.type == "Income") Color.Green else Color.Red,
                onDeleteClicked = {
                    coroutineScope.launch {
                        viewModel.deleteExpense(item)
                    }
                }
            )
        }
    }

}

@Composable
fun TransactionItem(title: String, amount: String,category:String, icon: Int, date: String, color: Color,onDeleteClicked: () -> Unit ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = icon), contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.inter_semi_bold))
                )
                Text(
                    text = date, fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium))
                )

                Text(
                    text =category , fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.inter_medium))
                )
            }
        }
        Column(
            modifier = Modifier.align(Alignment.CenterEnd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "₹$amount",
                fontSize = 18.sp,
                color = color,
                fontWeight = FontWeight.Bold,
            )
            IconButton(onClick = {
                onDeleteClicked
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription =null )
            }
        }

    }
}

@Composable
fun CardRowItem(
    modifier: Modifier, title: String, amount: String, image: Int
) {
    Column(
        modifier = modifier

    ) {
        Row {
            Image(painter = painterResource(id = image), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title, fontSize = 16.sp, color = Color.White,
                fontFamily = FontFamily(Font(R.font.inter_semi_bold))
            )
        }
        Text(
            text = amount, fontSize = 20.sp, color = Color.White,
            fontFamily = FontFamily(Font(R.font.inter_bold))
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    //HomeScreen()
}