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
package uk.co.randomcoding.drinkfinder.android

import android.app.Activity
import android.os.Bundle
import SearchDrinkActivity._
import util.ExternalStorageHelper._
import android.util.Log
import uk.co.randomcoding.drinkfinder.android.model.drink.Drink
import java.io.File
import uk.co.randomcoding.drinkfinder.android.util.JsonDrinkDataLoader

/**
 * Activity to get and display all search results
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
class DisplayResultsActivity extends Activity with TypedActivity {

  private[this] def TAG = "Display Results Activity"

  private[this] val FAILED_TO_PARSE_DIALOGUE_ID = 1

  private[this] val searchExtraKeys = Seq(NAME_SEARCH_EXTRA, DESCRIPTION_SEARCH_EXTRA)

  private[this] val drinkNameSearch = (drink: Drink, name: String) => drink.name.toLowerCase.contains(name.toLowerCase)
  private[this] val drinkDescriptionSearch = (drink: Drink, words: Seq[String]) => words.filterNot(word => drink.description.toLowerCase.contains(word.toLowerCase)).isEmpty

  private[this] val matchAny = (drink: Drink) => true

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val intent = getIntent
    val festivalId = intent.getStringExtra(FESTIVAL_ID_EXTRA)

    val dataFile = festivalDataFile(this, festivalId)

    val nameSearch = intent.getStringExtra(NAME_SEARCH_EXTRA)
    val descriptionSearch = intent.getStringExtra(DESCRIPTION_SEARCH_EXTRA)
    val searchFuncs = getSearchFuncs(intent.getExtras)
    Log.d(TAG, "Name: %s; Description: %s; Festival: %s".format(nameSearch, descriptionSearch, festivalId))

    val drinkData = loadDrinks(dataFile)

    val matchingDrinks = drinkData.filter(allSearchesMatch(_, searchFuncs))
  }

  private[this] def displayResults(drinks: Seq[Drink]) {
    // TODO: Clear display and create new entries
  }

  private[this] def allSearchesMatch(drink: Drink, searchFunctions: Seq[Drink => Boolean]): Boolean = {
    searchFunctions.filter(_(drink) == false).isEmpty
  }

  private[this] def getSearchFuncs(extras: Bundle): Seq[Drink => Boolean] = {
    for {
      key <- searchExtraKeys
      if extras.containsKey(key)
      searchFuncOpt = key match {
        case NAME_SEARCH_EXTRA => Some(drinkNameSearch(_: Drink, extras.getString(key)))
        case DESCRIPTION_SEARCH_EXTRA => Some(drinkDescriptionSearch(_: Drink, extras.getString(key).replaceAll("""[,._-]""", " ").split("""\s""")))
        case _ => {
          Log.e(TAG, "Unhandled key: %s".format(key))
          None
        }
      }
      if searchFuncOpt.isDefined
    } yield { searchFuncOpt.get }
  }

  private[this] def loadDrinks(dataFile: File): Seq[Drink] = {
    try {
      JsonDrinkDataLoader.loadJson(dataFile)
    }
    catch {
      case e: Exception => {
        Log.e(TAG, "Failed to parse data file %s".format(dataFile.getAbsolutePath), e)
        showDialog(FAILED_TO_PARSE_DIALOGUE_ID)
        Nil
      }
    }
  }
}