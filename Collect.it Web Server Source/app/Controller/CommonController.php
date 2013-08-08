<?php 

class CommonController extends AppController 
{
	
	var $uses = array('User', 'Location', 'Petdevice', 'Pet', 'Location');
	
	public function beforeFilter()
	{
		parent::beforeFilter();//get anything defined in base class
		$this->Auth->allow(array('returnLocationData', 'returnDeviceMode', 'setDeviceMode', 'changeUserPassword', 'savePetDetails', 'updatePetDetails', 'clearDeviceQue', 'returnLocationLastData','getUserNotifications', 'getDefaultPet'));//it's a webservice and sessions can not be controlled
	}
	
	
	/*
		Author :: Munish
		Inputs :: searchField, searchString
		Output :: Will return last lat long of user location
	*/
	
	
	public function returnLocationData($searchField = null , $searchString = null, $returnUserData = false, $returnPetData = false)
	{
	
		if($searchField == null || $searchString == null)
		{
			$response = false;
		}
		else
		{
			$fields = array('Petdevice.*');
			
			if($searchField == 'pet_id')
			{	
				$conds = array('Petdevice.pet_id' => $searchString, 'Petdevice.pet_id <>' => null);
				
			}
			elseif($searchField == 'device_id')
			{
				$conds = array('Petdevice.deviceid' => $searchString, 'Petdevice.deviceid <>' => null);
				
			}
			elseif($searchField == 'gsm_number')
			{
				$conds = array('Petdevice.gsm_number' => $searchString, 'Petdevice.gsm_number <>' => null);
			}
			if(!empty($returnPetData))
			{
				//$joins = array('table' => 'users',	'alias' => 'User',		'type' => 'inner',	'foreignKey' => false, 'conditions'=> array('User.id = Location.user_id')) ;
				$joins = array('table' => 'locations',	
								'alias' => 'Location',
								'type' => 'left',
								'foreignKey' => false,
								'conditions'=> array('Location.petdevice_id = Petdevice.id'),
								'limit' => 30,
								
								) ;
					
			}
			else
			{
				$joins = null;
			}
			
			if(!empty($returnPetData))
			{
				array_push($fields, 'Pet.*');
			}
			if(!empty($returnUserData))
			{
				array_push($fields, 'User.*');
			}
			
			if(!empty($conds) && !empty($fields))
			{
				if(!empty($joins))
				{
					
					$response = $this->Petdevice->find('first', array('conditions' => $conds, 'joins' => array($joins), 'fields' => $fields,  'order' => 'Petdevice.id Desc'));
					
				}
				else
				{
					$response = $this->Petdevice->find('first', array('conditions' => $conds, 'fields' => $fields, 'Order' => 'Petdevice.id Desc'));
				}
			}
			else
			{
				$response = false;
			}
		
		}
		
		return  $response;
	}
	
	
	/*
		Author:: Munish
		Inputs::
		OutPuut :: pet's last location's lat long
	*/
	
		/*
		Author :: Munish
		Inputs :: searchField, searchString
		Output :: Will return last lat long of user location
	*/
	
	
	public function returnLocationLastData($searchField = null , $searchString = null, $returnUserData = false, $returnPetData = false)
	{
	
		if($searchField == null || $searchString == null)
		{
			$response['response'] = false;
			$response['responseText'] = 'Input parameters are not defined';
		}
		else
		{
			$fields = array('Petdevice.*');
			
			if($searchField == 'pet_id')
			{	
				$conds = array('Petdevice.pet_id' => $searchString, 'Petdevice.pet_id <>' => null);
				
			}
			elseif($searchField == 'device_id')
			{
				$conds = array('Petdevice.deviceid' => $searchString, 'Petdevice.deviceid <>' => null);
				
			}
			elseif($searchField == 'gsm_number')
			{
				$conds = array('Petdevice.gsm_number' => $searchString, 'Petdevice.gsm_number <>' => null);
			}
			
			
			if(!empty($returnPetData))
			{
				array_push($fields, 'Pet.*');
			}
			if(!empty($returnUserData))
			{
				array_push($fields, 'User.*');
			}
			
			if(!empty($conds) && !empty($fields))
			{
				$petDeviceData = $this->Petdevice->find('first', array('conditions' => $conds ));
				if(!empty($petDeviceData))
				{	
					$petLastLocation = $this->Location->find('first',array('conditions' => array('Location.petdevice_id' => $petDeviceData['Petdevice']['id']), 'order' => 'Location.id DESC'));
					
					if(!empty($petLastLocation))
					{
						$response['response'] = false;
						$response['responseText'] = '';
						$response['responseData'] = $petLastLocation;
					}
					else
					{	
						$response['response'] = false;
						$response['responseText'] = 'No Location data available for this pet.';
					}
				}
				else
				{
					$response['response'] = false;
					$response['responseText'] = 'Input parameters are not defined';
				}
			}
			else
			{
				$response['response'] = false;
				$response['responseText'] = 'Input parameters are not defined';
			}
		
		}
		debug($response);
		return  $response;
	}
	
	
	
