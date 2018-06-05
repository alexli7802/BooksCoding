package pinscala.c34

import scala.swing._
import scala.swing.event._

object TempConverter extends SimpleSwingApplication {
  
  val ts = new TextField(5)
  val ls = new Label(" Celsius = ")
  val tf = new TextField(5)
  val lf = new Label(" Fahrenheit")
  
  val panel = new FlowPanel {
    contents += ts
    contents += ls
    contents += tf
    contents += lf
    border = Swing.EmptyBorder(15, 10, 10, 10)
  }
  
  def top = new MainFrame {
    title = "Celsius/Fahrenheit Converter"
    contents = panel
    
    listenTo(ts, tf)
    reactions += {
      case EditDone(`ts`) =>
        val c = ts.text.toDouble
        val f = c * 9 / 5 + 32
        tf.text = "%.2f".format(f)
      case EditDone(`tf`) =>
        val f = tf.text.toDouble
        val c = (f - 32) * 5/ 9
        ts.text = "%.2f".format(c)
    }
  }
}