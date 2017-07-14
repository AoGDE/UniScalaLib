package russoul.lib.common.math.geometry.simple

import russoul.lib.common.{immutable, tbsp}
import russoul.lib.common.math.geometry.simple.general.{CenteredShape3, Shape3}
import russoul.lib.common.Implicits._
import russoul.lib.common.TypeClasses.{CanonicalEuclideanSpaceOverField, Field}
import shapeless.Nat
import shapeless.Nat._

/**
  * Created by Russoul on 18.07.2016.
  */
@immutable class PlaneOver[V[_,_ <: Nat],@tbsp F : Field]private(val point:V[F,_3],val normal:V[F,_3])(implicit ev : CanonicalEuclideanSpaceOverField[V,F,_3]) extends Shape3[V[F,_3],F]{


  override def translate(v: V[F,_3]): PlaneOver[V,F] = new PlaneOver[V,F](point, normal)

  override def toString(): String = {
    "Plane( point = " + point + "; normal = " + normal + " )"
  }

}

object PlaneOver{
  def apply[V[_,_ <: Nat],@tbsp F : Field](point:V[F,_3], normal:V[F,_3])(implicit ev : CanonicalEuclideanSpaceOverField[V,F,_3]) = new PlaneOver[V,F](point, normal)
}