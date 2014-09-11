package com.countrygamer.chalked.client.render

import com.countrygamer.chalked.common.tile.ICamouflage
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{EntityRenderer, RenderBlocks, Tessellator}
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11

/**
 *
 *
 * @author CountryGamer
 */
object BlockCamouflageRenderer extends ISimpleBlockRenderingHandler {

	override def getRenderId: Int = {
		5000
	}

	override def shouldRender3DInInventory(modelId: Int): Boolean = {
		true
	}

	override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int,
			renderer: RenderBlocks): Unit = {
		val tessellator: Tessellator = Tessellator.instance
		block.setBlockBoundsForItemRender
		renderer.setRenderBoundsFromBlock(block)
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F)
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F)
		//tessellator.startDrawingQuads

		tessellator.startDrawingQuads()
		tessellator.setNormal(0.0F, -1.0F, 0.0F)
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 0, metadata))
		tessellator.draw()

		/*
		if (flag && this.useInventoryTint) {
			k = block.getRenderColor(metadata)
			f2 = (float) (k >> 16 & 255) / 255.0F
			f3 = (float) (k >> 8 & 255) / 255.0F
			float f4 = (float) (k & 255) / 255.0F
			GL11.glColor4f(f2 * p_147800_3_, f3 * p_147800_3_, f4 * p_147800_3_, 1.0F)
		}
		*/

		tessellator.startDrawingQuads()
		tessellator.setNormal(0.0F, 1.0F, 0.0F)
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 1, metadata))
		tessellator.draw()

		/*
		if (flag && this.useInventoryTint) {
			GL11.glColor4f(p_147800_3_, p_147800_3_, p_147800_3_, 1.0F)
		}
		*/

		tessellator.startDrawingQuads()
		tessellator.setNormal(0.0F, 0.0F, -1.0F)
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 2, metadata))
		tessellator.draw()
		tessellator.startDrawingQuads()
		tessellator.setNormal(0.0F, 0.0F, 1.0F)
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 3, metadata))
		tessellator.draw()
		tessellator.startDrawingQuads()
		tessellator.setNormal(-1.0F, 0.0F, 0.0F)
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 4, metadata))
		tessellator.draw()
		tessellator.startDrawingQuads()
		tessellator.setNormal(1.0F, 0.0F, 0.0F)
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D,
			renderer.getBlockIconFromSideAndMetadata(block, 5, metadata))
		tessellator.draw()

		GL11.glTranslatef(0.5F, 0.5F, 0.5F)
	}

	override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block,
			modelId: Int, renderer: RenderBlocks): Boolean = {
		val tileEntity: TileEntity = world.getTileEntity(x, y, z)
		if (tileEntity != null) {
			tileEntity match {
				case camo: ICamouflage =>
					if (camo.hasCamouflage && camo.isCamouflaged) {
						val camoBlock: Block = camo.getCamouflage.getBlock
						if (camoBlock != null && camoBlock != Blocks.air) {

							val color: Int = block.colorMultiplier(world, x, y, z)
							var r: Float = (color >> 16 & 255).asInstanceOf[Float] / 255.0F
							var g: Float = (color >> 8 & 255).asInstanceOf[Float] / 255.0F
							var b: Float = (color & 255).asInstanceOf[Float] / 255.0F

							if (EntityRenderer.anaglyphEnable) {
								val altR: Float = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F
								val altG: Float = (r * 30.0F + g * 70.0F) / 100.0F
								val altB: Float = (r * 30.0F + b * 70.0F) / 100.0F
								r = altR
								g = altG
								b = altB
							}

							if (Minecraft.isAmbientOcclusionEnabled && block.getLightValue == 0) {
								if (renderer.partialRenderBounds) {
									renderer.renderStandardBlockWithAmbientOcclusionPartial(
										camoBlock, x, y, z, r, g, b
									)
								}
								else {
									renderer.renderStandardBlockWithAmbientOcclusion(
										camoBlock, x, y, z, r, g, b
									)
								}
							}
							else {
								renderer.renderStandardBlockWithColorMultiplier(
									camoBlock, x, y, z, r, g, b
								)
							}

							true
						}
					}
				case _ =>
			}
		}
		false
	}

}
