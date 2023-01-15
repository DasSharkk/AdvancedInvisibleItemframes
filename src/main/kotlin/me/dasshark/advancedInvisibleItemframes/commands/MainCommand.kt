package me.dasshark.advancedInvisibleItemframes.commands

import me.dasshark.advancedInvisibleItemframes.Main
import me.dasshark.advancedInvisibleItemframes.utils.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class MainCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sendHelp(sender)
            return false
        } else if (args.size == 1) {
            if (args[0] == "help") {
                sendHelp(sender)
                return true
            } else if (args[0] == "recipe") {
                if (sender !is Player) {
                    sender.sendMessage("${Main.prefix}§cYou need to be a player to execute this command")
                    return false
                }
                val inv = Bukkit.createInventory(null, 27, "§7Change Recipe")
                val bedrockSlots = listOf(0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20, 24, 25, 26)
                for (i in bedrockSlots) {
                    inv.setItem(i, ItemBuilder(Material.BEDROCK).setName("§7").setLocalizedName("cancel").itemStack)
                }
                inv.setItem(3, getItemStackByRecipe(1, 0))
                inv.setItem(4, getItemStackByRecipe(1, 1))
                inv.setItem(5, getItemStackByRecipe(1, 2))
                inv.setItem(12, getItemStackByRecipe(2, 0))
                inv.setItem(13, getItemStackByRecipe(2, 1))
                inv.setItem(14, getItemStackByRecipe(2, 2))
                inv.setItem(21, getItemStackByRecipe(3, 0))
                inv.setItem(22, getItemStackByRecipe(3, 1))
                inv.setItem(23, getItemStackByRecipe(3, 2))
                sender.openInventory(inv)
                return true
            } else if (args[0] == "get") {
                if (sender !is Player) {
                    sender.sendMessage("${Main.prefix}§cYou need to be a player to execute this command")
                    return false
                }
                sender.inventory.addItem(Main.instance.getInvisFrame()!!)
                sender.sendMessage("${Main.prefix}§aYou have received an invisible item frame")
                return true
            } else if (args[0] == "reload") {
                Main.instance.unregisterRecipe()
                Main.instance.server.pluginManager.getPlugin("AdvancedInvisibleItemframes")?.reloadConfig()
                Main.instance.registerRecipe()
                sender.sendMessage("${Main.prefix}§aConfig and Recipe reloaded")
                return true
            } else {
                sendHelp(sender)
                return false
            }
        } else if (args.size == 2) {
            if (args[0] == "amount") {
                try {
                    Main.instance.config.set("crafting.amount", Integer.parseInt(args[1]))
                    Main.instance.config.save("plugins/AdvancedInvisibleItemframes/config.yml")
                    sender.sendMessage(Main.prefix + "§aAmount changed!")
                    sender.sendMessage(Main.prefix + "§aPlease reload the plugin to apply the changes.")
                } catch (e: NumberFormatException) {
                    sender.sendMessage("${Main.prefix}§cPlease enter a valid number")
                }
                return true
            } else {
                sendHelp(sender)
                return false
            }
        } else {
            sendHelp(sender)
            return false
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            return listOf("help", "recipe", "get", "reload", "amount").filter { it.startsWith(args[0]) }
        }
        return listOf()
    }

    private fun sendHelp(sender: CommandSender) {
        sender.sendMessage("${Main.prefix}§e/aiif help §8- §7Displays this menu")
        sender.sendMessage("${Main.prefix}§e/aiif recipe §8- §7Opens an inventory to change the crafting recipe")
        sender.sendMessage("${Main.prefix}§e/aiif amount <amount> §8- §7Changes how many items the player gets when he crafts the invisible Itemframe(s)")
        sender.sendMessage("${Main.prefix}§e/aiif get §8- §7Gives you an invisible Itemframe")
        sender.sendMessage("${Main.prefix}§e/aiif reload §8- §7Reloads the config")
    }

    private fun getItemStackByRecipe(line: Int, index: Int): ItemStack {
        val shapeLine = Main.instance.config.getString("crafting.shape.$line") ?: return ItemStack(Material.AIR)
        val materialChar = shapeLine[index]
        var ingredient = Material.AIR
        for (s: String in Main.instance.config.getStringList("recipe.ingredients")) {
            val split = s.split(":")
            if (split[0] == materialChar.toString()) {
                ingredient = Material.getMaterial(split[1])!!
            }
        }
        return ItemStack(ingredient)
    }
}