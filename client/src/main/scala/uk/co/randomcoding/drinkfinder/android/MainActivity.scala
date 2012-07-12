package uk.co.randomcoding.drinkfinder.android

import android.app.Activity
import android.os.Bundle
import uk.co.randomcoding.drinkfinder.android._
import android.view.View

class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)
  
  }
  
  def showSearchDrinks(view: View) {
	  // TODO: create intent to switch to the search view
  }
  
  def updateData(view: View) {
	  // TODO: Update the data from the online store and save in zipped file
  }
  
  def showWishList(view: View) {
	  // TODO: Create intent to switch to the wishlist view
  }
}
