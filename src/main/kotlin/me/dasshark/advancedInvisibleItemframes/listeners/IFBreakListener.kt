package me.dasshark.advancedInvisibleItemframes.listeners

import me.dasshark.advancedInvisibleItemframes.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.persistence.PersistentDataType


class IFBreakListener : Listener {

    @EventHandler
     fun onHangingBreak(event: HangingBreakEvent) {
         if (event.entity.persistentDataContainer.has(Main.invisibleKey, PersistentDataType.BYTE)) {
             event.isCancelled = true
             event.entity.remove()
             event.entity.world.dropItemNaturally(event.entity.location, Main.instance.getInvisFrame(false)!!)
         }
     }

}