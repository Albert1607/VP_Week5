package com.example.vp_week5

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vp_week5.ui.theme.VP_Week5Theme

data class Car(
    val id: Int,
    val name: String,
    val transmission: String,
    val year: Int,
    val mileage: String,
    val price: String,
    val location: String,
    val fuelType: String,
    val imageRes: Int,
    val description: String,
    val isFavorite: Boolean = false
)

fun getSampleCars() = listOf(
    Car(1, "Tiggo 9 CSH", "Auto", 2024, "5,000 km", "Rp 485.000.000", "Jakarta", "PHEV", R.drawable.tiggo, "Luxury plug-in hybrid SUV offering state-of-the-art tech and 7-seater comfort.", true),
    Car(2, "Honda CRV", "Auto", 2013, "15,200 km", "Rp 195.000.000", "Bandung", "PETROL", R.drawable.crv, "Well-maintained 2.0 AT with low mileage, comfortable suspension, and reliable performance.", false),
    Car(3, "Toyota Yaris Cross", "Auto", 2024, "30,500 km", "Rp 320.000.000", "Surabaya", "HYBRID", R.drawable.yariscross, "Hybrid S HV with advanced safety features, modern powertrain, and dynamic styling.", true),
    Car(4, "Daihatsu Xenia", "Manual", 2022, "28,000 km", "Rp 175.000.000", "Tangerang", "PETROL", R.drawable.xenia, "1.3 R MT modern MPV featuring spacious interior and excellent fuel economy.", false),
    Car(5, "Toyota Yaris", "Auto", 2021, "52,400 km", "Rp 245.000.000", "Semarang", "PETROL", R.drawable.yaris, "The Toyota Yaris G AT is a sporty compact hatchback with 1.5L dual VVT-i engine, 7 airbags, and premium features.", true),
    Car(6, "Honda HRV", "Auto", 2022, "18,000 km", "Rp 310.000.000", "Jakarta", "PETROL", R.drawable.hrv, "1.5 SE CVT with panoramic glass roof, modern interior layout, and Honda Sensing.", true),
    Car(7, "Innova Reborn", "Auto", 2020, "42,000 km", "Rp 335.000.000", "Yogyakarta", "PETROL", R.drawable.reborn, "2.0 V AT offering unmatched durability, 3-row seating, and smooth automatic transmission.", false),
    Car(8, "Daihatsu Ayla", "Manual", 2021, "22,000 km", "Rp 115.000.000", "Malang", "PETROL", R.drawable.ayla, "1.2 R MT compact city car perfect for narrow urban streets with great fuel economy.", true),
    Car(9, "MG 4 EV", "Auto", 2023, "12,000 km", "Rp 395.000.000", "Jakarta", "ELECTRIC", R.drawable.mg4, "Fully electric hatchback featuring futuristic design, instant torque, and smart tech.", false),
    Car(10, "JAC J5", "Auto", 2023, "8,500 km", "Rp 260.000.000", "Bekasi", "PETROL", R.drawable.j5, "Modern compact sedan with comfortable leather seats and keyless entry.", false)
)

@Composable
fun Soal1Screen() {
    val carList = remember { mutableStateListOf<Car>().apply { addAll(getSampleCars()) } }
    var selectedCarId by remember { mutableStateOf<Int?>(null) }
    val selectedCar = carList.find { it.id == selectedCarId }

    fun toggleFavorite(carId: Int) {
        val idx = carList.indexOfFirst { it.id == carId }
        if (idx != -1) carList[idx] = carList[idx].copy(isFavorite = !carList[idx].isFavorite)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF8F9FA)) {
        if (selectedCar != null) {
            CarDetailScreen(
                car = selectedCar,
                onBackClick = { selectedCarId = null },
                onToggleFavorite = { toggleFavorite(selectedCar.id) }
            )
        } else {
            OnSaleScreen(
                carList = carList,
                savedCount = carList.count { it.isFavorite },
                onCarClick = { selectedCarId = it.id },
                onToggleFavorite = { toggleFavorite(it.id) }
            )
        }
    }
}

@Composable
fun OnSaleScreen(
    carList: List<Car>,
    savedCount: Int,
    onCarClick: (Car) -> Unit,
    onToggleFavorite: (Car) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("On Sale", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E252B))
                Text("Find your perfect ride", fontSize = 14.sp, color = Color(0xFF8A94A6))
            }

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFFFF0ED))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Favorite, null, tint = Color(0xFFFF5733), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("$savedCount saved", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFF5733))
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(carList, key = { it.id }) { car ->
                CarCard(car = car, onCarClick = { onCarClick(car) }, onToggleFavorite = { onToggleFavorite(car) })
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun CarCard(car: Car, onCarClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onCarClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(120.dp, 90.dp).clip(RoundedCornerShape(12.dp))) {
                Image(
                    painter = painterResource(car.imageRes),
                    contentDescription = car.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                val (tagBg, tagText) = when (car.fuelType) {
                    "PHEV" -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
                    "HYBRID" -> Color(0xFFE8F5E9) to Color(0xFF388E3C)
                    "ELECTRIC" -> Color(0xFFEDE7F6) to Color(0xFF7B1FA2)
                    else -> Color(0xFFFFF3E0) to Color(0xFFE65100)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(tagBg)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(car.fuelType, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = tagText)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f).height(90.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(car.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E252B), maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                    IconButton(onClick = onToggleFavorite, modifier = Modifier.size(28.dp)) {
                        Icon(
                            imageVector = if (car.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (car.isFavorite) Color(0xFFFF5733) else Color(0xFFB0B0B0),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFF1F3F5))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(car.transmission, fontSize = 11.sp, color = Color(0xFF6C757D), fontWeight = FontWeight.Medium)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFFF5733)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(car.year.toString(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E252B))
                    }
                    Text(car.mileage, fontSize = 12.sp, color = Color(0xFF8A94A6))
                }
            }
        }
    }
}

@Composable
fun CarDetailScreen(car: Car, onBackClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).navigationBarsPadding()
    ) {
        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                Image(
                    painter = painterResource(car.imageRes),
                    contentDescription = car.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(top = 12.dp, start = 16.dp)
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = Color(0xFF1E252B), modifier = Modifier.size(20.dp))
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFFFF3E0))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(car.fuelType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(car.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E252B))
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (car.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (car.isFavorite) Color(0xFFFF5733) else Color(0xFFB0B0B0),
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFFF5733)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(car.location, fontSize = 14.sp, color = Color(0xFF8A94A6))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFF0ED))
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(car.price, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF5733))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("SPECIFICATIONS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8A94A6), letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SpecItem("YEAR", car.year.toString(), Modifier.weight(1f))
                    SpecItem("MILEAGE", car.mileage, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SpecItem("TRANSMISSION", car.transmission, Modifier.weight(1f))
                    SpecItem("FUEL TYPE", car.fuelType.lowercase().replaceFirstChar { it.uppercase() }, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("ABOUT THIS CAR", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8A94A6), letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(car.description, fontSize = 14.sp, color = Color(0xFF4A5568), lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFFF0ED))
                    .clickable { onToggleFavorite() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (car.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (car.isFavorite) Color(0xFFFF5733) else Color(0xFFB0B0B0),
                    modifier = Modifier.size(24.dp)
                )
            }

            Button(
                onClick = {},
                modifier = Modifier.weight(1f).height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5733))
            ) {
                Text("Contact Seller", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun SpecItem(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF8F9FA))
            .padding(12.dp)
    ) {
        Column {
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8A94A6), letterSpacing = 0.5.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E252B))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Soal1Preview() {
    VP_Week5Theme { Soal1Screen() }
}
