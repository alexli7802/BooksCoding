package pinscala.c29

/*  KeyNotes:
 *  - Divide system into modules for separate compiling
 *  - Easy change of implementation modules in particular context  
 *    (testing: Unit -> Integration -> Deployment)
 *  - Modularization essentials:
 *  	1. separation of interface and implementation
 *  	2. switch modules without re-building
 *  	3. dependency injection (wire-up modules via XML configuration), like Spring, Guice
 */

/* Takeaways:
 *  - Scala object is commonly used as a 'module'
 *  - use 'abstract class' for interface, and 'object' for implementation
 *  - split all functions into multiple 'traits', if necessary
 */

object TestApp {
  
  // based on app.conf, you wire-up different modules to assemble your application
  def test(args: Array[String]): Unit = {
    val opt = args.headOption.getOrElse("1").toInt

    val backendDB: Database = if (opt == 1) SimpleDatabase else StudentDatabase
    object browserCli extends BrowserClient {
      val db = backendDB
    }
    
    backendDB.allFoods.foreach(f => {
      println(s"$f => ${browserCli.recipeUsing(f)}")
    })
  }
  
  
  def main(args: Array[String]): Unit = {
    
    println("========== Testing examples in chapter-29 ==========")
    test(args)
  }
}