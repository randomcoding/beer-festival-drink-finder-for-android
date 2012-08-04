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

import android.content.DialogInterface
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context

/**
 * Helper functions for use with `Dialog`s
 *
 * @author RandomCoder <randomcoder@randomcoding.co.uk>
 *
 * Created On: 4 Aug 2012
 */
object DialogueHelpers {

  type DialogueButtonFunc = (DialogInterface, Int) => Unit
  type DialogueButtonParam = (String, DialogueButtonFunc)
  type DismissDialogueFunc = DialogInterface => Unit

  def buildAlertDialogue(context: Context, message: String, cancelable: Boolean,
    positiveButton: Option[DialogueButtonParam],
    negativeButton: Option[DialogueButtonParam] = None,
    neutralButton: Option[DialogueButtonParam] = None,
    dismissFunction: Option[DismissDialogueFunc] = None): Dialog = {
    var builder = new AlertDialog.Builder(context).setMessage(message).setCancelable(cancelable)

    builder = positiveButton match {
      case Some((text, f)) => builder.setPositiveButton(text, f)
      case _ => builder
    }

    dismissFunction match {
      case Some(f) => {
        val dialogue = builder.create
        dialogue.setOnDismissListener(f)
        dialogue
      }
      case _ => builder.create
    }
  }

  implicit def functionToDialogueOnClickListener(f: (DialogInterface, Int) => Unit): DialogInterface.OnClickListener = {
    new DialogInterface.OnClickListener() {
      override def onClick(dialogue: DialogInterface, id: Int) {
        f(dialogue, id)
      }
    }
  }

  implicit def functionToOnDismissListener(f: (DialogInterface) => Unit): DialogInterface.OnDismissListener = {
    new DialogInterface.OnDismissListener() {
      override def onDismiss(dialogue: DialogInterface) {
        f(dialogue)
      }
    }
  }
}