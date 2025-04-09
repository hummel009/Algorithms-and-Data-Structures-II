package com.github.hummel.ads.lab2

private var list: MutableList<Item> = ArrayList()

fun main() {
	Launcher.init()
	while (true) {
		print("Enter the command: ")
		val command = readln()

		if ("exit" == command) {
			break
		}

		Launcher.functions[command]?.invoke() ?: println("Unknown command!")
	}
}

private object Launcher {
	val functions: MutableMap<String, () -> Unit> = HashMap()

	fun init() {
		functions.clear()
		functions["init"] = ::initItems
		functions["edit"] = ::editItem
		functions["remove"] = ::removeItem
		functions["show"] = ::showAllItems
		functions["sortByName"] = ::sortByName
		functions["sortByIds"] = ::sortByIds
		functions["commands"] = ::showCommands
	}

	private fun showCommands() {
		functions.forEach { println(it.key) }
	}

	private fun initItems() {
		val base1SubIds = mutableListOf(73, 86)
		val base1Ids = mutableListOf(17, 81)
		val base2Sub1Ids = mutableListOf(11, 20, 33, 98)
		val base2Sub2Ids = mutableListOf(21, 27, 23, 79)
		val base2Ids = mutableListOf(16, 72, 32, 39)
		val base3SubSubIds = mutableListOf(45, 15, 68)
		val base3SubIds = mutableListOf(14, 56, 69)
		val base3Ids = mutableListOf(14, 51, 36)

		val base1Sub = mutableListOf(Item("b", base1SubIds))
		val base2Sub = mutableListOf(Item("y", base2Sub1Ids), Item("b", base2Sub2Ids))
		val base3SubSub = mutableListOf(Item("o", base3SubSubIds))
		val base3Sub = mutableListOf(Item("j", base3SubIds, base3SubSub))

		list.add(Item("a", base1Ids, base1Sub))
		list.add(Item("x", base2Ids, base2Sub))
		list.add(Item("z", base3Ids, base3Sub))

		println("Items were added.")
		showAllItems()
	}

	private fun editItem(item: Item) {
		item.ids.clear()
		item.subs.clear()
		println("Enter the new ids of the old item:")
		while (true) {
			val id = readIntSafe()
			if (id == 0) {
				break
			}
			item.ids.add(id)
		}
		item.ids.sort()
		while (true) {
			print("Has sub-item? [Yes/No]: ")
			val read = readln()
			if (read == "No") {
				break
			}
			val ids = ArrayList<Int>()
			print("Enter the name: ")
			val name = readln()
			println("Enter the new ids:")
			while (true) {
				val id = readIntSafe()
				if (id == 0) {
					break
				}
				ids.add(id)
			}
			ids.sort()
			val sub = Item(name, ids)
			item.subs.add(sub)
		}
	}

	private fun editItem() {
		val arr = list.toTypedArray()
		arr.indices.forEachIndexed { i, item -> println("$i. $item") }
		print("Enter the number of the old item: ")
		val id = readIntSafe()
		if (id in arr.indices) {
			val item = arr[id]
			editItem(item)
		} else {
			print("Wrong! Enter the sub-item name then: ")
			val subName = readln()
			list.forEach { item ->
				item.subs.asSequence().filter { it.name == subName }.forEach { editItem(it) }
			}
		}
		showAllItems()
	}

	private fun removeItem() {
		val arr = list.toTypedArray()
		arr.indices.forEachIndexed { i, item -> println("$i. $item") }
		print("Enter the number of the removal item: ")
		val id = readIntSafe()
		if (id in arr.indices) {
			val item = arr[id]
			list.remove(item)
		} else {
			print("Wrong! Enter the sub-item name then: ")
			val subName = readln()
			list.forEach { item ->
				item.subs.asSequence().filter { it.name == subName }.forEach { item.subs.remove(it) }
			}
		}
		showAllItems()
	}

	private fun showAllItems() {
		list.forEach { println(it) }
	}

	private fun sortByIds() {
		val comparator = Comparator.comparing { item: Item -> item.ids[0] }
		list.sortWith(comparator)
		list.forEach { it.subs.sortWith(comparator) }
		showAllItems()
	}

	private fun sortByName() {
		val comparator = Comparator.comparing { item: Item -> item.name }
		list.sortWith(comparator)
		list.forEach { it.subs.sortWith(comparator) }
		showAllItems()
	}
}

class Item(
	var name: String, var ids: MutableList<Int>, var subs: MutableList<Item> = mutableListOf()
) {
	override fun toString(): String {
		if (subs.isEmpty()) {
			return "$name: $ids, sub does not exist."
		}
		return buildString {
			append("$name: $ids, subs:\n")
			append(subs.joinToString("\n") { "$it" })
		}
	}
}

private fun readIntSafe(): Int {
	return try {
		readln().toInt()
	} catch (_: Exception) {
		print("Error! Enter the correct value: ")
		readIntSafe()
	}
}