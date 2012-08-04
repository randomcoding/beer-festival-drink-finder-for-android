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

  private type DialogueButtonFunc = (DialogInterface, Int) => Unit

  /**
   * A pair of a `String` and `(DialogInterface, Int) => Unit`.
   *
   * These are used as the input types to create a button on a `Dialog`
   */
  type DialogueButtonParam = (String, DialogueButtonFunc)

  /**
   * The function type used for the dismiss listener of a dialog
   */
  type DismissDialogueFunc = DialogInterface => Unit

  /**
   * Build an `AlertDialog`
   *
   * @param context The Activity Context that is calling this method. Allows the Dialog to be correctly parented.
   * @param message The text to display on the dialogue
   * @param cancelable If `true` the dialogue can be cancelled, or not if `false`
   * @param positiveButton An Optional `DialogueButtonParam` that will set the positive button if defined.
   * @param negativeButton An Optional `DialogueButtonParam` that will set the negative button if defined.
   * @param neutralButton An Optional `DialogueButtonParam` that will set the neutral button if defined.
   */
  def alertDialogue(context: Context, message: String, cancelable: Boolean,
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

  private implicit def functionToDialogueOnClickListener(f: DialogueButtonFunc): DialogInterface.OnClickListener = {
    new DialogInterface.OnClickListener() {
      override def onClick(dialogue: DialogInterface, id: Int) {
        f(dialogue, id)
      }
    }
  }

  private implicit def functionToOnDismissListener(f: DismissDialogueFunc): DialogInterface.OnDismissListener = {
    new DialogInterface.OnDismissListener() {
      override def onDismiss(dialogue: DialogInterface) {
        f(dialogue)
      }
    }
  }
}