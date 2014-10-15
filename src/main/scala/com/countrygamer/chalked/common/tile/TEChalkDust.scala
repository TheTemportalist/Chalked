package com.countrygamer.chalked.common.tile

import java.util

import com.countrygamer.cgo.library.common.lib.LogHelper
import com.countrygamer.cgo.library.common.utility.Drops
import com.countrygamer.cgo.wrapper.common.tile.TEWrapper
import com.countrygamer.chalked.common.Chalked
import com.countrygamer.chalked.common.init.CItems
import com.countrygamer.chalked.common.lib.{Hex, HexHelper}
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraftforge.oredict.OreDictionary

/**
 *
 *
 * @author CountryGamer
 */
class TEChalkDust() extends TEWrapper("Chalk Dust") {

	private val inputs: util.ArrayList[ItemStack] = new util.ArrayList[ItemStack]()
	private var chalkCount: Int = 0
	private var inputColors: Array[Int] = Array[Int](0, 0, 0)
	private var outputColors: Array[Int] = Array[Int](0, 0, 0)
	private var outputHex: Int = 0x000000
	private var leftoverColors: Array[Int] = Array[Int](0, 0, 0)
	private var shouldBreak: Boolean = false

	def add(itemStack: ItemStack, doAdd: Boolean): Boolean = {

		val stack: ItemStack = itemStack.copy()
		stack.stackSize = 1

		if (stack == null) {
			return false
		}

		if (stack.getItem == CItems.chalk) {
			if (doAdd) {
				this.inputs.add(stack)
				this.chalkCount += 1
				if (stack.hasTagCompound) {
					val tagCom: NBTTagCompound = stack.getTagCompound
					val colors: Array[Int] = tagCom.getIntArray("colors")
					this.inputColors(0) += colors(0)
					this.inputColors(1) += colors(1)
					this.inputColors(2) += colors(2)
				}
			}
			return true
		}

		if (this.isDye(stack)) {
			if (doAdd) {
				this.inputs.add(stack)
				val colors: Array[Int] = Hex.getRGBFromDye(stack)
				this.inputColors(0) += colors(0)
				this.inputColors(1) += colors(1)
				this.inputColors(2) += colors(2)
			}
			return true
		}

		false
	}

	def isDye(itemStack: ItemStack): Boolean = {
		val oreIDs: Array[Int] = OreDictionary.getOreIDs(itemStack)
		for (i <- 0 until oreIDs.length) {
			if (OreDictionary.getOreName(oreIDs(i)).contains("dye"))
				return true
		}
		false
	}

	def getInputColors(): Array[Int] = {
		this.inputColors
	}

	def getOutputColors(): Array[Int] = {
		this.outputColors
	}

	def getOutputColor(): Int = {
		this.outputHex
	}

	def getLeftoverColors(): Array[Int] = {
		this.leftoverColors
	}

	def setOutputColors(colors: Array[Int]): Unit = {
		this.outputColors = colors
		this.outputHex = Hex.toHex(this.outputColors(0), this.outputColors(1), this.outputColors(2))
		this.calculateLeftovers()

		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord)

	}

	def calculateLeftovers(): Unit = {
		this.leftoverColors(0) = this.inputColors(0) - this.outputColors(0)
		this.leftoverColors(1) = this.inputColors(1) - this.outputColors(1)
		this.leftoverColors(2) = this.inputColors(2) - this.outputColors(2)
	}

	override def updateEntity(): Unit = {
		super.updateEntity()

		this.calculateLeftovers()

	}

	override def getDrops(drops: util.ArrayList[ItemStack], block: Block,
			metadata: Int): Unit = {
		drops.clear()
		if (this.shouldBreak)
			drops.addAll(this.inputs)

	}

	def make(): Unit = {
		val drops: util.ArrayList[ItemStack] = new util.ArrayList[ItemStack]()

		var chalkToDrop: Int = this.chalkCount

		//
		val output: ItemStack = new ItemStack(CItems.chalk, 1, 0)
		val outputTag: NBTTagCompound = new NBTTagCompound()

		HexHelper.setHex(
			outputTag, this.outputColors(0), this.outputColors(1), this.outputColors(2)
		)

		output.setTagCompound(outputTag)
		drops.add(output)
		chalkToDrop -= 1
		this.logAr("", this.outputColors, "")
		LogHelper.info(Chalked.pluginName,
			"Adding stack with hex " + Hex.toHexString(output.getTagCompound.getInteger("hex")))
		//

		var colors: Array[Int] = null
		// iterate for each of RGB
		for (i <- 0 until 3) {
			// Store the leftover as a variable for iteration
			var leftoverColor: Int = this.leftoverColors(i)
			// Have to iterate in case the leftover is greater than 256
			while (chalkToDrop > 0 && leftoverColor > -1) {
				colors = Array[Int](0, 0, 0)
				colors(i) = leftoverColor

				val leftover: ItemStack = new ItemStack(CItems.chalk, 1, 0)
				val leftoverTag: NBTTagCompound = new NBTTagCompound()
				HexHelper.setHex(leftoverTag, colors(0), colors(1), colors(2))

				leftover.setTagCompound(leftoverTag)
				drops.add(leftover)

				chalkToDrop -= 1
				leftoverColor -= 256
			}
		}

		while (chalkToDrop > 0) {
			drops.add(new ItemStack(CItems.chalk, 1, 0))
			chalkToDrop -= 1
		}

		Drops.spawnDrops(this.worldObj, this.xCoord, this.yCoord, this.zCoord, drops)

		this.shouldBreak = false
		this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord)
	}

	override def writeToNBT(tagCom: NBTTagCompound): Unit = {
		super.writeToNBT(tagCom)

		val inputList: NBTTagList = new NBTTagList
		for (i <- 0 until this.inputs.size()) {
			val inputTag: NBTTagCompound = new NBTTagCompound
			this.inputs.get(i).writeToNBT(inputTag)
			inputList.appendTag(inputTag)
		}
		tagCom.setTag("inputs", inputList)

		tagCom.setInteger("chalkCount", this.chalkCount)
		tagCom.setIntArray("inputColors", this.inputColors)
		//this.logAr("output write", this.outputColors, "")
		tagCom.setIntArray("outputColors", this.outputColors)
		tagCom.setIntArray("leftoverColors", this.leftoverColors)

	}

	override def readFromNBT(tagCom: NBTTagCompound): Unit = {
		super.readFromNBT(tagCom)

		this.inputs.clear()
		val inputList: NBTTagList = tagCom.getTagList("inputs", 10)
		for (i <- 0 until inputList.tagCount()) {
			this.inputs.add(ItemStack.loadItemStackFromNBT(inputList.getCompoundTagAt(i)))
		}

		this.chalkCount = tagCom.getInteger("chalkCount")
		this.inputColors = tagCom.getIntArray("inputColors")
		this.outputColors = tagCom.getIntArray("outputColors")
		//this.logAr("output read", this.outputColors, "")
		this.leftoverColors = tagCom.getIntArray("leftoverColors")

	}

	def logAr(pre: String, ar: Array[Int], post: String): Unit = {
		LogHelper.info(Chalked.pluginName,
			"\n" + pre + "\n" + ar(0) + "\n" + ar(1) + "\n" + ar(2) + "\n" + post)
	}

}
