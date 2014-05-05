import scala.io.Source;
import java.io._
import java.util.{Date, LinkedList}
;
object mashUp{

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