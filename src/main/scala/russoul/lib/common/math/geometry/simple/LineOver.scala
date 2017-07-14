package russoul.lib.common.math.geometry.simple

import russoul.lib.common.TypeClasses._
import russoul.lib.common.TypeClasses.CanonicalEuclideanSpaceOverField
import russoul.lib.common.Implicits._
import russoul.lib.common.immutable
import russoul.lib.common.math.algebra.Mat
import russoul.lib.common.math.geometry.simple.general.{CenteredShape3, Shape3}
import shapeless.Nat._
import russoul.lib.common._
import shapeless.Nat

/**
  * Created by Russoul on 18.07.2016.
  */
@immutable class LineOver[V[_,_ <: Nat], @tbsp F : Field]private(val start:V[F,_3], val end:V[F,_3])(implicit ev: CanonicalEuclideanSpaceOverField[V,F,_3], tensor1: Tensor1[F,V,_3])  extends Shape3[V[F,_3],F] {

  override def translate(v: V[F,_3]): LineOver[V,F] = {
    new LineOver(start + v, end + v)
  }

  def genDir(): V[F,_3] = (end - start).normalize()

  def genRay() = RayOver[V,F](start, genDir())

  override def toString(): String = {
    "Line(start = " + start + ";end = " + end + " )"

  }
}

object LineOver
{
  def apply[V[_,_ <: Nat], @tbsp F : Field](pos: V[F,_3], start: F, end: F, yaw: F, pitch: F)(implicit ev: CanonicalEuclideanSpaceOverField[V,F,_3], c: ConvertibleFromDouble[F], tensor1: Tensor1[F,V,_3]): LineOver[V,F] = {
    val alpha = -yaw
    val t = ev.scalar.toRadians(90D.as[F] - alpha)
    val cosT = ev.scalar.cos(t)
    val sinT = ev.scalar.sin(t)
    val t2 = ev.scalar.toRadians(pitch)
    val sinT2 = ev.scalar.sin(t2)
    val cosT2 = ev.scalar.cos(t2)

    val k = makeVector(_3, cosT * cosT2, sinT2, -sinT * cosT2)

    val p1 = k * start + pos
    val p2 = k * end + pos

    new LineOver(p1, p2)
  }

  def apply[V[_,_ <: Nat], @tbsp F : Field](start:V[F,_3], end:V[F,_3])(implicit ev: CanonicalEuclideanSpaceOverField[V,F,_3], tensor1: Tensor1[F,V,_3]) = new LineOver[V,F](start, end)
}