	/*
		Author :: Munish
		Inputs :: searchField, searchString
		Output :: Will return device mode/active/passive
	*/
	public function returnDeviceMode($searchField = null , $searchString = null, $returnUserData = false, $returnPetData = false)
	{
		$this->Petdevice->unBindModel(array('hasMany' => array('Location')));//location data is not rewuired here
		if($searchField == null || $searchString == null)
		{
			$response = false;
		}
		else
		{
			$fields = array('Petdevice.deviceid','Petdevice.mode','Petdevice.gsm_number');
			
			if($searchField == 'pet_id')
			{	
				$conds = array('Petdevice.pet_id' => $searchString, 'Petdevice.pet_id <>' => null);
				
			}
			elseif($searchField == 'device_id')
			{
				$conds = array('Petdevice.deviceid' => $searchString, 'Petdevice.deviceid <>' => null);
				
			}
			elseif($searchField == 'gsm_number')
			{
				$conds = array('Petdevice.gsm_number' => $searchString, 'Petdevice.gsm_number <>' => null);
			}
			
			if(!empty($returnPetData))
			{
				array_push($fields, 'Pet.*');
			}
			if(!empty($returnUserData))
			{
				array_push($fields, 'User.*');
			}
					
			if(!empty($conds) && !empty($fields))
			{
				$response = $this->Petdevice->find('first', array('conditions' => $conds, 'fields' => $fields, 'Order' => 'Petdevice.id Desc'));
				
			}
			else
			{
				$response = false;
			}
		}
		return  $response;
	}
		
	/*
		Author :: Munish
		Inputs :: searchField, searchString, setval
		Output :: Will set device mode active/passive
	*/	
	public function setDeviceMode($searchField = null , $searchString = null, $setVal = 1 )
	{
		$allowedModes = array(1,2);
		if($searchField == null || $searchString == null)
		{
			$response['response'] = false;
			$response['responseText'] = 'Invalid Input Data. Please retry';
		}
		elseif(empty($searchField))
		{
			$response['response'] = false;
			$response['responseText'] = 'Invalid Input Data. Please retry';
		}
		elseif(empty($searchString))
		{
			$response['response'] = false;
			$response['responseText'] = 'Invalid Input Data. Please retry';
		}
		elseif(!in_array($setVal, $allowedModes))
		{
			$response['response'] = false;
			$response['responseText'] = 'Invalid Input Data. Please retry';
		}
		else
		{
			
			
			if($searchField == 'pet_id')
			{	
				$conds = array('Petdevice.pet_id' => $searchString, 'Petdevice.pet_id <>' => null);
			}
			elseif($searchField == 'device_id')
			{
				$conds = array('Petdevice.deviceid' => $searchString, 'Petdevice.deviceid <>' => null);
			}
			elseif($searchField == 'gsm_number')
			{
				$conds = array('Petdevice.gsm_number' => $searchString, 'Petdevice.gsm_number <>' => null);
			}
			else
			{
				$conds = array('Petdevice.gsm_number' => 'sadfasfagsgsdfsfgdsfgwe4356464567tegdrgd', 'Petdevice.gsm_number <>' => 'dfsdfsdfsdfsdfergherhyrhehethethtrhr');//will never match if in else
			}
			
			if(!empty($conds))
			{
			
				$upData = array('Petdevice.mode' => $setVal);
				$this->Petdevice->unBindModel(array('hasMany' => array('Location')));//location data is not rewuired here
				$checkResponse = $this->Petdevice->find('first', array('conditions' => $conds, 'fields' => array('Petdevice.id', 'Petdevice.deviceid'), 'Order' => 'Petdevice.id Desc'));
				if(!empty($checkResponse))
				{
					$response12 = $this->Petdevice->updateAll($upData, $conds);
					if(!empty($response12))
					{
						$response['response'] = true;
						$response['responseText'] = null;
						$response['responseData'] = $response12;
						if($setVal == 1)
						{
							$this->clearDeviceQue($checkResponse);
						}
					}
					else
					{	
						$response['response'] = false;
						$response['responseText'] = 'Invalid Pet.';
					}
				}
				else
				{	
					$response['response'] = false;
					$response['responseText'] = 'Invalid Pet.';
				}
				
			}
			else
			{
				$response['response'] = false;
				$response['responseText'] = 'Invalid Input Data. Please retry';
			}
		}
		return  $response;
	}
	
