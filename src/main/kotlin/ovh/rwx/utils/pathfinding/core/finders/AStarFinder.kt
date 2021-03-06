/*
 * Copyright (C) 2015-2018 jomp16 <root@rwx.ovh>
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

package ovh.rwx.utils.pathfinding.core.finders

import ovh.rwx.utils.pathfinding.IFinder
import ovh.rwx.utils.pathfinding.core.*
import ovh.rwx.utils.pathfinding.core.heuristics.ManhattanHeuristic
import ovh.rwx.utils.pathfinding.core.heuristics.OctileHeuristic
import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

open class AStarFinder(
        private val diagonalMovement: DiagonalMovement = DiagonalMovement.ONLY_WHEN_NO_OBSTACLES,
        private val heuristic: IHeuristic = if (diagonalMovement == DiagonalMovement.NEVER) OctileHeuristic() else ManhattanHeuristic()
) : IFinder {
    override fun findPath(grid: Grid, startX: Int, startY: Int, endX: Int, endY: Int, overrideBlocking: Boolean): List<Path> {
        val openList: Queue<Node> = PriorityQueue(compareBy(Node::f))
        val closedList: MutableList<Node> = mutableListOf()
        val startNode = grid.getNodeAt(startX, startY)
        val endNode = grid.getNodeAt(endX, endY)
        var node: Node
        var neighbors: List<Node>
        // set the 'g' and 'f' value of the start node to be 0
        startNode.g = 0.toDouble()
        startNode.f = 0.toDouble()
        // push the start node into the open list
        openList.offer(startNode)
        // while the open list is not empty
        while (openList.isNotEmpty()) {
            // pop the position of node which has the minimum 'f' value.
            node = openList.poll()

            if (!closedList.contains(node)) closedList.add(node)
            // if reached the end position, construct the path and return it
            if (node == endNode) {
                val path: MutableList<Path> = mutableListOf()
                var target: Node? = endNode

                while (target != startNode) {
                    if (target != null) {
                        path.add(Path(target.x, target.y))

                        target = target.parent
                    }
                }

                return path.reversed()
            }
            // get neighbours of the current node
            neighbors = grid.getNeighbors(node, diagonalMovement, overrideBlocking)

            neighbors.forEach { neighbor ->
                if (closedList.contains(neighbor)) return@forEach
                val x = neighbor.x
                val y = neighbor.y
                // get the distance between current node and the neighbor
                // and calculate the next g score
                val ng = node.g + if (x - node.x == 0 || y - node.y == 0) 1.toDouble() else sqrt(2.toDouble())
                // check if the neighbor has not been inspected yet, or
                // can be reached with smaller cost from the current node
                if (!openList.contains(neighbor) || ng < neighbor.g) {
                    neighbor.g = ng
                    neighbor.h = heuristic.getCost(grid, abs((x - endX).toDouble()).toInt(), abs((y - endY).toDouble().toInt()))
                    neighbor.f = neighbor.g + neighbor.h
                    neighbor.parent = node

                    if (!openList.contains(neighbor)) openList.offer(neighbor)
                }
            }
        }

        return emptyList()
    }
}