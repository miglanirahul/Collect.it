<?php
App::uses('AppController', 'Controller');

App::uses('CakeEmail', 'Network/Email');
/**
 * WebservicesController Controller
 *
 * @property User $User
 */
class WebservicesController extends AppController {
	
	/*
	
	*/
	var $isLoggedIn = null;
	//var $components = array('Auth');
	var $uses = array('User', 'Item', 'Tag', 'UserDetail', 'Collection', 'Itemimage', 'ItemsTag', 'ItemsCollection', 'UserSharingstatus', 'UserSearchterm');
	var $components = array('Common', 'ImageUpload');
	public function beforeFilter()
	{
		parent::beforeFilter();//get anything defined in base class
		/*$this->Auth->allow(array('*', 'isUser', 'signup', 'returnLocationData', 'getLocations', 'getTrackingMode', 'setTrackingMode', 'savePetDetails', 'updatePetDetails', 'getPetDetails', 'getAllPets', 'getCityFromLatLong', 'getLocationLastData'));*///it's a webservice and sessions can not be controlled
	}

	/**
	* User Login
	*
	* Inputs :: email, password
	* Out put result of value mathc in database
	* @return void	
	*/
	
	public function isUser() {
	
	/*
		$userUsername = 'munish12.kumar';
		$userPassword = 'ihagzKkn';
	*/
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
			
		$userUsername = $obj->userName;
		$userPassword = $obj->userPassword;
	
		$inData['User']['user_name'] = $userUsername;
		$inData['User']['password'] = $userPassword;
		
	
		$result['response'] = false;
		$result['errorText'] = 'Input Parameters are not defined.';
		$result['msg'] = null;
		$result['responseData'] = null;
		
		if(empty($userUsername))/*if email empty*/
		{
			$result['response'] = false;
			$result['errorText'] = 'Username is empty.';
			
		}
		elseif(empty($userPassword))/*if password is empty*/
		{
			$result['response'] = false;
			$result['errorText'] = 'Password is empty.';
			
		}
		else
		{
			/*Now we will query User if this is a right User?*/
			$userData = $this->User->validateUserLogin($inData);
		
			/*if this a valid User */
			if(!empty($userData))
			{
					$result['response'] = true;
					$result['errorText'] = '';
					$result['msg'] = 'User details matched.';
					$result['responseData'] = $userData;
			}
			else
			{
				$result['response'] = false;
				$result['errorText'] = 'Entered Username/Password are invalid';
				
			}
		}
		echo json_encode($result);
		die();//I am a webservice and require no view so exit at this  point
	}
	
	/**
	* User signup
	*
	* Inputs :: User Signup data
	* Output  :: result of insertion
	* @return void	
	*/
	
	public function signup() {
		/*Extract data From Device*/
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		
		//inputs required 
		
		$userName = $obj->userName;
		$userEmail = $obj->userEmail;
		$userPassword = $obj->userPassword;
		$userGid = $obj->userGid;
		$userFbID = $obj->userFbID;
		$userRegType = $obj->userRegType;
		
		$userFirstName = $obj->userFirstName;
		$userLastName = $obj->userLastName;
		$userGender = $obj->userGender;
		$userAboutMe = $obj->userAboutMe;
		$userProfilePic = $obj->userProfilePic;
		
		/*
		$userName = 'fbusuaa';
		$userEmail = 'fbuus@mymail.net';
		$userPassword = '112233';
		$userGid = '';
		$userRegType = 1;
		
		$userFirstName = 't first name';
		$userLastName = 't last name';
		$userGender = 'male';
		$userAboutMe = 'huhahuha';
		*/
		/*Some Validation Starts From here*/
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		if(empty($userName))
		{
			$response['response'] = false;
			$response['errorText'] = 'Username is Empty.';
			$response['msg'] = '';
		}
		else if(empty($userEmail))
		{
			$response['response'] = false;
			$response['errorText'] = 'Email is Empty.';
			$response['msg'] = '';
		}
		else if(empty($userPassword))
		{
			$response['response'] = false;
			$response['errorText'] = 'Password is Empty.';
		}
		else if(empty($userFirstName))
		{
			$response['response'] = false;
			$response['errorText'] = 'First Name is Empty.';
			$response['msg'] = '';
		}
		else if($this->User->check_username_exist( $userName ))
		{
			$response['response'] = false;
			$response['errorText'] = 'Username already taken.';
			$response['msg'] = '';
		}
		else if(!empty($userFbID) && $this->User->check_fbid_exist( $userFbID ))
		{
			$response['response'] = false;
			$response['errorText'] = 'FBID already registered.';
			$response['msg'] = '';
		}
		else if(!empty($userGid) && $this->User->check_gid_exist( $userGid )) {
			$response['response'] = false;
			$response['errorText'] = 'Google Id already taken.';
			$response['msg'] = '';
		} else {
			/*Convert it to framework format*/
			$data['User']['user_name'] = $userName;
			$data['User']['email'] = 	$userEmail ;
			$data['User']['password'] = 	$userPassword ;
			$data['User']['gid'] = 	$userGid ;
			$data['User']['fbid'] = 	$userFbID ;
			$data['User']['login_type'] = 	$userRegType ;
				
			$data['UserDetail']['first_name'] = 	$userFirstName;
			$data['UserDetail']['last_name'] = 	$userLastName ;
			$data['UserDetail']['gender'] = 	$userGender;
			$data['UserDetail']['about'] = 	$userAboutMe ;
			$data['UserDetail']['user_type'] = 	2 ;
			$userFileName = 'userfile_'.uniqid(); 
			$data['UserDetail']['profile_pic'] = 	$this->Common->uploadImage($userProfilePic, 'jpg', $userFileName);
			
			/*Here Comes Sharing status data initially will not be sharing with any of social media*/
				
			if(!empty($userFbID)){
				$data['UserSharingstatus']['fb_sharing_status'] = 1;
				$data['UserSharingstatus']['fb_id'] = $userFbID;
			} else {
				$data['UserSharingstatus']['fb_sharing_status'] = 0;
			}
			
			
			if(!empty($userGid)){
				$data['UserSharingstatus']['google_sharing_status'] = 1;
				$data['UserSharingstatus']['google_id'] = $userGid;
			} else {
				$data['UserSharingstatus']['google_sharing_status'] = 0;
			}
			$data['UserSharingstatus']['twitter_sharing_status'] = 0;
			/*Sharing status data ends here */ 
			
			
			if($this->User->saveAll($data))	{
				$this->Common->uploadImage($userProfilePic, 'jpg', $userName);
				$response['response'] = true;
				$response['responseData'] = $this->User->id;
				$response['errorText'] = '';
				$response['msg'] = 'Details saved successfully.';
			}
		}
		echo json_encode($response);
		die;// no view should be rendered
	}
	
	
	/**
	* User signup
	* Here User Will login not sign up to app using social medias google and facebook here.
	* user logging iun first time the details will be saved next time the details will be updated the social media id will be unique id here
	* Inputs :: User Signup data
	* Output  :: result of insertion
	* @return void	
	*/
	
