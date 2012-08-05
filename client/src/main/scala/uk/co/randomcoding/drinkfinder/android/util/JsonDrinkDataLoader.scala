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

import uk.co.randomcoding.drinkfinder.android.model.drink.Drink
import java.io.File
import scala.io.Source
import java.io.FileNotFoundException

/**
 * Loads Drink data from various Json sources
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
object JsonDrinkDataLoader {
  /**
   * Load data from a file containing JSON Drink Data
   */
  def loadJson(jsonFile: File): Seq[Drink] = {
    try {
      Drink.fromJson(Source.fromFile(jsonFile).getLines.mkString("\n"))
    }
    catch {
      case e: FileNotFoundException => throw new JsonDrinkLoaderException("Data file %s not found.".format(jsonFile.getAbsolutePath), e)
      case e: Exception => throw new JsonDrinkLoaderException("Caught Exception: %s".format(e.getMessage), e)
    }
  }
}

class JsonDrinkLoaderException(message: String, cause: Throwable) extends Exception(message, cause)