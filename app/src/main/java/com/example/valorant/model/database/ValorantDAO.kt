package com.example.valorant.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ValorantDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRol(categories : List<Rol>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgent(agent : Agent)


    @Query("SELECT * FROM rol" +
            " ORDER BY name")
    fun loadAllRoles(): List<Rol>

    @Query("SELECT id, rol, name, description, image, roledescription, abilities FROM agent" +
            " WHERE SUBSTR(name, 1, 1) == :initial" +
            " ORDER BY name")
    fun loadAllAgentsByInitial(initial : String): List<Agent>

    @Query("SELECT id, rol, agent.name, description, image, roledescription, abilities FROM agent" +
            " LEFT JOIN rol on agent.rol == rol.name" +
            " WHERE rol.name == :rol" +
            " ORDER BY agent.name")
    fun loadAllAgentsByRol(rol : String): List<Agent>
}