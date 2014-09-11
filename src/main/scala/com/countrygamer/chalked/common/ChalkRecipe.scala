package com.countrygamer.chalked.common

import java.util

import scala.util.control.Breaks
import scala.util.control.Breaks._

import com.countrygamer.chalked.common.init.CItems
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ChalkRecipe(private val output: ItemStack, inputs: ItemStack*) extends IRecipe {

	private val components: java.util.List[ItemStack] = new util.ArrayList[ItemStack]()
	private var tagCom: NBTTagCompound = null

	{
		for (i <- 0 until inputs.length) {
			components.add(inputs(i))
		}
	}

	override def getRecipeOutput: ItemStack = {
		this.output
	}

	override def getRecipeSize: Int = {
		this.components.size()
	}

	override def matches(inventory: InventoryCrafting, world: World): Boolean = {
		val componentsCopy: util.ArrayList[ItemStack] = new
						util.ArrayList[ItemStack](this.components)

		for (col <- 0 until 3) {
			for (row <- 0 until 3) {
				val itemStack: ItemStack = inventory.getStackInRowAndColumn(row, col)
				if (itemStack != null) {

					if (itemStack.getItem == CItems.chalk) {
						this.output.setTagCompound(itemStack.getTagCompound)
					}

					var isInList: Boolean = false
					val iterator: util.Iterator[ItemStack] = componentsCopy.iterator()
					breakable {
						while (iterator.hasNext) {
							val invStack: ItemStack = iterator.next()
							if (itemStack.getItem == invStack.getItem &&
									(invStack.getItemDamage == 32767 ||
											itemStack.getItemDamage == invStack.getItemDamage)) {
								isInList = true
								componentsCopy.remove(invStack)
								Breaks.break()
							}
						}
					}
					if (!isInList) {
						return false
					}
				}
			}
		}

		componentsCopy.isEmpty
	}

	override def getCraftingResult(inventory: InventoryCrafting): ItemStack = {
		this.output.copy()
	}

}
