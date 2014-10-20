package com.temportalist.chalked.common.init

import com.temportalist.chalked.common.Chalked
import com.temportalist.chalked.common.block.BlockChalkDust
import com.temportalist.chalked.common.tile.{TEChalkDust, TEColored}
import com.temportalist.origin.library.client.render.BlockCamouflageRender
import com.temportalist.origin.library.common.register.BlockRegister
import com.temportalist.origin.wrapper.common.block.BlockWrapperTE
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.{MapColor, Material, MaterialLogic}
import net.minecraft.world.IBlockAccess

/**
 *
 *
 * @author TheTemportalist
 */
object CBlocks extends BlockRegister {

	var chalkDust: Block = null
	var coloredBlock: Block = null

	override def registerTileEntities(): Unit = {
		GameRegistry
				.registerTileEntity(classOf[TEChalkDust], "ChalkDust")
		GameRegistry
				.registerTileEntity(classOf[TEColored], "ColoredTE")

	}

	override def register(): Unit = {

		val mat: Material = //Material.circuits
			new MaterialLogic(MapColor.airColor) {
				override def blocksMovement(): Boolean = {
					true
				}
			}
		CBlocks.chalkDust = new BlockChalkDust(mat, Chalked.pluginID, "Chalk Dust")
		CBlocks.coloredBlock = new
						BlockWrapperTE(Material.ground, Chalked.pluginID, "Smeared Block",
							classOf[TEColored]) {
			override def getRenderType: Int = {
				BlockCamouflageRender.getRenderId
			}

			override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
				world.getTileEntity(x, y, z).asInstanceOf[TEColored].getColor()
			}

			override def hasTileEntityDrops(metadata: Int): Boolean = {
				true
			}
		}

	}

	override def registerCrafting(): Unit = {

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
