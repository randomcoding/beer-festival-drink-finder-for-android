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

import uk.co.randomcoding.drinkfinder.android.SearchDrinkActivity.{ NAME_SEARCH_EXTRA, DESCRIPTION_SEARCH_EXTRA }
import uk.co.randomcoding.drinkfinder.android.model.drink.Drink

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * Loads the current drink data from a file and finds matching entries based on the extra data passed in the bundle.
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
object DrinkSearcher {
  private[this] val TAG = "Drink Searcher"

  private[this] val searchExtraKeys = Seq(NAME_SEARCH_EXTRA, DESCRIPTION_SEARCH_EXTRA)

  private[this] val drinkNameSearch = (drink: Drink, name: String) => drink.name.toLowerCase.contains(name.toLowerCase)
  private[this] val drinkDescriptionSearch = (drink: Drink, words: Seq[String]) => words.filterNot(word => drink.description.toLowerCase.contains(word.toLowerCase)).isEmpty

  private[this] val matchAny = (drink: Drink) => true

  def getMatchingDrinks(activity: Activity, dataFile: File, searchData: Map[String, String], loadFailedDialogueId: Int): Seq[Drink] = {
    val drinkData = loadDrinks(activity, dataFile, loadFailedDialogueId)
    val searchFuncs = getSearchFuncs(searchData)

    drinkData.filter(allSearchesMatch(_, searchFuncs))
  }

  private[this] def allSearchesMatch(drink: Drink, searchFunctions: Seq[Drink => Boolean]): Boolean = {
    searchFunctions.filter(_(drink) == false).isEmpty
  }

  private[this] def getSearchFuncs(extras: Map[String, String]): Seq[Drink => Boolean] = {
    for {
      key <- searchExtraKeys
      searchFuncOpt = extras.get(key) match {
        case Some(v) => key match {
          case NAME_SEARCH_EXTRA => Some(drinkNameSearch(_: Drink, v))
          case DESCRIPTION_SEARCH_EXTRA => Some(drinkDescriptionSearch(_: Drink, v.replaceAll("""[,._-]""", " ").split("""\s""")))
          case _ => {
            Log.e(TAG, "Unhandled key: %s".format(key))
            None
          }
        }
        case _ => None
      }
      if searchFuncOpt.isDefined
    } yield { searchFuncOpt.get }
  }

  private[this] def loadDrinks(activity: Activity, dataFile: File, loadFailedDialogueId: Int): Seq[Drink] = {
    try {
      JsonDrinkDataLoader.loadJson(dataFile)
    }
    catch {
      case e: Exception => {
        Log.e(TAG, "Failed to parse data file %s".format(dataFile.getAbsolutePath), e)
        activity.showDialog(loadFailedDialogueId)
        Nil
      }
    }
  }
}