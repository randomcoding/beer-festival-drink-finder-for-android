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

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.GivenWhenThen
import org.scalatest.FunSuite
import java.io.File

/**
 * Tests for the loading of drink data from a file
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
class JsonDrinkDataLoaderTest extends FunSuite with ShouldMatchers with GivenWhenThen {
  private[this] val dataFile = new File(getClass.getResource("/testbeerdata.json").toURI)

  test("Load Data from File loads correct number of drinks") {
    val drinks = JsonDrinkDataLoader.loadJson(dataFile)

    drinks should have size (10)
  }

  test("Load of data from file that does not exist throws correct exception") {
    val filePath = "/does/not/exist.json"

    val exception = intercept[JsonDrinkLoaderException] {
      JsonDrinkDataLoader.loadJson(new File(filePath))
    }

    exception.getMessage should be("Data file %s not found.".format(filePath))
  }
}