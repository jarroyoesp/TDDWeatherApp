package es.jarroyo.tddweatherapp.data.source.disk.dao

import android.arch.persistence.room.*
import es.jarroyo.tddweatherapp.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity): Long

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("DELETE FROM USER_TABLE")
    fun deleteAll()

    @Query("SELECT * FROM USER_TABLE")
    fun getAll():List<UserEntity>

    @Query("SELECT * FROM USER_TABLE LIMIT 1")
    fun getFirst():UserEntity
}