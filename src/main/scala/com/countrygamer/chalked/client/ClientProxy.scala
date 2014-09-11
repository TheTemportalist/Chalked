package com.countrygamer.chalked.client

import com.countrygamer.chalked.client.gui.GuiChalkDust
import com.countrygamer.chalked.client.render.{BlockCamouflageRenderer, BlockChalkDustRenderer}
import com.countrygamer.chalked.common.tile.TEChalkDust
import com.countrygamer.chalked.common.{Chalked, CommonProxy}
import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ClientProxy() extends CommonProxy() {

	override def registerRender(): Unit = {
		RenderingRegistry.registerBlockHandler(BlockChalkDustRenderer)
		RenderingRegistry.registerBlockHandler(BlockCamouflageRenderer)

	}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		val tile: TileEntity = world.getTileEntity(x, y, z)
		if (ID == Chalked.guiChalkDust && tile.isInstanceOf[TEChalkDust]) {
			return new GuiChalkDust(tile.asInstanceOf[TEChalkDust])
		}
		null
	}

}
