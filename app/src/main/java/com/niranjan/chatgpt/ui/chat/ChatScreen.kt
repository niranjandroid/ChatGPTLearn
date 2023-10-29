import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.niranjan.chatgpt.data.remote.Message
import com.niranjan.chatgpt.di.ChatGPTModule
import com.niranjan.chatgpt.ui.chat.ChatViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var viewModel = ChatViewModel(ChatGPTModule().getChatGptApi()) // DI
    var text by remember { mutableStateOf("") }
    val messages by viewModel.messages.collectAsStateWithLifecycle()

    Box {
        TopAppBar(title = { Text(text = "ChatGPT4") })
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                ComposeBox(text,
                    onValueChange = {
                        text = it
                    },
                    onClick = {
                        if (text.isNotBlank()) {
                            viewModel.requestGpt(text)
                            text = ""
                        }
                    })
            }) { paddingValues -> MessageHistory(paddingValues, messages)
        }
    }
}

@Composable
fun MessageHistory(paddingValues: PaddingValues, messages: List<Message>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues), horizontalAlignment = Alignment.Start, reverseLayout = true) {
        items(messages.reversed()) { message ->
            MessageBubble(
                message = message,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeBox(text: String, onValueChange: (String) -> Unit, onClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            singleLine = true,
            placeholder = {
                Text("Type a message...")
            }
        )

        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    val bubbleColor = if (message.isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onTertiaryContainer
    val alignment = if(message.isUser) Alignment.BottomEnd else Alignment.BottomStart

    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .widthIn(0.dp, 300.dp)
                    .background(shape = RoundedCornerShape(8.dp), color = bubbleColor)
                    .border(1.dp, bubbleColor, shape = MaterialTheme.shapes.small)
                    .padding(8.dp)
                    .align(alignment)
            )
        }
    }
}