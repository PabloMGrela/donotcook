import android.content.Context
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.inject

object FileManager : KoinComponent {

    private val gson = Gson()

    fun <T> saveToFile(objectToSave: T, fileName: String) {
        val context by inject<Context>()
        val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        val jsonObject = gson.toJson(objectToSave)
        fos.write(jsonObject.toByteArray())
        fos.close()
    }

    fun <T> readFromFile(fileName: String, clazz: Class<T>): T {
        val context by inject<Context>()
        val fis = context.openFileInput(fileName)
        val inputText = String(fis.readBytes())
        fis.close()
        return gson.fromJson(inputText, clazz)
    }

    fun deleteFile(fileName: String) {
        val context by inject<Context>()
        val file = context.getFileStreamPath(fileName)
        file.delete()
    }

}