package com.countrygamer.chalked.client.gui

import com.countrygamer.cgo.library.common.nethandler.PacketHandler
import com.countrygamer.cgo.wrapper.client.gui.GuiScreenWrapper
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.lib.{Hex, HexHelper}
import com.countrygamer.chalked.common.network.PacketSaveColors
import com.countrygamer.chalked.common.tile.TEChalkDust
import cpw.mods.fml.client.config.GuiSlider
import net.minecraft.client.gui.GuiButton
import net.minecraft.util.EnumChatFormatting

/**
 *
 *
 * @author CountryGamer
 */
class GuiChalkDust(private val tile: TEChalkDust) extends GuiScreenWrapper() {

	private val sliders: Array[GuiSlider] = new Array[GuiSlider](3)

	override def initGui(): Unit = {
		super.initGui()

		val x: Int = this.getGuiLeft() + this.getXSize() / 2 - 200
		val yBase: Int = (this.getYSize() * 0.75).toInt

		for (i <- 0 until 3) {
			this.sliders(i) = new GuiSlider(i, x, yBase + (i * 25),
				250, 20,
				"" + EnumChatFormatting.RED,
				"", 0, HexHelper.cap(this.tile.getInputColors()(i), 255),
				this.tile.getOutputColors()(i), false, true
			)
			this.buttonList.asInstanceOf[java.util.List[Any]].add(this.sliders(i))
		}

	}

	override def actionPerformed(button: GuiButton): Unit = {
		if (button.id == this.sliders(0).id || button.id == this.sliders(1).id ||
				button.id == this.sliders(2).id) {
			this.saveSliders()
		}
	}

	override def onGuiClosed(): Unit = {
		super.onGuiClosed()
		this.saveSliders()
	}

	def saveSliders(): Unit = {
		val colors: Array[Int] = Array[Int](
			this.sliders(0).getValueInt,
			this.sliders(1).getValueInt,
			this.sliders(2).getValueInt
		)

		this.tile.setOutputColors(colors)
		val packet: PacketSaveColors = new
						PacketSaveColors(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord,
							colors)
		PacketHandler.sendToServer(Chalked.pluginID, packet)

	}

	override def drawScreen(mouseX: Int, mouseY: Int, renderPartialTicks: Float): Unit = {
		super.drawScreen(mouseX, mouseY, renderPartialTicks)
		this.saveSliders()
	}

	override protected def drawGuiBackgroundLayer(mouseX: Int, mouseY: Int,
			renderPartialTicks: Float): Unit = {
		super.drawGuiBackgroundLayer(mouseX, mouseY, renderPartialTicks)

	}

	override protected def drawGuiBackground(): Unit = {}

	override protected def drawGuiForegroundLayer(mouseX: Int, mouseY: Int,
			renderPartialTicks: Float): Unit = {
		super.drawGuiForegroundLayer(mouseX, mouseY, renderPartialTicks)

		val color: Int = this.tile.getOutputColor()
		this.drawString(Hex.toHexString(color), 10, 10, color)
		val leftover: Array[Int] = this.tile.getLeftoverColors()
		this.drawString("RED: " + leftover(0), 10, 20, 0xFF0000)
		this.drawString("GREEN: " + leftover(1), 10, 30, 0x00FF00)
		this.drawString("BLUE: " + leftover(2), 10, 40, 0x0000FF)

	}

	override def doesGuiPauseGame(): Boolean = {
		false
	}

}
