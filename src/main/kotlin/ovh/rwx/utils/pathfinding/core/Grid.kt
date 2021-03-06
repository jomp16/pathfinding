/*
 * Copyright (C) 2015-2019 jomp16 <root@rwx.ovh>
 *
 * This file is part of pathfinding.
 *
 * pathfinding is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pathfinding is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pathfinding. If not, see <http://www.gnu.org/licenses/>.
 */

package ovh.rwx.utils.pathfinding.core

/**
 * The Grid class, which serves as the encapsulation of the layout of the nodes.
 *
 * @param width: The width
 * @param height: The height
 * @param isWalkable: a Kotlin function with param grid, x, y and returns a boolean
 */
class Grid(val width: Int, val height: Int, val isWalkable: (Grid, Int, Int, Boolean) -> Boolean = { grid, x, y, overrideBlock -> overrideBlock || grid.isInside(x, y) }) {
    private val nodes: Array<Array<Node>> = Array(height) { y -> Array(width) { x -> Node(x, y) } }

    fun getNodeAt(x: Int, y: Int): Node = nodes[y][x]

    fun isInside(x: Int, y: Int): Boolean = x in 0 until width && y >= 0 && y < height

    fun getNeighbors(node: Node, diagonalMovement: DiagonalMovement, overrideBlocking: Boolean): List<Node> {
        val x = node.x
        val y = node.y
        val neighbors: MutableList<Node> = mutableListOf()
        val nodes1 = nodes.copyOf()
        var s0 = false
        var d0 = false
        var s1 = false
        var d1 = false
        var s2 = false
        var d2 = false
        var s3 = false
        var d3 = false
        // ↑
        if (isWalkable(this, x, y - 1, overrideBlocking)) {
            neighbors += nodes1[y - 1][x]

            s0 = true
        }
        // →
        if (isWalkable(this, x + 1, y, overrideBlocking)) {
            neighbors += nodes1[y][x + 1]

            s1 = true
        }
        // ↓
        if (isWalkable(this, x, y + 1, overrideBlocking)) {
            neighbors += nodes1[y + 1][x]

            s2 = true
        }
        // ←
        if (isWalkable(this, x - 1, y, overrideBlocking)) {
            neighbors += nodes1[y][x - 1]

            s3 = true
        }

        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (diagonalMovement) {
            DiagonalMovement.ONLY_WHEN_NO_OBSTACLES -> {
                d0 = s3 && s0
                d1 = s0 && s1
                d2 = s1 && s2
                d3 = s2 && s3
            }
            DiagonalMovement.IF_AT_MOST_ONE_OBSTACLE -> {
                d0 = s3 || s0
                d1 = s0 || s1
                d2 = s1 || s2
                d3 = s2 || s3
            }
            DiagonalMovement.ALWAYS -> {
                d0 = true
                d1 = true
                d2 = true
                d3 = true
            }
        }
        // ↖
        if (d0 && isWalkable(this, x - 1, y - 1, overrideBlocking)) {
            neighbors += nodes[y - 1][x - 1]
        }
        // ↗
        if (d1 && isWalkable(this, x + 1, y - 1, overrideBlocking)) {
            neighbors += nodes[y - 1][x + 1]
        }
        // ↘
        if (d2 && isWalkable(this, x + 1, y + 1, overrideBlocking)) {
            neighbors += nodes[y + 1][x + 1]
        }
        // ↙
        if (d3 && isWalkable(this, x - 1, y + 1, overrideBlocking)) {
            neighbors += nodes[y + 1][x - 1]
        }

        return neighbors.toList()
    }
}