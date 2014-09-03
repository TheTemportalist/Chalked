package com.countrygamer.chalked.addon

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
object RayTracer {

	def retraceBlock(world: World, player: EntityPlayer, x: Integer, y: Integer,
			z: Integer): MovingObjectPosition = {
		try {
			EnderStorage.RayTracer.getMethod("retraceBlock",
				classOf[World], classOf[EntityPlayer], Integer.TYPE,
				Integer.TYPE, Integer.TYPE).invoke(null, world, player, x, y, z)
					.asInstanceOf[MovingObjectPosition]
		}
		catch {
			case e: Exception =>
				e.printStackTrace()
				null
		}
	}

}
