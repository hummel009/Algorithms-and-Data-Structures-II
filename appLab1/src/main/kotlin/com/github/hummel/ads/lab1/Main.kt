package com.github.hummel.ads.lab1

import com.github.hummel.ads.lab1.Room.RoomType
import com.github.hummel.ads.lab1.Room.WindowType
import java.util.*

private const val separator: String = "|=======================================|"
private val random: Random = Random()

fun main() {
	println("─────────────────────────────────────────────────────────────────────────")
	println("─██████──────────██████─██████████████─██████████████████─██████████████─")
	println("─██░░██████████████░░██─██░░░░░░░░░░██─██░░░░░░░░░░░░░░██─██░░░░░░░░░░██─")
	println("─██░░░░░░░░░░░░░░░░░░██─██░░██████░░██─████████████░░░░██─██░░██████████─")
	println("─██░░██████░░██████░░██─██░░██──██░░██─────────████░░████─██░░██─────────")
	println("─██░░██──██░░██──██░░██─██░░██████░░██───────████░░████───██░░██████████─")
	println("─██░░██──██░░██──██░░██─██░░░░░░░░░░██─────████░░████─────██░░░░░░░░░░██─")
	println("─██░░██──██████──██░░██─██░░██████░░██───████░░████───────██░░██████████─")
	println("─██░░██──────────██░░██─██░░██──██░░██─████░░████─────────██░░██─────────")
	println("─██░░██──────────██░░██─██░░██──██░░██─██░░░░████████████─██░░██████████─")
	println("─██░░██──────────██░░██─██░░██──██░░██─██░░░░░░░░░░░░░░██─██░░░░░░░░░░██─")
	println("─██████──────────██████─██████──██████─██████████████████─██████████████─")
	println("─────────────────────────────────────────────────────────────────────────")

	print("Enter the color of the floor (green, black, grey): ")
	val floor = readEnumSafe<Floors>()

	print("Enter the color of the room (green, black, grey): ")
	val color = readln()

	print("Enter if there is glowing in the room (true/false): ")
	val hasGlowing = readBooleanSafe()

	print("Enter if glowing is direct in the room (true/false): ")
	val isGlowingDirect = readBooleanSafe()

	print("Enter if there is med environment in the room (true/false): ")
	val hasMedEnvironment = readBooleanSafe()

	print("Enter the room type of the room: ")
	val roomType = readEnumSafe<RoomType>()

	print("Enter the window type of the room: ")
	val windowType = readEnumSafe<WindowType>()

	val room = Room(color, hasGlowing, isGlowingDirect, hasMedEnvironment, roomType, windowType)

	val blackRoom = Room(
		"grey",
		hasGlowing = true,
		isGlowingDirect = false,
		hasMedEnvironment = false,
		roomType = RoomType.CORRIDOR,
		windowType = WindowType.SMALL
	)
	val lab = Room(
		"grey",
		hasGlowing = true,
		isGlowingDirect = true,
		hasMedEnvironment = true,
		roomType = RoomType.ROOM,
		windowType = WindowType.BIG
	)
	val prison = Room(
		"green",
		hasGlowing = false,
		isGlowingDirect = false,
		hasMedEnvironment = false,
		roomType = RoomType.BLOCK,
		windowType = WindowType.NONE
	)

	println(separator)
	println("|==============  GO BACK  ==============|")
	println(separator)
	println()
	when (floor) {
		Floors.BLACK -> if (room == blackRoom) {
			println(
				"""
				You are on the first floor.
				Turn left and go 12 steps.
				Turn to the angle of 45 degrees to right and go 50 steps.
				Turn to the angle of 45 degrees to right and go 18 steps.
				Turn to the angle of 90 degrees to right and go forward.
				Turn to the angle of 45 degrees to right and go 48 steps.
				Then use lift. Turn to the angle of 45 degrees to left and go 50 metres.
				""".trimIndent()
			)
		} else {
			pseudoWay("first")
		}

		Floors.GREY -> if (room == lab) {
			println(
				"""
				You are on the second floor.
				Turn to the angle of 45 degrees to right and go forward.
				Turn to the angle of 90 degrees to right and go forward.
				Turn to the angle of 45 degrees to right and go forward.
				Then use lift.
				You are on the first floor.
				Turn left and go 12 steps.
				Turn to the angle of 45 degrees to right and go 50 steps.
				Turn to the angle of 45 degrees to right and go 18 steps.
				Turn to the angle of 90 degrees to right and go forward.
				Turn to the angle of 45 degrees to right and go 48 steps.
				Then use lift.Turn to the angle of 45 degrees to left and go 50 metres.
				""".trimIndent()
			)
		} else {
			pseudoWay("second")
		}

		Floors.GREEN -> if (room == prison) {
			println(
				"""
				You are on the third floor.
				Turn left and go 8 steps.
				Then use lift.
				You are on the second floor.
				Turn to the angle of 45 degrees to right and go forward.
				Turn to the angle of 90 degrees to right and go forward.
				Turn to the angle of 45 degrees to right and go forward.
				Then use lift.
				You are on the first floor.
				Turn left and go 12 steps.
				Turn to the angle of 45 degrees to right and go 50 steps.
				Turn to the angle of 45 degrees to right and go 18 steps.
				Turn to the angle of 90 degrees to right and go forward.
				Turn to the angle of 45 degrees to right and go 48 steps.
				Then use lift.
				Turn to the angle of 45 degrees to left and go 50 metres.
				""".trimIndent()
			)
		} else {
			pseudoWay("third")
		}
	}
}

