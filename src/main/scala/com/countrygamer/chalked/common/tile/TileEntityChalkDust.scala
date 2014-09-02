package com.countrygamer.chalked.common.tile

import java.util

import com.countrygamer.cgo.common.lib.LogHelper
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
class TileEntityChalkDust() extends TEWrapper("Chalk Dust") {

	private val inputs: util.ArrayList[ItemStack] = new util.ArrayList[ItemStack]()
	private var chalkCount: Int = 0
	private var inputColors: Array[Int] = Array[Int](0, 0, 0)
	private var outputColors: Array[Int] = Array[Int](0, 0, 0)
	private var leftoverColors: Array[Int] = Array[Int](0, 0, 0)

	def add(itemStack: ItemStack): Boolean = {

		val stack: ItemStack = itemStack.copy()
		stack.stackSize = 1

		if (stack == null) {
			return false
		}

		if (stack.getItem == CItems.chalk) {
			this.inputs.add(stack)
			this.chalkCount += 1
			if (stack.hasTagCompound) {
				val tagCom: NBTTagCompound = stack.getTagCompound
				val colors: Array[Int] = tagCom.getIntArray("colors")
				this.inputColors(0) += colors(0)
				this.inputColors(1) += colors(1)
				this.inputColors(2) += colors(2)
			}
			return true
		}

		if (this.isDye(stack)) {
			this.inputs.add(stack)
			val colors: Array[Int] = Hex.getRGBFromDye(stack)
			this.inputColors(0) += colors(0)
			this.inputColors(1) += colors(1)
			this.inputColors(2) += colors(2)
			return true
		}

		false
	}

	def isDye(itemStack: ItemStack): Boolean = {
		val oreIDs: Array[Int] = OreDictionary.getOreIDs(itemStack)
		for (i <- 0 until oreIDs.length) {
			return OreDictionary.getOreName(oreIDs(i)).contains("dye")
		}
		false
	}

	override def updateEntity(): Unit = {
		super.updateEntity()

		this.leftoverColors(0) = this.inputColors(0) - this.outputColors(0)
		this.leftoverColors(1) = this.inputColors(1) - this.outputColors(1)
		this.leftoverColors(2) = this.inputColors(2) - this.outputColors(2)

		LogHelper.info(Chalked.pluginName, this.inputs.size() + "")

	}

	override def getDrops(drops: util.ArrayList[ItemStack], block: Block,
			metadata: Int): Unit = {
		drops.clear()
		drops.addAll(this.inputs)

	}

	def make(drops: util.ArrayList[ItemStack]): Unit = {
		var chalkToDrop: Int = this.chalkCount

		val chalk: ItemStack = new ItemStack(CItems.chalk, 1, 0)
		var tagCom: NBTTagCompound = new NBTTagCompound()

		HexHelper.setHex(tagCom, this.outputColors(0), this.outputColors(1), this.outputColors(2))

		chalk.setTagCompound(tagCom)
		drops.add(chalk)

		chalkToDrop -= 1
		chalk.setTagCompound(null)
		tagCom = new NBTTagCompound()

		var colors: Array[Int] = null
		// iterate for each of RGB
		for (i <- 0 until 3) {
			// Store the leftover as a variable for iteration
			var leftoverColor: Int = this.leftoverColors(i)
			// Have to iterate in case the leftover is greater than 256
			while (leftoverColor > -1) {
				colors = Array[Int](0, 0, 0)
				colors(i) = leftoverColor

				HexHelper.setHex(tagCom, colors(0), colors(1), colors(2))

				chalk.setTagCompound(tagCom)
				drops.add(chalk)

				chalkToDrop -= 1
				leftoverColor -= 256

				chalk.setTagCompound(null)
				tagCom = new NBTTagCompound()
			}
		}

		while (chalkToDrop > 0) {
			drops.add(chalk)
			chalkToDrop -= 1
		}
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
		this.leftoverColors = tagCom.getIntArray("leftoverColors")

	}

}
