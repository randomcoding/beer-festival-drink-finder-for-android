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

import java.io.{ FileOutputStream, File }

import uk.co.randomcoding.drinkfinder.android.util.DialogueHelpers.alertDialogue
import uk.co.randomcoding.drinkfinder.android.util.RestDownloader
import uk.co.randomcoding.drinkfinder.android.util.ExternalStorageHelper._

import android.app.{ Dialog, Activity }
import android.content.{ Intent, DialogInterface }
import android.os.Bundle
import android.util.Log
import android.view.View

/**
 * Activity view to handle the update of the apps data cache.
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 27 Jul 2012
 */
class UpdateDataActivity extends Activity with TypedActivity {

  private[this] val TAG = "Update Data Activity"

  private[this] val DOWNLOAD_SUCCESS_DIALOGUE = 1
  private[this] val DOWNLOAD_FAILED_DIALOGUE = 2

  override def onCreate(state: Bundle) = {
    super.onCreate(state)
    setContentView(R.layout.activity_update_data)
  }

  def updateData(view: View) {
    // TODO: Replace with looked up value (or derived from view data
    val festivalId = "WCBCF"
    val dataUri = "http://192.168.2.15:8080/api/%s/drinks/all".format(festivalId)

    // TODO: Add progress monitoring
    val dataFile = festivalDataFile(this, festivalId)
    Log.d(TAG, "Got File Handle: %s".format(dataFile.getAbsolutePath))

    try {
      val downloadResult = RestDownloader.uriGet(dataUri, new FileOutputStream(dataFile))

      Log.d(TAG, "Downloaded %d bytes".format(downloadResult))

      if (downloadResult > 0) {
        showDialog(DOWNLOAD_SUCCESS_DIALOGUE)
      }
      else {
        Log.e(TAG, "Failed to download Data!")
        showDialog(DOWNLOAD_FAILED_DIALOGUE)
      }
    }
    catch {
      case e: Exception => {
        Log.e(TAG, "Caught Exception: %s".format(e.getMessage), e)
        showDialog(DOWNLOAD_FAILED_DIALOGUE)
      }
    }

  }

  override protected def onCreateDialog(dialogueId: Int): Dialog = {
    dialogueId match {
      case DOWNLOAD_SUCCESS_DIALOGUE => {
        val switchToSearchFunc = (d: DialogInterface, id: Int) => startActivity(new Intent(this, classOf[SearchDrinkActivity]))
        alertDialogue(this, "Successfully Downloaded Festival Data", false, Some(("Ok", switchToSearchFunc)))
      }
      case DOWNLOAD_FAILED_DIALOGUE => {
        val switchToMainFunc = (d: DialogInterface, id: Int) => startActivity(new Intent(this, classOf[MainActivity]))
        alertDialogue(this, "Failed to Downloaded Festival Data", false, Some(("Ok", switchToMainFunc)))
      }
      case _ => {
        Log.e(TAG, "Unknown Dialogue Id: %d".format(dialogueId))

        null
      }
    }
  }

}