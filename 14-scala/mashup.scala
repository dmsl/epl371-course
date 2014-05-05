import scala.io.Source;
import java.io._
import java.util.{Date, LinkedList}
;
object mashUp{

def sort(xs: Array[Long], geo_files: Array[Message]): Array[Message] = {
    def swap(i: Int, j: Int) {
      val t = xs(i);
      val s: Message = new Message;
      s.setCount(geo_files(i).getCount);
      s.setText(geo_files(i).getText);
      s.setTimestamp(geo_files(i).getTime);
      xs(i) = xs(j);
      geo_files(i) = geo_files(j);
      xs(j) = t;
      geo_files(j) = s;
    }

    def sort1(l: Int, r: Int) {
      val pivot = xs((l + r) / 2)
      var i = l;
      var j = r
      while (i <= j) {
        while (xs(i) < pivot) i += 1
        while (xs(j) > pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }
    sort1(0, xs.length - 1)
    return geo_files
  }


  def geo_sort(geo_files: Array[Message]) = {
    val times = new Array[Long](geo_files.length)
    var geo_files_sorted = new Array[Message](geo_files.length)

    for (a <- 0 until (geo_files.length)) {
      times(a) = geo_files(a).getTime.toLong
    }

    geo_files_sorted = sort(times, geo_files)
    println(">> Geo sort Message is: ");
    for (a <- (geo_files_sorted.length-1) to 0 by -1) {
      println("-> Text: " + geo_files_sorted(a).getText);

      var formatted_time: Long = geo_files(a).getTime.toLong
      var datetime: Date = new Date(formatted_time)

      println("-> Time: " + datetime);
      println("-> Recount: " + geo_files_sorted(a).getCount);
      println();
    }
  }


  def latest_sort(latest_files: Array[Message]) = {
    val times = new Array[Long](latest_files.length)
    var latest_files_sorted = new Array[Message](latest_files.length)

    for (a <- 0 until (latest_files.length)) {
      times(a) = latest_files(a).getTime.toLong
    }

    latest_files_sorted = sort(times, latest_files)
    println(">> Latest sort Message is: ");
    for (a <- 0 until (latest_files_sorted.length)) {
      println("-> Text: " + latest_files_sorted(a).getText);
      var formatted_time: Long = latest_files(a).getTime.toLong
      var datetime: Date = new Date(formatted_time)

      println("-> Time: " + datetime);
      println("-> Recount: " + latest_files_sorted(a).getCount);
      println();
    }
  }


  def geo_before_date(year: Int, geo_files: Array[Message]) = {
    println(">> Messages before " + year + " are:");
    println();
    for (a <- 0 until (geo_files.length)) {
      var formatted_time: Long = geo_files(a).getTime.toLong
      var datetime: Date = new Date(formatted_time)
      var msg_year: Int = datetime.getYear()
      println()
      if (msg_year < year) {
        println("-> Text: " + geo_files(a).getText);
        var formatted_time: Long = geo_files(a).getTime.toLong
        var datetime: Date = new Date(formatted_time)

        println("-> Time: " + datetime);
        println("-> Recount: " + geo_files(a).getCount);
        println();
      }
    }
  }


  def geo_max_retransmitted(geo_files: Array[Message]) = {
    var max: Int = 0
    var message: Message = null

    for (a <- 0 until (geo_files.length )) {
      if (geo_files(a).getCount > max){
        message=geo_files(a);
        max=message.getCount;
    }
    }

    var formatted_time: Long = message.getTime.toLong
    var datetime: Date = new Date(formatted_time * 1000L)

    println(">> Geo max retransmitted Message is: ");
    println("-> Text: " + message.getText);
    println("-> Time: " + datetime);
    println("-> Recount: " + message.getCount);
  }

  def geo_oldest(geo: Array [Message])={
    var oldest= new Message;
    oldest.setTimestamp(scala.Long.MaxValue.toString);
    val bound:Int=(geo.length)-1;
    for (i <- 0 to bound){
      if ((geo(i).getTime.toLong)<(oldest.getTime.toLong)){
        oldest.setText(geo(i).getText);
        oldest.setTimestamp(geo(i).getTime.toString);
        oldest.setCount(geo(i).getCount);
      }
    }
    println(">> Geo Oldest Message is: ");
    println("-> Text: "+ oldest.getText);
    var formatted_time: Long = oldest.getTime.toLong
    var datetime: Date = new Date(formatted_time)

    println("-> Time: " + datetime);
    println("-> Recount: "+ oldest.getCount);

  }


  def latest_oldest(latest: Array [Message])={
    var oldest= new Message;
    oldest.setTimestamp(scala.Long.MaxValue.toString);
    val bound:Int=latest.length-1;
    for (i <- 0 to bound){
      if ((latest(i).getTime.toLong)<(oldest.getTime.toLong)){
        oldest.setText(latest(i).getText);
        oldest.setTimestamp(latest(i).getTime.toString);
        oldest.setCount(latest(i).getCount);
      }
    }
    println(">> Latest Oldest Message is: ");
    println("-> Text: "+ oldest.getText);
    var formatted_time: Long = oldest.getTime.toLong
    var datetime: Date = new Date(formatted_time)

    println("-> Time: " + datetime);
    println("-> Recount: "+ oldest.getCount);
  }


  def all_build_lexicon(geo:Array[Message], latest:Array[Message])={
    val msgCount: Int= geo.length+latest.length;
    var msgs=new Array[String](msgCount);
    var position:Int=0;
    var value:Int=0;

    for (i <- 0 to (geo.length-1)){
      msgs(i)=geo(i).getText;
    }
    for (i<- 0 to (latest.length-1)){
      msgs(i+geo.length)=latest(i).getText;
    }
    var listOfStrings=new LinkedList[String]();
    var listOfCounts=new LinkedList[Int]();

    for (i <- 0 to (msgCount-1)){
      msgs(i)=msgs(i).toLowerCase;
      msgs(i)=msgs(i).replaceAll("\"","");
      msgs(i)=msgs(i).replaceAll(":","");
      msgs(i)=msgs(i).replaceAll("!","");
      msgs(i)=msgs(i).replaceAll("-","");
      msgs(i)=msgs(i).replaceAll("\\?","");
      msgs(i)=msgs(i).replaceAll("@","");
      msgs(i)=msgs(i).replaceAll("#","");
      msgs(i)=msgs(i).replaceAll("$","");
      msgs(i)=msgs(i).replaceAll("=","");
      msgs(i)=msgs(i).replaceAll("\\.","");
      msgs(i)=msgs(i).replaceAll("\\\\","");
      val splitted=msgs(i).split(" +");

      for (j <- 0 until splitted.length ){
        if (!listOfStrings.contains(splitted(j))){
          listOfStrings.add(splitted(j));
          listOfCounts.add(1);
        }
        else {
          position=listOfStrings.indexOf(splitted(j));
          value=listOfCounts.get(position);
          value=value+1;
          listOfCounts.set(position, value);
        }
      }
    }
    val strings=new Array[String](listOfStrings.size());
    val counts=new Array[Int](listOfCounts.size());

    for (i<- 0 until listOfStrings.size()){
      strings(i)=listOfStrings.get(i);
    }
    for (i<- 0 until listOfCounts.size()){
      counts(i)=listOfCounts.get(i);
    }
    sort_lexicon(strings,counts);
  }


  def sort_lexicon(strings: Array[String], counts:Array[Int]){
    var temp:Int=0;
    var temp1:String="";
    for(i<- 0 until strings.length){

      //Inner loop to perform comparision and swapping between adjacent numbers
      //After each iteration one index from last is sorted
      for(j<- 1 until (strings.length-i)){
        //If current number is greater than swap those two
        if(counts(j-1) < counts(j)){
          temp = counts(j);
          temp1=strings(j);
          counts(j) = counts(j-1);
          strings(j)=strings(j-1);
          counts(j-1) = temp;
          strings(j-1)=temp1;
        }
      }
    }

    val writer = new PrintWriter(new File("lexicon.txt" ))
    for (i<- 0 until counts.length){
      writer.write(strings(i)+ "\t"+ counts(i) + "\n");
    }
    writer.close()
  }


  def readMessagesFilesGeo:Array[Message]={
    //Find the Rayzit Messages
    val rayzitCount= new java.io.File("geo_msgs/rayzit_msgs/").listFiles().length;
    val twitterCount= new java.io.File("geo_msgs/twitter_msgs/").listFiles().length;
    val count=rayzitCount+twitterCount;
    val files =new Array[Message](count);
    var filename: String="";
    var path:String ="";
    var line: String="";

    for (i <- 0 to (rayzitCount-1)){
      filename=(i+1) + ".txt";
      path = "geo_msgs/rayzit_msgs/" + filename
      var newEntry=new Message;
      var source = Source.fromFile(path).getLines
      while (source.hasNext) {
        if ((line=source.next)!= null){
          if (line.startsWith("Text: ")){
            newEntry.setText(line.substring(6));
          }
          else if (line.startsWith("Time: ")){
            newEntry.setTimestamp(line.substring(6))
          }
          else if (line.startsWith("Rerayzs: ")){
            newEntry.setCount(line.substring(9).toInt)
          }
        }
      }
      files(i)=newEntry;
    }

    for (i <- 0 to (twitterCount-1)){
      filename=(i+1)+".txt";
      path="geo_msgs/twitter_msgs/"+filename
      var newEntry= new Message();
      var source = Source.fromFile(path).getLines
      while (source.hasNext) {
        if ((line=source.next)!=null){
          if (line.startsWith("Text: ")){
            newEntry.setText(line.substring(6));
          }
          else if (line.startsWith("Time: ")){
            newEntry.setTimestamp(line.substring(6))
          }
          else if (line.startsWith("Retweets: ")){
            newEntry.setCount(line.substring(10).toInt)
          }
        }
      }
      files(rayzitCount+i)=newEntry;
    }
    return files;
  }


  def readMessagesFilesLatest:Array[Message]={
    //Find the Rayzit Messages
    val rayzitCount=new java.io.File("latest_msgs/rayzit_msgs/").listFiles().length;
    val twitterCount=new java.io.File("latest_msgs/twitter_msgs/").listFiles.length;
    val count=rayzitCount+twitterCount;
    val files =new Array[Message](count);
    var filename: String="";
    var path:String ="";
    var line:String="";

    for (i <- 0 to (rayzitCount-1)){
      filename=(i+1) + ".txt";
      path = "latest_msgs/rayzit_msgs/" + filename
      var newEntry= new Message();
      var source = Source.fromFile(path).getLines
      while (source.hasNext) {
        if ((line=source.next)!=null){
          if (line.startsWith("Text: ")){
            newEntry.setText(line.substring(6));
          }
          else if (line.startsWith("Time: ")){
            newEntry.setTimestamp(line.substring(6));
          }
          else if (line.startsWith("Rerayzs: ")){
            newEntry.setCount(line.substring(9).toInt)
          }
        }
      }
      files(i)=newEntry;
    }

    for (i <- 0 to (twitterCount-1)){
      filename=(i+1)+".txt";
      path="latest_msgs/twitter_msgs/"+filename
      var newEntry= new Message();
      var source = Source.fromFile(path).getLines
      while (source.hasNext) {
        if ((line=source.next)!= null){
          if (line.startsWith("Text: ")){
            newEntry.setText(line.substring(6));
          }
          else if (line.startsWith("Time: ")){
            newEntry.setTimestamp(line.substring(6));
          }
          else if (line.startsWith("Retweets: ")){
            newEntry.setCount(line.substring(10).toInt)
          }
        }
      }
      files(rayzitCount+i)=newEntry;
    }
    return files;
  }
  
    def option_match(option: String, value: String, filesGeo: Array[Message], filesLatest: Array[Message]) = option match {
    case "geo-sort" => geo_sort(filesGeo);
    case "latest-sort" => latest_sort(filesLatest);
    case "geo-max-retransmitted" => geo_max_retransmitted(filesGeo);
    case "geo-before-date" => geo_before_date(value.toInt, filesGeo);
    case "geo-oldest"=> geo_oldest(filesGeo);
    case "latest-oldest" => latest_oldest(filesLatest);
    case "all-build-lexicon" => all_build_lexicon(filesGeo, filesLatest);
    case _ => println(">> Invalid arguments"); System.exit(1);
  }

  def main (args: Array[String]){
    val filesGeo=readMessagesFilesGeo;
    val filesLatest=readMessagesFilesLatest;

    var option = args(0);
    var value: String = "";
    if (args.length > 1)
      value = args(1);
    option_match(option, value, filesGeo, filesLatest);

  }
}