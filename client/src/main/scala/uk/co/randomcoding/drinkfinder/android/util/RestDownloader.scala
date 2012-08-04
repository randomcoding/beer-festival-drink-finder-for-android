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

import java.io.ByteArrayOutputStream
import org.apache.http.HttpStatus
import org.apache.http.impl.client.DefaultHttpClient
import java.io.IOException
import org.apache.http.client.methods.HttpGet
import java.io.OutputStream
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import android.util.Log

/**
 * Download data from a rest endpoint
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 1 Aug 2012
 */
object RestDownloader {

  private[this] def TAG = "Rest Downloader"

  /**
   * Performs a `Http GET` request with the given Uri and writes the response to
   * the provided `OutputStream`
   *
   * @throws IOException If the URI cannot be accessed or the `OutputStream` accessed
   */
  def uriGet(uri: String, outStream: OutputStream): Long = {
    Log.d(TAG, "Accessing Uri: %s".format(uri))

    val downloader = new DefaultHttpClient()
    val response = downloader.execute(new HttpGet(uri))
    Log.d(TAG, "Performed Request")
    val statusLine = response.getStatusLine
    statusLine.getStatusCode match {
      case HttpStatus.SC_OK => {
        response.getEntity.writeTo(outStream)
        outStream.close()
        Log.d(TAG, "Received Data: %s".format(outStream.toString))

        response.getEntity.getContentLength
      }
      case _ => {
        response.getEntity.getContent.close
        throw new IOException(statusLine.getReasonPhrase)
      }
    }
  }
}