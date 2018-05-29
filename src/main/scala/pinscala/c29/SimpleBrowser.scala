package pinscala.c29

abstract class BrowserClient {
  val db: Database
  
  def recipeUsing(food: Food) = db.allRecipes.filter(r => 
    r.ingredients.contains(food))
    
  def displayCategory(cat: SimpleDatabase.FoodCategory) = {
    println(cat)
  }
  
}


object SimpleBrowser extends BrowserClient {
  val db: Database = SimpleDatabase
}

object StudentBrowser extends BrowserClient {
  val db: Database = StudentDatabase
}