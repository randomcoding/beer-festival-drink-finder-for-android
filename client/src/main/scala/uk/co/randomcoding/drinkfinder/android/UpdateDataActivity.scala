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
import android.content.Context
import android.app.DownloadManager
import android.net.Uri
import java.io.File
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpStatus
import java.io.IOException
import uk.co.randomcoding.drinkfinder.android.util.RestDownloader
import java.io.FileOutputStream
import uk.co.randomcoding.drinkfinder.android.util.BackgroundDownloader
import android.content.Intent
import android.util.Log
import uk.co.randomcoding.drinkfinder.android.util.RestDownloader
import java.io.OutputStream

/**
 * Activity view to handle the update of the apps data cache.
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 27 Jul 2012
 */
class UpdateDataActivity extends Activity with TypedActivity {

  private[this] val TAG = "Update Data Activity"

  override def onCreate(state: Bundle) = {
    super.onCreate(state)
    setContentView(R.layout.activity_update_data)
  }

  def updateData(view: View) {
    // TODO: Replace with looked up value (or derived from view data
    val dataUri = "http://192.168.2.15:8080/api/WCBCF/drinks/all"

    // TODO: Add progress monitoring
    val dataFile = new File(getExternalFilesDir(null), "WCBCFData.json")
    Log.d(TAG, "Got File Handle: %s".format(dataFile.getAbsolutePath))

    try {
      val downloadResult = RestDownloader.uriGet(dataUri, new FileOutputStream(dataFile))

      Log.d(TAG, "Downloaded %d bytes".format(downloadResult))

      if (downloadResult > 0) startActivity(new Intent(this, classOf[SearchDrinkActivity])) else Log.e(TAG, "Failed to download Data!")
    }
    catch {
      case e: Exception => Log.e(TAG, "Caught Exception: %s".format(e.getMessage), e)
      // TODO display dialog to tell user download failed.
    }

  }

}