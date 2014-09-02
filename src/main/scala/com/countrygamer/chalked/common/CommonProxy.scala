package com.countrygamer.chalked.common

import com.countrygamer.cgo.wrapper.common.ProxyWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class CommonProxy() extends ProxyWrapper {

	override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		null
	}

	override def registerRender(): Unit = {
		// NOOP
	}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		// NOOP
		null
	}

}
