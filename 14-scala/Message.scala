class Message {
  var text :String="";
  var time :String="";
  var re  :Int=0;

  def setText(message: String){
    this.text=message
  }

  def setTimestamp(time: String){
    this.time=time
  }

  def setCount(number: Int){
    this.re=number
  }

  def getText:String ={
    this.text
  }

  def getTime:String ={
    this.time
  }

  def getCount: Int ={
    this.re
  }

}