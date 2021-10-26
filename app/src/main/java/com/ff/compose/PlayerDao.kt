package com.ff.compose

import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    suspend fun getPlayers(): List<Player>

    // @Query("SELECT * FROM players WHERE weeknumber LIKE :weeknumber ORDER BY id ASC")
    @Query("SELECT * FROM players WHERE weeknumber LIKE :weeknumber")
    suspend fun getPlayersWeek(weeknumber: Int): List<Player>

    @Query("SELECT * FROM players WHERE name LIKE :name")
    fun getPlayer(name: String): Player

    @Query("SELECT COUNT(name) FROM players")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg todo: Player)

    @Delete
    fun delete(player: Player)

    @Query("DELETE FROM players")
    suspend fun deleteAll();

    @Update
    fun updatePlayer(vararg player: Player)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun populatePlayerUnknowns() {
    }


}