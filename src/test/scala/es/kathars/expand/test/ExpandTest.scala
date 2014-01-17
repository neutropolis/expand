package es.kathars.expand.test

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import es.kathars.expand._

class ExpandTest extends FunSpec with ShouldMatchers {

  @expand(3) class MyTupleα[Tα](val _α: Tα)

  describe("MyTupleα") {

    it("should generate a valid 1-tuple") {
      val t1: MyTuple1[Int] = new MyTuple1(1)
      t1._1 should be(1)
      // t1._2 => error! 
    }

    it("should generate a valid 2-tuple") {
      val t2: MyTuple2[Int, String] = new MyTuple2(2, "two")
      t2._1 should be(2)
      t2._2 should be("two")
      // t2._3 => error!
    }

    it("should generate a valid 3-tuple") {
      val t3: MyTuple3[Int, String, String] = new MyTuple3(3, "three", "III")
      t3._1 should be(3)
      t3._2 should be("three")
      t3._3 should be("III")
      // t4._4 => error!
    }
  }
}