	public function smSignup() {
		/*Extract data From Device*/
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		//inputs required 
		//smuser
		$userName = $obj->userName;
		$userEmail = $obj->userEmail;
		$userPassword = $obj->userPassword;
		$userGid = $obj->userGid;
		$userFbID = $obj->userFbID;
		$userRegType = $obj->userRegType;
		
		$userFirstName = $obj->userFirstName;
		$userLastName = $obj->userLastName;
		$userGender = $obj->userGender;
		$userAboutMe = $obj->userAboutMe;
		$userProfilePic = $obj->userProfilePic;
		$userSMUser = $obj->userSMUser;
		//is this a social media User? 2 means yes 1 means no

	
	

		/*Some Validation Starts From here*/
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
	
		if(empty($userEmail))
		{
			$response['response'] = false;
			$response['errorText'] = 'Email is Empty.';
			$response['msg'] = '';
		}
		else if(empty($userFirstName))
		{
			$response['response'] = false;
			$response['errorText'] = 'First Name is Empty.';
			$response['msg'] = '';
		}
	
		else if(empty($userGid) &&	empty($userFbID))
		{
			$response['response'] = false;
			$response['errorText'] = 'Please provide google+ or Facebook Id.';
			$response['msg'] = '';
		}
		else
		{
		
			$data['User']['email'] = 	$userEmail ;
			$data['User']['gid'] = 	$userGid ;
			$data['User']['fbid'] = 	$userFbID ;
			$data['User']['login_type'] = 	$userRegType ;
				
			$data['UserDetail']['first_name'] = 	$userFirstName;
			$data['UserDetail']['last_name'] = 	$userLastName ;
			$data['UserDetail']['gender'] = 	$userGender;
			$data['UserDetail']['about'] = 	$userAboutMe ;
			$data['UserDetail']['user_type'] = 	2 ;
			$userFileName = 'userfile_'.uniqid(); 
			$data['UserDetail']['profile_pic'] = 	$this->Common->uploadImage($userProfilePic, 'jpg', $userFileName);
		
		
		
			/*Here Comes Sharing status data initially will not be sharing with any of social media*/
			if(!empty($userFbID))
			{
				$data['UserSharingstatus']['fb_sharing_status'] = 1;
				$data['UserSharingstatus']['fb_id'] = $userFbID;
			}
			else
			{
				$data['UserSharingstatus']['fb_sharing_status'] = 0;
			}
			
			
			if(!empty($userGid))
			{
				$data['UserSharingstatus']['google_sharing_status'] = 1;
				$data['UserSharingstatus']['google_id'] = $userGid;
			}
			else
			{
				$data['UserSharingstatus']['google_sharing_status'] = 0;
			}
			$data['UserSharingstatus']['twitter_sharing_status'] = 0;
			/*Sharing status data ends here */ 
			/*Sharing status data ends here */ 
			
		
		
			/*this is a scoial media user sop if the user has already logged in or registerewd we will update the data and return Id*/
			
			if(!empty($userRegType) AND $userRegType == 1)
			{
				$conditions = array('User.gid' => $userGid,'User.gid <>' => null );
			}
			else if(!empty($userRegType) AND $userRegType == 2)
			{
				$conditions = array('User.fbid' => $userFbID,'User.fbid <>' => null );
			}
			
			$isRegistered = $this->User->find('first', array('conditions' => $conditions)); //?
			
			if(!empty($isRegistered))
			{ 
				$this->User->id = $isRegistered['User']['id'];
				$data['User']['id'] = $isRegistered['User']['id'];
				$data['UserDetail']['id'] = $isRegistered['UserDetail']['id'];
							
				$isSaved = $this->User->saveAssociated($data);
			
			}
			else
			{
				$isSaved = $this->User->saveAll($data);
			}
			if($isSaved)
			{
				$this->Common->uploadImage($userProfilePic, 'jpg', $userName);
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Details saved successfully.';
			}
			else
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Internal Error Updating Record.';
			}
			
		}
		
