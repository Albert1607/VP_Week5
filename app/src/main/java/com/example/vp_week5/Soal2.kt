package com.example.vp_week5

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

data class Hero(
    val id: Int,
    val name: String,
    val heroClass: String,
    val rarity: String,
    val level: Int,
    val cp: Int,
    val hp: Int,
    val maxHp: Int,
    val mp: Int,
    val maxMp: Int,
    val imageRes: Int,
    val atk: Int,
    val def: Int,
    val crit: String,
    val spd: Int,
    val skillName: String,
    val skillDescription: String,
    val inSquad: Boolean = false,
    val isExpanded: Boolean = false
)

fun getSampleHeroes() = listOf(
    Hero(1, "Alistair", "High Templar", "MYTHIC", 50, 5600, 1650, 1800, 650, 800, R.drawable.paladin, 380, 540, "22%", 105, "Wrath of the Heavens", "Calls down a celestial hammer, stunning all enemies for 3 seconds and taunting survivors.", inSquad = true, isExpanded = true),
    Hero(2, "Theresa", "Divine Oracle", "MYTHIC", 49, 5480, 1100, 1200, 2100, 2400, R.drawable.cleric, 210, 320, "15%", 112, "Blessing of the Sun", "Restores HP to all party members and increases defense by 30% for 5 seconds.", inSquad = true),
    Hero(3, "Vespera", "Shadowblade", "MYTHIC", 48, 5350, 890, 950, 410, 600, R.drawable.rogue, 620, 210, "45%", 145, "Shadow Strike", "Teleports behind the highest CP target, dealing massive critical damage."),
    Hero(4, "Elaria", "Archmage", "MYTHIC", 48, 5120, 450, 600, 1950, 2000, R.drawable.archmage, 710, 180, "38%", 118, "Meteor Rain", "Summons meteors to scorch the battlefield dealing area damage over 4 seconds.", inSquad = true),
    Hero(5, "Kaelen", "Berserker", "LEGENDARY", 47, 4980, 2100, 2200, 300, 500, R.drawable.berserker, 580, 410, "28%", 98, "Blood Frenzy", "Sacrifices 10% HP to boost attack speed and critical damage by 50%.")
)

@Composable
fun Soal2Screen() {
    val heroList = remember { mutableStateListOf<Hero>().apply { addAll(getSampleHeroes()) } }
    var selectedFilter by remember { mutableStateOf("All (14)") }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF0D0F17)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Party Roster", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Guild Vanguard • 14 Heroes Available", fontSize = 13.sp, color = Color(0xFF8E95A5))
                }

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFFFFB800), CircleShape)
                        .background(Color(0xFF221E12))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Expedition", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB800))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExpeditionSquadCard(squadHeroes = heroList.filter { it.inSquad })

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF161922))
                    .border(1.dp, Color(0xFF262B3A), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Search, "Search", tint = Color(0xFF6C757D), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Search by hero name, class, or rarity...", fontSize = 13.sp, color = Color(0xFF6C757D))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val filters = listOf("All (14)", "Berserker (2)", "Archmage (3)", "Paladin (2)", "Cleric (3)", "Rogue (4)")

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    items(filters) { filterName ->
                        val isSelected = filterName == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) Color(0xFFFFB800) else Color(0xFF161922))
                                .border(1.dp, if (isSelected) Color(0xFFFFB800) else Color(0xFF262B3A), CircleShape)
                                .clickable { selectedFilter = filterName }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = filterName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color(0xFF0D0F17) else Color(0xFF8E95A5)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF161922))
                        .border(1.dp, Color(0xFF262B3A), CircleShape)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("Sort: Power", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFFFB800))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(heroList, key = { it.id }) { hero ->
                    HeroCard(
                        hero = hero,
                        onCardClick = {
                            val idx = heroList.indexOfFirst { it.id == hero.id }
                            if (idx != -1) heroList[idx] = heroList[idx].copy(isExpanded = !heroList[idx].isExpanded)
                        },
                        onHealClick = {
                            val idx = heroList.indexOfFirst { it.id == hero.id }
                            if (idx != -1) heroList[idx] = heroList[idx].copy(hp = (heroList[idx].hp + 250).coerceAtMost(heroList[idx].maxHp))
                        },
                        onToggleSquadClick = {
                            val idx = heroList.indexOfFirst { it.id == hero.id }
                            if (idx != -1) heroList[idx] = heroList[idx].copy(inSquad = !heroList[idx].inSquad)
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
fun ExpeditionSquadCard(squadHeroes: List<Hero>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141722)),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF262B3A)))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF4CAF50)))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("EXPEDITION SQUAD (${squadHeroes.size}/4)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8E95A5), letterSpacing = 0.5.sp)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚔", fontSize = 12.sp, color = Color(0xFFFFB800))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("14750 CP", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB800))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 0 until 3) {
                        val hero = squadHeroes.getOrNull(i)
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFF1E2230))
                                .border(1.dp, Color(0xFF32384A), RoundedCornerShape(10.dp))
                        ) {
                            if (hero != null) {
                                Image(
                                    painter = painterResource(hero.imageRes),
                                    contentDescription = hero.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF181B26))
                            .border(1.dp, Color(0xFF2B3042), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, "Add", tint = Color(0xFF525A6C), modifier = Modifier.size(20.dp))
                    }
                }

                Button(
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB800)),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text("Deploy ⚡", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D0F17))
                }
            }
        }
    }
}

