import scala.io.Source;

object mashup {
  def main(args: Array[String]) {

    rayzit.main(args)
    /*Get latest_rayz*/
    var latest_rayz = new java.io.File("latest_rayz").listFiles().length
    var i = 0
    var j = 0
    var filename = "rayz"
    var latest_rayzs = Array.ofDim[String](latest_rayz, 3)
    var path = ""
    for (i <- 1 to latest_rayz) {
      filename = "rayz_" + i
      path = "latest_rayz\\" + filename
      j = 0
      for (line <- Source.fromFile(path).getLines()) {
        latest_rayzs(i - 1)(j) = line
        j += 1
        //println(line)
      }
      //println
    }

    /*Get geo_rayz*/
    var geo_rayz = new java.io.File("geo_rayz").listFiles().length
    i = 0
    j = 0
    filename = "rayz"
    var geo_rayzs = Array.ofDim[String](geo_rayz, 3)
    path = ""
    for (i <- 1 to geo_rayz) {
      filename = "rayz_" + i
      path = "geo_rayz\\" + filename
      j = 0
      for (line <- Source.fromFile(path).getLines()) {
        geo_rayzs(i - 1)(j) = line
        j += 1
        //println(line)
      }
      //println
    }

    /*Start Mashup*/
    var col1_t = ""
    var col2_t = ""
    var col3_t = ""
    println("1.Geo-sort\n2.Geo-max-retransmitted\n3.Geo-Oldest\n4.Latest-sort\n5.Latest-Oldest")
    print("Enter your choice:")
    var choice = scala.Console.readInt();
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
    }
    if (choice == 2) {
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
    }
    if (choice == 3) {
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
    if (choice == 4) {
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

    }
    if (choice == 5) {
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
