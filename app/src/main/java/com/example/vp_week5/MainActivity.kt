package com.example.vp_week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.vp_week5.ui.theme.VP_Week5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week5Theme {
                Soal1Screen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    VP_Week5Theme {
        Soal1Screen()
    }
}
