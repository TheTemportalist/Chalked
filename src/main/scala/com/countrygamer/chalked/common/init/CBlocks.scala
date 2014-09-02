package com.countrygamer.chalked.common.init

import com.countrygamer.cgo.wrapper.common.registries.BlockRegister
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.block.BlockChalkDust
import com.countrygamer.chalked.common.tile.TileEntityChalkDust
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material

/**
 *
 *
 * @author CountryGamer
 */
object CBlocks extends BlockRegister {

	var chalkDust: Block = null

	override def registerTileEntities(): Unit = {
		GameRegistry
				.registerTileEntity(classOf[TileEntityChalkDust], Chalked.pluginID + "_ChalkDust")

	}

	override def register(): Unit = {

		CBlocks.chalkDust = new BlockChalkDust(Material.circuits, Chalked.pluginID, "Chalk Dust")

	}

	override def registerCrafting(): Unit = {

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
