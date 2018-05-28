package pinscala.c26

import scala.util.matching.Regex

/*
 *	1. Extractor, Regex can be greatly helpful when dealing with: file-path, domain-hierarchy, email-address. (maybe: dsl-syntax-tree ...)  
 */

object EmailAddr {
  def apply(userName: String, domName: String) = 
    userName + "@" + domName
    
  def unapply(addr: String): Option[(String, String)] = {
	  val parts = addr.split("@")
	  if (parts.length == 2) Some((parts(0), parts(1))) else None
  } 
}

object Extractor {

  def extractDecimalNumbers(): Unit = {
	  val DecimalNumber = new Regex("""(-)?(\d+)(\.\d*)?""")     // regex for 'decimal numbers'
    DecimalNumber.findAllIn("-2a3.5b6 is my number").foreach(println)
    val DecimalNumber(a,b,c) = DecimalNumber.findFirstIn("your id is -23.12").get
    println(s"$a, $b, $c")
  }
  
  def extractEmailAddr(): Unit = {
    "alex.xin.li@sohu.com" match {
      case EmailAddr(u, d) => println(s"user-name: $u, domain: $d")
      case _ => println("not a valid email-address!")
    }
  }

  // app entry-point:
  def main(args: Array[String]): Unit = {

    println("Testing Scala.Extractor!")
    
    extractDecimalNumbers()
    extractEmailAddr()
  }
}