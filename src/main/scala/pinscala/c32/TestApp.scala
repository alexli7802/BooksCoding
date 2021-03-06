package pinscala.c32

/*  KeyNotes:
 *   - scala.concurrent.Future[+T]       v.s. java.util.concurrent.Future[V]
 *   - scala.concurrent.ExecutionContext v.s. java.util.concurrent.ExecutorService
 *   
 *   - java.util.concurrent: 
 *     1. 'data sharing' -> 'race conditions' 
 *     2. 'synchronize' -> 'lock issues: deadlock'
 */

/*
 *  Takeaways:
 *   - use scala.concurrent.Future[+T] to avoid blocking
 *   - in testing, we should accept 'blocking', like 'org.scalatest.concurrent.ScalaFutures'
 * */

object TestApp {

	import scala.concurrent.Future
	import scala.util.{Try,Success,Failure}
	import scala.concurrent.ExecutionContext.Implicits.global

	def test_future1(): Unit = {
    
    val f0 = Future {
      for (i <- 1 to 3 ) {
    	  Thread.sleep(3000)
    	  println("\t\t---- computing")
      }
      10000
    }
    
    val f1 = f0.map(v => (v, v * 0.15))
    
    while (!f1.isCompleted) { Thread.sleep(2000); println("waiting...")}
    f1.value.get match {
      case Success(v) => println("the result is: " + v)
      case Failure(e) => e.printStackTrace()
    }
  }
	
	def test_future2(): Unit = {
	  val f0 = Future {
	    for (i <- 0 to 4) { Thread.sleep(4000); println("\tf0 is under computing") }  
	    51000
	  }
	  
	  val f1 = Future {
	    for (i <- 0 to 6) { Thread.sleep(4000); println("\t\tf1 is under computing") }  
	    0.012
	  }
	  
	  val f = for {
	    a <- f0
	    b <- f1
	  } yield (a * b)
	  
    while (!f.isCompleted) { Thread.sleep(2000); println("waiting...")}
    f.value.get match {
      case Success(v) => println("the result is: " + f)
      case Failure(e) => e.printStackTrace()
    }
	  
	}
  
	def test_future3(): Unit = {
	  val f0 = Future { 3 }

	  val f1 = f0.filter(_ > 0)
	  
	  val f2 = for {
	    v <- f0
	    if (v < 0)
	  } yield v
	  
	  val f3 = f0.collect {
	    case v if v > 0 => v * 2
	  }
	  
	  Thread.sleep(1000)
	  println(f3)
	}
	
	def test_failed_futures(): Unit = {
	  val fgood = Future { 5 / 2 }
	  val fbad1 = Future { 4 / 2 }
	  val fbad2 = Future { throw new RuntimeException("Boom!!") }
	  
	  val fr1 = fbad2.recover {
	    case ex: ArithmeticException => -1
	  }
	  
	  val fret = fbad1.fallbackTo(fbad2)

	  Thread.sleep(500)
	  println(fr1.value)
	}

	def test_transform(): Unit = {
	  val f = Future { 10 + 2 }
	  val ft = f.transform(i => i / 0, ex => ex)
	  Thread.sleep(100)
	  println(ft.value)
	}
	
	def test_future4(): Unit = {
	  val fa = Future { Thread.sleep(4000); 39.7 / 0 }
	  val fb = Future { Thread.sleep(6000); 26.8 }
	  val fc = fa.zip(fb).map(v => v._1 + v._2)

/*	  
	  val fseq = Future.sequence(List(fa,fb,fc))

	  fseq.onComplete {
	    case Success(tup) => println(tup)
	    case Failure(e) => e.printStackTrace()
	  }
*/

/*
	  fa.foreach(println)
*/
	  
	  val fd = fa.andThen {
	    case Success(age) => throw new RuntimeException("BAD!!!")
	  }.andThen {
	    case Success(age) => println("check: age=" + age)
	    case Failure(ex) => ex.printStackTrace()
	  }.andThen {
    	case Success(age) => println("check again: age=" + age) 
    	case Failure(ex) => ex.printStackTrace()
  	}
	  
	  while (!fd.isCompleted) {
	    Thread.sleep(1000)
	    println("waiting")
	  }
	  println("Future is completed!")
	}
	
	def test_nested_futures(): Unit = {
	  val incomeTax = Future {
	    val tax = Future {
	      Thread.sleep(2000)
	      val factor = 0.25
	    }
	    tax	    
	  }
	    
	  incomeTax.flatten.foreach(println)
	}
	
	def test_await(): Unit = {
	  val f0 = Future {
	    Thread.sleep(4000)
	    23.9
	  }
	  
	  import scala.concurrent.Await
	  import scala.concurrent.duration.Duration
	  val f1 = Await.ready(f0, Duration.Inf)
	  
	  if (f1.isCompleted) 
	    println("result is " + f1.value.get.get ) 
	  else 
	    println("time-out: still not completed!")
	}
	
  def main(args: Array[String]): Unit = {
    
    println("========== Testing examples in chapter-32 ==========")
    test_await()
  }
}