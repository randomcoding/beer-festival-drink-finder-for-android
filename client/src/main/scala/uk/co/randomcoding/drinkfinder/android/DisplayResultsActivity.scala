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

import uk.co.randomcoding.drinkfinder.android.SearchDrinkActivity.{ NAME_SEARCH_EXTRA, FESTIVAL_ID_EXTRA, DESCRIPTION_SEARCH_EXTRA }
import uk.co.randomcoding.drinkfinder.android.model.drink.Drink
import uk.co.randomcoding.drinkfinder.android.util.DrinkSearcher._
import uk.co.randomcoding.drinkfinder.android.util.ExternalStorageHelper._
import uk.co.randomcoding.drinkfinder.android.util.ViewHelpers._
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import android.view.View
import android.content.Intent
import android.widget.TableLayout
import android.widget.ArrayAdapter
import android.app.ListActivity

/**
 * Activity to get and display all search results
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
class DisplayResultsActivity extends ListActivity with TypedActivity {

  val DISPLAY_DRINK_NAME_EXTRA = "uk.co.randomcoding.android.drinkfinder.display_drink_name"

  private[this] def TAG = "Display Results Activity"

  val FAILED_TO_PARSE_DIALOGUE_ID = 1

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_display_results)
    val intent = getIntent
    val festivalId = intent.getStringExtra(FESTIVAL_ID_EXTRA)

    val dataFile = festivalDataFile(this, festivalId)

    val matchingDrinks = getMatchingDrinks(this, dataFile, intent.getExtras, FAILED_TO_PARSE_DIALOGUE_ID).toList

    displayResults(matchingDrinks)
  }

  private[this] implicit def bundleToSearchMap(b: Bundle): Map[String, String] = {
    Map(NAME_SEARCH_EXTRA -> b.getString(NAME_SEARCH_EXTRA),
      DESCRIPTION_SEARCH_EXTRA -> b.getString(DESCRIPTION_SEARCH_EXTRA))
  }

  private[this] def displayResults(drinks: List[Drink]) {
    val titleText = findView(TR.drinkResultsTitle)
    titleText.setText("Search Results: (%d)".format(drinks.size))

    val drinkTitles = drinks match {
      case Nil => Array("There are no drinkd matching your search")
      case _ => drinks.map(drinkToText).toArray
    }

    setListAdapter(new ArrayAdapter[String](this, R.id.drinkResultsContentText, drinkTitles))
  }

  private[this] def drinkToText(drink: Drink): String = {
    val descriptionText = drink.description.trim match {
      case "" => ""
      case description => "Description: %s\n".format(description)
    }

    // setup variable display entries
    val abvEntry = drink.abv match {
      case 0.0 => ("", "")
      case abv => ("ABV", "%.1f%%".format(abv))
    }

    val priceEntry = drink.price match {
      case 0.0 => ("", "")
      case price => ("Price", "£%.2f".format(price))
    }

    val variableText = Seq(abvEntry, priceEntry).map(_ match {
      case ("", "") => ""
      case (label, text) => "%s: %s".format(label, text)
    }).mkString("   ")

    "%s\n%s%s".format(drink.name, descriptionText, variableText)
  }
}