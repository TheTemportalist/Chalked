package com.temportalist.chalked.client

import java.util

import com.temportalist.chalked.client.gui.GuiChalkDust
import com.temportalist.chalked.client.render.BlockChalkDustRenderer
import com.temportalist.chalked.common.tile.TEChalkDust
import com.temportalist.chalked.common.{Chalked, CommonProxy}
import cpw.mods.fml.client.IModGuiFactory
import cpw.mods.fml.client.IModGuiFactory.{RuntimeOptionCategoryElement, RuntimeOptionGuiHandler}
import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 *
 *
 * @author TheTemportalist
 */
class ClientProxy() extends CommonProxy with IModGuiFactory {

	override def registerRender(): Unit = {
		RenderingRegistry.registerBlockHandler(BlockChalkDustRenderer)

	}

	override def getClientElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, tileEntity: TileEntity): AnyRef = {
		if (ID == Chalked.guiChalkDust && tileEntity.isInstanceOf[TEChalkDust]) {
			return new GuiChalkDust(tileEntity.asInstanceOf[TEChalkDust])
		}
		null
	}

	override def getHandlerFor(element: RuntimeOptionCategoryElement): RuntimeOptionGuiHandler = {
		null
	}

	override def runtimeGuiCategories(): util.Set[RuntimeOptionCategoryElement] = {
		null
	}

	override def mainConfigGuiClass(): Class[_ <: GuiScreen] = {
		null
	}

	override def initialize(minecraftInstance: Minecraft): Unit = {
	}

}
