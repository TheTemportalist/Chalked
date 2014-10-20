package com.temportalist.chalked.common.init

import com.temportalist.chalked.common.ChalkRecipe
import com.temportalist.chalked.common.item.{ItemChalk, ItemGoo}
import com.temportalist.origin.library.common.Origin
import com.temportalist.origin.library.common.register.ItemRegister
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Items
import net.minecraft.item.{Item, ItemStack}

/**
 *
 *
 * @author TheTemportalist
 */
object CItems extends ItemRegister {

	var chalk: Item = null
	var coloredGoo: Item = null

	override def register(): Unit = {

		CItems.chalk = new ItemChalk("Chalk")
		Origin.addItemToTab(CItems.chalk)
		CItems.coloredGoo = new ItemGoo("Colored Goo")
		Origin.addItemToTab(CItems.coloredGoo)

	}

	override def registerItemsPostBlock(): Unit = {

	}

	override def registerCrafting(): Unit = {

		GameRegistry.addShapelessRecipe(
			new ItemStack(CItems.chalk, 1, 0),
			new ItemStack(Items.paper, 1, 0),
			new ItemStack(Items.clay_ball, 1, 0)
		)

		GameRegistry.addRecipe(new ChalkRecipe(
			new ItemStack(CItems.coloredGoo, 1, 0),
			new ItemStack(CItems.chalk, 1, 0),
			new ItemStack(Items.clay_ball, 1, 0),
			new ItemStack(Items.water_bucket, 1, 0)
		))

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
