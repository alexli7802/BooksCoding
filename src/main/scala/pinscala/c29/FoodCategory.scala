package pinscala.c29

trait FoodCategory {
  case class FoodCategory(name: String, foods: List[Food])
  def allCategories: List[FoodCategory]
}

trait SimpleFoods {
  object Apple extends Food("Apple")
  object Orange extends Food("Orange")
  object Cream extends Food("Cream")
  object Sugar extends Food("Sugar")
  object Pear extends Food("Pear")
  
  def allFoods = List(Apple, Pear)
}

// as SimpleRecipes has a dependency on 'SimpleFoods', so they need to be
// mixed-in together.
// solution: 'self type'
trait SimpleRecipes {
  this: SimpleFoods =>
  
  object FruitSalad extends Recipe(
    "fruit salad", List(Apple, Pear), "Stir it all together"  )
  
  def allRecipes: List[Recipe] = List(FruitSalad)
}