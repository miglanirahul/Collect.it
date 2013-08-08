<?php 
App::uses('Component', 'Controller');
class CommonComponent extends Component 
{
	/*
		* function  to input pic	 
	*/
	public function uploadImage($fileData=NULL, $fileType=NULL, $filename = null)
	{
		$dbFileName = '';
		if(!empty($fileData) && !empty($fileType) && !empty($filename)) 
		{
			$userImgFolderPath = WWW_ROOT."/UserImages/";
		    $unqName = $filename.'.'.$fileType ;
			/*name end*/
			$decodedFileData = bin2hex(base64_decode($fileData));
			$data = pack("H" . strlen($decodedFileData), $decodedFileData);
			$filename = $userImgFolderPath.$unqName;
			$f = fopen($filename,'wb');
			fwrite($f, $data);
			fclose($f);
		   /*file exists*/
			if(file_exists($filename)) 
			{
				$dbFileName = $unqName;
			}
		}
		return $dbFileName;
	}
	
	public function uploadItemImage($fileData=NULL, $fileType=NULL, $filename = null)
	{
		$dbFileName = '';
		if(!empty($fileData) && !empty($fileType) && !empty($filename)) 
		{
			$userImgFolderPath = WWW_ROOT."/item_images/";
		    $unqName = $filename.'.'.$fileType ;
			/*name end*/
			$decodedFileData = bin2hex(base64_decode($fileData));
			$data = pack("H" . strlen($decodedFileData), $decodedFileData);
			$filename = $userImgFolderPath.$unqName;
			$f = fopen($filename,'wb');
			fwrite($f, $data);
			fclose($f);
		   /*file exists*/
			if(file_exists($filename)) 
			{
				$dbFileName = $unqName;
			}
		}
		return $dbFileName;
	}
}