@Composable
fun HeroCard(
    hero: Hero,
    onCardClick: () -> Unit,
    onHealClick: () -> Unit,
    onToggleSquadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { onCardClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161922)),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF262B3A)))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFFD32F2F))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(hero.rarity, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF221E12))
                            .border(1.dp, Color(0xFFFFB800), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(hero.heroClass, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB800))
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (hero.inSquad) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF202A1E))
                                .border(1.dp, Color(0xFF4CAF50), RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("IN SQUAD", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Text("⚔", fontSize = 12.sp, color = Color(0xFFFFB800))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${hero.cp} CP", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB800))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E2230))
                        .border(1.dp, Color(0xFF32384A), RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(hero.imageRes),
                        contentDescription = hero.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(RoundedCornerShape(topStart = 6.dp, bottomEnd = 12.dp))
                            .background(Color(0xFF0D0F17))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Lvl ${hero.level}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(hero.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)

                    BarRow("HP", hero.hp, hero.maxHp, Color(0xFFFF3B30))
                    BarRow("MP", hero.mp, hero.maxMp, Color(0xFF2196F3))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = if (hero.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    tint = Color(0xFF8E95A5),
                    modifier = Modifier.size(24.dp)
                )
            }

            AnimatedVisibility(visible = hero.isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFF262B3A))
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        StatCard("ATK", hero.atk.toString(), Modifier.weight(1f))
                        StatCard("DEF", hero.def.toString(), Modifier.weight(1f))
                        StatCard("CRIT", hero.crit, Modifier.weight(1f))
                        StatCard("SPD", hero.spd.toString(), Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(hero.skillName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFB800))
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(hero.skillDescription, fontSize = 11.sp, color = Color(0xFF8E95A5), lineHeight = 16.sp)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(
                            onClick = onHealClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4CAF50)),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF17241A), contentColor = Color(0xFF4CAF50))
                        ) {
                            Text("⚡ Heal (+250 HP)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = onToggleSquadClick,
                            modifier = Modifier.weight(1f).height(40.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (hero.inSquad) Color(0xFFFF3B30) else Color(0xFFFFB800)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (hero.inSquad) Color(0xFF2A1719) else Color(0xFF221E12),
                                contentColor = if (hero.inSquad) Color(0xFFFF3B30) else Color(0xFFFFB800)
                            )
                        ) {
                            Text(if (hero.inSquad) "⚔ Remove" else "⚔ Add to Squad", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BarRow(label: String, value: Int, maxValue: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8E95A5), modifier = Modifier.width(22.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF222736))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(fraction = (value.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f))
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text("$value / $maxValue", fontSize = 10.sp, color = Color(0xFF8E95A5), fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF10121A))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8E95A5))
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Soal2Preview() {
    VP_Week5Theme { Soal2Screen() }
}
