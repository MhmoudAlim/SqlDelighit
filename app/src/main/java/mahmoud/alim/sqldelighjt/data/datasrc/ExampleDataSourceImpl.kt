package mahmoud.alim.sqldelighjt.data.datasrc


import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import demo.exampledb.ExampleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import mahmoud.alim.sqldelighit.ExampleDataBase

/**
 * @author Mahmoud Alim on 28/01/2023.
 */

class ExampleDataSourceImpl(db: ExampleDataBase) : ExampleDataSource {

    private val queries = db.exampleEntityQueries

    override suspend fun insertEntity(title: String, description: String, id: Long) {
        withContext(Dispatchers.IO){
            queries.insertEntity(id = id , title = title , description = description)
        }
    }

    override fun getAllEntities(): Flow<List<ExampleEntity>> {
        return queries.getAllEntities().asFlow().mapToList()
    }

    override suspend fun getEntityByName(name: String): ExampleEntity? {
        return withContext(Dispatchers.IO) {
            queries.getEntityByName(name).executeAsOneOrNull()
        }
    }

    override suspend fun deleteEntityById(id: Long) {
        return withContext(Dispatchers.IO) {
            queries.deleteEntityById(id)
        }
    }


}