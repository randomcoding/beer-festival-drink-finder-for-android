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
import uk.co.randomcoding.drinkfinder.android.model.drink.DrinkType._

/**
 * TODO: Comment for
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
object TestDrinks {

  val tangerineDuck = Drink(BEER, "Tangerine Duck",
    "Copper coloured beer with a hint of tangerine from the late addition of crystal hops. Named after the Atomic Boys famous duck Puskas at Blackpool FC in the 50s.",
    4.4, 2.6, "Plenty", "Fuzzy Duck", List("Brown", "Best Bitter"))

  val astonDark = Drink(BEER, "Aston Dark",
    "Dark tanned and complex ale lightly hopped with Fuggles. Underlying malt gives way to hints of dark chocolate and coffee.",
    3.6, 2.10, "Plenty", "ABC", List("Mild"))

  val rotundaRed = Drink(BEER, "Rotunda Red",
    "Traditional ruby coloured ale of distinct character.  Lightly hopped with English Fuggles, finished with the distinct aroma of Liberty hops and a toffee aftertaste.",
    4.8, 2.20, "Plenty", "ABC", List("Brown", "Strong"))

  val buttermereBeauty = Drink(BEER, "Buttermere Beauty",
    "Golden pilsner style, made from lager malt, Saaz hops and a lager yeast fermented at cool temperature.",
    4.8, 2.30, "Plenty", "Cumbrian Legendary Ales", List("Lager", "Golden"))

  val seville = Drink(BEER, "Seville",
    "Dark Star's first fruit beer. Made with malted wheat as well as barley and Eldorado hops, matured with a hint of Spanish bitter oranges.",
    4.0, 2.20, "Plenty", "Dark Star", List("Stout / Porter", "Speciality"))
}