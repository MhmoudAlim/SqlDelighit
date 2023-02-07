package mahmoud.alim.sqldelighjt.data.datasrc

import demo.exampledb.ExampleEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Mahmoud Alim on 28/01/2023.
 */

interface ExampleDataSource {

    suspend fun insertEntity(title: String, description: String, id: Long)

    fun getAllEntities(): Flow<List<ExampleEntity>>

    suspend fun getEntityByName(name: String): ExampleEntity?

    suspend fun deleteEntityById(id: Long)

}