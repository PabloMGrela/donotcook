import com.grela.data.datasource.DoNotCookLocalDataSourceContract
import com.grela.domain.model.ProfileGeneralModel

class DoNotCookLocalDataSourceImplementation : DoNotCookLocalDataSourceContract {
    override fun saveProfile(profile: ProfileGeneralModel) {
        FileManager.saveToFile(profile, "profile")
    }

}