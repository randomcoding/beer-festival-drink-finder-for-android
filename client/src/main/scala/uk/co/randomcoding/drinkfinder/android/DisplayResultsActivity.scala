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

/**
 * Activity to get and display all search results
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
class DisplayResultsActivity extends Activity with TypedActivity {

  private[this] def TAG = "Display Results Activity"

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

    val intent = getIntent
    val festivalId = intent.getStringExtra(FESTIVAL_ID_EXTRA)

    val dataFile = festivalDataFile(this, festivalId)
    val nameSearch = intent.getStringExtra(NAME_SEARCH_EXTRA)
    val descriptionSearch = intent.getStringExtra(DESCRIPTION_SEARCH_EXTRA)
    Log.d(TAG, "Name: %s; Description: %s; Festival: %s".format(nameSearch, descriptionSearch, festivalId))
  }
}