import com.grela.data.datasource.DoNotCookLocalDataSourceContract
import com.grela.domain.DataResult
import com.grela.domain.model.ProfileGeneralModel

class DoNotCookLocalDataSourceImplementation : DoNotCookLocalDataSourceContract {
    companion object {
        const val PROFILE = "profile"
    }

    override fun saveProfile(profile: ProfileGeneralModel) {
        FileManager.saveToFile(profile, PROFILE)
    }

    override fun getProfile(): DataResult<Error, ProfileGeneralModel> {
        return try {
            DataResult.Success(FileManager.readFromFile(PROFILE, ProfileGeneralModel::class.java))
        } catch (e: Exception) {
            DataResult.Error(Error())
        }
    }

    override fun deleteProfile() {
        FileManager.deleteFile(PROFILE)
    }
}