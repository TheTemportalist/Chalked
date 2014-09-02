package com.countrygamer.chalked.common.init

import com.countrygamer.cgo.common.Origin
import com.countrygamer.cgo.wrapper.common.registries.ItemRegister
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.item.ItemChalk
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}

/**
 *
 *
 * @author CountryGamer
 */
object CItems extends ItemRegister {

	var chalk: Item = null

	override def register(): Unit = {

		CItems.chalk = new ItemChalk(Chalked.pluginID, "Chalk")
		Origin.addItemToTab(CItems.chalk)

	}

	override def registerItemsPostBlock(): Unit = {

	}

	override def registerCrafting(): Unit = {

		GameRegistry.addShapelessRecipe(
			new ItemStack(CItems.chalk, 1, 0),
			new ItemStack(Items.paper, 1, 0),
			new ItemStack(Items.clay_ball, 1, 0)
		)

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
