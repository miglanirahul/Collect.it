<?php
App::uses('AppModel', 'Model');
/**
 * User Model
 *
 * @property Petdevice $Petdevice
 * @property Pet $Pet
 * @property Userdetail $Userdetail
 */
class User extends AppModel {


	//The Associations below have been created with all possible keys, those that are not needed can be removed

/**
 * hasMany associations
 *
 * @var array
 */
	var $actsAs = array('Containable');

	public $hasOne = array(
	'UserDetail' => array(
			'className' => 'UserDetail',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
	'UserSearchterm' => array(
			'className' => 'UserSearchterm',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
		'UserSharingstatus' => array(
			'className' => 'UserSharingstatus',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		));
		
	public $hasMany = array(
		'Collection' => array(
			'className' => 'Collection',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
		'Itemimage' => array(
			'className' => 'Itemimage',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
		'Item' => array(
			'className' => 'Item',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		),
		'Tag' => array(
			'className' => 'Tag',
			'foreignKey' => 'user_id',
			'dependent' => false,
			'conditions' => '',
			'fields' => '',
			'order' => '',
			'limit' => '',
			'offset' => '',
			'exclusive' => '',
			'finderQuery' => '',
			'counterQuery' => ''
		)
	);
	
	/*
		Author :: Munish
		Inputs ::  Options Generally Blank No inputs
		OutPut :: Will hash the password and save to database
		Descrition :: Password Hashing
		Will convert the simple pssword to hashed One
	*/
	public function beforeSave($options = array()) 
	{
		if (isset($this->data[$this->alias]['password'])) 
		{
			$this->data[$this->alias]['password'] = md5($this->data[$this->alias]['password']);
		}
		return true;
	}
	
	/*
		Author :: Munish 
		Inputs :: Detais of user wants to login
		Output :: will return user data if matched
		Description :: MAtch User Some Custom Login Is added Here 
	*/
	
	public function validateLogin($inData )
	{
		
		if(!empty($inData) )
		{
			$email = $inData[$this->alias]['email'];
			$uPwd =  md5($inData[$this->alias]['password']);
			$validatedUser = $this->find('first', array('conditions' => array('email' => $email, 'password' => $uPwd,  'email <> ' =>  null, 'password <> ' => null)));
			
			if(!empty($validatedUser))
			{
				return $validatedUser;
			}
			return false;
		}
		return false;
	}
	
	/*
	* validate login based on username not email	
	*/
	public function validateUserLogin($inData )
	{
		
		if(!empty($inData))
		{
			$user_name = $inData[$this->alias]['user_name'];
			$uPwd =  md5($inData[$this->alias]['password']);
			$validatedUser = $this->find('first', array('conditions' => array('user_name' => $user_name, 'password' => $uPwd,  'user_name <> ' =>  null, 'password <> ' => null)));
			
			if(!empty($validatedUser))
			{
				return $validatedUser;
			}
			return false;
		}
		return false;
	}
	
	/*
		Author :: Munish 
		Inputs :: Detais of user wants to login
		Output :: will return user data if matched
		Description :: Will cvalidate user based on user Id... primarly will be used by webservice
	*/
	
	public function validateLoginId($inData)
	{
		
		if(!empty($inData) )
		{
			$id = $inData[$this->alias]['id'];
			$uPwd =  md5($inData[$this->alias]['password']);
			$validatedUser = $this->find('first', array('conditions' => array('User.id' => $id, 'User.password' => $uPwd,  'User.id <> ' =>  null, 'User.password <> ' => null)));
			
			if(!empty($validatedUser))
			{
				return $validatedUser;
			}
			return false;
		}
		return false;
	}
	
	
	
	
	/*
		Author :: Munish 
		Inputs :: Email Address Of User
		Output :: will return true/false on base of matchin database enteries
		Description :: MAtch User Some Custom Login Is added Here 
	*/
	function check_email_exist( $email )
	{
		$userData   = $this->find( 'first',array('conditions' =>  array( 'User.email' => $email, 'User.email <>' => null) ) );
			
		if($userData)		
			return true;						
		else
			return false;
	}
	/*
		Author :: Munish 
		Inputs :: Username Of User
		Output :: will return true/false on base of matchin database enteries
		Description :: MAtch User Some Custom Login Is added Here 
	*/
	function check_username_exist( $username )
	{
		$userData   = $this->find( 'first',array('conditions' =>  array( 'User.user_name' => $username, 'User.user_name <>' => null) ) );
			
		if($userData)		
			return true;						
		else
			return false;
	}
	
	/*
		Author :: Munish 
		Inputs :: FBID Of User
		Output :: will return true/false on base of matchin database enteries
		Description :: MAtch User Some Custom Login Is added Here 
	*/
	function check_gid_exist( $gid )
	{
		$userData   = $this->find( 'first',array('conditions' =>  array( 'User.gid' => $gid, 'User.gid <>' => null) ) );
			
		if($userData)		
			return true;						
		else
			return false;
	}
	
	/*
		Author :: Munish 
		Inputs :: google PLUS Of User
		Output :: will return true/false on base of matchin database enteries
		Description :: MAtch User Some Custom Login Is added Here 
	*/
	function check_fbid_exist( $fbid )
	{
		$userData   = $this->find( 'first',array('conditions' =>  array( 'User.fbid' => $fbid, 'User.fbid <>' => null) ) );
			
		if($userData)		
			return true;						
		else
			return false;
	}
	
	
	
}
