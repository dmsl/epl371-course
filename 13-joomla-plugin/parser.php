<?php

class Parser{
	
	private $description;
	private $link;
	private $title;
	
	private function parseDesc($tag){
		$del='"';
		
		/*User friendly*/
		if($tag=="image"){
			$tag="src=\"";
		}else if($tag=="speaker"){
			$tag="Speaker: </strong>";
		}else if($tag=="time"){
			$tag="Time: </strong>";
		}else if($tag=="date"){
			$tag="Date: </strong>";
		}else if($tag=="location"){
			$tag="Location: </strong>";
		}
		
		//print $tag;
		$charsDes=str_split($this->description);
		$charsTag = str_split($tag);
		$i=0;
		$j=0;
		while($i!=strlen($this->description)){
			
			if($charsDes[$i]==$charsTag[$j]){
				$j++;
			}else{
				$j=0;
			}
			if($j==strlen($tag)){//if found
				//print "FOUND";
				$i++;
				//print $charsDes[$i+15];
				//print $charsDes[$i+1];
				//i now is on the right character
				while($charsDes[$i]!='"' and $charsDes[$i]!='<'){
					$res.=$charsDes[$i];
					$i++;
				}
				//print $res;
				return $res;
			}
			$i++;
		}
	}
	
	public function getLink(){
		return $this->link;
	}
	
	public function getTitle(){
		return $this->title;
	}
	
	public function __construct($url){
		//print "Parser is constructed!\n";
		//Takes to much time and stops execution
		$this->downloadFile($url);
		//will change to the downloaded rss.xml
		
		
	}
	
	public function parse($post,$tag){
		$xml=simplexml_load_file("/tmp/rss.xml");
		//channel is always 0 , item for each post
		$this->title=$xml->channel[0]->item[$post]->title;
		$this->link= $xml->channel[0]->item[$post]->link;
		$this->description = $xml->channel[0]->item[$post]->description;
		return $this->parseDesc($tag);
		//echo $xml->channel[0]->item[$post]->title;
		//print $this->description;
	}

	private function downloadFile($url){
	
		
		$file = fopen($url,"rb");
		if($file){
			$newFile=fopen("/tmp/rss.xml","wb");
		}else{
			die("ERROR!\n");
		}
		
		if($newFile){
			while(!feof($file)){
				fwrite($newFile,fread($file,1024*8),1024*8);
			}
		}else{
			die("ERROR!\n");
		}
		
		if($file){
			fclose($file);
		}
		
		if($newFile){
			fclose($newFile);
		}
		
	}
	
	
	public function __destruct(){
		unlink("/tmp/rss.xml");
	}

}


$parser = new Parser("https://www8.cs.ucy.ac.cy/conferences/colloquium/rss.xml");
//$parser->downloadFile("https://www8.cs.ucy.ac.cy/conferences/colloquium/rss.xml");
$res=$parser->parse(0,"speaker");
$res1=$parser->parse(1,"speaker");
$title=$parser->getTitle();
$link = $parser->getLink();
print $res."<BR>";
print $res1;
//print $title;
//print $link;


?>
