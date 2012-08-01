/**
 * Copyright (C) 2012 - RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *    RandomCoder <randomcoder@randomcoding.co.uk> - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.drinkfinder.android.util

import android.os.Environment

/**
 * TODO: Comment for
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 1 Aug 2012
 */
object ExternalStorageHelper {

  def externalStorageState(): ExternalStorageState.state = {

    val state = Environment.getExternalStorageState();

    state match {
      case Environment.MEDIA_MOUNTED => ExternalStorageState.MOUNTED_WRITABLE
      case Environment.MEDIA_MOUNTED_READ_ONLY => ExternalStorageState.MOUNTED_READ_ONLY
      case _ => ExternalStorageState.NOT_USABLE
    }
  }
}

object ExternalStorageState extends Enumeration {
  type state = Value
  val MOUNTED_WRITABLE, MOUNTED_READ_ONLY, NOT_USABLE = Value
}