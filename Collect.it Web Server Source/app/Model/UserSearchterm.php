<?php
App::uses('AppModel', 'Model');
/**
 * Userdetail Model
 *
 * @property User $User
 */
class UserSearchterm extends AppModel {


	//The Associations below have been created with all possible keys, those that are not needed can be removed

/**
 * belongsTo associatio ns f
 * 
 * @var array
 */
	public $belongsTo = array(
		'User' => array(
			'className' => 'User',
			'foreignKey' => 'user_id',
			'conditions' => '',
			'fields' => '',
			'order' => ''
		)
	);
}
