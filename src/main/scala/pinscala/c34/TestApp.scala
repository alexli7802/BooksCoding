package pinscala.c34

/* KeyNotes:
 * 	- 'MainFrame'   (closing it will shut down the GUI application)
 * 		1. title bar 
 *		2. container: add/remove components/contents 
 * 	- 'Event Handling'   (publish/subscribe approach)
 * 		1. 'trait Event' is scala way of 'publishing events'
 * */

import scala.swing._
import scala.swing.event.ButtonClicked

object TestApp extends SimpleSwingApplication {
  
  def top = new MainFrame {
    title = "== c34: GUI Programming =="
    
    val btn = new Button { text = "Click me" }
    val lab = new Label { text = "No button clicks registered" }
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += btn
      contents += lab
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
    
    listenTo(btn)
    var clckCount = 0
    reactions += {
      case ButtonClicked(b) => {
        clckCount += 1
        lab.text = "clicks: " + clckCount
      }
    }
  }
  
  
}