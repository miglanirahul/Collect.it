package com.collect.it.constants;

/**
 * This class defines the enum classes/interfaces for the application
 */
public class CollectItEnums {
	/**
	 * Fragment tags enum class. This class used for the tags that are
	 * associated with related fragments
	 */
	public enum FragmentTagNames {
		/** tag for signup through google plus or facebook screen */
		SIGNUP_FACEBOOK_GOOGLEPLUS,
		/** tag for signup screen */
		SIGNUP,
		/**
		 * tag for signup screen's upper fragment that displays the connectivity
		 * animation while signup through google plus or facebook
		 */
		SIGNUP_CONNECTIVITY_ANIM,
		/** tag for after login home tab screen */
		HOME_TAB,
		/** tag for search screen on tab */
		SEARCH_TAB,
		/** tag for after login add item screen */
		ADD_ITEM_TAB,
		/** tag for notification screen on tab */
		NOTIFICATION_TAB,
		/** tag for profile screen on tab */
		PROFILE_TAB,
		/** tag for setting screen screen */
		SETTING,
		/** tag for change password screen */
		CHANGE_PASSWORD,
		/** tag for edit profile screen */
		EDIT_PROFILE,
		/** tag for detail screen */
		ITEM_DETAIL_MAIN,
		/** tag for detail screen detail button */
		ITEM_DETAIL,
		/** tag for comment list for detail screen */
		ITEM_DETAIL_COMMENT,
		/** tag for edit item detail screen */
		EDIT_ITEM_DETAIL,
		/** tag for search item list tab screen available on search tab*/
		SEARCH_ITEM_LIST,
		/** tag for search recent terms list*/
		SEARCH_RECENT_TERMS_LIST,
		/**tag for collection's item fragment*/
		COLLECTION_ITEM_LIST;
	}

	/**
	 * View pager task ids enum class. This class is used to identify the
	 * related adapter class for the further process
	 */
	public enum ViewPagerIds {
		/** collections gallery view pager */
		COLEECTION_GALLERY_ID,
		/** tags gallery view pager */
		TAG_GALLERY_ID,
		/** images gallery view pager */
		IMAGE_GALLERY_ID;
	}

}