private fun pseudoWay(floor: String) {
	val left1 = random.nextInt(20) + 20
	val left2 = random.nextInt(20) + 20
	val right = random.nextInt(20) + 20
	println(
		"""
				You are on the $floor floor.
				Turn left and go $left1 steps.
				Then turn right and go $right steps.
				Turn left and go $left2 steps.
				Then use lift.""".trimIndent()
	)
	drawAsAMatrix(left1, right, left2)
}

private fun drawAsAMatrix(left1: Int, right: Int, left2: Int) {
	var lLeft1 = left1 / 5
	var lRight = right / 5
	var lLeft2 = left2 / 5
	val matrix = Array(26) { Array(26) { " " } }
	var x = 12
	var y = 12
	while (lLeft1 > 0) {
		matrix[x][y] = "G"
		x--
		lLeft1--
	}
	while (lRight > 0) {
		matrix[x][y] = "G"
		y--
		lRight--
	}
	while (lLeft2 > 0) {
		matrix[x][y] = "G"
		x++
		lLeft2--
	}
	matrix[x][y] = "L"
	matrix[12][12] = "S"
	println()
	println(separator)
	println("|===============  ROUTE  ===============|")
	println(separator)
	println()
	println(separator)
	matrix.forEach { row -> row.forEach { print(it) }; println() }
	println(separator)
}

private inline fun <reified E : Enum<E>> readEnumSafe(): E {
	while (true) {
		try {
			val input = readln().uppercase()
			return enumValueOf<E>(input)
		} catch (_: Exception) {
			print("Error! Enter the correct value: ")
		}
	}
}

private fun readBooleanSafe(): Boolean {
	return try {
		readln().toBoolean()
	} catch (_: Exception) {
		print("Error! Enter the correct value: ")
		readBooleanSafe()
	}
}

private enum class Floors {
	BLACK, GREY, GREEN;
}

private data class Room(
	val color: String,
	val hasGlowing: Boolean,
	val isGlowingDirect: Boolean,
	val hasMedEnvironment: Boolean,
	val roomType: RoomType,
	val windowType: WindowType
) {
	enum class RoomType {
		BLOCK, ROOM, CORRIDOR;
	}

	enum class WindowType {
		BIG, SMALL, NONE;
	}
}
