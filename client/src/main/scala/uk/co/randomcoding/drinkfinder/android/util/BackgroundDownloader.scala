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

import android.os.AsyncTask
import java.io.OutputStream

/**
 * TODO: Comment for
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 1 Aug 2012
 */
class BackgroundDownloader extends AsyncTask[(String, OutputStream), String, Unit] {
  override def doInBackground(urisToTarget: (String, OutputStream)*): Unit = {
    // only use the first pair
    val (dataUri, outStream) = urisToTarget(0)
    RestDownloader.getUri(dataUri, outStream)
  }

}