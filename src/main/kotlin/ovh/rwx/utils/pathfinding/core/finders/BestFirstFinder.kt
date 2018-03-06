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

import ovh.rwx.utils.pathfinding.core.DiagonalMovement
import ovh.rwx.utils.pathfinding.core.Grid
import ovh.rwx.utils.pathfinding.core.IHeuristic
import ovh.rwx.utils.pathfinding.core.heuristics.ManhattanHeuristic
import ovh.rwx.utils.pathfinding.core.heuristics.OctileHeuristic

@Suppress("unused")
open class BestFirstFinder(
        diagonalMovement: DiagonalMovement = DiagonalMovement.ONLY_WHEN_NO_OBSTACLES,
        heuristic: IHeuristic = object : IHeuristic {
            override fun getCost(grid: Grid, dx: Int, dy: Int): Double {
                val heuristic1 = if (diagonalMovement == DiagonalMovement.NEVER) OctileHeuristic() else ManhattanHeuristic()

                return heuristic1.getCost(grid, dx, dy) * 1000000
            }
        }
) : AStarFinder(diagonalMovement, heuristic)