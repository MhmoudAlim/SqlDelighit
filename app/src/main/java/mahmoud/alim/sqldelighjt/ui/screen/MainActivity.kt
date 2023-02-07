package mahmoud.alim.sqldelighjt.ui.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import mahmoud.alim.sqldelighjt.ui.theme.SqlDelighitTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: EntitiesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SqlDelighitTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextFeilds()
                    CTAS()
                    Spacer(modifier = Modifier.height(16.dp))
                    EntitiesList()
                }
            }
        }
    }

    @Composable
    private fun TextFeilds() {
        OutlinedTextField(
            value = vm.title.value,
            onValueChange = { vm.title.value = it },
            label = { Text(text = "Title") })
        OutlinedTextField(
            value = vm.disc.value,
            label = { Text(text = "Description") },
            onValueChange = { vm.disc.value = it })
    }

    @Composable
    private fun CTAS() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { vm.insertEntity(vm.title.value, vm.disc.value) }) {
                Text(text = "Add To DB")
            }
            Button(onClick = { onSearchClick() }) {
                Text(text = "Find Entity by title")
            }
            val icon = when (vm.searchedEntity.collectAsState().value) {
                EntitiesViewModel.SearchState.Idle -> null
                EntitiesViewModel.SearchState.Failed -> Icons.Default.Warning
                is EntitiesViewModel.SearchState.Success -> Icons.Default.Done
            }

            icon?.let {
                Icon(
                    it,
                    null,
                    tint = if (vm.searchedEntity.collectAsState().value == EntitiesViewModel.SearchState.Failed) Color.Red else Color.Green
                )
            }
        }
    }

    private fun onSearchClick() {
        if (vm.title.value.isBlank()) {
            Toast.makeText(this, "please enter a value", Toast.LENGTH_SHORT).show()
        } else {
            vm.getEntityByName(vm.title.value)
        }

    }

    @Composable
    private fun EntitiesList() {
        val list = vm.getAllEntities().collectAsState(emptyList()).value
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(list) {
                Text(text = it.title, Modifier.padding(8.dp))
            }
        }
    }
}