	/*
	Author :: Munish
	Inputs :: User Inner Id, Old Password, New Password
	*/
	public function changeUserPassword($userId = null, $oldPassword = null, $newPassword = null )
	{
		$reponse['response'] = false;
		$reponse['responseText'] = 'Input Parameters Not Defined';
		
		if(!empty($userId) && !empty($oldPassword) && !empty($newPassword))
		{
			//Is this a valid user_error
			$this->User->unBindModel(array('hasMany' => array('Petdevice','Pet')));
			$isValidUser = $this->User->find('first', array('conditions' => array('User.id' => $userId, 'User.password' => md5($oldPassword)),'fields' => 'User.id'));
		
			if(!empty($isValidUser))
			{
				$this->User->id = $isValidUser["User"]["id"];
				$data['User']['password'] = $newPassword;
				if($this->User->Save($data))
				{
					$reponse['response'] = true;
					$reponse['responseText'] = 'Password changed successfully.';
				}
				else
				{	
					$reponse['response'] = false;
					$reponse['responseText'] = 'Internal Error updating password. Please try after some time.';
				}
			}
			else
			{
				$reponse['response'] = false;
				$reponse['responseText'] = 'Wrong User Password.';
			}
		}
		else
		{	
			$reponse['response'] = false;
			$reponse['responseText'] = 'Input Parameters Not Defined';
		}
		return $reponse;
	}
	
	
	/*
		Author :: Munish
		Inputs :: User Id, Pet Name, DeviceID/GsmNumber, Update interval .. we are considering it in minutes for now)
		Output :: Will save pet details and device details
	*/	
	
	public function savePetDetails($userId = null,$petName = null, $deviceId = null,$gsmNumber = null, $updateInterval = null )
	{
		$reponse['response'] = false;
		$reponse['responseText'] = 'Input Parameters Not Defined';
		if( !empty($userId) && !empty($petName) &&( !empty($deviceId) || !empty($gsmNumber)) && !empty($updateInterval))
		{
			if(!empty($deviceId) && empty($gsmNumber))//if it is a device id
			{
				$checkCond = array('Petdevice.deviceid' => $deviceId) ;
				$dataPetdevice['Petdevice']['deviceid'] = $deviceId;
			}
			elseif(empty($deviceId) && !empty($gsmNumber))
			{	
				$checkCond = array('Petdevice.gsm_number' => $gsmNumber) ;
				$dataPetdevice['Petdevice']['gsm_number'] = $gsmNumber;
			}
			else/*if(!empty($deviceId) && !empty($gsmNumber))*/
			{
				$checkCond = array('Petdevice.deviceid' => $deviceId,'Petdevice.gsm_number' => $gsmNumber) ;
				
				$dataPetdevice['Petdevice']['deviceid'] = $deviceId;
				$dataPetdevice['Petdevice']['gsm_number'] = $gsmNumber;
			}
			$isDeviceAssigned = $this->Petdevice->find('first' , array('conditions' => $checkCond));
			if(empty($isDeviceAssigned))
			{
				/*
				data starts from here
				*/
				$data['Pet']['user_id'] = $userId;
				$data['Pet']['name'] = $petName;
				$dataPetdevice['Petdevice']['update_interval'] = $updateInterval;$dataPetdevice['Petdevice']['user_id'] = $userId;
				$dataPetdevice['Petdevice']['pet_id'] = null;
				/*
				data ends here	
				*/
				$insertPet = $this->Pet->save($data);
				if(!empty($insertPet))
				{
					$dataPetdevice['Petdevice']['pet_id'] = $this->Pet->id;
					$insertDevice = $this->Petdevice->save($dataPetdevice);
					if(!empty($insertDevice))
					{
						$reponse['response'] = true;
						$reponse['responseText'] = 'Pet details saved successfully.';
					}
					else
					{
						$reponse['response'] = false;
						$reponse['responseText'] = 'Internal error saving device details. Please try after some time.';
					}
				}
				else
				{
					$reponse['response'] = false;
					$reponse['responseText'] = 'Internal error saving Pet details. Please try after some time.';
				}
			}
			else
			{
				$reponse['response'] = false;
				$reponse['responseText'] = 'This device is already assigned to other user and can not be assigned';
			}
		}
		else
		{
			$reponse['response'] = false;
			$reponse['responseText'] = 'Input Parameters Not Defined';
		}
		return $reponse ; 
	}
	
	
	/*
		Author :: Munish
		Inputs :: Pet Id, User Id(will be only for checkups), Pet Name, DeviceID/GsmNumber, Update interval .. we are considering it in minutes for now)
		Output :: Will update pet details and device details
	*/	
	
