import twitter4j._
import java.io._
import java.text.SimpleDateFormat
import java.util.Locale._
import java.util.Date
object latest_msgs {

  def getTwitter(account:String)={
    new File("latest_msgs/twitter_msgs/").mkdir()
    var cb: twitter4j.conf.ConfigurationBuilder = new twitter4j.conf.ConfigurationBuilder();
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("bxzYrO49tTSBu8siRDLwJg")
      .setOAuthConsumerSecret("obSgpjObxacgBV9jtyTfcJndLBs25f9mclAZsFrFXKY")
      .setOAuthAccessToken("209014117-eg4QZ1PqzZz06uaIadaTBYIXgqF81szbYQLmMUcQ")
      .setOAuthAccessTokenSecret("MCQv27eEhRZxdYm7FVuyyHA4Kp3MvX7PBLHrsL7MNxl0C");
    var tf:TwitterFactory = new TwitterFactory(cb.build());
    var twitter:Twitter = tf.getInstance();
    var user:User= twitter.showUser(account);
    var userID:Long=user.getId;
    var tweets:ResponseList[Status]=twitter.getUserTimeline(userID);
    var filename:String="";
    for (i<-0 until tweets.size()){
      var tweet: Status=tweets.get(i);
      filename=(i+1)+ ".txt"
      val writer = new PrintWriter(new File("latest_msgs/twitter_msgs/",filename ))
      val sdf :SimpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy" , ENGLISH);
      val date :Date = sdf.parse(tweet.getCreatedAt.toString)
      var unix_time :Long = date.getTime()
      writer.write("Text: "+  tweet.getText +"\n" + "Time: "+ unix_time+ "\n" + "Retweets: " +tweet.getRetweetCount);
      writer.close()
    }
  }

  def getRayzit(time:Int)={
    val url_t = "http://api.rayzit.com/latest/rayz/" + time

    val result_t = scala.io.Source.fromURL(url_t, "UTF-8").getLines.mkString

    new File("latest_msgs/rayzit_msgs").mkdir();

    val splitted_l = result_t.split( """}},""").toArray
    for (i <- 0 to splitted_l.length - 1) {
      if (i == 0) {
        splitted_l(0) = splitted_l(0).replaceFirst("\\{\"status\":\"success\",\"counter\":" + splitted_l.length + ",\"latest\":\\[", "")
      }
    }

    val latest_rayzs = Array.ofDim[String](splitted_l.length, 3)

    if(latest_rayzs.length >1 ){

      for (i <- 0 until (splitted_l.length)) {
        var file = "latest_msgs/rayzit_msgs/" + (i + 1) + ".txt"
        var writer = new PrintWriter(new File(file))

        val splittedcl = splitted_l(i).split( """,""").toArray

        for (j <- 0 to splittedcl.length - 1) {

          if (splittedcl(j).contains("rayz_message")) {
            splittedcl(j) = splittedcl(j).replaceAll("\"", "").replaceFirst("\\{", "")
            splittedcl(j) = splittedcl(j).replaceAll("rayz_message:","Text: ")
            latest_rayzs(i)(0) = splittedcl(j)
          }

          if (splittedcl(j).contains("timestamp")) {
            splittedcl(j) = splittedcl(j).replaceAll("\"", "")
            splittedcl(j) = splittedcl(j).replaceAll("timestamp:","Time: ")
            latest_rayzs(i)(1) = splittedcl(j)
          }

          if (splittedcl(j).contains("rerayz")) {
            splittedcl(j) = splittedcl(j).replaceAll("\"", "")
            splittedcl(j) = splittedcl(j).replaceAll("rerayz:","Rerayzs: ")
            latest_rayzs(i)(2) = splittedcl(j)
          }

        }

        writer.write(latest_rayzs(i)(0) + "\n")
        writer.write(latest_rayzs(i)(1) + "\n")
        writer.write(latest_rayzs(i)(2) + "\n")
        writer.close()
      }
    }


  }
  def main(args: Array[String]){
    new File("latest_msgs/").mkdir();
    getTwitter(args(0));
    getRayzit(args(1).toInt)


  }
}