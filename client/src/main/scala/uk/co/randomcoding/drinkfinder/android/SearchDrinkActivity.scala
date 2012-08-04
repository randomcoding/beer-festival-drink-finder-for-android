/**
 * Copyright (C) 2012 RandomCoder <randomcoder@randomcoding.co.uk>
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
 *    RandomCoder - initial API and implementation and/or initial documentation
 */
package uk.co.randomcoding.drinkfinder.android

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.content.Intent

/**
 * Activity to manage entering search parameters for drink finding
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 11 Jul 2012
 *
 */
class SearchDrinkActivity extends Activity with TypedActivity {
  import SearchDrinkActivity._
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_drink)
  }

  def searchDrinks(view: View) {
    val intent = new Intent(this, classOf[DisplayResultsActivity])
    intentExtras foreach (extra => intent.putExtra(extra._1, extra._2))

    startActivity(intent)
  }

  private[this] def intentExtras: Map[String, String] = {
    // TODO get input parameters and activate results activity
    val nameSearch = findView(TR.drinkNameSearchEntry).getText.toString
    val descriptionSearch = findView(TR.drinkDescriptionSearchEntry).getText.toString

    Map(NAME_SEARCH_EXTRA -> nameSearch,
      DESCRIPTION_SEARCH_EXTRA -> descriptionSearch,
      FESTIVAL_ID_EXTRA -> "WCBCF")
  }
}

object SearchDrinkActivity {
  private def extra(key: String): String = "uk.co.randomcoding.android.drinkfinder.%s".format(key)

  val NAME_SEARCH_EXTRA = extra("NameSearch")
  val DESCRIPTION_SEARCH_EXTRA = extra("DescriptionSearch")
  val FESTIVAL_ID_EXTRA = extra("WCBCF")
}