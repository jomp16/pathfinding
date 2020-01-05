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

package ovh.rwx.utils.pathfinding.core.heuristics

import ovh.rwx.utils.pathfinding.core.Grid
import ovh.rwx.utils.pathfinding.core.IHeuristic
import kotlin.math.sqrt

class OctileHeuristic : IHeuristic {
    override fun getCost(grid: Grid, dx: Int, dy: Int): Double {
        val r = sqrt(2.toDouble()) - 1

        return if (dx < dy) r * dx + dy else r * dy + dx
    }
}