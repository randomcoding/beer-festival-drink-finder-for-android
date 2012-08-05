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
package uk.co.randomcoding.drinkfinder.android.model.drink

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.GivenWhenThen
import scala.io.Source

/**
 * Tests for extracting Drinks from JSON
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
class DrinkFronJsonTest extends FunSuite with ShouldMatchers with GivenWhenThen {
  private[this] val drinkJson = """{
  "drinkType":"Beer",
  "name":"Dorothy Goodbody's Country Ale",
  "description":"Strong, full-bodied ruby ale. A 2011 Suggestabeer from Nick Bracey of Walsall.",
  "abv":"6.0",
  "price":"£2.40",
  "remaining":"Plenty",
  "brewer":"Wye Valley",
  "features":["Dark","Strong"]
}"""

  private[this] val allDrinksJson = Source.fromInputStream(getClass.getResourceAsStream("/testbeerdata.json")).getLines.mkString("\n")

  test("Parse of single drink from JSON") {
    val drink = Drink.fromJson(drinkJson)
    val expectedDrink = Drink(DrinkType.BEER, "Dorothy Goodbody's Country Ale",
      "Strong, full-bodied ruby ale. A 2011 Suggestabeer from Nick Bracey of Walsall.",
      6.0, 2.40, "Plenty", "Wye Valley", List("Dark", "Strong"))

    drink should be(List(expectedDrink))
  }

  test("Parse of multiple drinks from JSON") {
    Drink.fromJson(allDrinksJson) should have size (10)
  }

  test("Parse of multiple drinks from JSON contains some expected drinks") {
    val noble600 = Drink(DrinkType.BEER, "Noble 600", "Light and bitter, picking up the distinctive grapefruit/citrus aroma and taste from Citra hops.",
      4.5, 2.20, "Not Yet Ready", "Kinver", List("Golden"))

    val stingray = Drink(DrinkType.BEER, "Stingray", "Strong and malty - yet extremely hoppy which gives a rich fruitiness.",
      5.5, 2.40, "Not Yet Ready", "Devilfish", List("Strong"))

    Drink.fromJson(allDrinksJson) should (contain(noble600) and contain(stingray))
  }
}