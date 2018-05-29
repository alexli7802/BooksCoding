package pinscala.c29

abstract class Food(val name: String) {
  override def toString = name
}


/////////////////////////////////////////////////////////////////////////////
class Recipe(
  val name: String,
  val ingredients: List[Food],
  val instructions: String
) {
  override def toString = name
}
