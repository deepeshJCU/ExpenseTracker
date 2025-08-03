package com.example.expensetracker.ui.screen

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun CategoryBarChart(
    categoryData: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    val entries = categoryData.entries.mapIndexed { index, entry ->
        BarEntry(index.toFloat(), entry.value.toFloat())
    }

    val labels = categoryData.keys.toList()
    val barDataSet = BarDataSet(entries, "Category Breakdown").apply {
        color = Color.rgb(63, 81, 181)
        valueTextColor = Color.BLACK
        valueTextSize = 12f
    }

    val barData = BarData(barDataSet)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    500
                )
                data = barData
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)
                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                description = Description().apply { text = "" }
                animateY(1000, Easing.EaseInOutQuad)
                legend.isEnabled = false
            }
        },
        update = { chart ->
            chart.data = barData
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    )
}

@Composable
fun CategoryPieChart(
    categoryData: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    if (categoryData.isEmpty()) return

    AndroidView(
        modifier = modifier,
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                setUsePercentValues(true)
                setEntryLabelTextSize(12f)
                setCenterText("Spending")
                setCenterTextSize(14f)
                legend.isEnabled = true
                setNoDataText("No category data")
            }
        },
        update = { chart ->
            val entries = categoryData.map { (category, amount) ->
                PieEntry(amount.toFloat(), category)
            }

            val dataSet = PieDataSet(entries, "Categories").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
                valueTextSize = 14f
            }

            val data = PieData(dataSet)
            chart.data = data
            chart.invalidate()
        }
    )
}
