package mahmoud.alim.sqldelighjt.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import demo.exampledb.ExampleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mahmoud.alim.sqldelighjt.data.repo.ExampleRepo
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Mahmoud Alim on 02/02/2023.
 */

@HiltViewModel
class EntitiesViewModel @Inject constructor(
    private val repo: ExampleRepo
) : ViewModel() {

    sealed class SearchState() {
        object Idle : SearchState()
        object Failed : SearchState()
        data class Success(val result: String) : SearchState()
    }

    private var _searchedEntity = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchedEntity: StateFlow<SearchState> = _searchedEntity

    val title = mutableStateOf("")
    val disc = mutableStateOf("")

    fun getAllEntities() = repo.getAllEntities()

    fun insertEntity(
        title: String,
        description: String,
        id: Long = Random.nextLong()
    ) {
        if (title.trim().isBlank()) return
        if (description.trim().isBlank()) return
        viewModelScope.launch {
            repo.insertEntity(title, description, id)
        }

    }

    fun deleteEntity(id: Long) {
        viewModelScope.launch {
            repo.deleteEntityById(id)
        }
    }


    fun getEntityByName(name: String) {
        viewModelScope.launch {
            repo.getEntityByName(name).let {
                if (it == null)
                    _searchedEntity.emit(SearchState.Failed)
                else
                    _searchedEntity.emit(SearchState.Success(it.title))
            }
        }
    }

}