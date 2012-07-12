package uk.co.randomcoding.drinkfinder.android.tests

import android.test.{AndroidTestCase, ActivityInstrumentationTestCase2}
import junit.framework.Assert.assertEquals
import uk.co.randomcoding.drinkfinder.android.MainActivity
import uk.co.randomcoding.drinkfinder.android.TR

class AndroidTests extends AndroidTestCase {
  def testPackageIsCorrect() {
    assertEquals("uk.co.randomcoding.drinkfinder.android", getContext.getPackageName)
  }
}

class ActivityTests extends ActivityInstrumentationTestCase2(classOf[MainActivity]) {
   def testHelloWorldIsShown() {
      val activity = getActivity
      /*val textview = activity.findView(TR.textview)
      assertEquals(textview.getText, "hello, world!")*/
    }
}
