package example.cp

import scala.scalajs.js
import example.cp
import scala.scalajs.js.annotation.JSName

@JSName("cp")
object ForwardCp extends js.Object{

  def momentForBox(m: Num, width: Num, height: Num): Num = ???
  def areaForPoly(verts: js.Array[Num]): Num = ???
  def momentForPoly(m: Num, verts: js.Array[Num], offset: Vect): Num = ???
  def centroidForPoly(verts: js.Array[Num]): Vect = ???
  def recenterPoly(verts: js.Array[Num]): Unit = ???
}
object Implicits {
  implicit def TupleToVect[A <% js.Number, B <% js.Number](t: (A, B)) = new cp.Vect(t._1, t._2)
  
  implicit class Point(val p: cp.Vect){
    import p._
    def +(other: cp.Vect) = new cp.Vect(x + other.x, y + other.y)
    def -(other: cp.Vect) = new cp.Vect(x - other.x, y - other.y)
    def %(other: cp.Vect) = new cp.Vect(x % other.x, y % other.y)
    def <(other: cp.Vect) = x < other.x && y < other.y
    def >(other: cp.Vect) = x > other.x && y > other.y
    def /(value: Double) = new cp.Vect(x / value, y / value)
    def *(value: Double) = new cp.Vect(x * value, y * value)
    def *(other: cp.Vect) = x * other.x + y * other.y
    def length: Double = Math.sqrt(lengthSquared)
    def lengthSquared = x * x + y * y
    def within(a: cp.Vect, b: cp.Vect, extra: cp.Vect = new cp.Vect(0, 0)) = {
      import math.{min, max}
      x >= min(a.x, b.x) - extra.x &&
        x < max(a.x, b.x) + extra.y &&
        y >= min(a.y, b.y) - extra.x &&
        y < max(a.y, b.y) + extra.y
    }
    def rotate(theta: Double) = {
      val (cos, sin) = (Math.cos(theta), math.sin(theta))
      new cp.Vect(cos * x - sin * y, sin * x + cos * y)
    }
  }
}
