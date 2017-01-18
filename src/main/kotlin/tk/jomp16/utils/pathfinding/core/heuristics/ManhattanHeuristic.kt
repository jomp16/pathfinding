/*
 * Copyright (C) 2015-2017 jomp16
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

package tk.jomp16.utils.pathfinding.core.heuristics

import tk.jomp16.utils.pathfinding.core.Grid
import tk.jomp16.utils.pathfinding.core.IHeuristic

class ManhattanHeuristic : IHeuristic {
    override fun getCost(grid: Grid, dx: Int, dy: Int) = (dx + dy).toDouble()
}