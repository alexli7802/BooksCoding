package pinscala.c29

abstract class Database extends FoodCategory {
	def allRecipes: List[Recipe]
  def allFoods: List[Food]
  def foodNamed(name: String): Option[Food] = allFoods.find(f => f.name == name)
}

object SimpleDatabase extends Database with SimpleFoods with SimpleRecipes {
  def allCategories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar))
  )
}

object StudentDatabase extends Database {
  object FrozenFood extends Food("FrozenFood")
  object HeatItUp extends Recipe(
      "heat it up", List(FrozenFood), "Microwave the 'food' for 10 minutes"
    )
  
  def allFoods = List(FrozenFood)
  def allRecipes = List(HeatItUp)
  def allCategories = List(
        FoodCategory("edible", List(FrozenFood))
      )
}