package com.kavira.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF111217)
private val Panel = Color(0xFF202125)
private val Panel2 = Color(0xFF292A2E)
private val TextPrimary = Color(0xFFF1F1F1)
private val TextSecondary = Color(0xFFA8A8AE)
private val Accent = Color(0xFF7FB5FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KaviraApp() }
    }
}

@Composable
fun KaviraApp() {
    var selected by remember { mutableStateOf("Home") }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Bg,
            surface = Panel,
            primary = Accent,
            onBackground = TextPrimary,
            onSurface = TextPrimary
        )
    ) {
        Row(Modifier.fillMaxSize().background(Bg)) {
            SideRail(selected) { selected = it }
            MainPanel(selected)
        }
    }
}

@Composable
private fun SideRail(selected: String, onSelect: (String) -> Unit) {
    val items = listOf(
        "Home" to Icons.Default.Home,
        "Discover" to Icons.Default.Explore,
        "Writers" to Icons.Default.People,
        "Publications" to Icons.Default.MenuBook,
        "Messages" to Icons.Default.Chat,
        "Bookmarks" to Icons.Default.Bookmark,
        "Notifications" to Icons.Default.Notifications,
        "Settings" to Icons.Default.Settings
    )

    NavigationRail(
        containerColor = Color(0xFF18191D),
        modifier = Modifier.width(82.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            "स",
            color = Accent,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 26.dp)
        )
        items.forEach { (label, icon) ->
            NavigationRailItem(
                selected = selected == label,
                onClick = { onSelect(label) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 9.sp) }
            )
        }
    }
}

@Composable
private fun MainPanel(page: String) {
    Column(Modifier.fillMaxSize()) {
        TopBar(page)
        when (page) {
            "Home" -> HomePage()
            "Discover" -> DiscoverPage()
            "Writers" -> WritersPage()
            "Publications" -> PublicationsPage()
            "Messages" -> MessagesPage()
            "Bookmarks" -> BookmarksPage()
            "Notifications" -> NotificationsPage()
            else -> SettingsPage()
        }
    }
}

@Composable
private fun TopBar(title: String) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text("Kavira", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Text(title, color = TextSecondary, fontSize = 13.sp)
        }
        IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
        IconButton(onClick = {}) { Icon(Icons.Default.AccountCircle, "Profile") }
    }
}

@Composable
private fun HomePage() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text("आपके साहित्य का घर", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "कविताएँ, कहानियाँ और साहित्य पढ़ें, लिखें और अपने पाठकों तक पहुँचाएँ।",
                        color = TextSecondary,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(18.dp))
                    Button(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                        Icon(Icons.Default.Edit, null)
                        Spacer(Modifier.width(8.dp))
                        Text("नई रचना लिखें")
                    }
                }
            }
        }

        item { SectionTitle("आज की रचनाएँ") }

        items(samplePosts) { post ->
            WritingCard(post)
        }
    }
}

@Composable
private fun DiscoverPage() {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { SectionTitle("खोजें") }
        item {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("कविता, लेखक, कहानी या विषय खोजें") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )
        }
        item { SectionTitle("लोकप्रिय विषय") }
        items(listOf("प्रेम", "बारिश", "विरह", "माँ", "गाँव", "ज़िंदगी")) {
            AssistChip(onClick = {}, label = { Text(it) })
        }
    }
}

@Composable
private fun WritersPage() {
    SimpleListPage(
        title = "लोकप्रिय लेखक",
        entries = listOf("आदित्य करण", "अनामिका", "राघव मिश्रा", "काव्या", "नमन")
    )
}

@Composable
private fun PublicationsPage() {
    SimpleListPage(
        title = "साहित्यिक प्रकाशन",
        entries = listOf("साहित्य दर्पण", "नई कलम", "शब्दलोक", "कथा संसार")
    )
}

@Composable
private fun MessagesPage() {
    SimpleListPage(
        title = "संदेश",
        entries = listOf("अनामिका", "साहित्य दर्पण", "राघव मिश्रा")
    )
}

@Composable
private fun BookmarksPage() {
    SimpleListPage(
        title = "सहेजी गई रचनाएँ",
        entries = listOf("बारिश के बाद", "एक अधूरी कहानी")
    )
}

@Composable
private fun NotificationsPage() {
    SimpleListPage(
        title = "सूचनाएँ",
        entries = listOf("अनामिका ने आपकी रचना पसंद की", "साहित्य दर्पण ने आपको संदेश भेजा")
    )
}

@Composable
private fun SettingsPage() {
    SimpleListPage(
        title = "सेटिंग्स",
        entries = listOf("प्रोफ़ाइल", "निजता", "सूचनाएँ", "भाषा", "डार्क मोड", "खाता")
    )
}

@Composable
private fun SimpleListPage(title: String, entries: List<String>) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { SectionTitle(title) }
        items(entries) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().clickable {}
            ) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Circle, null, tint = Accent, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(14.dp))
                    Text(it, fontSize = 16.sp)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, null, tint = TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
}

data class Post(val title: String, val author: String, val type: String)

private val samplePosts = listOf(
    Post("बारिश के बाद", "आदित्य करण", "कविता"),
    Post("एक अधूरी कहानी", "अनामिका", "कहानी"),
    Post("शहर की शाम", "राघव मिश्रा", "कविता")
)

@Composable
private fun WritingCard(post: Post) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(post.type, color = Accent, fontSize = 12.sp)
            Spacer(Modifier.height(7.dp))
            Text(post.title, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(7.dp))
            Text(post.author, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                Icon(Icons.Default.FavoriteBorder, null, tint = TextSecondary)
                Icon(Icons.Default.Comment, null, tint = TextSecondary)
                Icon(Icons.Default.BookmarkBorder, null, tint = TextSecondary)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Default.Share, null, tint = TextSecondary)
            }
        }
    }
}
