package es.jarroyo.tddweatherapp.domain.usecase.base

import es.jarroyo.tddweatherapp.domain.model.Response

abstract class BaseUseCase<R : BaseRequest, T>() {

    var request: R? = null

    suspend fun execute(request: R? = null): Response<T> {
        this.request = request

        val validated = request?.validate() ?: true
        if (validated) return run()
        return Response(data = null, error = "Error validating request data")
    }

    abstract suspend fun run(): Response<T>
}
