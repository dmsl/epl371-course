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
    print("Menu\n====\n1.Geo Rayzs\n2.Latest Rayzs\n\nPlease enter the number of your choice:")
    var choice = scala.Console.readInt()

    /*Initialization of variables*/
    var j = 0;
    var i = 0;
    var col1_t = "";
    var col2_t = "";
    var col3_t = "";

    if (choice == 1) {
      /*For geo rayzs*/
      println("1.Geo-sort\n2.Geo-max-retransmitted\n3.Geo-Oldest")
      print("Please enter your choice:")
      choice = scala.Console.readInt();
      print("Please enter the radius you would like:")
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

      /*For Debugging*/
      val splitted = result.split("""}},""").toArray
      for (i <- 0 to splitted.length - 1) {
        if (i == 0)
          splitted(0) = splitted(0).replaceFirst("\\{\"status\":\"success\",\"counter\":" + splitted.length + ",\"nearestRayz\":\\[", "")
      }

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

        /*For debugging*/ /*
        println(geo_rayzs(i)(0))
        println(geo_rayzs(i)(1))
        println(geo_rayzs(i)(2))
				*/
        writer.write(geo_rayzs(i)(0) + "\n")
        writer.write(geo_rayzs(i)(1) + "\n")
        writer.write(geo_rayzs(i)(2) + "\n")
        writer.close()
      }

      if (choice == 1) {
        /*Start of geo-sort*/
        println("\nGeo-Sort")
        for (i <- 0 to geo_rayzs.length - 2) {
          for (j <- i + 1 to geo_rayzs.length - 1) {
            if (geo_rayzs(i)(1) < geo_rayzs(j)(1)) {
              col1_t = geo_rayzs(i)(0)
              col2_t = geo_rayzs(i)(1)
              col3_t = geo_rayzs(i)(2)
              geo_rayzs(i)(0) = geo_rayzs(j)(0)
              geo_rayzs(i)(1) = geo_rayzs(j)(1)
              geo_rayzs(i)(2) = geo_rayzs(j)(2)
              geo_rayzs(j)(0) = col1_t
              geo_rayzs(j)(1) = col2_t
              geo_rayzs(j)(2) = col3_t
            } //end if
          } //end of inner for
        } //end of outter for

        for (i <- 0 to geo_rayzs.length - 1) {
          println(geo_rayzs(i)(1) + " " + geo_rayzs(i)(0) + " " + geo_rayzs(i)(2))
        }
        /*End of geo-sort*/
      } else if (choice == 2) {
        /*Start of geo-max-retransmitted*/
        println("\nGeo-max-retransmitted")
        for (i <- 0 to geo_rayzs.length - 2) {
          for (j <- i + 1 to geo_rayzs.length - 1) {
            if (geo_rayzs(i)(2) < geo_rayzs(j)(2)) {
              col1_t = geo_rayzs(i)(0)
              col2_t = geo_rayzs(i)(1)
              col3_t = geo_rayzs(i)(2)
              geo_rayzs(i)(0) = geo_rayzs(j)(0)
              geo_rayzs(i)(1) = geo_rayzs(j)(1)
              geo_rayzs(i)(2) = geo_rayzs(j)(2)
              geo_rayzs(j)(0) = col1_t
              geo_rayzs(j)(1) = col2_t
              geo_rayzs(j)(2) = col3_t
            } //end if
          } //end of inner for
        } //end of outter for

        println(geo_rayzs(0)(2) + " " + geo_rayzs(0)(1) + " " + geo_rayzs(0)(0))

        /*End of geo-max-retransmitted*/
      } else {
        /*Start of Geo-oldest*/
        println("\nGeo-oldest")
        for (i <- 0 to geo_rayzs.length - 2) {
          for (j <- i + 1 to geo_rayzs.length - 1) {
            if (geo_rayzs(i)(1) < geo_rayzs(j)(1)) {
              col1_t = geo_rayzs(i)(0)
              col2_t = geo_rayzs(i)(1)
              col3_t = geo_rayzs(i)(2)
              geo_rayzs(i)(0) = geo_rayzs(j)(0)
              geo_rayzs(i)(1) = geo_rayzs(j)(1)
              geo_rayzs(i)(2) = geo_rayzs(j)(2)
              geo_rayzs(j)(0) = col1_t
              geo_rayzs(j)(1) = col2_t
              geo_rayzs(j)(2) = col3_t
            } //end if
          } //end of inner for
        } //end of outter for
        println(geo_rayzs(geo_rayzs.length - 1)(2) + " " + geo_rayzs(geo_rayzs.length - 1)(1) + " " + geo_rayzs(geo_rayzs.length - 1)(0))
        /*End of Geo-oldest*/
      }
    } else {
      /*-------------------
     * 	For time rayz		*
     * -----------------*/
      println("1.Latest-sort\n2.Latest-Oldest")
      print("Please enter your choice:")
      choice = scala.Console.readInt();
      print("Please enter the time you would like:")
      val time = scala.Console.readInt()
      println("The number you enter is " + time)

      val url_t = "http://api.rayzit.com/latest/rayz/" + time
      println(url_t)

      val result_t = scala.io.Source.fromURL(url_t, "UTF-8").getLines.mkString

      /*WOKRING*/
      //println(scala.io.Source.fromURL("http://api.rayzit.com/nearbyrayz/33.221232/35.231231/500000","UTF-8").getLines.mkString("\n"))

      new File("latest_rayz").mkdir();

      //var i = 0;
      /*For Debugging*/
      val splitted_l = result_t.split("""}},""").toArray
      for (i <- 0 to splitted_l.length - 1) {
        if (i == 0) {
          splitted_l(0) = splitted_l(0).replaceFirst("\\{\"status\":\"success\",\"counter\":" + splitted_l.length + ",\"latest\":\\[", "")
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

        /*For debugging*/ /*
        println(latest_rayzs(i)(0))
        println(latest_rayzs(i)(1))
        println(latest_rayzs(i)(2))*/

        writer.write(latest_rayzs(i)(0) + "\n")
        writer.write(latest_rayzs(i)(1) + "\n")
        writer.write(latest_rayzs(i)(2) + "\n")
        writer.close()
        //println()
      }

      if (choice == 1) {
        /*Start of latest-sort*/
        println("\nLatest-sort")
        for (i <- 0 to latest_rayzs.length - 2) {
          for (j <- i + 1 to latest_rayzs.length - 1) {
            if (latest_rayzs(i)(1) < latest_rayzs(j)(1)) {
              col1_t = latest_rayzs(i)(0)
              col2_t = latest_rayzs(i)(1)
              col3_t = latest_rayzs(i)(2)
              latest_rayzs(i)(0) = latest_rayzs(j)(0)
              latest_rayzs(i)(1) = latest_rayzs(j)(1)
              latest_rayzs(i)(2) = latest_rayzs(j)(2)
              latest_rayzs(j)(0) = col1_t
              latest_rayzs(j)(1) = col2_t
              latest_rayzs(j)(2) = col3_t
            } //end if
          } //end of inner for
        } //end of outter for

        for (i <- 0 to latest_rayzs.length - 1) {
          println(latest_rayzs(i)(1) + " " + latest_rayzs(i)(0) + " " + latest_rayzs(i)(2))
        }
        /*End of latest-sort*/
      } else if (choice == 2) {
        /*Start of latest-oldest*/
        println("\nLatest-Oldest")
        for (i <- 0 to latest_rayzs.length - 2) {
          for (j <- i + 1 to latest_rayzs.length - 1) {
            if (latest_rayzs(i)(1) < latest_rayzs(j)(1)) {
              col1_t = latest_rayzs(i)(0)
              col2_t = latest_rayzs(i)(1)
              col3_t = latest_rayzs(i)(2)
              latest_rayzs(i)(0) = latest_rayzs(j)(0)
              latest_rayzs(i)(1) = latest_rayzs(j)(1)
              latest_rayzs(i)(2) = latest_rayzs(j)(2)
              latest_rayzs(j)(0) = col1_t
              latest_rayzs(j)(1) = col2_t
              latest_rayzs(j)(2) = col3_t
            } //end if
          } //end of inner for
        } //end of outter for

        println(latest_rayzs(latest_rayzs.length - 1)(1) + " " + latest_rayzs(latest_rayzs.length - 1)(0) + " " + latest_rayzs(latest_rayzs.length - 1)(2))
        /*End of latest-oldest*/
      }
    }
  }
}