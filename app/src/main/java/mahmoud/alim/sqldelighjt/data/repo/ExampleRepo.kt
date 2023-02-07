package mahmoud.alim.sqldelighjt.data.repo

import demo.exampledb.ExampleEntity
import kotlinx.coroutines.flow.Flow
import mahmoud.alim.sqldelighjt.data.datasrc.ExampleDataSource
import javax.inject.Inject

/**
 * @author Mahmoud Alim on 02/02/2023.
 */
class ExampleRepo @Inject constructor(
    private val dataSource: ExampleDataSource) {

     suspend fun insertEntity(title: String, description: String, id: Long) {
         dataSource.insertEntity(title,description,id)
    }

     fun getAllEntities(): Flow<List<ExampleEntity>> {
        return dataSource.getAllEntities()
    }

     suspend fun getEntityByName(name: String): ExampleEntity? {
        return dataSource.getEntityByName(name)

    }

     suspend fun deleteEntityById(id: Long) {
         dataSource.deleteEntityById(id)
        }

}