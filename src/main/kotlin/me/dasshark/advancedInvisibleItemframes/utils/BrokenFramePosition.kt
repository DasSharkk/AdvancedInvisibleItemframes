package me.dasshark.advancedInvisibleItemframes.utils

import org.bukkit.Location
import org.bukkit.entity.Item
import org.bukkit.scheduler.BukkitTask
import org.bukkit.util.BoundingBox


class BrokenFramePosition(location: Location?) {
    private val box: BoundingBox
    var removal: BukkitTask? = null

    init {
        box = BoundingBox.of(location!!, 1.0, 1.0, 1.0)
    }

    fun isFrame(item: Item): Boolean {
        return box.contains(item.boundingBox)
    }
}