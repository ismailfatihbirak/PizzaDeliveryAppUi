package com.isodev.pizzadeliveryappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isodev.pizzadeliveryappui.ui.theme.PizzaDeliveryAppUiTheme
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzaDeliveryAppUiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun MainScreen(modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painterResource(R.drawable.ellipse_top),
            contentDescription = "",
            modifier = Modifier.align(
                Alignment.TopCenter
            )
        )
        Image(
            painterResource(R.drawable.pizza_party),
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .offset(y = 40.dp)
        )
        Image(
            painterResource(R.drawable.big_pizza),
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .offset(y = -100.dp)
        )
        Text(
            text = "Farmhouse",
            fontSize = 32.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.align(Alignment.Center)
        )
        Image(
            painterResource(R.drawable.large_text),
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .offset(y = 50.dp)
        )
        Image(
            painterResource(R.drawable.text_desc),
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.Center
                )
                .offset(y = 90.dp)
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 160.dp)
                .clip(shape = RoundedCornerShape(35.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF5757), // FF5757
                            Color(0xFFFFD687)  // FFD687
                        )
                    )
                )
        ) {
            Text(text = "Add to Cart", color = Color.White, fontSize = 16.sp)
        }
        Image(
            painterResource(R.drawable.ellipse_bottom),
            contentDescription = "",
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )
        Image(
            painterResource(R.drawable.elips),
            contentDescription = "",
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )
        Image(
            painterResource(R.drawable.direction),
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .offset(y = -25.dp)
        )
        val scrollState = rememberScrollState()
        val scrollOffset = scrollState.value
        val rotationAngle = scrollOffset / 100f * 36f
        Canvas(modifier = Modifier
            .size(200.dp)
            .align(Alignment.BottomCenter)
            .offset(y = 155.dp)) {
            val gradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFFF7A7A), Color(0xFFFFC8C8)),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )
            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 40f), 0f)
            drawArc(
                brush = gradientBrush,
                startAngle = rotationAngle,
                sweepAngle = 359f,
                useCenter = false,
                style = Stroke(
                    width = 8.dp.toPx(),
                    pathEffect = dashEffect,
                    cap = StrokeCap.Round
                )
            )
        }

        CurvedScrollView(5, scrollState = scrollState) { index ->
            Image(
                painter = painterResource(
                    id = when (index) {
                        0 -> R.drawable.ss
                        1 -> R.drawable.ss
                        2 -> R.drawable.ss
                        3 -> R.drawable.ss
                        4 -> R.drawable.ss
                        else -> R.drawable.ss
                    }
                ),
                contentDescription = "Curved Image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 30.dp, end = 30.dp)
                    .clip(RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp))
            )

        }
    }
}

@Composable
fun CurvedScrollView(
    itemCount: Int,
    scrollState: ScrollState,
    item: @Composable (Int) -> Unit,
) {
    var size = remember { mutableStateOf(IntSize.Zero) }
    val scope = rememberCoroutineScope()
    val indices = remember { IntArray(itemCount) { 0 } }

    val flingBehaviour = object : FlingBehavior {
        override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
            val value = scrollState.value
            indices.minByOrNull { abs(it - value) }?.let {
                scope.launch {
                    scrollState.animateScrollTo(it)
                }
            }
            return initialVelocity
        }
    }

    Box(
        modifier = Modifier
            .onSizeChanged {
                size.value = it
            }
    ) {
        Layout(
            content = {
                repeat(itemCount) {
                    item(it)
                }
            },
            modifier = Modifier.horizontalScroll(
                scrollState, flingBehavior = flingBehaviour
            )
        ) { measurables, constraints ->
            val itemSpacing = 0.dp.roundToPx()
            var contentWidth = (itemCount - 1) * itemSpacing

            val placeables = measurables.mapIndexed { index, measurable ->
                val placeable = measurable.measure(constraints = constraints)
                contentWidth += if (index == 0 || index == measurables.lastIndex) {
                    placeable.width / 2
                } else {
                    placeable.width
                }
                placeable
            }

            layout(size.value.width + contentWidth, constraints.maxHeight) {
                val startOffset = size.value.width / 2 - placeables[0].width / 2
                var xPosition = startOffset

                val scrollPercent = scrollState.value.toFloat() / scrollState.maxValue

                placeables.forEachIndexed { index, placeable ->
                    val elementRatio = index.toFloat() / placeables.lastIndex
                    val interpolatedValue = cos((scrollPercent - elementRatio) * PI)
                    val indent =
                        interpolatedValue * size.value.height / 3.8


                    placeable.placeRelativeWithLayer(
                        x = xPosition,
                        y = size.value.height - indent.toInt()
                    ) {
                        alpha = 1f
                    }
                    indices[index] = xPosition - startOffset
                    xPosition += placeable.width + itemSpacing
                }
            }
        }
    }
}





