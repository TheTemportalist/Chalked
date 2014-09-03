package com.countrygamer.chalked.common.init

import com.countrygamer.cgo.wrapper.common.registries.BlockRegister
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.block.BlockChalkDust
import com.countrygamer.chalked.common.tile.TEChalkDust
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.{MapColor, Material, MaterialLogic}

/**
 *
 *
 * @author CountryGamer
 */
object CBlocks extends BlockRegister {

	var chalkDust: Block = null

	override def registerTileEntities(): Unit = {
		GameRegistry
				.registerTileEntity(classOf[TEChalkDust], "ChalkDust")

	}

	override def register(): Unit = {

		val mat: Material = //Material.circuits
			new MaterialLogic(MapColor.airColor) {
				override def blocksMovement(): Boolean = {
					true
				}
			}
		CBlocks.chalkDust = new BlockChalkDust(mat, Chalked.pluginID, "Chalk Dust")

	}

	override def registerCrafting(): Unit = {

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
