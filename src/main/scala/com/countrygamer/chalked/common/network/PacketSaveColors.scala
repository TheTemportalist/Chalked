package com.countrygamer.chalked.common.network

import com.countrygamer.cgo.library.common.nethandler.IPacket
import com.countrygamer.chalked.common.tile.TEChalkDust
import io.netty.buffer.ByteBuf
import net.minecraft.entity.player.EntityPlayer

/**
 *
 *
 * @author CountryGamer
 */
class PacketSaveColors(var x: Int, var y: Int, var z: Int, var colors: Array[Int])
		extends IPacket {

	def this() {
		this(0, 0, 0, null)
	}

	override def writeTo(buffer: ByteBuf): Unit = {
		buffer.writeInt(x)
		buffer.writeInt(y)
		buffer.writeInt(z)
		buffer.writeInt(this.colors(0))
		buffer.writeInt(this.colors(1))
		buffer.writeInt(this.colors(2))

	}

	override def readFrom(buffer: ByteBuf): Unit = {
		x = buffer.readInt()
		y = buffer.readInt()
		z = buffer.readInt()
		this.colors = Array[Int](
			buffer.readInt(),
			buffer.readInt(),
			buffer.readInt()
		)
	}

	override def handle(player: EntityPlayer): Unit = {
		player.worldObj.getTileEntity(
			this.x, this.y, this.z
		).asInstanceOf[TEChalkDust].setOutputColors(this.colors)
	}

}
