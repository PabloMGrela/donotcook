package com.grela.clean

import com.grela.clean.DataGenerator.givenASportRepository
import com.grela.domain.interactor.CoroutineContextProvider
import com.grela.domain.interactor.GetRestaurantsUseCase
import com.grela.domain.interactor.UseCaseInvoker
import kotlinx.coroutines.Dispatchers

object DomainGenerator {

    fun givenAGetCountryUseCase(isSuccess: Boolean) =
        GetRestaurantsUseCase(givenASportRepository(if (isSuccess) DataGenerator.RepositoryStatus.SUCCESS else DataGenerator.RepositoryStatus.ERROR))
}

class TestContextProvider : CoroutineContextProvider() {
    override val main = Dispatchers.Unconfined

    override val background = Dispatchers.Unconfined
}

object InvokerInstruments {
    fun givenAnInvoker() = UseCaseInvoker(TestContextProvider())
}