	public function updatePetDetails($petId = null, $userId = null,$petName = null, $deviceId = null,$gsmNumber = null, $updateInterval = null )
	{
		$reponse['response'] = false;
		$reponse['responseText'] = 'Input Parameters Not Defined';
		if( !empty($petId) && !empty($userId) && !empty($petName) &&( !empty($deviceId) || !empty($gsmNumber)) && !empty($updateInterval))
		{
			if(!empty($deviceId) && empty($gsmNumber))//if it is a device id
			{
				$checkCond = array('Petdevice.deviceid' => $deviceId) ;
				$dataPetdevice['Petdevice']['deviceid'] = $deviceId;
			}
			elseif(empty($deviceId) && !empty($gsmNumber))
			{	
				$checkCond = array('Petdevice.gsm_number' => $gsmNumber) ;
				$dataPetdevice['Petdevice']['gsm_number'] = $gsmNumber;
			}
			else/*if(!empty($deviceId) && !empty($gsmNumber))*/
			{
				$checkCond = array('Petdevice.deviceid' => $deviceId,'Petdevice.gsm_number' => $gsmNumber) ;
				
				$dataPetdevice['Petdevice']['deviceid'] = $deviceId;
				$dataPetdevice['Petdevice']['gsm_number'] = $gsmNumber;
			}
			$isMyPet = $this->Pet->find('first' , array('conditions' => array('Pet.id' => $petId, 'Pet.user_id' => $userId)));
			
			
			/*additional condition to checkup*/
			array_push($checkCond, array('Petdevice.pet_id <>' => $petId));
			
			$isDeviceAssigned = $this->Petdevice->find('first' , array('conditions' => $checkCond));
			
			/*debug($this->Petdevice->getDataSource()->getLog());
			
			debug($checkCond);die;*/
			if(!empty($isMyPet))
			{
				if(empty($isDeviceAssigned))
				{
					/*
					data starts from here
					*/
					$data['Pet']['user_id'] = $userId;
					$data['Pet']['name'] = $petName;
					$dataPetdevice['Petdevice']['update_interval'] = $updateInterval;$dataPetdevice['Petdevice']['user_id'] = $userId;
					$dataPetdevice['Petdevice']['pet_id'] = null;
					/*
					data ends here	
					*/
					$this->Pet->id = $petId;
					$insertPet = $this->Pet->save($data);
					if(!empty($insertPet))
					{
					
						$this->Petdevice->id = $isMyPet['Petdevice']['id'];
						$dataPetdevice['Petdevice']['pet_id'] = $this->Pet->id;
						$insertDevice = $this->Petdevice->save($dataPetdevice);
						if(!empty($insertDevice))
						{
							$reponse['response'] = true;
							$reponse['responseText'] = 'Pet details saved successfully.';
						}
						else
						{
							$reponse['response'] = false;
							$reponse['responseText'] = 'Internal error saving device details. Please try after some time.';
						}
					}
					else
					{
						$reponse['response'] = false;
						$reponse['responseText'] = 'Internal error saving Pet details. Please try after some time.';
					}
				}
				else
				{
					$reponse['response'] = false;
					$reponse['responseText'] = 'This device is already assigned to other user and can not be assigned';
				}
			}
			else
			{
				$reponse['response'] = false;
				$reponse['responseText'] = 'You are not autorized to edit this pet details.';
			}
		}
		else
		{
			$reponse['response'] = false;
			$reponse['responseText'] = 'Input Parameters Not Defined';
		}
		return $reponse ; 
	}
	
