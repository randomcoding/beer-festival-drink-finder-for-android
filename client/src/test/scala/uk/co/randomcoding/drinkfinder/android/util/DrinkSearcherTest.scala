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

import java.io.File

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite

import uk.co.randomcoding.drinkfinder.android.SearchDrinkActivity.{ NAME_SEARCH_EXTRA, DESCRIPTION_SEARCH_EXTRA }
import uk.co.randomcoding.drinkfinder.android.model.drink.Drink
import uk.co.randomcoding.drinkfinder.android.util.TestDrinks._

import DrinkSearcher.getMatchingDrinks

/**
 * Tests for the Searcher for Drings from data
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
class DrinkSearcherTest extends FunSuite with ShouldMatchers {
  private[this] val beersOnlyDataFile = new File(getClass.getResource("/testbeerdata.json").toURI)

  import DrinkSearcher.getMatchingDrinks

  test("Empty Search matches all beers") {
    val results = doSearch(Map.empty)

    results should have size (10)
  }

  test("Search for beers by name only returnes expected results") {
    val searchData = Map(NAME_SEARCH_EXTRA -> "Duck")

    doSearch(searchData) should (have size (1) and contain(tangerineDuck))
  }

  test("Search for beers by single description word returns expected results") {
    val searchData = Map(DESCRIPTION_SEARCH_EXTRA -> "fuggles")

    doSearch(searchData) should (have size (2) and
      contain(astonDark) and
      contain(rotundaRed))
  }

  test("Search for beers by name and single description word returns expected results") {
    val searchData = Map(NAME_SEARCH_EXTRA -> "Beauty",
      DESCRIPTION_SEARCH_EXTRA -> "pilsner")

    doSearch(searchData) should (have size (1) and contain(buttermereBeauty))
  }

  test("Search for beers by multiple description words returns expected results") {
    val searchData = Map(DESCRIPTION_SEARCH_EXTRA -> "dark malt")

    doSearch(searchData) should (have size (2) and
      contain(astonDark) and
      contain(seville))
  }

  test("Search for beers by name and multiple description words returns expected results") {
    val searchData = Map(DESCRIPTION_SEARCH_EXTRA -> "dark malt",
      NAME_SEARCH_EXTRA -> "Dark")

    doSearch(searchData) should (have size (1) and
      contain(astonDark))
  }

  private[this] def doSearch(searchData: Map[String, String], dataFile: File = beersOnlyDataFile): Seq[Drink] = {
    getMatchingDrinks(null, dataFile, searchData, 0)
  }
}