import com.grela.data.datasource.DoNotCookLocalDataSourceContract
import org.koin.dsl.module

object LocalModules {
    val modules = module {
        factory<DoNotCookLocalDataSourceContract> { DoNotCookLocalDataSourceImplementation() }
    }
}