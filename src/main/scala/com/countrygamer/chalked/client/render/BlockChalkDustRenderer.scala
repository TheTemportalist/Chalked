package com.countrygamer.chalked.client.render

import com.countrygamer.cgo.wrapper.client.render.SBRHWrapper
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.util.IIcon
import net.minecraft.world.IBlockAccess

/**
 *
 *
 * @author CountryGamer
 */
object BlockChalkDustRenderer extends SBRHWrapper(5000) {

	override def shouldRender3DInInventory(modelId: Int): Boolean = {
		false
	}

	override def render(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block,
			modelId: Int, renderer: RenderBlocks): Boolean = {
		val tessellator: Tessellator = Tessellator.instance
		//val l: Int = world.getBlockMetadata(x, y, z)
		val iicon: IIcon = block.getIcon(0, 1)
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))
		/*
		val f: Float = l.asInstanceOf[Float] / 15.0F
		var f1: Float = f * 0.6F + 0.4F
		if (l == 0) {
			f1 = 0.3F
		}
		var f2: Float = f * f * 0.7F - 0.5F
		var f3: Float = f * f * 0.6F - 0.7F
		if (f2 < 0.0F) {
			f2 = 0.0F
		}
		if (f3 < 0.0F) {
			f3 = 0.0F
		}
		*/
		val l: Int = block.colorMultiplier(world, x, y, z)
		val f1: Float = (l >> 16 & 255).asInstanceOf[Float] / 255.0F
		val f2: Float = (l >> 8 & 255).asInstanceOf[Float] / 255.0F
		val f3: Float = (l & 255).asInstanceOf[Float] / 255.0F
		tessellator.setColorOpaque_F(f1, f2, f3)
		val f4: Float = (x + 0).asInstanceOf[Float]
		val f5: Float = (x + 1).asInstanceOf[Float]
		val f6: Float = (z + 0).asInstanceOf[Float]
		val f7: Float = (z + 1).asInstanceOf[Float]
		val l1: Int = 16
		val i2: Int = 16
		val k1: Int = 0
		val j1: Int = 0
		tessellator.addVertexWithUV(f5.asInstanceOf[Double],
			y.asInstanceOf[Double] + 0.015625D, f7.asInstanceOf[Double],
			iicon.getInterpolatedU(l1.asInstanceOf[Double]).asInstanceOf[Double],
			iicon.getInterpolatedV(i2.asInstanceOf[Double]).asInstanceOf[Double])
		tessellator.addVertexWithUV(f5.asInstanceOf[Double],
			y.asInstanceOf[Double] + 0.015625D, f6.asInstanceOf[Double],
			iicon.getInterpolatedU(l1.asInstanceOf[Double]).asInstanceOf[Double],
			iicon.getInterpolatedV(k1.asInstanceOf[Double]).asInstanceOf[Double])
		tessellator.addVertexWithUV(f4.asInstanceOf[Double],
			y.asInstanceOf[Double] + 0.015625D, f6.asInstanceOf[Double],
			iicon.getInterpolatedU(j1.asInstanceOf[Double]).asInstanceOf[Double],
			iicon.getInterpolatedV(k1.asInstanceOf[Double]).asInstanceOf[Double])
		tessellator.addVertexWithUV(f4.asInstanceOf[Double],
			y.asInstanceOf[Double] + 0.015625D, f7.asInstanceOf[Double],
			iicon.getInterpolatedU(j1.asInstanceOf[Double]).asInstanceOf[Double],
			iicon.getInterpolatedV(i2.asInstanceOf[Double]).asInstanceOf[Double])
		true
	}

}
