package es.jarroyo.tddweatherapp.data.repository

import es.jarroyo.tddweatherapp.data.entity.UserEntity
import es.jarroyo.tddweatherapp.data.source.disk.DiskDataSource
import es.jarroyo.tddweatherapp.data.source.network.INetworkDataSource

class UserRepository(
    private val networkDataSource: INetworkDataSource,
    private val diskDataSource: DiskDataSource
) {

    val TAG = UserRepository::class.java.simpleName

    /***********************************************************************************************
     * SAVE USER
     **********************************************************************************************/
    suspend fun saveUser() {

    }

    /***********************************************************************************************
     * GET CURRENT USER FROM LOCAL
     **********************************************************************************************/
    fun getCurrentUserLocal(): UserEntity? {
        val user = diskDataSource.getUser()

        if (user == null) {
            val invitedUser = UserEntity(0, "Invitado")
            val id = diskDataSource.insertUser(invitedUser)
            invitedUser.id = id!!
            return  invitedUser
        }

        return user
    }
}