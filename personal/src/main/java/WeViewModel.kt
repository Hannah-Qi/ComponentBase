import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wecompose.ui.theme.WeComposeTheme

class WeViewModel: ViewModel() {
    var theme by mutableStateOf(WeComposeTheme.Theme.Light)
}