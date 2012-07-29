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

import android.view.View
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.app.Activity
import TypedResource._

/**
 * Activity view to handle the update of the apps data cache.
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 27 Jul 2012
 */
class UpdateDataActivity extends Activity with TypedActivity {

  override def onCreate(state: Bundle) = {
    super.onCreate(state)
    setContentView(R.layout.activity_update_data)

    val festivalSelect: Spinner = findView(TR.selectFestival)

    festivalSelect.setAdapter(createSpinnerAdapter)

    // TODO: Add listener - implement new class that takes funcs for each of the common activities
    // and provides empty defaults for all.
    // Also needs to implement common functionality such as selection getters etc.
  }

  private[this] def createSpinnerAdapter: ArrayAdapter[CharSequence] = {
    ArrayAdapter.createFromResource(this, R.array.available_festival_names, android.R.layout.simple_spinner_dropdown_item)
  }

}