package core

import java.io.File

import grapevineType.BottomType.BottomType
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/17/15.
 */

object C {
  def apply(t: (Map[String, Any], Int, Int)) : C  = new C(t._1, t._2, t._3)
  def apply(filePath:String) : C = C(ContextSummary.loadJsonAll(filePath))
}

case class C(var m: Map[String, Any], var j:Int = 0, var jc:Int = 0) extends ContextSummary(m, j, jc) {
  override def getSizes(): (Int, Int, Int) = ???
  override def getSize(): Int = ???
  override def get(key: String): Any = ???
  override def setup(dict: Map[String, Any]): Unit = ???
  override def serialize(): Array[Byte] = ???
}

class TestContextSummary extends FunSuite with BeforeAndAfter {
  var c: C = _
  before {
    val filePath = "contextSummary/src/test/scala/resource/unittest.json"
    c = C(filePath)
  }

  test("test getKeys") {
    assert(c.getKeys().toSet == Set("date", "latitude", "message", "time"))
  }

  test("test map values") {
    //    {
    //      "latitude": [10, 10, 10, 10],
    //      "message": ,
    //      "time": [11, 11],
    //      "date":[2014, 10, 1]
    //    }
    val m = c.getJsonMap()
    assert(m.get("latitude").get == (10,10,10,10))
    assert(m.get("message").get == "Hello, world")
    assert(m.get("time").get == (11,11))
    assert(m.get("date").get == (2014,10,1))
  }

  test("test Load") {
    // c is already loaded with unittest.json
    assert(c.getJsonSize() == 107)
    val filePath = "contextSummary/src/test/scala/resource/simple.json"
    c.load(filePath)
    assert(c.getJsonSize() == 55)
  }

  test("test ToString") {
    val result =
      """|{
         |  "date":[2014, 10, 1],
         |  "latitude":[10, 10, 10, 10],
         |  "message":"Hello, world",
         |  "time":[11, 11]
         |}
         |""".stripMargin

    assert(c.toString() == result)
  }

  test("test save") {
    val filePath = "contextSummary/src/test/scala/resource/unittest_test_result.json"
    val fp = new File(filePath)
    if (fp.exists()) {
      fp.delete()
    }
    assert(new File(filePath).exists() == false)
    c.save(filePath)
    assert(new File(filePath).exists())
  }
}
