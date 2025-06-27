package com.example.expensetracker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Vertical
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize(),
        border = BorderStroke(2.dp, Color.Red)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow ,list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBar){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.Yellow)
                .padding(top = 64.dp , start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                Column(modifier = Modifier.border(width = 2.dp, color = Color.Yellow)) {

                    Text(text = "Good Afternoon",
                        fontSize = 16.sp,
                        color = Color.White)
                    Text(text = "Good Afternoon Deepesh",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White)
                }

                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(CenterEnd)
//                        .border(width = 2.dp, color = Color.Yellow)
//                        .padding(16.dp)
                )
            }
            CardItem(modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
                })
            TransactionList(modifier = Modifier.fillMaxWidth().constrainAs(list){
                top.linkTo(card.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            })

        }
    }
}

@Composable
fun CardItem(modifier: Modifier) {
    Column(modifier = modifier
        .padding(16.dp)
        .height(200.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(color = Color.Blue)
        .padding(16.dp)
    ) {
    Box(modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black).weight(1f)) {
        Column(modifier = Modifier.align(CenterStart)) {
            Text(text = "Total Balance",
                fontSize = 16.sp,
                color = Color.White)
            Text(text = "$ 5000",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            }
        Image(
            painter = painterResource(R.drawable.dots_menu),
            contentDescription = null,
            modifier = Modifier.align(TopEnd)
//                        .border(width = 2.dp, color = Color.Yellow)
//                        .padding(16.dp)
        )
        }
    Box(modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Black).weight(1f))
    {
        CardRowItem (
            modifier = Modifier.align(CenterStart),
            title = "Income",
            amount = "$4000",
            image = R.drawable.ic_income
        )
        CardRowItem (
            modifier = Modifier.align(CenterEnd),
            title = "Expense",
            amount = "$3000",
            image = R.drawable.ic_expense
        )
    }
    }
}

@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, image: Int) {
    Column(modifier = modifier) {
        Row{
            Image(painter = painterResource(id = image),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title,
                fontSize = 16.sp,
                color = Color.White)
        }
        Text(text = amount,
            fontSize = 16.sp,
            color = Color.White)
    }
}

@Composable
fun TransactionList(modifier: Modifier) {
    Column(modifier = modifier.border(width = 2.dp, color = Color.Red).padding(horizontal = 16.dp)) {
        Box(modifier = Modifier.fillMaxWidth().border(width = 2.dp, color = Color.Red).padding(vertical = 16.dp)) {
            Text(text= "Recent Transactions", fontSize = 20.sp)
            Text(text = "See All" ,
                fontSize = 16.sp ,
//                color = Color.White,
                modifier = Modifier.align(CenterEnd))
        }
        TransactionItem(
            title = "Netflix",
            amount = "$130",
            icon = R.drawable.ic_netflix,
            date = "today",
            color = Color.Red
        )
        TransactionItem(
            title = "Netflix",
            amount = "$130",
            icon = R.drawable.ic_paypal,
            date = "today",
            color = Color.Red
        )
        TransactionItem(
            title = "Netflix",
            amount = "$130",
            icon = R.drawable.ic_youtube,
            date = "today",
            color = Color.Red
        )
        TransactionItem(
            title = "Netflix",
            amount = "$130",
            icon = R.drawable.ic_paypal,
            date = "today",
            color = Color.Red
        )

    }
}

@Composable
fun TransactionItem(title: String, amount: String, icon: Int, date:String, color: Color) {
    Box(modifier = Modifier.padding(8.dp).fillMaxWidth().border(width = 2.dp, color = Color.Red)) {
        Row {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier =  Modifier.size(50.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = "title" , fontSize = 16.sp)
                Text(text = "date" , fontSize = 12.sp)
            }
        }
        Text(
            text = amount,
            fontSize = 20.sp,
            modifier = Modifier.align(CenterEnd),
            color = color
        )
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewHomeScreen() {
    HomeScreen()
}