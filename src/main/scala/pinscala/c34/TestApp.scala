package pinscala.c34

/* KeyNotes:
 * 	- 
 * 
 * */

import scala.swing._

object TestApp extends SimpleSwingApplication {
  
  def top = new MainFrame {
    title = "== c34: GUI Programming =="
    contents = new Button { text = "Click me" }
  }
}