	/*
	Author:: Munish
	Input :: Petdevice all Data as array
	Desc :: Will CLear Redisi Server Que
	*/
	
	public function clearDeviceQue($petdevice = null)
	{	
		
		if(!empty($petdevice['Petdevice']['deviceid']))
		{
			$baseUrl = ROUTER::URL('/', TRUE);
			$urltopost = $baseUrl.'credis/logs/clearredis.php';
			
			/*Clear que thorugh curl*/
			$datatopost = array ("device_id" => $petdevice['Petdevice']['deviceid'] );
			$ch = curl_init ($urltopost);
			curl_setopt ($ch, CURLOPT_POST, true);
			curl_setopt ($ch, CURLOPT_POSTFIELDS, $datatopost);
			curl_setopt ($ch, CURLOPT_RETURNTRANSFER, true);
			$returndata = curl_exec ($ch);
			
		}
		/*debug($petdevice);
		die($petdevice['Petdevice']['deviceid']);*/
		return  true;
	}
	/*
	* Author :: Munish
	* Inputs :: User id
	* Outputs :: Will Return notifications for user devices
	*/
	public function getUserNotifications($userId = null)
	{
		$response['response'] = true;
		$response['responseText'] = '';
		if(!empty($userId))
		{
			if($this->User->exists($userId))
			{
				/*get least battery level from configs*/
				$batteryLevel = Configure::read('Client.BatterNotificationLevel'); 
				
				$this->Petdevice->unbindModel(array('hasMany' => array('Location')), true);
				/*Get if I have any device that need attension or notification*/
				
				$notifiyDevices = $this->Petdevice->find('all', array('conditions' => array('Petdevice.user_id <>' => null, 'Petdevice.user_id' => $userId, 'OR' => array('Petdevice.battery_level <=' => $batteryLevel, 'Petdevice.detach_status' => 2   ) )));
				if(!empty($notifiyDevices))
				{
					$notificationText = '';
					
					foreach($notifiyDevices as $sDevice)
					{
						$lowBatteryStr = str_replace("[device_id]" , $sDevice['Petdevice']['deviceid'], str_replace("[battery_level]",$sDevice['Petdevice']['battery_level'],str_replace("[BatterNotificationLevel]",$batteryLevel,   Configure::read('Client.LowBatteryNotification'))));
							
						$deviceDetactStr = str_replace("[device_id]" , $sDevice['Petdevice']['deviceid'], Configure::read('Client.DetachNotification'));
						
						if($sDevice['Petdevice']['battery_level'] <= 20)
						{
							$notificationText .= "<span class = 'lowBattery innerNotification'>".$lowBatteryStr."</span>";
						}
						if($sDevice['Petdevice']['detach_status'] == 2)
						{
							$notificationText .= "<span class = 'detached innerNotification'>".$deviceDetactStr."</span>";
						}
						
					}
					$response['response'] = true;
					$response['responseText'] = $notificationText;
				
				}
				else
				{
					$response['response'] = false;
					$response['responseText'] = 'No Notifications Data.';
				}
			}
			else
			{
				$response['response'] = false;
				$response['responseText'] = 'Invalid userId.';
			}
		}
		else
		{
			$response['response'] = false;
			$response['responseText'] = 'No User Id Defined.';
		}
		echo json_encode($response);
		die;
	}
	/*
	Author :: Munish Kumar
	Inputs :: User Id(generally will be logged in user id)
	Output :: Will return pet data (sorted by active and lastly added)
	*/
	function getDefaultPet($userId)
	{
		$this->Petdevice->unBindModel(array('hasMany' => array('Location')));
		$getDefaultPet = $this->Petdevice->find('first', array('conditions' => array('Petdevice.user_id <>' => null, 'Petdevice.user_id' => $userId), 'order' => array('Petdevice.mode desc,Petdevice.id desc ')));
		return $getDefaultPet;
		die;
	}
}