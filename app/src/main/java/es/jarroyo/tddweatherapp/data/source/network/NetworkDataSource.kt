package es.jarroyo.tddweatherapp.data.source.network

import com.microhealth.lmc.utils.NetworkSystemAbstract
import es.jarroyo.tddweatherapp.domain.model.currentWeather.CurrentWeather


class NetworkDataSource(private val networkSystem: NetworkSystemAbstract) : INetworkDataSource(networkSystem) {

    /**
     * GET CURRENT WEATHER
     */
    override suspend fun getCurrentWeather(): CurrentWeather? {

        /*if (networkSystem.isNetworkAvailable()) {
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://api.github.com")
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(CoroutineCallAdapterFactory())
            }.build()

            val github = retrofit.create(GithubAPI::class.java)
            val repositories =
                github.listRepos(request.username)
                    .await().take(10)
            return repositories
        } else {
            throw NetworkConnectionException()
        }*/
        // return github.users().await()

        return null
    }

}