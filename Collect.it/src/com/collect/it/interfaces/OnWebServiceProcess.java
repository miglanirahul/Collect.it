package com.collect.it.interfaces;

/**This interface used to reduce redundant use of code 
 * for server request and works with WebServiceAsync Class
 * to fetch and insert the data on to server
*/
public interface OnWebServiceProcess {
	void getServerValues(String response, int id, boolean isOk, String exception);
	void setServerError(int id, String msg);
}
