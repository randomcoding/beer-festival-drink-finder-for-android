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

import android.view.View
import android.app.Activity
import android.content.Intent
import android.util.Log

/**
 * Helpers for use with Views
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 5 Aug 2012
 */
object ViewHelpers {

  /**
   * Implicit construction of an `OnClickListener` from a `View => Unit` function
   */
  implicit def funcToOnCLickListener(f: View => Unit): View.OnClickListener = {
    new View.OnClickListener() {
      override def onClick(view: View) = f(view)
    }
  }

  val openActivity: (Activity, Class[_ <: Activity], Map[String, Any]) => Unit = (context, targetActivityClass, extraData) => {
    val intent = new Intent(context, targetActivityClass)
    extraData.foreach(entry => entry._2 match {
      case s: String => intent.putExtra(entry._1, s)
      case d: Double => intent.putExtra(entry._1, d)
      case other => Log.e("Open Activity: %s".format(targetActivityClass.getName), "Unhandled extra type: %s".format(other))
    })

    context.startActivity(intent)
  }
}