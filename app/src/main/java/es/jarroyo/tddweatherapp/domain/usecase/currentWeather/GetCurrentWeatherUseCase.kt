package es.jarroyo.tddweatherapp.domain.usecase.currentWeather

import es.jarroyo.tddweatherapp.data.exception.NetworkConnectionException
import es.jarroyo.tddweatherapp.data.repository.WeatherRepository
import es.jarroyo.tddweatherapp.domain.model.Response
import es.jarroyo.tddweatherapp.domain.model.forecast.CurrentWeather
import es.jarroyo.tddweatherapp.domain.usecase.base.BaseUseCase

open class GetCurrentWeatherUseCase(val repository: WeatherRepository) : BaseUseCase<GetCurrentWeatherRequest, CurrentWeather>() {

    override suspend fun run(): Response<CurrentWeather> {
        /*try {
            return Response(repository.getGitHubContributors(request!!))
        } catch (e: NetworkConnectionException) {
            return Response(error = "NetworkConnectionException", exception = e)
        } catch (e: HttpException) {
            return Response(error = "HttpException", exception = e)
        }*/
        return Response(error = "HttpException", exception = NetworkConnectionException())
    }
}