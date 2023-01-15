package me.dasshark.advancedInvisibleItemframes

import me.dasshark.advancedInvisibleItemframes.commands.MainCommand
import me.dasshark.advancedInvisibleItemframes.listeners.IFBreakListener
import me.dasshark.advancedInvisibleItemframes.listeners.IFPlaceListener
import me.dasshark.advancedInvisibleItemframes.listeners.InventoryListener
import me.dasshark.advancedInvisibleItemframes.utils.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {

        override fun onEnable() {
                instance = this
                saveDefaultConfig()
                invisibleKey = NamespacedKey(this, "invisible")

                getCommand("advancedinvisibleitemframes")?.setExecutor(MainCommand())
                getCommand("advancedinvisibleitemframes")?.tabCompleter = MainCommand()
                server.pluginManager.registerEvents(IFBreakListener(), this)
                server.pluginManager.registerEvents(IFPlaceListener(), this)
                server.pluginManager.registerEvents(InventoryListener(), this)

                registerRecipe()
        }

        override fun onDisable() {
                unregisterRecipe()
        }

        companion object {
                lateinit var invisibleKey: NamespacedKey
                lateinit var instance: Main
                const val prefix = "§8» §x§f§b§0§0§0§0A§x§f§c§3§8§0§0I§x§f§c§6§f§0§0I§x§f§d§a§7§0§0F §7"
        }

        fun registerRecipe() {
                if (!config.getBoolean("crafting.enabled")) return
                val item: ItemStack = getInvisFrame()!!
                // create a NamespacedKey for your recipe
                val key = NamespacedKey(this, "invisible_item_frame")

                // Create our custom recipe variable
                val recipe = ShapedRecipe(key, item)

                // Here we will set the places.
                recipe.shape(config.getStringList("crafting.shape")[0], config.getStringList("crafting.shape")[1], config.getStringList("crafting.shape")[2])

                // Set what the letters represent.
                for (s: String in config.getStringList("crafting.ingredients")) {
                        val split = s.split(":").toMutableList()
                        if (split[1] == "null") split[1] = "AIR"
                        try {
                                recipe.setIngredient(split[0].toCharArray()[0], Material.getMaterial(split[1])!!)
                        } catch (e: NullPointerException) {
                                Bukkit.getLogger().severe("Invalid material in config: ${split[1]}")
                                Bukkit.getLogger().severe("Please check your config.yml and correct the error or delete the entire file.")
                                server.pluginManager.disablePlugin(this)
                        }
                }

                // Add the recipe to the server
                Bukkit.addRecipe(recipe)
        }

        fun unregisterRecipe() {
                val key = NamespacedKey(this, "invisible_item_frame")
                if (Bukkit.getRecipe(key) != null)
                        Bukkit.removeRecipe(key)
        }

        fun getInvisFrame(): ItemStack? {
                if (config.getBoolean("invisible-itemframe.enchanted")) {
                        try {
                                return ItemBuilder(Material.ITEM_FRAME, config.getInt("crafting.amount")).setName(ChatColor.translateAlternateColorCodes('&', config.getString("invisible-itemframe.name")!!)).setInvisible().addEnchant(Enchantment.ARROW_DAMAGE, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).itemStack
                        } catch (e: NullPointerException) {
                                Bukkit.getLogger().warning("The item name or amount is not set in the config.yml!")
                                Bukkit.getLogger().severe("Please check your config.yml and correct the error or delete the entire file.")
                                server.pluginManager.disablePlugin(this)
                                return null
                        }
                } else {
                        try {
                                return ItemBuilder(Material.ITEM_FRAME, config.getInt("crafting.amount")).setName(ChatColor.translateAlternateColorCodes('&', config.getString("invisible-itemframe.name")!!)).setInvisible().itemStack
                        } catch (e: NullPointerException) {
                                Bukkit.getLogger().warning("The item name or amount is not set in the config.yml!")
                                Bukkit.getLogger().severe("Please check your config.yml and correct the error or delete the entire file.")
                                server.pluginManager.disablePlugin(this)
                                return null
                        }
                }
        }
}