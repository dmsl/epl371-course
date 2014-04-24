/**
 *
 */
/**
 * @author Stefanos
 *
 */
import Predef._
import scala.io._
import sys.process._
import java.io._

object rayzit {
  def main(args: Array[String]) {

    /*For geo rayzs*/
    print("Enter a number:")
    val radius = scala.Console.readInt()
    println("The number you enter is " + radius)

    val latitude = 33.221232
    val longitude = 35.231231
    //"http://api.rayzit.com/nearbyrayz/33.221232/35.231231/500000"
    val url = "http://api.rayzit.com/nearbyrayz/" + latitude + "/" + longitude + "/" + radius
    //println(url)
    val result = scala.io.Source.fromURL(url, "UTF-8").getLines.mkString

    /*WOKRING*/
    //println(scala.io.Source.fromURL("http://api.rayzit.com/nearbyrayz/33.221232/35.231231/500000","UTF-8").getLines.mkString("\n"))

    new File("geo_rayz").mkdir();

    var i = 0;
    /*For Debugging*/
    val splitted = result.split("""}},""").toArray
    for (i <- 0 to splitted.length - 1) {
      if (i == 0)
        splitted(0) = splitted(0).replaceFirst("\\{\"status\":\"success\",\"counter\":50,\"nearestRayz\":\\[", "")
    }

    var j = 0;
    val geo_rayzs = Array.ofDim[String](splitted.length, 3)
    for (i <- 0 to splitted.length - 1) {
      var file = "geo_rayz/rayz_" + (i + 1)
      var writer = new PrintWriter(new File(file))

      val splittedc = splitted(i).split(""",""").toArray

      for (j <- 0 to splittedc.length - 1) {

        if (splittedc(j).contains("rayz_message")) {
          splittedc(j) = splittedc(j).replaceAll("\"", "").replaceFirst("\\{", "")
          geo_rayzs(i)(0) = splittedc(j)
        }

        if (splittedc(j).contains("timestamp")) {
          splittedc(j) = splittedc(j).replaceAll("\"", "")
          geo_rayzs(i)(1) = splittedc(j)
        }

        if (splittedc(j).contains("rerayz")) {
          splittedc(j) = splittedc(j).replaceAll("\"", "")
          geo_rayzs(i)(2) = splittedc(j)
        }

      }

      /*For debugging*/
      println(geo_rayzs(i)(0))
      println(geo_rayzs(i)(1))
      println(geo_rayzs(i)(2))

      writer.write(geo_rayzs(i)(0) + "\n")
      writer.write(geo_rayzs(i)(1) + "\n")
      writer.write(geo_rayzs(i)(2) + "\n")
      writer.close()
      println()
    }

    /*-------------------
     * 	For time rayz		*
     * -----------------*/

    print("Enter a number:")
    val time = scala.Console.readInt()
    println("The number you enter is " + time)
    
    val url_t = "http://api.rayzit.com/latest/rayz/"+time
    println(url_t)
    
    
     val result_t = scala.io.Source.fromURL(url_t, "UTF-8").getLines.mkString

    /*WOKRING*/
    //println(scala.io.Source.fromURL("http://api.rayzit.com/nearbyrayz/33.221232/35.231231/500000","UTF-8").getLines.mkString("\n"))

    new File("latest_rayz").mkdir();

    //var i = 0;
    /*For Debugging*/
    val splitted_l = result_t.split("""}},""").toArray
    for (i <- 0 to splitted_l.length - 1) {
      if (i == 0){
        splitted_l(0) = splitted_l(0).replaceFirst("\\{\"status\":\"success\",\"counter\":3,\"latest\":\\[", "")
      }
    }

    //var j = 0;
    val latest_rayzs = Array.ofDim[String](splitted_l.length, 3)
    for (i <- 0 to splitted_l.length - 1) {
      var file = "latest_rayz/rayz_" + (i + 1)
      var writer = new PrintWriter(new File(file))

      val splittedcl = splitted_l(i).split(""",""").toArray

      for (j <- 0 to splittedcl.length - 1) {

        if (splittedcl(j).contains("rayz_message")) {
          splittedcl(j) = splittedcl(j).replaceAll("\"", "").replaceFirst("\\{", "")
          latest_rayzs(i)(0) = splittedcl(j)
        }

        if (splittedcl(j).contains("timestamp")) {
          splittedcl(j) = splittedcl(j).replaceAll("\"", "")
          latest_rayzs(i)(1) = splittedcl(j)
        }

        if (splittedcl(j).contains("rerayz")) {
          splittedcl(j) = splittedcl(j).replaceAll("\"", "")
          latest_rayzs(i)(2) = splittedcl(j)
        }

      }

      /*For debugging*/
      println(latest_rayzs(i)(0))
      println(latest_rayzs(i)(1))
      println(latest_rayzs(i)(2))

      writer.write(latest_rayzs(i)(0) + "\n")
      writer.write(latest_rayzs(i)(1) + "\n")
      writer.write(latest_rayzs(i)(2) + "\n")
      writer.close()
      println()
    }
  }
}