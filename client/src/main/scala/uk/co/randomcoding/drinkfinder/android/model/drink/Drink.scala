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

import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONArray
import android.util.Log
import scala.util.parsing.json.JSONObject

/**
 * Describes a drink
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
case class Drink(drinkType: DrinkType.drinkType, name: String, description: String, abv: Double, price: Double, remaining: String, brewer: String, features: List[String])

/**
 * Provides parsing of drink JSON data into a `List[Drink]`
 */
object Drink {
  private[this] val TAG = "Drink Parser"

  def fromJson(jsonData: String): Seq[Drink] = {
    JSON.parseFull(jsonData) match {
      case Some(data) => data match {
        case jsonDrinks: List[_] => jsonDrinks.map(_ match {
          case drinkMap: Map[_, _] => Some(drinkFromMap(drinkMap map (entry => (entry._1.toString -> entry._2))))
          case _ => None
        }).filter(_.isDefined).map(_.get)
        case drinkMap: Map[_, _] => Seq(drinkFromMap(drinkMap map (entry => (entry._1.toString -> entry._2))))
        case other => {
          Log.e(TAG, "Unknown JSON Object: %s".format(other))
          Nil
        }
      }
      case failedParse => {
        Log.e(TAG, "Failed to Parse:\n%s".format(failedParse))
        Nil
      }
    }
  }

  private[this] def drinkFromMap(drinkMap: Map[String, Any]): Drink = {
    val drinkType = drinkMap("drinkType").toString
    val drinkName = drinkMap("name").toString
    val description = drinkMap("description").toString
    val abv = drinkMap("abv").toString.toDouble
    val price = drinkMap("price").toString.drop(1).toDouble
    val remaining = drinkMap("remaining").toString
    val brewer = drinkMap("brewer").toString
    val features = drinkMap("features").asInstanceOf[List[String]]

    Drink(DrinkType(drinkType), drinkName, description, abv, price, remaining, brewer, features)
  }
}
object DrinkType extends Enumeration("Beer", "Cider", "Perry", "Wine") {
  type drinkType = Value

  val BEER, CIDER, PERRY, WINE = Value

  def apply(typeString: String): drinkType = typeString.toLowerCase match {
    case "beer" => BEER
    case "cider" => CIDER
    case "perry" => PERRY
    case "wine" => WINE
  }
}