		echo json_encode($response);
		die;// no view should be rendered
	}
	
	
	
	
	/*
	* Webservice for forget password	
	*/
	public function forgetPassword()
	{
		$userUsername = null;
		$userEmail = null;
		$str = null;
	
		/*$userUsername = 'munish12.kumar';
		$userEmail = '12345678';*/
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
			
		$userUsername = $obj->userName;
		$userEmail = $obj->userEmail;
		
	
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(!empty($userUsername) /*|| !empty($userEmail) */)
		{
			$chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			$size = strlen( $chars );
			for( $i = 0; $i < 8; $i++ )
			{
				$str .= $chars[ rand( 0, $size - 1 ) ];
			}
			$randStr = $str;
			//echo "New Password will be ".$randStr;
			$userData = null;
			if(!empty($userEmail))
			{
				$userData = $this->User->find( 'first', array('conditions' => array('User.email' => $userEmail,  'User.email <>' => null )));
			}
			if(!empty($userUsername)) 
			{
				$userData = $this->User->find( 'first', array('conditions' => array('User.user_name' => $userUsername , 'User.user_name <>' => null  )));
			
			}	
			if(!empty($userData))
			{
				$email = new CakeEmail();
				$email->from(array('noreply@collectit.net' => 'Collect-It'));
				$email->to($userData["User"]['email']);
				$email->subject('New Password For Collect It Login.');
				$email->sendAs = 'both';
				
				$message = 'New Password for website login is : '.$randStr;
				if($email->send($message))
				{
						$this->User->id = $userData["User"]["id"];
						$data['User']['password'] = $randStr;
						$this->User->Save($data);
						$this->set('successMsg','We have sent new password to your email. Please check your inbox and login with new password.');
						
						$response['response'] = true;
						$response['errorText'] = '';
						$response['msg'] = 'New Password sent to your email.';
				}
				else
				{	
						$response['response'] = false;
						$response['errorText'] = 'Internal Error occured while updating password';
						$response['msg'] = '';
				}
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'Username/Email does not exists.';
				$response['msg'] = '';
			}
			
		}
		echo json_encode($response);
		die;// no view should be rendered
	}
	
	
	/*
	* WebService to check if user is a social media User and already existing
	* Input :: type(1: Google Plus, 2: Facebook, 3: General website(probaly will not be useed here ))
	*/
	
	
	public function isSmUser()
	{
	
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		$userGid = $obj->userGid;
		$userFbID = $obj->userFbID;
		$userRegType = $obj->userRegType;
		/*
		$userFbID = 'FBINT001';
		$userRegType = 1;
		*/
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if((!empty($userFbID) || !empty($userGid)))//!empty($userRegType) && 
		{	
			if($userRegType == 1)//google plus'User.login_type' => $userRegType,
			{
				$userData = $this->User->find('first', array('conditions' => array( 'User.gid' => $userGid)));
			}
			elseif($userRegType == 2)//'User.login_type' => $userRegType,
			{
				$userData = $this->User->find('first', array('conditions' => array( 'User.fbid' => $userFbID)));
			}
			else
			{	
				$response['response'] = false;
				$response['errorText'] = 'Invalid Type defined';
				$response['msg'] = '';
			}
			if(!empty($userData))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'User Exists';
				$response['responseData'] = $userData;
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'No such user exists in database.';
				$response['msg'] = '';
			}
			
		}
		echo json_encode($response);
		die;
	}
	/*
	* Webservice to get User details	
	* Input :: UserId
	* Output :: Userdata or Error Message
	*/
	
	public function getUserDetails()
	{
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$userId = $obj->userId;
		

			
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		$this->User->unbindModel(array('hasMany' => array('Collection', 'Tag', 'Item', 'Itemimage')));
		if(!empty($userId))
		{
			if($this->User->exists($userId))
			{
				$this->User->unbindModel(array('hasMany' => array('Collection', 'Tag', 'Item', 'Itemimage')));
				$userData =  $this->User->find('first', array('conditions' => array('User.id' => $userId, 'User.id <>' => null), 'order' => 'User.id DESC'));
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'User Exists';
				$response['responseData'] = $userData;
				$response['responseData']['UserDetail']['image_url'] = Router::Url('/', true).'/UserImages/'.$userData['UserDetail']['profile_pic'];
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'No Such User Exists in Database';
				$response['msg'] = '';
			}			
		}
		echo json_encode($response);die;
	}
	/*Webservice for edit profie */
	public function updateProfile()
	{
		/*Extract data From Device*/
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		$userId = $obj->userId;
	
		$userEmail = $obj->userEmail;
		
		$userGid = $obj->userGid;
		$userFbID = $obj->userFbID;
	
		
		$userFirstName = $obj->userFirstName;
		$userLastName = $obj->userLastName;
		$userGender = $obj->userGender;
		$userAboutMe = $obj->userAboutMe;
		$userProfilePic = $obj->userProfilePic;
	
	
		
		$fileNmae = 'userfile_'.uniqid();
	    
		/*Some Validation Starts From here*/
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		elseif($this->User->exists($userId))
		{
			//read user details here
			$this->User->id = $userId;
			$userDetails = $this->User->read();
			/*Convert it to framework format*/
			$data['User']['email'] = 	$userEmail ;
			$data['User']['gid'] = 	$userGid ;
			$data['User']['fbid'] = 	$userFbID ;
		
			$data['UserDetail']['first_name'] = 	$userFirstName;
			$data['UserDetail']['last_name'] = 	$userLastName ;
			$data['UserDetail']['gender'] = 	$userGender;
			$data['UserDetail']['about'] = 	$userAboutMe ;
			$data['UserDetail']['user_type'] = 	2 ;
			
			
			$this->User->id = $userDetails['User']['id'];
			$data['User']['id'] = $userDetails['User']['id'];
			$data['UserDetail']['id'] = $userDetails['UserDetail']['id'];
					
			
			
			/*Will check if this user is existings... also we will need to fetch UserDFetails Id Here as updates will go there as well*/
			
			
			if(!empty($userProfilePic))
			{
				//$data['UserDetail']['profile_pic'] = 	$this->Common->uploadImage($userProfilePic, 'jpg', $userName);
				$data['UserDetail']['profile_pic'] = 	$this->Common->uploadImage($userProfilePic, 'jpg', $fileNmae);
			}
			
			$isSaved = $this->User->saveAssociated($data);
			
			if($isSaved)
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Details saved successfully.';
			}
			else
			{	
				$response['response'] = false;
				$response['errorText'] = 'Internal Server Error updating records. Please try again later.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);
		die;// no view should be rendered
	}
	/*Update funcionality ends here*/
		
	/*
	* Webservice for change password
	* Inputs : User Id, current password, new password
	*/
	public function changePassword()
	{	
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		$userId = $obj->userId;
		$userCurrentPassword = $obj->userCurrentPassword;
		$userNewPassword = $obj->userNewPassword;
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		elseif(empty($userCurrentPassword))
		{
			$response['response'] = false;
			$response['errorText'] = 'Current Password is empty.';
			$response['msg'] = '';
		}
		elseif(empty($userNewPassword))
		{
			$response['response'] = false;
			$response['errorText'] = 'New Password is empty';
			$response['msg'] = '';
		}
		else
		{
			/*Will Check if it is a valid request*/
			$inData['User']['id'] = $userId;
			$inData['User']['password'] = $userCurrentPassword;
			$isValidUser = $this->User->validateLoginId($inData );
			if(!empty($isValidUser))
			{
				$this->User->id = $userId;
				$data['User']['password'] = $userNewPassword;
				$isUpdated = $this->User->save($data);
				if($isUpdated)
				{
					$response['response'] = true;
					$response['errorText'] = '';
					$response['msg'] = 'Password changed successfully.';
				}
				else
				{
					$response['response'] = false;
					$response['errorText'] = "Password can't be changed due to intenal server error. Please try again after some time.";
					$response['msg'] = '';
				}
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'Wrong password entered';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);die;
	}
	
	/*
	* Function to Add Item
	* Inputs :: Item Details, Tags, Collections, User Id
	* If item details are entered successfully then we will enter tags. collections in it's table and then associate them with items 
	*/
	
	public function addItem()
	{	
		/*$userId = 7;
		$itemName = 'My	Second Item';
		$itemShortDesc  = 'My First Item short description is here.';
		$itemDesc = 'My First Item';
		
		$itemTagsArray = array(0 => array( 'tagId' => 1, 'tagParent' => 0), 1 => array('tagTitle' => 'Tag3', 'tagParent' => 0));
		
		$itemCollectionArray = array(0 => array('collectionId' => 1, 'collectionTitle' => 'Collection1'), 1 => array('collectionTitle' => 'collectionTitle3'));
	*/
	//	$inputArray = '{"itemName":"and test","itemDesc":"and test","itemTagsArray":[{"tagTitle":"Tag1","tagId":"1","tagParent":"0"}],"itemCollectionArray":[{"collectionId":"1","collectionTitle":"Collection1"}],"userId":"27"}';
	
	
		
	/*	$userId = 27;
		$itemName = 'ttttyyýggjjiiu';
		$itemDesc = 'ttttyyýggjjiiu';
		$itemTagsArray = array(0 => array('tagTitle' => 'tag1122'));
		$itemCollectionArray = array(0 => array('collectionTitle' => 'coll1122'));*/
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		/*convert tags to array*/
	
		
		
		$userId = $obj->userId;
		$itemName = $obj->itemName;
		$itemShortDesc = $obj->itemShortDesc;
		$itemDesc = $obj->itemDesc;
		$itemTagsArrayTemp = $obj->itemTagsArray;
		$statusId = $obj->statusId;
		$itemTagsArray = array();
		$tagCounter = 0;
		foreach($itemTagsArrayTemp as $sItem){
			
			$itemTagsArray[$tagCounter]['tagTitle'] = $sItem->tagTitle;
			$itemTagsArray[$tagCounter]['tagId'] = $sItem->tagId;
			$itemTagsArray[$tagCounter]['tagParent'] = $sItem->tagParent;
			$tagCounter++;
		}
		
		
		$itemCollectionArrayTemp = $obj->itemCollectionArray;
		$itemCollectionArray = array();
		$colCounter = 0;
		
		foreach($itemCollectionArrayTemp as $sCItem)
		{
		
			$itemCollectionArray[$colCounter]['collectionId'] = $sCItem->collectionId ; 
			$itemCollectionArray[$colCounter]['collectionTitle'] = $sCItem->collectionTitle ; 
			$colCounter++;
		}
	
		/*
			Can't use save all here as the tags	and collections also need to be entered here.
		*/
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		elseif(empty($itemDesc))
		{
			$response['response'] = false;
			$response['errorText'] = 'Item Desc is empty.';
			$response['msg'] = '';
		}
		else
		{
			
			$data['Item']['user_id'] = $userId ;
			$data['Item']['item_name'] = $itemName ;
			//$data['Item']['item_shortdescription'] = $itemShortDesc ;
			$data['Item']['item_description'] = $itemDesc ;
			$data['Item']['status_id'] = $statusId ;
			$isItemSaved = $this->Item->save($data);
			$itemId = $this->Item->id;
							
			if(!empty($isItemSaved))
			{		
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Item added to database.';
				$response['responseData']['itemId'] = $itemId;
				
				//save data for tags and collections			
				if(!empty($itemTagsArray))
				{
					foreach($itemTagsArray as $sTag)
					{
						if(!empty($sTag['tagId']))
						{
							$tagId = $sTag['tagId'];
							$this->Tag->id = $tagId;
							$tagData['Tag']['modified'] = date('y-m-d h:i:s');
							$isTagSaved = $this->Tag->save($tagData); 
						}
						else
						{
							//if there is any such tag existing 
							
							$isTag = $this->Tag->find('first', array('conditions' => array('Tag.tag_title' => $sTag['tagTitle'], 'Tag.tag_title <>' => null, 'Tag.user_id' => $userId, 'Tag.user_id <>' => null)));
							
							if(!empty($isTag))
							{
								$tagId = $isTag['Tag']['id'];
								$this->Tag->id = $tagId;
								$tagData['Tag']['modified'] = date('y-m-d h:i:s');
								$isTagSaved = $this->Tag->save($tagData); 
							}
							else
							{
								$this->Tag->create();
								$tagData['Tag']['tag_title'] = $sTag['tagTitle'];
								$tagData['Tag']['parent_tag'] = $sTag['tagParent'];
								$tagData['Tag']['user_id'] = $userId;
								$isTagSaved = $this->Tag->save($tagData); 
								$tagId = $this->Tag->id;
							}
						}
						/*save associated data */
						$itemtagData['ItemsTag']['item_id'] = $itemId ;
						$itemtagData['ItemsTag']['tag_id'] = $tagId;
						$this->ItemsTag->create();
						$this->ItemsTag->save($itemtagData);		
					
					}
				}
				/*get collections now*/
				if(!empty($itemCollectionArray))
				{
					foreach($itemCollectionArray as $sCollection)
					{
						if(!empty($sCollection['collectionId']))
						{
							$collectionId = $sCollection['collectionId'];
								$this->Collection->id = $collectionId;
								$colData['Collection']['modified'] = date('y-m-d h:i:s');
								$isColSaved = $this->Collection->save($colData); 
						}
						else
						{
						
							$isCollection = $this->Collection->find('first', array('conditions' => array('Collection.collection_title' => $sCollection['collectionTitle'], 'Collection.collection_title <>' => null, 'Collection.user_id' => $userId, 'Collection.user_id <>' => null)));
							if(!empty($isCollection))
							{
								$collectionId = $isCollection['Collection']['id'];
									$this->Collection->id = $collectionId;
								$colData['Collection']['modified'] = date('y-m-d h:i:s');
								$isColSaved = $this->Collection->save($colData); 
							}
							else
							{
								$this->Collection->create();
								$colData['Collection']['collection_title'] = $sCollection['collectionTitle'];
								$colData['Collection']['user_id'] = $userId;
								$isColSaved = $this->Collection->save($colData); 
								$collectionId = $this->Collection->id;
							}
						}
						/*save associated data */
						$itemColData['ItemsCollection']['item_id'] = $itemId ;
						$itemColData['ItemsCollection']['collection_id'] = $collectionId;
						$this->ItemsCollection->create();
						$this->ItemsCollection->save($itemColData);		
					}
				}
			}
			else
			{	
				$response['response'] = false;
				$response['errorText'] = 'Item can not be inserted due to internal error.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);
		die;
	}
	
	/*
	Get User's tags  last 40 generally
	Input :: User id, limit of records
	*/
	public function getRecentTags()
	{
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$userId = $obj->userId;
		$limit = $obj->limit;
		
		$limit = !empty($limit) ?  $limit : 30   ;
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		//remove exta data from array
		$this->Tag->unbindModel(array('hasAndBelongsToMany' => array('Item'), 'belongsTo' => array('User')));
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		else
		{
			// 'limit' => $limit, 
			$tagData = $this->Tag->find('all', array('conditions' => array('Tag.user_id' => $userId),'fields' => array('Tag.id, Tag.tag_title, Tag.tag_description, Tag.parent_tag'), 'order' => array('Tag.modified' => 'desc'))); 
			if(!empty($tagData))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Tags found in database.';
				$response['responseData'] = $tagData;
				
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'No Recent tags for this user.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);	die;
	}
	
	/*
	get User's collections  last 30 generally
	Input :: User id, limit of records
	*/
	public function getRecentCollections()
	{
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$userId = $obj->userId;
		$limit = $obj->limit;
		 
		$limit = !empty($limit) ?  $limit : 30   ;
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		//remove exta data from array
		$this->Collection->unbindModel(array('hasAndBelongsToMany' => array('Item'), 'belongsTo' => array('User')));
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		else
		{
			//'limit' => $limit,
			$collectionData = $this->Collection->find('all', array('conditions' => array('Collection.user_id' => $userId),  'fields' => array('Collection.id, Collection.collection_title, Collection.collection_description'), 'order' => array('Collection.modified' => 'desc') )); 
			if(!empty($collectionData))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Collections found in database.';
				$response['responseData'] = $collectionData;
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'No Recent collections for this user.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);	die;
	}
	
	/*Web service to set user social sharing status*/
	public function setSharingStatus()
	{
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$userId = $obj->userId;
		
		$userFbId = $obj->userFbID ;
		$userFbStatus = $obj->userFbStatus ;
		$userGId = $obj->userGid ;
		$userGStatus = $obj->userGStatus ;
		$userTId  = $obj->userTId ;
		$userTStatus = $obj->userTStatus;
		/*
		
		$userId = 1;
		$userFbId = 'FBINT001' ;
		$userFbStatus = 1;
		
		*/
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		//remove exta data from array
		$this->Collection->unbindModel(array('hasAndBelongsToMany' => array('Item'), 'belongsTo' => array('User')));
		
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		else
		{
			//get this user's shring status Id
			$sharingData =  $this->UserSharingstatus->find('first', array('conditions' => array('UserSharingstatus.user_id' => $userId)));	
			if(!empty($sharingData))
			{	
				$this->UserSharingstatus->id = $sharingData['UserSharingstatus']['id'];
			}
			$data['UserSharingstatus']['user_id'] = $userId;
			$data['UserSharingstatus']['fb_id'] = $userFbId ;
			$data['UserSharingstatus']['fb_sharing_status'] = $userFbStatus ;
			$data['UserSharingstatus']['google_id'] = $userGId ;
			$data['UserSharingstatus']['google_sharing_status'] = $userGStatus ;
			$data['UserSharingstatus']['twitter_id'] = $userTId ;
			$data['UserSharingstatus']['twitter_sharing_status'] = $userTStatus;
			
			
			/*Also we will Update the user's table as the shared user can also use login */
			if(!empty($userGId))
			{
				$uData['User']['gid'] = $userGId;
			}
			if(!empty($userFbId))
			{
				$uData['User']['fbid'] = $userFbId;
			}
			//update user details as well here
			
			$this->User->id = $userId;
			$this->User->save($uData);
			
			if($this->UserSharingstatus->save($data))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'User data added successfully.';
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'Internal Error Updating data. Please try again later';
				$response['msg'] = '';
			}
		}
		echo json_encode($response); die;
	}
	
	/*
		* Upload Multiple images for an item	
	*/
	public function uploadItemImages(){
	
		/*adding image upload for testing right here */
		?>
		<!--<link rel="stylesheet" href="http://truthrecruiting.netsmartz.us/dropdowns.1css" type="text/css" />-->
			<script src="http://truthrecruiting.netsmartz.us/javascript/jquery-1.9.1.js"></script>
			<!-- Js Files for multifile uploader -->
			<script src="http://truthrecruiting.netsmartz.us/javascript/multifile/multifile/jquery.form.js"></script>
			<script src="http://truthrecruiting.netsmartz.us/javascript/multifile/multifile/jquery.MetaData.js"></script>
			<script src="http://truthrecruiting.netsmartz.us/javascript/multifile/multifile/jquery.MultiFile.js"></script>
			<script src="http://truthrecruiting.netsmartz.us/javascript/multifile/multifile/jquery.MultiFile.pack.js"></script>
		<!--<script src="http://truthrecruiting.netsmartz.us/javascript/multifile/multifile/jquery.blockUI.js"></script>-->
		<form name="user_edit_school" id="user_edit_school" method="post" action=""  class="form" id="ffffgfgfg" enctype="multipart/form-data">
		<input type="file"  accept="jpg|png|gif" class="multi look MultiFile-applied" size="50" name="data[ItemImages][]" value="">
		<input type = 'submit' name = 'submit' value = 'upload' />
		</form>
		
		
		<?php 
		/*add test image ends here */
	
	
		//set_time_limit(3000);
        $json = file_get_contents('php://input');
        $obj = json_decode($json);

		$itemId = 1;
		$userId = 27;
		
		echo "Data starts from here";
		//debug($_FILES);
		debug($this->request->data);
			
		if(count($this->request->data["ItemImages"])> 0 && isset($this->request->data["ItemImages"]))
		{
			foreach($this->request->data["ItemImages"] as $arrImageData)
			{
				if(isset($arrImageData['name']) && $arrImageData['name'] != '' && isset($arrImageData['size']) && $arrImageData['size'] > 0 )
				{
					$response = $this->ImageUpload->upload($arrImageData, 'item_images','jpg,jpeg,gif,png,bmp', 6000000, true, 350);
					if($response['result'] == '' )
						array_push($fileUploadResults, $response);
					else if($response['result'] != '' && isset($response['result']) )
						array_push($fileUploadError, $response);
				}
			}
		}		
		//test image Upload component		
		//$response = $this->ImageUpload->upload($arrImageData, 'event_images','jpg,jpeg,gif,png,bmp', 6000000, true, 350);
		
		die;
	}
	
	/*
	* This method will be used to upload item's single image	
	* This will be based inbase 64 encoding 
	*/
	public function addItemImage()
	{
		
	/*	$itemId = 1;
		$userId = 27;
		$itemPic = '123';
		$itemImageId = 1;
		
		*/
	
	
		$json = file_get_contents('php://input');
        $obj = json_decode($json);

		$itemId = $obj->itemId;
		$userId = $obj->userId;
		$itemPic = $obj->itemPic;
		$itemImageId = $obj->itemImageId;
		
		
		$this->Itemimage->deleteAll(array( 'Itemimage.item_id' => $itemId ), false);
		
		
		$userFileName = 'item_'.$itemId.'_image'.uniqid(); 
		$data['Itemimage']['user_id'] = $userId;
		$data['Itemimage']['item_id'] =  $itemId;
		$data['Itemimage']['image_url'] = 	$this->Common->uploadItemImage($itemPic, 'jpg', $userFileName);
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id not defined.';
			$response['msg'] = '';
		}
		elseif(empty($itemId))
		{
			$response['response'] = false;
			$response['errorText'] = 'Item id is not defined.';
			$response['msg'] = '';
		}
		elseif(empty($itemPic))
		{
			$response['response'] = false;
			$response['errorText'] = 'Item Pic is not given.';
			$response['msg'] = '';
		}
		else
		{
			if(!empty($itemImageId))
			{
				$this->Itemimage->id = $itemImageId;
			}
		
			$isImageSaved = $this->Itemimage->save($data);
			if(!empty($isImageSaved))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Item Added Successfully.';
				$response['responseData']['itemId'] = $itemId;
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'Internal Error adding Image. Please retry after some time.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);
		die();
	}
	
	/*
		* This method will edit the item details
		*
		* Inputs :: Item Id and Item Details
	*/
	
	public function editItemDetails()
	{
		/*$itemId = 1;
	
		$itemName = 'My	Second Item Updated';
		$itemShortDesc  = 'My First Item short description is here but updated .';
		$itemDesc = 'My First Item description updated ';
		
		$itemTagsArray = array(0 => array( 'tagId' => 1, 'tagParent' => 0), 1 => array('tagTitle' => 'Tag3', 'tagParent' => 0));
		
		$itemCollectionArray = array(0 => array('collectionId' => 1, 'collectionTitle' => 'Collection1'));
		
		$userId = 7;
		
		*/
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		
		
		$itemId = $obj->itemId;
		$userId = $obj->userId;
		$itemName = $obj->itemName;
		$itemShortDesc = $obj->itemShortDesc;
		$itemDesc = $obj->itemDesc;
		$statusId = $obj->statusId;
		$itemTagsArrayTemp = $obj->itemTagsArray;
		$itemTagsArray = array();
		$tagCounter = 0;
		foreach($itemTagsArrayTemp as $sItem){
			
			$itemTagsArray[$tagCounter]['tagTitle'] = $sItem->tagTitle;
			$itemTagsArray[$tagCounter]['tagId'] = $sItem->tagId;
			$itemTagsArray[$tagCounter]['tagParent'] = $sItem->tagParent;
			$tagCounter++;
		}
		
		
		$itemCollectionArrayTemp = $obj->itemCollectionArray;
		$itemCollectionArray = array();
		$colCounter = 0;
		
		foreach($itemCollectionArrayTemp as $sCItem)
		{
		
			$itemCollectionArray[$colCounter]['collectionId'] = $sCItem->collectionId ; 
			$itemCollectionArray[$colCounter]['collectionTitle'] = $sCItem->collectionTitle ; 
			$colCounter++;
		}
	
		/*
			Can't use save all here as the tags	and collections also need to be entered here.
		*/
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(empty($itemDesc))
		{
			$response['response'] = false;
			$response['errorText'] = 'Item Desc is empty.';
			$response['msg'] = '';
		}
		else
		{
			$data['Item']['user_id'] = $userId ;
			$data['Item']['item_name'] = $itemName ;
			//$data['Item']['item_shortdescription'] = $itemShortDesc ;
			$data['Item']['item_description'] = $itemDesc ;
			$data['Item']['status_id'] = $statusId ;
			
			/*Update item main data here */
			$this->Item->id = $itemId ;
			
			$isItemSaved = $this->Item->save($data);
			
							
			if(!empty($isItemSaved))
			{		
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Item updated successfully.';
				
				$response['responseData']['itemId'] = $itemId;
				//save data for tags and collections		
				
				
				
				
				
				if(!empty($itemTagsArray))
				{
					$this->ItemsTag->deleteAll(array('ItemsTag.item_id' => $itemId ));
					
					foreach($itemTagsArray as $sTag)
					{
						if(!empty($sTag['tagId']))
						{
							$tagId = $sTag['tagId'];
							$this->Tag->id = $tagId;
							$tagData['Tag']['modified'] = date('y-m-d h:i:s');
							$isTagSaved = $this->Tag->save($tagData); 

						}
						else
						{
							//if there is any such tag existing 
							
							$isTag = $this->Tag->find('first', array('conditions' => array('Tag.tag_title' => $sTag['tagTitle'], 'Tag.tag_title <>' => null, 'Tag.user_id' => $userId, 'Tag.user_id <>' => null)));
							
							
							if(!empty($isTag))
							{
								$tagId = $isTag['Tag']['id'];
								$this->Tag->id = $tagId;
								$tagData['Tag']['modified'] = date('y-m-d h:i:s');
								$isTagSaved = $this->Tag->save($tagData); 

							}
							else
							{
								$this->Tag->create();
								$tagData['Tag']['tag_title'] = $sTag['tagTitle'];
								$tagData['Tag']['parent_tag'] = $sTag['tagParent'];
								$tagData['Tag']['user_id'] = $userId;
								$isTagSaved = $this->Tag->save($tagData); 
								$tagId = $this->Tag->id;
							}
						}
						/*save associated data */
						
						/*if we have any such relationship earlier we will not insert it to avoid duplicate records */
						
						
						
						$isSuchItem = $this->ItemsTag->find('first',  array('conditions' => array('ItemsTag.item_id <>' => null,'ItemsTag.tag_id <>' => null,'ItemsTag.item_id' => $itemId,'ItemsTag.tag_id' => $tagId )));
						
						if(empty($isSuchItem))
						{
							$itemtagData['ItemsTag']['item_id'] = $itemId ;
							$itemtagData['ItemsTag']['tag_id'] = $tagId;
							$this->ItemsTag->create();
							$this->ItemsTag->save($itemtagData);		
						}
					}
				}
				/*get collections now*/
				if(!empty($itemCollectionArray))
				{
					$this->ItemsCollection->deleteAll(array( 'ItemsCollection.item_id' => $itemId ), false);
					foreach($itemCollectionArray as $sCollection)
					{
						if(!empty($sCollection['collectionId']))
						{
							$collectionId = $sCollection['collectionId'];
							$this->Collection->id = $collectionId;
							$colData['Collection']['modified'] = date('y-m-d h:i:s');
							$isColSaved = $this->Collection->save($colData); 
						}
						else
						{
						
							$isCollection = $this->Collection->find('first', array('conditions' => array('Collection.collection_title' => $sCollection['collectionTitle'], 'Collection.collection_title <>' => null, 'Collection.user_id' => $userId, 'Collection.user_id <>' => null)));
							if(!empty($isCollection))
							{
								$collectionId = $isCollection['Collection']['id'];
								$this->Collection->id = $collectionId;
								$colData['Collection']['modified'] = date('y-m-d h:i:s');
								$isColSaved = $this->Collection->save($colData); 
							}
							else
							{
								$this->Collection->create();
								$colData['Collection']['collection_title'] = $sCollection['collectionTitle'];
								$colData['Collection']['user_id'] = $userId;
								$isColSaved = $this->Collection->save($colData); 
								$collectionId = $this->Collection->id;
							}
						}
						
						/*save associated data */
						
						
					
						$isSuchCollection = $this->ItemsCollection->find('first',  array('conditions' => array('ItemsCollection.item_id <>' => null,'ItemsCollection.collection_id <>' => null,'ItemsCollection.item_id' => $itemId,'ItemsCollection.collection_id' => $collectionId )));
						
						if(empty($ItemsCollection))
						{
							$itemColData['ItemsCollection']['item_id'] = $itemId ;
							$itemColData['ItemsCollection']['collection_id'] = $collectionId;
							$this->ItemsCollection->create();
							$this->ItemsCollection->save($itemColData);		
						}
					}
				}
			}
			else
			{	
				$response['response'] = false;
				$response['errorText'] = 'Item can not be inserted due to internal error.';
				$response['msg'] = '';
			}
		}
		echo json_encode($response);
		die;
		
	}
	
	/*
	* Function to get all details of Item
	* Input : Item Id
	*/
	public function getItems()
	{
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$userId = $obj->userId;
		$start =  $obj->start;
		$limit = $obj->limit;
		
		$start = !empty($start) ? $start : 0; 
		$limit = !empty($limit) ? $limit : 10; 
		
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		//remove exta data from array
		/*	
		if(empty($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id is empty.';
			$response['msg'] = '';
		}
		elseif(!$this->User->exists($userId))
		{
			$response['response'] = false;
			$response['errorText'] = 'User Id does not exists in database.';
			$response['msg'] = '';
		}
		else */
	 	{ //'Collection',  removed
			$this->Item->unbindModel(array('hasAndBelongsToMany' => array('Tag')));
			
			if(!empty($userId))
			{
				$itemData = $this->Item->find('all', array('conditions' => array('Item.user_id <> '=> null, 'Item.user_id' =>  $userId ), 'limit'=> $limit, 'offset'=> $start, 'order' => 'Item.id DESC'));
			}
			else
			{
				$itemData = $this->Item->find('all', array( 'limit'=> $limit, 'offset'=> $start, 'order' => 'Item.id DESC'));
			}
			
			/* We will also count the total number if items */	
			if(!empty($userId))
			{
				$userTotalItems = $this->Item->find('count', array('conditions' => array('Item.user_id <>' => null, 'Item.user_id' => $userId)));
			}
			else
			{
				$userTotalItems = $this->Item->find('count');
			}
		
			if(!empty($itemData))
			{
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Data found for device.';	
				$response['totalItems'] = $userTotalItems;
				//append Item's  Image full Url			
				
				$imCntr = 0;
				foreach($itemData as $sItemData)
				{
					/*Also we need user detail data here */
					$userDetailData = $this->UserDetail->find('first', array('conditions' => array('UserDetail.user_id' => $sItemData['User']['id'])));
					$itemData[$imCntr]['UserDetail'] = $userDetailData['UserDetail'];
					$itemData[$imCntr]['UserDetail']['image_url'] =  ROUTER::URL('/', true).'timthumb/timthumb.php?src=UserImages/'.$userDetailData['UserDetail']['profile_pic'].'&h=200&w=200&q=100';
					
					/*user Detail Data ends here */
				
				
					$inCntr = 0;
					foreach($sItemData['Itemimage']  as $ItemImage)
					{
						/*$itemData[$imCntr]['Itemimage'][$inCntr]['imagewPath'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src='.ROUTER::URL('/', true).'item_images/'.$ItemImage['image_url'].'&h=200&w=200&q=10' ;*/
						$itemData[$imCntr]['Itemimage'][$inCntr]['imagewPath'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src=/item_images/'.$ItemImage['image_url'].'&h=500&w=500&q=100' ;
						$inCntr++;
					}
					$imCntr++; 
				}
				$response['responseData'] = $itemData;
			}
			else
			{
				$response['response'] = false;
				$response['errorText'] = 'Item Id does not exists in database.';
				$response['msg'] = '';
			}
		}
		
		echo json_encode($response);
		die;
	}
	
	/*
		Action to get Item Details 
		Inputs : Item Id
		Output : Will contain reponse data and iotem array
	*/
	
	public function getItemDetails() 
	{
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$itemId = $obj->itemId;
	
	
		$itemData = $this->Item->find('first', array('conditions' => array('Item.id' => $itemId,'Item.id <>' => null)));
			
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(!empty($itemData)) 
		{			
			$response['response'] = true;
			$response['errorText'] = '';
			$response['msg'] = 'Data found for this Item';		
			
			$userDetailData = $this->UserDetail->find('first', array('conditions' => array('UserDetail.user_id' => $itemData['User']['id'])));
			$itemData['UserDetail'] = $userDetailData['UserDetail'];
			$itemData['UserDetail']['image_url'] = Router::Url('/', true).'/UserImages/'.$userDetailData['UserDetail']['profile_pic'];
			
			
			$inCntr = 0;
			foreach($itemData['Itemimage']  as $ItemImage)
			{
				$itemData['Itemimage'][$inCntr]['imagewPath'] = ROUTER::URL('/', true).'item_images/'.$ItemImage['image_url'] ;
				$inCntr++;
			}
			$response['responseData'] = $itemData;
		}
		else 
		{
			$response['response'] = false;
			$response['errorText'] = 'No Input Fields are entered';
			$response['msg'] = '';
		}
		echo json_encode($response);die;
	}
	
	public function searchText()
	{
		
		
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$term = $obj->term;
		$user_id = $obj->userId;
		
		$start =  $obj->start;
		$limit = $obj->limit; 
	/*	$term = 'guitar';
		$user_id = 27;
		*/

		/*First Of All we will save search terms in database */
		$start = !empty($start) ? $start : 0; 
		$limit = !empty($limit) ? $limit : 15; 
		$data['UserSearchterm']['user_id'] = $user_id;
		$data['UserSearchterm']['term'] = $term;
		
		
		/*We will now get associated tags and collection search....*/

		
		
		$searchTags = $this->Tag->find('all', array('conditions' => array('or' => array('Tag.tag_title LIKE' => "%".$term."%" ,'Tag.tag_description LIKE' => "%".$term."%" ))));
		$searctItemIds = array();
		foreach($searchTags as $sSearchTags) {
			foreach($sSearchTags['Item'] as $sItem) {
				array_push($searctItemIds, $sItem['id']);//will get associated Item Ids
			}
		}
	
		$searchCollections = $this->Collection->find('all', array('conditions' => array('or' => array('Collection.collection_title LIKE' => "%".$term."%" ,'Collection.collection_description LIKE' => "%".$term."%" ))));
	
		foreach($searchCollections as $sSearchCollections) {
			foreach($sSearchCollections['Item'] as $sCItem) {
				array_push($searctItemIds, $sCItem['id']);
			}
		}	
		
		if(!empty($user_id)) {
			$this->UserSearchterm->save($data);
		}
		/*saving code ends here 
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';*/
		if(!empty($term)) { 
		
			
			$itemCond = array('or' => array('Item.item_name LIKE' => "%".$term."%" ,'Item.item_shortdescription LIKE' => "%".$term."%" , 'Item.item_description LIKE' => "%".$term."%", 'Item.id' => $term , 'Item.id IN' => $searctItemIds ));
		
		
			if(!empty($searctItemIds))
			{	
				$itemCond = array('or' => array('Item.item_name LIKE' => "%".$term."%" ,'Item.item_shortdescription LIKE' => "%".$term."%" , 'Item.item_description LIKE' => "%".$term."%", 'Item.id' => $term , 'Item.id' => $searctItemIds ));
				
			}
			else
			{
				$itemCond = array('or' => array('Item.item_name LIKE' => "%".$term."%" ,'Item.item_shortdescription LIKE' => "%".$term."%" , 'Item.item_description LIKE' => "%".$term."%", 'Item.id' => $term ));
			}
			
			$itemData = $this->Item->find('all', array('conditions' => $itemCond, 'limit'=> $limit, 'offset'=> $start, 'order' => 'Item.id DESC'));
			$totalSearchItems = $this->Item->find('count', array('conditions' => $itemCond)) ;
			
			if(!empty($itemData))
			{
				$response['itemResults']['response'] = true;
				$response['itemResults']['errorText'] = '';
				$response['itemResults']['msg'] = 'Image Data found for device.';	
				//$response['itemResults']['totalItems'] = $userTotalItems;
				//$response['itemResults']['totalItems'] = $totalSearchItems ;
				//append Item's  Image full Url			
				
				$imCntr = 0;
				foreach($itemData as $sItemData)
				{
					/*Also we need user detail data here */
					$userDetailData = $this->UserDetail->find('first', array('conditions' => array('UserDetail.user_id' => $sItemData['User']['id'])));
					$itemData[$imCntr]['UserDetail'] = $userDetailData['UserDetail'];
					$itemData[$imCntr]['UserDetail']['image_url'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src=UserImages/'.$userDetailData['UserDetail']['profile_pic'].'&h=200&w=200&q=100';
					/*user Detail Data ends here */
						
					$inCntr = 0;
					foreach($sItemData['Itemimage']  as $ItemImage)
					{
						$itemData[$imCntr]['Itemimage'][$inCntr]['imagewPath'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src=item_images/'.$ItemImage['image_url'].'&h=500&w=500&q=100' ;
						$inCntr++;
					}
					$imCntr++;
				}
				$response['itemResults']['responseData'] = $itemData;
				$response['itemResults']['totalItems'] = $totalSearchItems;
			}
			else
			{
				$response['itemResults']['response'] = false;
				$response['itemResults']['errorText'] = 'No Item Found In Database.';
				$response['itemResults']['msg'] = '';	
				 
			}
			/*Collectgions*/
			$collectionCond = array('or' => array('Collection.collection_title LIKE' => "%".$term."%" ,'Collection.collection_description LIKE' => "%".$term."%" , 'Collection.id' => $term ));
			$this->Collection->unBindModel(array('belongsTo' => array('User'), 'hasMany' => 'Item'));
			
			$collectionData = $this->Collection->find('all', array('conditions' => $collectionCond, 'limit'=> $limit, 'offset'=> $start, 'order' => 'Collection.id DESC'));
			
			
			$totalCollections = $this->Collection->find('count', array('conditions' => $collectionCond)) ;
			
			if(!empty($collectionData)) {
				$response['collectionResults']['response'] = true;
				$response['collectionResults']['errorText'] = '';
				$response['collectionResults']['msg'] = 'Collection data available.';
				$response['collectionResults']['totalCollections'] = $totalCollections;
				
				
				//getCollectiuon IMages here 
				$colCounter = 0;
				foreach($collectionData as $sColData)
				{
					$colId = $sColData['Collection']['id'] ;
				
					$collectionImages = $this->getCollectionImage($colId);
					
					
					if(!empty($collectionImages)) {
						$collectionData[$colCounter]['collectionImages'] = $collectionImages;
					}
					$colCounter++;
				}
				$response['collectionResults']['responseData'] = $collectionData;
				
			} else {
				$response['collectionResults']['response'] = false;
				$response['collectionResults']['errorText'] = 'No Collection Found In Database.';
				$response['collectionResults']['msg'] = '';	
			}
			
			/*Users Search*/
		
			
			$userCond = array('or' => array('UserDetail.first_name LIKE' => "%".$term."%" , 'UserDetail.last_name LIKE' => "%".$term."%" ,'UserDetail.gender LIKE' => "%".$term."%" ,'UserDetail.about LIKE' => "%".$term."%" ,'User.user_name LIKE' => "%".$term."%" ,'User.email LIKE' => "%".$term."%" ,'User.id' => $term ));
			
			$userData = $this->UserDetail->find('all', array('conditions' => $userCond, 'limit'=> $limit, 'offset'=> $start, 'order' => 'User.id DESC'));
			
			$totalUsers = $this->UserDetail->find('count', array('conditions' => $userCond)) ;
		
			if(!empty($userData)) {
				$response['userResults']['response'] = true;
				$response['userResults']['errorText'] = '';
				$response['userResults']['msg'] = 'User data available.';
				$response['userResults']['totalUsers'] = $totalUsers;
				$response['userResults']['responseData'] = $userData;
			} else {
				$response['userResults']['response'] = false;
				$response['userResults']['errorText'] = 'No Such Users Found In Database.';
				$response['userResults']['msg'] = '';	
			}
		}
		else
		{
				$response['response'] = false;
				$response['errorText'] = 'Please provide search text.';
				$response['msg'] = '';	
		}

		echo json_encode($response);
		die;
	}

	
	public function getUserRcentSearch() 
	{
		
		$json = file_get_contents('php://input');
		
		$obj = json_decode($json);
		$userId = $obj->userId;
		
		$allSearchItems = $this->UserSearchterm->find('all', array('conditions' => array('UserSearchterm.user_id' => $userId), 'order' => 'UserSearchterm.id DESC'));
		
		/*saving code ends here */
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		if(!empty($userId)) {
			if(!empty($allSearchItems)) {
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Recent Search data available.';
				$response['responseData'] = $allSearchItems;
			} else {
				$response['response'] = false;
				$response['errorText'] = 'No Recent Search found for this user in database.';
				$response['msg'] = '';	
			}
		}	
		echo json_encode($response);
		die;
		
	}
	
	/*Delete a term of user that is to delete search history */
	public function deleteTerm() {
		
		$json = file_get_contents('php://input');
		
		$obj = json_decode($json);
		$userId = $obj->userId;
		$term = $obj->term;

		
		/*saving code ends here */
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(!empty($userId) && !empty($term)) {
			$isDeleted = $this->UserSearchterm->deleteAll(array('UserSearchterm.user_id' => $userId, 'UserSearchterm.term' => $term)) ; 
			if(!empty($isDeleted)) {
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Term deleted successfully.';
			} else {
				$response['response'] = false;
				$response['errorText'] = 'Error deleting search term.';
				$response['msg'] = '';	
			}
		}	
		
		echo json_encode($response);
		die;		
	}
	 
	
	/*Delete a item of user that is to delete search history */
	public function deleteItem() {
		
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$itemId = $obj->itemId;
	
		/*saving code ends here */

		/*saving code ends here */
		$response['response'] = false;
		$response['errorText'] = 'No Input Fields are entered';
		$response['msg'] = '';
		
		if(!empty($itemId)) {
			$isDeleted = $this->Item->delete($itemId, true) ;
			if(!empty($isDeleted)) {
				$response['response'] = true;
				$response['errorText'] = '';
				$response['msg'] = 'Item deleted successfully.';
			} else {
				$response['response'] = false;
				$response['errorText'] = 'Error deleting search term.';
				$response['msg'] = '';	
			}
		}	
		
		echo json_encode($response);
		die;		
	
	}
	
	public function getCollectionImage($collectionId = null){
		
		$collectionDetails = $this->Collection->find('first', array('conditions' => array('Collection.id' => $collectionId, 'Collection.id <>' => null), ''));
	
		$itemsArray = array();
		/*We will now extract Item images from here */
		foreach($collectionDetails['Item'] as $sItem) {
			$itemsArray[] = $sItem['id'];
		}
		//now find image 
		
		$itemImageData = $this->Itemimage->find('first', array('conditions' => array('Itemimage.item_id' => $itemsArray,'Itemimage.image_url <>' => null), 'order' => array('rand()')));
		if(!empty($itemImageData)) {
			$itemImageData['Itemimage']['imagewPath'] = ROUTER::URL('/', true).'item_images/'.$itemImageData['Itemimage']['image_url'] ;
			return $itemImageData['Itemimage'];
		}
		else
		{
			return false;
		}
		
	
	}
	
	
	public function getCollectionItems() {
	
	
		$json = file_get_contents('php://input');
		$obj = json_decode($json);
		$collectionId = $obj->collectionId;
		
		$response['response'] = true;
		$response['errorText'] = 'No Input data available';
		$response['msg'] = '';
		$response['responseData'] = $collectionDetails;

		if(!empty($collectionId))
		{
		
			$collectionDetails = $this->Collection->find('first', array('conditions' => array('Collection.id' => $collectionId, 'Collection.id <>' => null)));
			$itemData = $collectionDetails['Item'];
			
			$itemCntr = 0;
			foreach($itemData as $sItemData){
			
				$this->Itemimage->unbindModel(array('belongsTo' => array('User','Item')));
				
				$itemImages = $this->Itemimage->find('all', array('conditions' => array('Itemimage.item_id' => $sItemData['id'])));	
				$imgCntr = 0;
				foreach($itemImages as $sItemImage )
				{
					
					$collectionDetails['Item'][$itemCntr]['Itemimage'][$imgCntr]['imagewPath'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src=item_images/'.$sItemImage['Itemimage']['image_url'].'&h=500&w=500&q=100';
					
					$imgCntr++;
				}
				//$collectionDetails['Item'][$itemCntr]['Itemimage'] = $itemImages;
				$itemCntr++;
			}
			$response['response'] = false;
			$response['errorText'] = 'No Input Fields are entered';
			$response['msg'] = '';
			
			/*user Image*/
			$this->UserDetail->unbindModel(array('belongsTo' => array('User')));
			$userDetails = $this->UserDetail->find('first', array('conditions' => array( 'UserDetail.user_id' => $collectionDetails['User']['id'] ))) ;
			
			$collectionDetails['UserDetail'] = $userDetails['UserDetail'] ;
			$collectionDetails['UserDetail']['image_url'] = ROUTER::URL('/', true).'timthumb/timthumb.php?src=UserImages/'.$userDetails['UserDetail']['profile_pic'].'&h=200&w=200&q=100';
			if(!empty($collectionDetails))
			{
					$response['response'] = true;
					$response['errorText'] = '';
					$response['msg'] = 'Collection Data available.';
					$response['responseData'] = $collectionDetails;
			}
		}
		echo json_encode($response);
		die;
	}
	
	/*
	* Step 4 bank auth Info	
	*/
	
	public function bankAuthInfo() {
		
	}
}