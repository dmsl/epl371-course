import twitter4j._
import java.io._
import java.text.SimpleDateFormat
import java.util.Locale._
import java.util.Date
object geo_msgs {

  def getTwitter(lat:Double, log:Double, radius:Double)={
    new File("geo_msgs/twitter_msgs/").mkdir()
    var cb: twitter4j.conf.ConfigurationBuilder = new twitter4j.conf.ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("bxzYrO49tTSBu8siRDLwJg")
      .setOAuthConsumerSecret("obSgpjObxacgBV9jtyTfcJndLBs25f9mclAZsFrFXKY")
      .setOAuthAccessToken("209014117-eg4QZ1PqzZz06uaIadaTBYIXgqF81szbYQLmMUcQ")
      .setOAuthAccessTokenSecret("MCQv27eEhRZxdYm7FVuyyHA4Kp3MvX7PBLHrsL7MNxl0C");
    var tf:TwitterFactory = new TwitterFactory(cb.build());
    var twitter:Twitter = tf.getInstance();
    var query: Query = new Query().geoCode(new GeoLocation(lat,log), radius, "km");
    var result:QueryResult = twitter.search(query);
    var tweets = result.getTweets();
    var filename:String="";
    for (i<-0 until tweets.size()){
      var tweet: Status=tweets.get(i);
      filename=(i+1)+ ".txt"
      val writer = new PrintWriter(new File("geo_msgs/twitter_msgs/", filename))
      val sdf :SimpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy" , ENGLISH);
      val date :Date = sdf.parse(tweet.getCreatedAt.toString)
      var unix_time :Long = date.getTime()
      writer.write("Text: "+  tweet.getText +"\n" + "Time: "+unix_time+ "\n" + "Retweets: " +tweet.getRetweetCount);
      writer.close()
    }
  }

  def getRayzit(lat:Double, log:Double, radius:Int)={
    /*Initialization of variables*/
    val url = "http://api.rayzit.com/nearbyrayz/" + lat + "/" + log + "/" + radius

    val result = scala.io.Source.fromURL(url, "UTF-8").getLines.mkString

    new File("geo_msgs/rayzit_msgs").mkdir();
    val splitted = result.split( """}},""").toArray
    for (i <- 0 to splitted.length - 1) {
      if (i == 0)
        splitted(0) = splitted(0).replaceFirst("\\{\"status\":\"success\",\"counter\":" + splitted.length + ",\"nearestRayz\":\\[", "")
    }

    val geo_rayzs = Array.ofDim[String](splitted.length, 3)

    if(geo_rayzs.length >1 ){

      for (i <- 0 to splitted.length - 1) {
        var file = "geo_msgs/rayzit_msgs/" + (i + 1) + ".txt"
        var writer = new PrintWriter(new File(file))

        val splittedc = splitted(i).split( """,""").toArray

        for (j <- 0 to splittedc.length - 1) {

          if (splittedc(j).contains("rayz_message")) {
            splittedc(j) = splittedc(j).replaceAll("\"", "").replaceFirst("\\{", "")
            splittedc(j) = splittedc(j).replaceAll("rayz_message:","Text: ")

            geo_rayzs(i)(0) = splittedc(j)
          }

          if (splittedc(j).contains("timestamp")) {
            splittedc(j) = splittedc(j).replaceAll("\"", "")
            splittedc(j) = splittedc(j).replaceAll("timestamp:","Time: ")
            geo_rayzs(i)(1) = splittedc(j)
          }

          if (splittedc(j).contains("rerayz")) {
            splittedc(j) = splittedc(j).replaceAll("\"", "")
            splittedc(j) = splittedc(j).replaceAll("rerayz:","Rerayzs: ")
            geo_rayzs(i)(2) = splittedc(j)
          }

        }
        writer.write(geo_rayzs(i)(0) + "\n")
        writer.write(geo_rayzs(i)(1) + "\n")
        writer.write(geo_rayzs(i)(2) + "\n")
        writer.close();

      }
    }

  }
  def main(args: Array[String]){
    new File("geo_msgs/").mkdir();
    getTwitter(args(0).toDouble, args(1).toDouble, (args(2).toDouble)/1000);
    getRayzit(args(0).toDouble, args(1).toDouble, args(2).toInt);


  }
}